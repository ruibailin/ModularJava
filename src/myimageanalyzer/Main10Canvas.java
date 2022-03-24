package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main10Canvas extends Image10Canvas{

	public Main10Canvas() {

	}

	public static void main(String[] args) {

		Display dis = new Display();
		Shell she = new Shell(dis);
		Main10Canvas imager = new Main10Canvas();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.initializeGroup();
		imager.initializeCombo();
		imager.initializeLabel();
		imager.initializeCanvas();
		imager.initializeLabel2();
		imager.initializeCanvas2();
		
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
