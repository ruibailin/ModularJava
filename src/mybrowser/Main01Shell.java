package mybrowser;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main01Shell extends Browser01Shell{

	public Main01Shell() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main01Shell app = new Main01Shell();
		
		app.initializeShell(dis, she);
		app.installShellListener();
		app.displayShell();
		app.initializeShellResource();
		app.scheduleShell();
		app.disposeShellResource();
		app.disposeShell();

	}

}
