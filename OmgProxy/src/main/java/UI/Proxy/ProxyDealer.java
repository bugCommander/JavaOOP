package UI.Proxy;
import Proxy.Proxy;

import java.io.IOException;

public class ProxyDealer {
   Thread workerThread;
   void createThread(int port) throws IOException {
       workerThread = new Thread(new Proxy(port),"ProxyThread");
       workerThread.start();


   }
   void closeThread(){
       workerThread.interrupt();
   }
}
