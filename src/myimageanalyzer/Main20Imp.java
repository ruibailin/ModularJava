package myimageanalyzer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main20Imp extends Image20Imp{

	public Main20Imp() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main20Imp imager = new Main20Imp();
		
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
		imager.initializeLabel12();
		imager.initializeText();
		imager.initializeLabel14();
		
		imager.displayShell();
		imager.initializeShellResource();
		
		imager.scheduleShell();
		
		imager.disposeShellResource();
		imager.disposeShell();

	}

}
