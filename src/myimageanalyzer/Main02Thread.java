package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main02Thread extends Image02Thread{

	public Main02Thread() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main02Thread imager = new Main02Thread();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.displayShell();
		imager.initializeShellResource();
		imager.initializeThread();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
