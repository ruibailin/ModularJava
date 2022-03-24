package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main05Combo extends Image05Combo{

	public Main05Combo() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main05Combo imager = new Main05Combo();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.initializeGroup();
		imager.initializeCombo();
		
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
