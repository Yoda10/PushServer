package server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class ScoresApiHandler implements HttpHandler
{
//	@Override
//	public void handle(final HttpExchange arg0) throws IOException {
//	}
	
	public abstract void handle(final HttpExchange arg0) throws IOException; // Yaron :)

	public boolean handled(final HttpExchange he) throws IOException {
		return false;
	}

	protected void send200(final HttpExchange he, final String response) {
		try {
			he.sendResponseHeaders(200, response.length());

			final OutputStream os = he.getResponseBody();
			os.write(response.getBytes());
			os.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void send400(final HttpExchange he){
		try {
			he.sendResponseHeaders(404, 0);
			he.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void send500(final HttpExchange he) {
		try {
			he.sendResponseHeaders(500, 0);
			he.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}