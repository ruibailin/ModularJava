package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main08Canvas extends Image08Canvas{

	public Main08Canvas() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main08Canvas imager = new Main08Canvas();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.initializeGroup();
		imager.initializeCombo();
		imager.initializeLabel();
		imager.initializeCanvas();
		
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
