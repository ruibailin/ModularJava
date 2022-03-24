package cveDisplay;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main03Client extends CVE03Client{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main03Client app = new Main03Client();
		
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
