package Proxy.Connections;

import Proxy.Connections.Message.HelloRequest;
import Proxy.Connections.Message.Request;
import Proxy.Connections.Message.ResponseOnRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Connection implements ConnectionHandler {
    private SocketChannel server = null;
    private  SocketChannel client;



    private  DNS dns;
    private static final int BUFFER_SIZE = 4096;

    private State state = State.HELLO;


    private ByteBuffer b_read = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private ByteBuffer b_write = null;

    private HelloRequest hello = null;
    private Request request = null;

    public static final byte SOCKS_5 = 0x05;
    public static final byte NO_AUTHENTICATION = 0x00;
    public static final byte NO_ACCEPTABLE_METHODS = (byte) 0xFF;

    public Connection(SocketChannel aux_client, DNS aux_dns){
        dns = aux_dns;
        client = aux_client;

    }

    @Override
    public void close() throws IOException {
        if (client != null) this.client.close();
        if (server != null) this.server.close();
    }



    @Override
    public void read(SelectionKey key) {

    }

    @Override
    public void write(SelectionKey key) {

    }

    @Override
    public void register(Selector selector) throws IOException {
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ, this);

    }

    private HelloRequest readHelloMessage() throws IOException {
        int read_bytes = client.read(b_read);
        if (-1 == read_bytes) {
            this.close();
            return null;
        }
        if (HelloRequest.isCorrectSizeOfMessage(b_read)) {
            b_read.flip();
            return new HelloRequest(b_read);
        }
        return null;
    }

    private Request readRequestMessage() throws IOException {
        int read_bytes = client.read(b_read);
        if (-1 == read_bytes) {
            this.close();
            return null;
        }
        if (Request.isCorrectSizeOfMessage(b_read)) {
            b_read.flip();
            return new Request(b_read);
        }
        return null;
    }

    public byte[] getResponse() {
        byte[] data = new byte[2];
        data[0] = SOCKS_5;
        if (!hello.hasMethod()) {
            data[1] = NO_ACCEPTABLE_METHODS;
        }
        else {
            data[1] = NO_AUTHENTICATION;
        }
        return data;
    }

    private void clientPart(SelectionKey key) throws IOException {
        if (key.isReadable()) { // мы получаем сообщение от клиента
            switch (state) {
                case HELLO: {
                    System.out.println("Get hello message");
                    hello = this.readHelloMessage();
                    if (null == hello) return;
                    key.interestOps(SelectionKey.OP_WRITE);
                    b_read.clear();
                    break;
                }
                case REQUEST: {
                    System.out.println("Get request message");
                    request = this.readRequestMessage();
                    if (null == request) return;
                    if (!this.connect()) {
                        server = null;
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                    else {
                        server.register(key.selector(), SelectionKey.OP_CONNECT, this);
                        key.interestOps(0);
                    }
                    b_read.clear();
                    break;
                }
                case MESSAGE: {
                    if (this.readFrom(client, b_read)) {
                        server.register(key.selector(), SelectionKey.OP_WRITE, this);
                        key.interestOps(0);
                    }
                    break;
                }
            }
        }
        else if (key.isWritable()) { // мы отправляем сообщение клиенту
            switch (state) {
                case HELLO: {
                    if (null == b_write) {
                        b_write = ByteBuffer.wrap(getResponse());
                    }
                    if (this.writeTo(client, b_write)) {
                        b_write = null;
                        if (hello.hasMethod()) {
                            key.interestOps(SelectionKey.OP_READ);
                            state = State.REQUEST;
                        }
                        else {
                            System.err.println("Not support");
                            this.close();
                        }
                        hello = null;
                    }
                    break;
                }
                case REQUEST: {
                    if (null == b_write) {
                        b_write = ByteBuffer.wrap(ResponseOnRequest.create(request, server != null)); // cоздаем массив
                        // в который кидает код ответа на запрос
                    }
                    if (this.writeTo(client, b_write)) {
                        b_write = null;
                        if (!request.isCommand(Request.CONNECT_TCP) || server == null) {
                            this.close();
                            System.out.println("Not support");
                        }
                        else {
                            key.interestOps(SelectionKey.OP_READ);
                            server.register(key.selector(), SelectionKey.OP_READ, this);
                            state = State.MESSAGE;
                        }
                        request = null;
                    }
                    break;
                }
                case MESSAGE: {
                    if (this.writeTo(client, b_read)) {
                        key.interestOps(SelectionKey.OP_READ);
                        server.register(key.selector(), SelectionKey.OP_READ, this);
                    }
                    break;
                }
            }
        }
    }

    private void serverPart(SelectionKey key) throws IOException {
        if (key.isWritable()) {
            if (this.writeTo(server, b_read)) {
                key.interestOps(SelectionKey.OP_READ);
                client.register(key.selector(), SelectionKey.OP_READ, this);
            }
        }
        else if (key.isReadable()) {
            if (this.readFrom(server, b_read)) {
                client.register(key.selector(), SelectionKey.OP_WRITE, this);
                key.interestOps(0); // selector устанавливается в ничто
            }
        }
        else if (key.isConnectable()) {
            if (!server.isConnectionPending()) return;
            if (!server.finishConnect()) return;
            key.interestOps(0);
            client.register(key.selector(), SelectionKey.OP_WRITE, this);
        }
    }

    @Override
    public void accept(SelectionKey key) {
        try {
            if (!key.isValid()) {
                this.close();
                key.cancel();
                return;
            }

            if (client == key.channel()) {
                clientPart(key);
            }
            else if (server == key.channel()) {
                serverPart(key);
            }
        }
        catch (IOException ex) {
            try {
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean connectToServer(InetAddress address) {
        System.out.println("Connect with Server" + address);
        try {
            server.connect(new InetSocketAddress(address, request.getDestPort()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean connect() throws IOException {
        server = SocketChannel.open();
        server.configureBlocking(false);
        switch (request.getAddressType()) {
            case Request.IPv4: {
                return this.connectToServer(InetAddress.getByAddress(request.getDestAddress()));
            }
            case Request.DOMAIN_NAME: {
                dns.sendToResolve(new String(request.getDestAddress()), this);
                break;
            }
            case Request.IPv6: {
                System.err.println("It's a IPv6");
                return false;
            }
        }
        return true;
    }

    private boolean readFrom(SocketChannel channel, ByteBuffer buffer) throws IOException {
        buffer.compact();
        int read_bytes = channel.read(buffer);
        if (-1 == read_bytes) {
            this.close();
            return false;
        }
        if (0 != read_bytes) {
            buffer.flip();
        }
        return 0 != read_bytes;
    }

    private boolean writeTo(SocketChannel channel, ByteBuffer buffer) throws IOException {
        channel.write(buffer);
        return !buffer.hasRemaining();
    }
}
