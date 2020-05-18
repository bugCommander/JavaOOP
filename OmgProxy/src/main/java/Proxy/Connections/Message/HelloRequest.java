package Proxy.Connections.Message;

import sun.net.SocksProxy;

import java.nio.ByteBuffer;

public class HelloRequest {


    public static final byte SOCKS_5 = 0x05;
    public static final byte NO_AUTHENTICATION = 0x00;
    public static final byte NO_ACCEPTABLE_METHODS = (byte) 0xFF;



    private final byte[] data;

    public HelloRequest(ByteBuffer buffer) {
        this.data = new byte[buffer.limit()];
        buffer.get(data);
        if (data[1] + 2 != data.length) {
            throw new IllegalArgumentException();
        }
    }

    public boolean hasMethod() {
        for (int i = 0; i < data[1]; ++i) {
            if (NO_AUTHENTICATION == data[i + 2]) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCorrectSizeOfMessage(ByteBuffer data) {
        return data.position() > 1 && data.position() >= 2 + data.get(1);
    }
}
