package mytexteditor;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main02StyledText extends Editor02StyledText{

	public Main02StyledText() {

	}

	public static void main(String[] args) {
		Display dis = new Display();
		Shell she = new Shell(dis);
		Main02StyledText editor = new Main02StyledText();
		
		editor.initShell(dis, she);
		editor.initStatusBar();
		editor.initStyledText();
			
		editor.displayShell();
		editor.displayStatusBar();
		
		Point cSize = new Point(0,0);
		editor.displayStyledText(cSize);
		
		editor.handleSystemEvent();

		while (!she.isDisposed()) {
			if (!dis.readAndDispatch())
				dis.sleep();
		}
		dis.dispose();
	}
}
