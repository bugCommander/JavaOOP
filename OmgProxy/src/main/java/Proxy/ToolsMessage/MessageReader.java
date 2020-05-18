package Proxy.ToolsMessage;

import Proxy.Connections.Connection;

import java.io.IOException;

public class MessageReader extends ToolsMessage {
    MessageReader(byte[] buff) {
        super(buff);
    }

    static public HelloRequest readHelloMessage(Connection session) throws IOException {
        int read_bytes = session.getClientChannel().read(session.getReadBuff());
        if (read_bytes == -1 ) {
            session.close();
            return null;
        }
        if (HelloRequest.isCorrectSizeOfMessage(session.getReadBuff())) {
            session.setReadBuff(session.getReadBuff().flip());
            return new HelloRequest(session.getReadBuff());
        }
        return null;
    }

    static public Request readRequestMessage(Connection session) throws IOException {
        int read_bytes = session.getClientChannel().read(session.getReadBuff());
        if (read_bytes == -1) {
            session.close();
            return null;
        }
        if (Request.isCorrectSizeOfMessage(session.getReadBuff())) {
            session.setReadBuff(session.getReadBuff().flip());
            return new Request(session.getReadBuff());
        }
        return null;
    }

    static public byte[] getResponse(HelloRequest hello) {
        byte[] data = new byte[2];
        data[0] = SOCKS_5;
        if (!hello.hasMethod()) {
            data[1] = NO_ACCEPTABLE_METHODS;
        }
        else {
            data[1] = NO_AUTHENTICATION;
        }
        return data;
    }
}
