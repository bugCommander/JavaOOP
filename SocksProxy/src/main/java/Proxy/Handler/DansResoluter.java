package Proxy.Handler;

import org.xbill.DNS.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class DnsResolover implements Handler {
    private final DatagramChannel channel;
    private final InetSocketAddress addressDnsServer;
    private final ByteBuffer b_read = ByteBuffer.allocateDirect(Message.MAXLENGTH);
    private final ByteBuffer b_write = ByteBuffer.allocateDirect(Message.MAXLENGTH);
    private final Deque<Message> deque = new LinkedList<>();
    private SelectionKey key;
    private final HashMap<Integer, Connection> attachments = new HashMap<>();
    public DnsResolover(int port) throws IOException {
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(port));
        String dnsServer = ResolverConfig.getCurrentConfig().server();
        addressDnsServer = new InetSocketAddress(dnsServer, 53);
        channel.connect(addressDnsServer);
        b_read.clear();
        b_write.clear();
    }
    @Override
    public void close() throws Exception {
        channel.close();

    }



    @Override
    public void register(Selector selector) throws IOException {
        channel.configureBlocking(false);
        channel.register(selector, 0);
        key = channel.keyFor(selector);

    }
    public void sendToResolve(String domainName, Connection handler) {
        try {
            Message dnsRequest = Message.newQuery(Record.newRecord(new Name(domainName + '.'), Type.A, DClass.IN));
            deque.addLast(dnsRequest);
            attachments.put(dnsRequest.getHeader().getID(), handler);
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
        }
        catch (TextParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void accept(SelectionKey key) throws Exception {
        try {
            if (!key.isValid()) {
                this.close();
                key.cancel();
                return;
            }
            if (key.isReadable()) {
                if (null != channel.receive(b_read)) {
                    b_read.flip();
                    byte[] data = new byte[b_read.limit()];
                    b_read.get(data);
                    b_read.clear();
                    Message response = new Message(data);
                    Connection session = attachments.remove(response.getHeader().getID());
                    for (Record record : response.getSectionArray(Section.ANSWER)) {
                        if (record instanceof ARecord) {
                            ARecord it = (ARecord) record;
                            if (session.connectToServer(it.getAddress())) {
                                break;
                            }
                        }
                    }
                }
                if (attachments.isEmpty()) {
                    key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);
                }
            }
            else if (key.isWritable()) {
                Message dnsRequest = deque.pollFirst();
                while (null != dnsRequest) {
                    b_write.clear();
                    b_write.put(dnsRequest.toWire());
                    b_write.flip();
                    if (0 == channel.send(b_write, addressDnsServer)) {
                        deque.addFirst(dnsRequest);
                        break;
                    }
                    key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                    dnsRequest = deque.pollFirst();
                }
                key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
