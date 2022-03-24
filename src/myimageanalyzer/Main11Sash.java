package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main11Sash extends Image11Sash{

	public Main11Sash() {

	}

	public static void main(String[] args) {

		Display dis = new Display();
		Shell she = new Shell(dis);
		Main11Sash imager = new Main11Sash();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.initializeGroup();
		imager.initializeCombo();
		imager.initializeLabel();
		imager.initializeCanvas();
		imager.initializeLabel2();
		imager.initializeCanvas2();
		imager.initializeSash();
		
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();
	}

}
