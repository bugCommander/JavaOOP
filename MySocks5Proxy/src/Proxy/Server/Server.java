package Proxy.Server;

import Proxy.Proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;

public class Server implements AutoCloseable{
    private ServerSocketChannel serverChannel = ServerSocketChannel.open();
    public Server(int port) throws IOException {
        serverChannel.bind(new InetSocketAddress(port));


    }
    public void register(Selector selector) throws IOException {
        serverChannel.configureBlocking(false);// Убираем блокировку
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    @Override
    public void close() throws Exception {
        serverChannel.close();

    }



    public void accept(SelectionKey key) throws IOException {
        SocketChannel newChannel = serverChannel.accept();
        newChannel.configureBlocking(false);
        newChannel.register(key.selector(),SelectionKey.OP_READ);


    }

    public void read(SelectionKey key) throws IOException{

    }


    public void write(SelectionKey key)throws IOException{

    }
    public  void connect(SelectionKey key) throws IOException{}
}
