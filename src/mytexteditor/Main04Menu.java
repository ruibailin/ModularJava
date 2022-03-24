package mytexteditor;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main04Menu extends Editor04Menu{

	public Main04Menu() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main04Menu editor = new Main04Menu();
		
		editor.initShell(dis, she);
		editor.initStatusBar();
		editor.initStyledText();
			
		editor.displayShell();
		editor.displayStatusBar();
		editor.createMenuBar();
		editor.createMemuPopup();
		
		Point cSize = new Point(0,0);
		editor.displayStyledText(cSize);
		
		editor.handleSystemEvent();

		while (!she.isDisposed()) {
			if (!dis.readAndDispatch())
				dis.sleep();
		}
		editor.freeStyleColorImage();
		editor.freeStyleFontImage();
		editor.freeEditMenuIcon();
		dis.dispose();
	}

}
