package Proxy.Connections;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server implements Handler {
    private ServerSocketChannel serverChannel = ServerSocketChannel.open();
    private DNS dns;
    public Server(int port,Selector selector) throws IOException {
        dns = new DNS(port, selector);
        serverChannel.bind( new InetSocketAddress(port)); /// айпишник+ порт
        serverChannel.configureBlocking(false);/// снимаем блокировку( неблокирующий ввод/вывод )
        serverChannel.register(selector, SelectionKey.OP_ACCEPT, this);///цепляем сервер к селектору(теперь селектор слушает этот канал)


    }
    public void closeDNS() throws IOException {
        dns.close();
    }
    @Override
    public void close() throws IOException {
        serverChannel.close();

    }

    @Override
    public void accept(SelectionKey key) {
        try {
            if (!key.isValid()) {
                /// A key is valid upon creation and remains so until it is cancelled,
                // its channel is closed, or its
                // selector is closed.
                close();
                return;
            }
            new Connection(serverChannel.accept(), dns,key.selector());
            ///создаем новое подключение
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }



}
