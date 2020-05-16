package Proxy;

import Proxy.Server.Server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class Proxy implements AutoCloseable,Runnable{
   private Selector selector = Selector.open();
    private Server server;


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
            while (selector.select() != -1) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid()) {
                        // Обработка всех возможнных событий ключа
                        try {
                            if (key.isAcceptable()) {
                                // Принимаем соединение
                            server.accept(key);
                            } else if (key.isConnectable()) {
                                // Устанавливаем соединение
                               server.connect(key);
                            } else if (key.isReadable()) {
                                // Читаем данные
                              server.read(key);
                            } else if (key.isWritable()) {
                                // Пишем данные
                               server.write(key);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ///тут с закрытием соединений поебаться
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
