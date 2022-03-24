package mybrowser;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main06Browser extends Browser06Browser{

	public Main06Browser() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main06Browser app = new Main06Browser();
		
		app.initializeShell(dis, she);
		app.initializeShellResource();
		app.installShellListener();
		app.initializeToolItem();
		app.initializeCanvas();
		app.initializeText();
		app.initializeLabel();
		app.initializeBrowser();
		
		app.displayShell();
		

		app.scheduleShell();
		app.disposeShellResource();
		app.disposeShell();
	}

}
