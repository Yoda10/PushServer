package home.yaron.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class MobileServerState
{
	final static String TAG = MobileServerState.class.getSimpleName();
	private final static String PROPERTIES_FILE = "mobileServerState.xml";

	private Properties properties = null;

	public void loadProperties()
	{
		FileInputStream inputStream = null;
		
		try
		{
			// Load the properties detail from a defined XML file.
			inputStream = new FileInputStream(PROPERTIES_FILE);
			properties.loadFromXML(inputStream);
			System.out.println(TAG+": mobile server properties loaded.");	
		} 
		catch(Exception ex) {		
			ex.printStackTrace();

			// Close the input stream.
			try
			{
				if( inputStream != null )				
					inputStream.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveProperties(Properties properties)
	{	
		OutputStream outputStream = null;

		try
		{
			// Store the properties detail into a defined XML file.
			outputStream = new FileOutputStream(PROPERTIES_FILE);
			properties.storeToXML(outputStream, null);
			System.out.println(TAG+": mobile server properties saved.");
		} 
		catch(Exception ex) {
			ex.printStackTrace();

			// Close the output stream.
			try
			{
				if( outputStream != null )				
					outputStream.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}		
	}
}
