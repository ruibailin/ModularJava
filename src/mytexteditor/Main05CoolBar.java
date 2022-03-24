package mytexteditor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main05CoolBar extends Editor05CoolBar{

	public Main05CoolBar() {
	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main05CoolBar editor = new Main05CoolBar();
		
		editor.initShell(dis, she);
		editor.initStatusBar();
		editor.initStyledText();
			
		editor.displayShell();
		editor.displayStatusBar();
		editor.createMenuBar();
		editor.createMemuPopup();
		
		editor.initToolBarImage();
		editor.createCoolBar();
		editor.displayCoolBar();
		
		editor.handleSystemEvent();
		
		editor.setStyle(UNDERLINE_SINGLE);

		while (!she.isDisposed()) {
			if (!dis.readAndDispatch())
				dis.sleep();
		}
		editor.freeStyleColorImage();
		editor.freeStyleFontImage();
		editor.freeEditMenuIcon();
		editor.freeToolBarImage();
		dis.dispose();
	}

}
