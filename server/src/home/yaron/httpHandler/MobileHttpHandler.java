package home.yaron.httpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MobileHttpHandler implements HttpHandler
{
	List<MobileBaseHandler> endPointHandlers = new ArrayList<>();

	public MobileHttpHandler()
	{
		endPointHandlers.add(new LoginHandler());
		endPointHandlers.add(new PostScoreHandler());
		endPointHandlers.add(new HighScoresListHandler());
		endPointHandlers.add(new ServerTestHandler());
	}

	@Override
	public void handle(final HttpExchange httpExchange) throws IOException {

		for (final MobileBaseHandler httpHandler : endPointHandlers) {
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
