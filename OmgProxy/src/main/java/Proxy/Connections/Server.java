package Proxy.Connections;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server implements ConnectionHandler{
    private ServerSocketChannel serverChannel = ServerSocketChannel.open();
    private DNS dns;
    public Server(int port) throws IOException {
        dns = new DNS(port);
        serverChannel.bind( new InetSocketAddress(port));


    }
    public void register(Selector selector) throws IOException {
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT, this);
        dns.register(selector);

    }

    @Override
    public void close() throws IOException {
        serverChannel.close();

    }

    @Override
    public void accept(SelectionKey key) {
        try {
            if (!key.isValid()) {
                this.close();
                return;
            }
            new Connection(serverChannel.accept(), dns).register(key.selector());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void read(SelectionKey key) {

    }

    @Override
    public void write(SelectionKey key) {

    }
}
