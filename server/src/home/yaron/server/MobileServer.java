package home.yaron.server;

import home.yaron.httpHandler.MobileHttpHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class MobileServer
{
	final static String TAG = MobileServer.class.getSimpleName();
	final static public String BASE_PATH = "/api/scores/";

	public static void main(final String[] args)
	{
		System.out.println(TAG+": main(..)");		

		try
		{
			final HttpServer server = HttpServer.create(new InetSocketAddress(8089), 0);
			server.createContext(BASE_PATH, new MobileHttpHandler());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();

			System.out.println(TAG+": mobile server started.");	
		} 
		catch (final Exception e)
		{
			System.out.println(TAG+": problem starting the mobile server.");
			e.printStackTrace();
		}
	}
}