package mybrowser;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Browser01Shell {
	static ResourceBundle resourceBundle = ResourceBundle.getBundle("examples_browser");

	static final String[] imageLocations = {
		"eclipse01.bmp", "eclipse02.bmp", "eclipse03.bmp", "eclipse04.bmp", "eclipse05.bmp",
		"eclipse06.bmp", "eclipse07.bmp", "eclipse08.bmp", "eclipse09.bmp", "eclipse10.bmp",
		"eclipse11.bmp", "eclipse12.bmp",};
	static final String iconLocation = "document.gif";
	
	Display display;
	Shell shell;
	Image icon = null;
	boolean title = false;
	
	int index=0;
	Image images[];
	
	SWTError error = null;
	public Browser01Shell() {

	}

	/*************************************************************/	
	//Static functions area
	/**
	 * Gets a string from the resource bundle.
	 * We don't want to crash because of a missing String.
	 * Returns the key if not found.
	 */
	static String getResourceString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}			
	}

	/*************************************************************/
	//Override functions area:must be public and need to be override
	
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	/**
	 * Frees the resources
	 */
	private void  iniResources() {
		final Class<? extends Browser01Shell> clazz = this.getClass();
		if (resourceBundle != null) {
			try {
				if (images == null) {
					images = new Image[imageLocations.length];
					for (int i = 0; i < imageLocations.length; ++i) {
						InputStream sourceStream = clazz.getResourceAsStream(imageLocations[i]);
						ImageData source = new ImageData(sourceStream);
						ImageData mask = source.getTransparencyMask();
						images[i] = new Image(null, source, mask);
						try {
							sourceStream.close();
						} catch (IOException e) {
							e.printStackTrace ();
						}
					}
				}
				return;
			} catch (Throwable t) {
			}
		}
		String error = (resourceBundle != null) ? getResourceString("error.CouldNotLoadResources") : "Unable to load resources";
		relResources();
		throw new RuntimeException(error);
	}
	private void  relResources() {
		if (images != null) {
			for (int i = 0; i < images.length; ++i) {
				final Image image = images[i];
				if (image != null) image.dispose();
			}
			images = null;
		}
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeShell(Display dis,Shell she) {
		display=dis;
		shell=she;
		shell.setLayout(new FillLayout());
		shell.setText(getResourceString("window.title"));
		InputStream stream = Browser01Shell.class.getResourceAsStream(iconLocation);
		Image icon = new Image(display, stream);
		shell.setImage(icon);
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void installShellListener() {

	}
	
	
	public void displayShell() {
		shell.setLayout(new FormLayout());		//relocate all controls
		shell.setText(getResourceString("Window_title")); //$NON-NLS-1$
		shell.setSize(1000, 700);
		shell.open();
	}
	
	public void initializeShellResource() {
		iniResources();
	}
	
	public void scheduleShell() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
		
	public void disposeShellResource() {
		relResources();
	}
	
	public void disposeShell(){
		display.dispose();
	}
	/*************************************************************/
}
