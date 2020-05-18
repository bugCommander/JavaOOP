package Proxy.Connections.Message;

import java.util.Arrays;

public class ResponseOnRequest {
    public static final byte COMMAND_NOT_SUPPORTED = 0x07;
    public static final byte ADDRESS_TYPE_NOT_SUPPORTED = 0x08;
    public static final byte SUCCEEDED = 0x00;
    public static final byte HOST_NOT_AVAILABLE = 0x04;
    public static final byte SOCKS_5 = 0x05;
    public static final byte NO_AUTHENTICATION = 0x00;
    public static final byte NO_ACCEPTABLE_METHODS = (byte) 0xFF;

    public static byte[] create(Request request, boolean isConnected) {
        byte[] data = Arrays.copyOf(request.getBytes(), request.getBytes().length);
        data[0] = SOCKS_5;
        data[1] = SUCCEEDED;
        if (!request.isCommand(Request.CONNECT_TCP)) {
            data[1] = COMMAND_NOT_SUPPORTED;
        }
        if (!isConnected) {
            data[1] = HOST_NOT_AVAILABLE;
        }
        if (request.getAddressType() == Request.IPv6) {
            data[1] = ADDRESS_TYPE_NOT_SUPPORTED;
        }
        return data;
    }
}
