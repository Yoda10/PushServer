package home.yaron.httpHandler;

import home.yaron.server.MobileServer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;

public class HighScoresListHandler extends MobileBaseHandler {

	final String ENDPOINT = MobileServer.BASE_PATH + "([0-9]+)/highscorelist";

	@Override
	public boolean checkRequestURI(final HttpExchange he) throws IOException {
		final String requestUriPath = he.getRequestURI().toString();
		if (Pattern.matches(ENDPOINT, requestUriPath)) {
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
		if (m.find()) {
			levelId = m.group(1);
		}

		String response = "";
//		final ArrayList<Score> highScores = ScoreManager.getHighScores(levelId);
//		for (final Score score : highScores) {
//			response += score.mUserId + "=" + score.mScore + ",";
//		}

		he.sendResponseHeaders(200, response.length());
		final OutputStream os = he.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
