package Proxy;

import Proxy.Connections.Handler;
import Proxy.Connections.Server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class Proxy implements AutoCloseable,Runnable {
    private Selector selector = Selector.open();
    private Server server;





    public Proxy(int port) throws IOException {
        server = new Server(port,selector);



    }


    @Override
    public void close() throws Exception {
        System.out.println("close Proxy");
        selector.close();

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int count = selector.select();
                if (count == 0) continue; //// если ни на одном из каналов,прослушиваемом селектором не произошло - скип
                Set<SelectionKey> modified = selector.selectedKeys();
                for (SelectionKey selected : modified) {
                    Handler key  =  (Handler)selected.attachment();
                    ////interface Conectionhandler- implements by Server/Server/DNS
                    key.accept(selected);
                    ///проходимся по  ключам, там где произошли какие-то события и чекаем эти события

                }
                modified.clear();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
