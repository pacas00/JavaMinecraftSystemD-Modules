package net.petercashel.jmsDd.modules.WebAPI;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class HTTPMain {

	public static com.sun.net.httpserver.HttpServer server;
	public HTTPMain(int port) throws UnknownHostException, IOException {
		
		InetSocketAddress socketAddress;
		socketAddress = new InetSocketAddress(port);
	
		
		server = HttpServer.create(socketAddress, 0);

		server.createContext("/", new DefaultHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("created server on port " + port);
		
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleWithFixedDelay(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	          System.gc();
	        }
	      }, 0, 5, TimeUnit.MINUTES);
	}
	
	public static void shutdownHTTPServer() {
		server.stop(0);
	}

}

