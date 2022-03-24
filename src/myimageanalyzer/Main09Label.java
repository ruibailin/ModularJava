package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main09Label extends Image09Label{

	public Main09Label() {

	}

	public static void main(String[] args) {

		Display dis = new Display();
		Shell she = new Shell(dis);
		Main09Label imager = new Main09Label();
		
		imager.initializeShell(dis, she);
		imager.installShellListener();
		imager.initializeMenu();
		imager.initializeGroup();
		imager.initializeCombo();
		imager.initializeLabel();
		imager.initializeCanvas();
		imager.initializeLabel2();
		
		imager.displayShell();
		imager.initializeShellThread();
		imager.initializeShellResource();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
