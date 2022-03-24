package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main01Shell extends Image01Shell{

	public Main01Shell() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main01Shell imager = new Main01Shell();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.displayShell();
		imager.initializeShellResource();
		imager.scheduleShell();
		imager.disposeShellResource();
		imager.disposeShell();
	}

}
