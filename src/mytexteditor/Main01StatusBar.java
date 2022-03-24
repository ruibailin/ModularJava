package mytexteditor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main01StatusBar extends Editor01StatusBar{

	public Main01StatusBar() {

	}

	public static void main(String[] args) {

		Display dis = new Display();
		Shell she = new Shell(dis);
		Main01StatusBar editor = new Main01StatusBar();
		
		editor.initShell(dis, she);
		editor.initStatusBar();
		
		editor.displayShell();
		editor.displayStatusBar();
		
		editor.handleControlEvent();

		while (!she.isDisposed()) {
			if (!dis.readAndDispatch())
				dis.sleep();
		}
		dis.dispose();
	}

}
