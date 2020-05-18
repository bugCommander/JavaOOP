import Proxy.Proxy;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You should write only port");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
          new Proxy(port).run();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
