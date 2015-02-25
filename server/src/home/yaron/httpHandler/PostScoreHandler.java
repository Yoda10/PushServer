package home.yaron.httpHandler;

import home.yaron.server.MobileServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;

public class PostScoreHandler extends MobileBaseHandler {

	final String ENDPOINT = MobileServer.BASE_PATH
			+ "([0-9]+)/score\\?sessionkey=(\\w+-\\w+-\\w+-\\w+-\\w+)";

	@Override
	public boolean checkRequestURI(final HttpExchange he) throws IOException {
		final String method = he.getRequestMethod();
		final String requestUriPath = he.getRequestURI().toString();
		if (((method.compareTo("POST") == 0) && Pattern.matches(ENDPOINT,
				requestUriPath))) {
			handle(he);
			return true;
		}

		return false;
	}

	@Override
	public void handle(final HttpExchange he) throws IOException {

		final Pattern p = Pattern.compile(ENDPOINT);
		final Matcher m = p.matcher(he.getRequestURI().toString());

		String levelId = null;
		String sessionId = null;
		String score = null;
		if (m.find()) {
			levelId = m.group(1);
			sessionId = m.group(2);

			final BufferedReader bis = new BufferedReader(
					new InputStreamReader(he.getRequestBody()));
			score = bis.readLine();
		}


		//if (SessionManager.exists(sessionId)) {
		//	ScoreManager.saveScore(levelId, score, sessionId);
		//	send200(he, "");
		//}// else {
			final String response = "That session does not exist";
			he.sendResponseHeaders(404, response.length());
			final OutputStream os = he.getResponseBody();
			os.write(response.getBytes());
			os.close();
		//}
	}
}
