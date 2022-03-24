package cveDisplay;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main02Frame extends CVE02Frame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main02Frame app = new Main02Frame();
		
		app.initializeShell(dis, she);
		app.installShellListener();
		app.initializeShellResource();
		app.initializeFrame();
		
		app.displayShell();
		app.scheduleShell();
		
		app.disposeShellResource();
		app.disposeShell();
	}

}
