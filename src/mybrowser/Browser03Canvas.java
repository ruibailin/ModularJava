package mybrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class Browser03Canvas extends Browser02ToolItem{
	//Add a loggo
	Canvas canvas;
	
	public Browser03Canvas() {
		// TODO Auto-generated constructor stub
	}
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	public void resCanvasMouseDownEvent(Event e){
		debugInfo("Canvas:MouseDown! \n");
//		browser.setUrl(getResourceString("Startup"));
	}
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void resCanvasPaintEvent(Event e){
		Rectangle rect = images[0].getBounds();
		Point pt = ((Canvas)e.widget).getSize();
		e.gc.drawImage(images[index], 0, 0, rect.width, rect.height, 0, 0, pt.x, pt.y);			
	}
	
	private void iniCanvas(){
		FormData data;
		canvas = new Canvas(shell, SWT.NO_BACKGROUND);
		data = new FormData();
		data.width = 24;
		data.height = 24;
		data.top = new FormAttachment(0, 5);
		data.right = new FormAttachment(100, -5);
		canvas.setLayoutData(data);
		

		canvas.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event e) {
				resCanvasPaintEvent(e);
			}
		});
		canvas.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e) {
				resCanvasMouseDownEvent(e);
			}
		});
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeCanvas(){
		iniCanvas();
	}
	
}
