package Proxy;

import Proxy.Connections.Connection;
import Proxy.Connections.ConnectionHandler;
import Proxy.Connections.Server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class Proxy implements AutoCloseable,Runnable {
    private Selector selector = Selector.open();
    private Server server;
    private final int TIMEOUT = 10000;





    public Proxy(int port) throws IOException {
        server = new Server(port);
        server.register(selector);



    }


    @Override
    public void close() throws Exception {
        selector.close();

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int count = selector.select(TIMEOUT);
                if (count == 0) continue;
                Set<SelectionKey> modified = selector.selectedKeys();
                for (SelectionKey selected : modified) {
                    ConnectionHandler key  =  (ConnectionHandler)selected.attachment();
                    key.accept(selected);
                }
                modified.clear();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
