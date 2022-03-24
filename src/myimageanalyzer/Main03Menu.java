package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main03Menu extends Image03Menu{

	public Main03Menu() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main03Menu imager = new Main03Menu();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		imager.scheduleShell();
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
