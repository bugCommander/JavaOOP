package Proxy.Connections;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public interface ConnectionHandler extends AutoCloseable{
    @Override
    void close() throws IOException;



    void accept(SelectionKey key);
    void read(SelectionKey key) throws IOException;
    void write(SelectionKey key) throws IOException;
    void register(Selector selector) throws IOException;
}
