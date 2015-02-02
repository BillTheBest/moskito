package net.anotheria.moskito.core.helper;

/**
 * Constants used at runtime.
 */
public class RuntimeConstants {
	/**
	 * Name of the application we are running in.
	 */
	private static String applicationName = "";

	/**
	 * The application wide name of the application.
	 * @return
	 */
	public static final String getApplicationName(){
		return applicationName == null ? "" : applicationName;
	}
	
	public static final void setApplicationName(String value){
		applicationName = value;
	}
}
