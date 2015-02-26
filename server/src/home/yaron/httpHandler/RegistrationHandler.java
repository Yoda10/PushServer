package home.yaron.httpHandler;

import home.yaron.server.MobileServer;
import home.yaron.server.MobileServerState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;


public class RegistrationHandler extends MobileBaseHandler
{
	final static String ENDPOINT = MobileServer.BASE_PATH + "register";

	@Override
	public boolean checkRequestURI(final HttpExchange httpExchange) //throws IOException
	{
		final String requestUriPath = httpExchange.getRequestURI().getPath();
		final String requestMethod = httpExchange.getRequestMethod();		
		if(Pattern.matches(ENDPOINT, requestUriPath) && requestMethod.equalsIgnoreCase(MobileBaseHandler.POST_METHOD))
		{			
			handleThread(httpExchange);
			return true;
		}
		return false;
	}

	@Override
	public void handle(final HttpExchange httpExchange) throws IOException
	{
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
		final Properties properties = new Properties();
		properties.load(bufferedReader);

		// Get the post values.
		final String registrationId = properties.getProperty("RegistrationId");
		final String deviceName = properties.getProperty("DeviceName");

		if( registrationId != null && deviceName != null )
		{
			MobileServerState.getInstance().registerDevice(deviceName,registrationId);			
			send200(httpExchange, "Device:"+deviceName+" successfully registered in mobile server.");
		}
	}
}
