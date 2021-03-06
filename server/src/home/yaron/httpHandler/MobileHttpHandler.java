package home.yaron.httpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MobileHttpHandler implements HttpHandler
{
	final static String TAG = MobileHttpHandler.class.getSimpleName();
	private List<MobileBaseHandler> endPointHandlers = new ArrayList<>();	

	public MobileHttpHandler()
	{
		System.out.println(TAG+": MobileHttpHandler(..)");
		
		// Initialization all end points.
		//endPointHandlers.add(new LoginHandler());
		//endPointHandlers.add(new PostScoreHandler());
		//endPointHandlers.add(new HighScoresListHandler());
		endPointHandlers.add(new ServerTestHandler());
		endPointHandlers.add(new RegistrationHandler());
		endPointHandlers.add(new SendMessageHandler());
	}	

	@Override
	public void handle(final HttpExchange httpExchange) throws IOException
	{
		System.out.println(TAG+": handle(..)");
		
		for(final MobileBaseHandler httpHandler : endPointHandlers)
		{
			if (httpHandler.checkRequestURI(httpExchange)) {
				return;
			}
		}

		httpExchange.sendResponseHeaders(404, 0);
		httpExchange.close();

		/*
		// left of debug
		final URI requestUri = he.getRequestURI();
		final String response = "This is the response for "
				+ requestUri.getPath();
		he.sendResponseHeaders(200, response.length());
		final OutputStream os = he.getResponseBody();
		os.write(response.getBytes());
		os.close();*/
	}
}
