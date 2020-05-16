package Proxy.Server;

import Proxy.Proxy;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

public class DnsResolver implements AutoCloseable{
    DatagramChannel ResolverChannel = DatagramChannel.open();

    public DnsResolver() throws IOException {
        ResolverChannel.configureBlocking(false);


    }

    @Override
    public void close() throws Exception {
        ResolverChannel.close();

    }
}
