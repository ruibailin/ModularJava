package mytexteditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;

public class Editor01StatusBar extends Editor00Shell {

	boolean insert;
	int offset,index;
	Label statusBar;

	int statusMargin;
	
	public Editor01StatusBar() {
		// TODO Auto-generated constructor stub
		insert = true;
		offset=0;
		index=0;
		statusMargin = 2;
	}
	
	/*************************************************************/
	public void initStatusBar() {
		statusBar = new Label(shell, SWT.NONE);
	}
	public void resizeStatusBar() {
		Rectangle rect = shell.getClientArea();
		Point sSize = statusBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		statusBar.setBounds(rect.x + statusMargin, rect.y + rect.height - sSize.y - statusMargin, rect.width - (2 * statusMargin), sSize.y);
	}
	
	public void updateStatusBar() {
		String disptext = getResourceString("Offset");
		disptext = disptext+ (offset + " ");
		disptext = disptext+ getResourceString("Line");
		disptext = disptext+ index + "\t";
		disptext = disptext+ getResourceString(insert ? "Insert" : "Overwrite");
		statusBar.setText(disptext);
	}
	/*************************************************************/
	public void displayStatusBar() {
		updateStatusBar();
		resizeStatusBar();	
	}
	
	/*************************************************************/
	public void responseControlEvent(ControlEvent event) {
		updateStatusBar();
		resizeStatusBar();
	}
	/*************************************************************/
	public void handleControlEvent() {	
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent event) {
				responseControlEvent(event);
			}
		});
	}
	/*************************************************************/
	public void handleSystemEvent(){
		handleControlEvent();
	}
}
