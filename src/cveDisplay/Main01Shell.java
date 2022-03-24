package cveDisplay;

import cveDisplay.Main01Shell;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main01Shell extends CVE01Shell{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
