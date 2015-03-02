package home.yaron.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Singleton object to hold the mobile server state.
 *  
 * @author Yaron Ronen
 * @date 26/02/2015 
 */
public class MobileServerState
{
	final static String TAG = MobileServerState.class.getSimpleName();
	private final static String PROPERTIES_FILE = "mobileServerState.xml";

	private static MobileServerState instance = null; // Singleton object

	public static MobileServerState getInstance()
	{
		if( instance == null )
			instance = new MobileServerState();
		return instance;
	}

	// ----- Singleton object -----

	private Properties properties = null;

	private MobileServerState()
	{		
		init();
	}

	private synchronized void init()
	{
		final Properties properties = loadProperties();
		if( properties != null )
			this.properties = properties;
		else
			this.properties = new Properties();
	}

	private Properties getProperties()
	{
		return properties;
	}	

	private Properties loadProperties()
	{
		Properties properties = null;
		FileInputStream inputStream = null;

		try
		{
			// Load the properties detail from a defined XML file.
			inputStream = new FileInputStream(PROPERTIES_FILE);
			properties = new Properties();
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

		return properties;
	}

	private synchronized void saveProperties(Properties properties)
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

	public void registerDevice(String deviceName, String registrationId)
	{
		// Parameters checking.
		if( deviceName == null || registrationId == null )		
			throw new IllegalArgumentException("Device registration parameters are illegal.");

		getProperties().setProperty(deviceName, registrationId);		
		saveProperties(getProperties());		
	}

	public void unregisterDevice(String deviceName)
	{
		// Parameters checking.
		if( deviceName == null )		
			throw new IllegalArgumentException("Device unregistration name parameter is illegal.");

		final Object value = getProperties().remove(deviceName);
		if( value != null )
			saveProperties(getProperties());	
	}

	public String getProperty(String key)
	{
		return getProperties().getProperty(key);		
	}
}
