package mybrowser;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main05Label extends Browser05Label{

	public Main05Label() {

	}

	public static void main(String[] args) {

		Display dis = new Display();
		Shell she = new Shell(dis);
		Main05Label app = new Main05Label();
		
		app.initializeShell(dis, she);
		app.initializeShellResource();
		app.installShellListener();
		app.initializeToolItem();
		app.initializeCanvas();
		app.initializeText();
		app.initializeLabel();
		
		app.displayShell();
		

		app.scheduleShell();
		app.disposeShellResource();
		app.disposeShell();
	}

}
