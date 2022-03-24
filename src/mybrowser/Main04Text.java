package mybrowser;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main04Text extends Browser04Text{

	public Main04Text() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);

		Main04Text app= new Main04Text();
		
		app.initializeShell(dis, she);
		app.initializeShellResource();
		app.installShellListener();
		app.initializeToolItem();
		app.initializeCanvas();
		app.initializeText();

		
		app.displayShell();
		
		app.scheduleShell();
		app.disposeShellResource();
		app.disposeShell();
	}

}
