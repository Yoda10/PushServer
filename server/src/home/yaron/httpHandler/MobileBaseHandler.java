package home.yaron.httpHandler;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class MobileBaseHandler implements HttpHandler
{
	//	@Override
	//	public void handle(final HttpExchange arg0) throws IOException {
	//	}

	public abstract void handle(final HttpExchange arg0) throws IOException; // Yaron :)

	public boolean checkRequestURI(final HttpExchange he) throws IOException {
		return false;
	}

	protected void send200(final HttpExchange httpExchange, final String response) {
		try {
			httpExchange.sendResponseHeaders(200, response.length());

			final OutputStream os = httpExchange.getResponseBody();
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