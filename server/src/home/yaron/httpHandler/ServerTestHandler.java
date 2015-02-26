package home.yaron.httpHandler;

import home.yaron.server.MobileServer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;

public class ServerTestHandler extends MobileBaseHandler {

	final static String ENDPOINT = MobileServer.BASE_PATH + "test";

	@Override
	public boolean checkRequestURI(final HttpExchange httpExchange) throws IOException
	{
		final String requestUriPath = httpExchange.getRequestURI().getPath();
		if (Pattern.matches(ENDPOINT, requestUriPath)) {
			//handle(httpExchange);
			handleThread(httpExchange);
			return true;
		}
		return false;
	}

	@Override
	public void handle(final HttpExchange httpExchange) throws IOException
	{		
		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	
		send200(httpExchange, "Server date and time:"+dateFormat.format(new Date()));
	}
}
