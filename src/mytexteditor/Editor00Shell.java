package mytexteditor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Editor00Shell {
	Display display;
	Shell shell;

	static final ResourceBundle resources = ResourceBundle.getBundle("examples_texteditor");  //$NON-NLS-1$

	static String getResourceString(String key) {
		try {
			return resources.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";  //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/*************************************************************/
	public Editor00Shell() {
	
	}
	
	public void initShell(Display dis,Shell she) {
		display=dis;
		shell=she;
	}
	
	public void displayShell() {
		shell.setText(getResourceString("Window_title")); //$NON-NLS-1$
		shell.setSize(1000, 700);
		shell.open();
	}
}
