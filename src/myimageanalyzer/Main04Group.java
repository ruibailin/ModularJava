package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main04Group extends Image04Group{

	public Main04Group() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main04Group imager = new Main04Group();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.initializeGroup();
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		imager.scheduleShell();
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
