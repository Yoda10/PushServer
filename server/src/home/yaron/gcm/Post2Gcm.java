package home.yaron.gcm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class Post2Gcm
{
	private final static String API_SERVER_KEY = "AIzaSyAd7A0j7NOqoAyQRu3R-xBaVIDODoe-_aM";

	public static int post(Content content)
	{
		int responseCode = -1;
		
		try
		{
			// 1. URL			
			final URL url = new URL("https://android.googleapis.com/gcm/send");

			// 2. Open connection
			final HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			// 3. Specify POST method
			conn.setRequestMethod("POST");

			// 4. Set the headers
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key="+API_SERVER_KEY);

			conn.setDoOutput(true);

			// 5. Add JSON data into POST request body
			// 5.1 Use GSON to convert Content object into JSON string.
			final Gson gson = new Gson();
			final String jsonString = gson.toJson(content);			

			// 5.2 Get connection output stream
			final DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());			

			// 5.3 Copy Content "JSON" into
			dataOutputStream.writeBytes(jsonString);			

			// 5.4 Send the request
			dataOutputStream.flush();

			// 5.5 close
			dataOutputStream.close();

			// 6. Get the response
			responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL: " + url);
			System.out.println("\nResponse Code: " + responseCode);

			// Response 200 is OK.
			if( responseCode != 200 )
				return responseCode;

			final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));			
			final StringBuffer response = new StringBuffer();

			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();

			// 7. Print result
			System.out.println(response.toString());			
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseCode;
	}
}