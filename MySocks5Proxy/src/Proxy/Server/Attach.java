package Proxy.Server;

import Proxy.Proxy;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class Attach {
    public static final int BUF_SIZE = 1024;
    ///в момент проксирования in == out
    public ByteBuffer inBuff;
    public ByteBuffer outBuff;
    ///Куда проксируем
    SelectionKey peer;

}
