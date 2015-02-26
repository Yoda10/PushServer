package home.yaron.httpHandler;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public abstract class MobileBaseHandler implements HttpHandler
{
	protected final static String POST_METHOD = "POST";

	//	@Override
	//	public void handle(final HttpExchange arg0) throws IOException {
	//	}

	public abstract void handle(final HttpExchange httpExchange) throws IOException;

	public boolean checkRequestURI(final HttpExchange httpExchange) throws IOException 
	{
		return false;
	}

	/**
	 * 200 - OK - Request has succeeded.	 
	 */
	protected void send200(final HttpExchange httpExchange, final String response)
	{
		try {
			httpExchange.sendResponseHeaders(200, response.length());

			final OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 400 - Bad request	 
	 */
	protected void send400(final HttpExchange he){
		try {
			he.sendResponseHeaders(404, 0);
			he.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 500 - Internal server error	
	 */
	protected void send500(final HttpExchange he) {
		try {
			he.sendResponseHeaders(500, 0);
			he.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void handleThread(final HttpExchange httpExchange)
	{
		final Thread thread = new Thread() {
			public void run() {
				try
				{
					handle(httpExchange); // Call the specific handler.
				}
				catch(Exception ex) {
					ex.printStackTrace();					
					try {
						httpExchange.sendResponseHeaders(406, 0); //TODO: 406 is the correct number ? general problem
						httpExchange.close();
					} 
					catch (IOException e) {						
						e.printStackTrace();
					}					
				}
			}  
		};

		thread.start();
	}
}