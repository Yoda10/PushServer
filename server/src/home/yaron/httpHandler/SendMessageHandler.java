package home.yaron.httpHandler;

import home.yaron.gcm.Content;
import home.yaron.gcm.Post2Gcm;
import home.yaron.server.MobileServer;
import home.yaron.server.MobileServerState;
import home.yaron.utils.ServerUtils;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;

public class SendMessageHandler extends MobileBaseHandler {

	final static String TAG = SendMessageHandler.class.getSimpleName();
	final static String ENDPOINT = MobileServer.BASE_PATH + "sendMessage";

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
		System.out.println(TAG+": handle(..)");
		
		// Get the post values from the mobile client.	
		final Map<String, String> postParams = ServerUtils.getParams(httpExchange.getRequestBody());			
		final String message = postParams.get("Message");
		final String deviceName = postParams.get("DeviceName");
		
		final String registrationId = MobileServerState.getInstance().getProperty(deviceName);		
		final Content content = new Content();
		content.addRegId(registrationId);
		content.createData("title1", "message1");
		content.createData("title2", "message2");
		
		// Post a request to the GCM service.
		Post2Gcm.post(content);
		
		System.out.println(TAG+":deviceName:"+deviceName+" message:"+message+" registrationId:"+registrationId);
		
		send200(httpExchange, "Good");
	}
}
