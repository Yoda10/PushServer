package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ScoresHttpHandler implements HttpHandler
{
	List<ScoresApiHandler> mEndpointHandlers = new ArrayList<>();

	public ScoresHttpHandler()
	{
		mEndpointHandlers.add(new LoginHandler());
		mEndpointHandlers.add(new PostScoreHandler());
		mEndpointHandlers.add(new HighScoresListHandler());
	}

	@Override
	public void handle(final HttpExchange he) throws IOException {

		for (final ScoresApiHandler httpHandler : mEndpointHandlers) {
			if (httpHandler.handled(he)) {
				return;
			}
		}

		he.sendResponseHeaders(404, 0);
		he.close();
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
