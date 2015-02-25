package home.yaron.httpHandler;

import home.yaron.server.MobileServer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;

public class LoginHandler extends MobileBaseHandler {

	final String LOGIN_ENDPOINT = MobileServer.BASE_PATH + "([0-9]+)" + "/login";

	@Override
	public boolean checkRequestURI(final HttpExchange he) throws IOException {
		final String requestUriPath = he.getRequestURI().getPath();
		if (Pattern.matches(LOGIN_ENDPOINT, requestUriPath)) {
			handle(he);
			return true;
		}
		return false;
	}

	@Override
	public void handle(final HttpExchange he) throws IOException {

		final Pattern p = Pattern.compile(LOGIN_ENDPOINT);
		final Matcher m = p.matcher(he.getRequestURI().getPath());

		String userId = null;
		if (m.find()) {
			userId = m.group(1);
			//send200(he, SessionManager.createNewSessionId(userId));
			send200(he, "Yaron:"+userId);
		} else {
			send500(he);
		}
	}
}
