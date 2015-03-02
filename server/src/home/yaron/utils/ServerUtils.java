package home.yaron.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ServerUtils
{
	public static Map<String,String> getParams(InputStream inputStream) throws IOException
	{
		final HashMap<String,String> hashMap = new HashMap<String,String>();

		String line = null;
		final StringBuffer stringBuffer = new StringBuffer();
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));		
		while( (line = bufferedReader.readLine()) != null )
		{
			stringBuffer.append(line);
		}
		
		final String[] keyValueList = stringBuffer.toString().split("&");
		for( final String keyValue : keyValueList )
		{
			final String[] pair = keyValue.split("=");
			if( pair.length == 2 )
				hashMap.put(pair[0], pair[1]); // a pair- key value
		}

		return hashMap;
	}
}
