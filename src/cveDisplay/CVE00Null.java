package cveDisplay;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CVE00Null {
	//Define parent class modulate

	static ResourceBundle bundle;
	
	public CVE00Null() {
	}
	
	/*************************************************************/	
	//Static functions area
	static String getResourceString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";  //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
	static void debugInfo(String msg){
		System.out.print(msg);
	}
	/*************************************************************/
	//Override functions area:must be public and need to be override
	
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	
	/*************************************************************/
	//Public functions area:called by main function
	
	public void initializeNullResource() {
		bundle = ResourceBundle.getBundle("cveDisplay");
	}
	
		
	public void disposeNullResource() {

	}
	
	/*************************************************************/
}
