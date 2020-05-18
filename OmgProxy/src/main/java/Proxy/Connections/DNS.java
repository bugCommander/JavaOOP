package Proxy.Connections;

import Proxy.Proxy;
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

public class DNS implements ConnectionHandler{
   private DatagramChannel resolverChannel = DatagramChannel.open();
   private InetSocketAddress DnsServerAddr;

    private final ByteBuffer readBuff = ByteBuffer.allocateDirect(Message.MAXLENGTH);
    private final ByteBuffer writeBuff = ByteBuffer.allocateDirect(Message.MAXLENGTH);

    private SelectionKey key;


    private final Deque<Message> deque = new LinkedList<>();

    private final HashMap<Integer, Connection> attachments = new HashMap<>();


    public DNS(int port) throws IOException {
        resolverChannel.bind(new InetSocketAddress(port));
        DnsServerAddr = ResolverConfig.getCurrentConfig().server();
        resolverChannel.connect(DnsServerAddr);
        readBuff.clear();
        writeBuff.clear();

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
    public void close() throws IOException {
        resolverChannel.close();

    }

    @Override
    public void accept(SelectionKey key) {
        try {
            if (!key.isValid()) {
                this.close();
                key.cancel();
                return;
            }
            if (key.isReadable()) {
                read(key);

            }
            else if (key.isWritable()) {
                write(key);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            try {
                this.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


   /* A DNS Message. A message is the basic unit
   of communication between the client and server of a DNS operation.
   A message consists of a Header and 4 message sections.
    */





    @Override
    public void read(SelectionKey key) throws IOException {
        if (resolverChannel.receive(readBuff) !=null) {
            readBuff.flip();
            byte[] data = new byte[readBuff.limit()];
            readBuff.get(data);
            readBuff.clear();
            Message response = new Message(data);
            Connection session = attachments.remove(response.getHeader().getID());
            for (Record record : response.getSection(Section.ANSWER)) {
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

    @Override
    public void write(SelectionKey key) throws IOException {
        Message dnsRequest = deque.pollFirst();
        while (null != dnsRequest) {
            writeBuff.clear();
            writeBuff.put(dnsRequest.toWire());
            writeBuff.flip();
            if (0 == resolverChannel.send(writeBuff, DnsServerAddr)) {
                deque.addFirst(dnsRequest);
                break;
            }
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            dnsRequest = deque.pollFirst();
        }
        key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);

    }

    @Override
    public void register(Selector selector) throws IOException {
        resolverChannel.configureBlocking(false);
        resolverChannel.register(selector, 0, this);
        key = resolverChannel.keyFor(selector);

    }
}

