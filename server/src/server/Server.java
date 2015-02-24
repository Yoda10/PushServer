package server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpServer;

public class Server
{
	final static String TAG = Server.class.getSimpleName();
	final static public String BASE_PATH = "/api/scores/";

	public static void main(final String[] args)
	{
		System.out.println(TAG+": main(..)");		

		try
		{
			final HttpServer server = HttpServer.create(new InetSocketAddress(8089), 0);
			server.createContext(BASE_PATH, new ScoresHttpHandler());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();

			System.out.println(TAG+": server started.");	
		} 
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}