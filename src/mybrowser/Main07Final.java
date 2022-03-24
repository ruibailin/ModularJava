package mybrowser;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main07Final extends Browser07Final{

	public Main07Final() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main07Final app = new Main07Final();
		
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
