package Proxy;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length !=1){
            System.out.println("you should write port");
            return;

        }
        int port = Integer.parseInt(args[0]);
        Proxy proxy = new Proxy(port);
        proxy.run();
    }
}
