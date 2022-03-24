package mybrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class Browser04Text extends Browser03Canvas{

	//Add URL input text
	Text urlBar;
	public Browser04Text() {

	}
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	public void resTextEvent(){
		debugInfo("Text:Event! \n");
//		browser.setUrl(urlBar.getText());
	}
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniUrlBar(){
		FormData data;
		urlBar = new Text(shell, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(toolBar, 0, SWT.TOP);
		data.left = new FormAttachment(toolBar, 5, SWT.RIGHT);
		data.right = new FormAttachment(canvas, -5, SWT.DEFAULT);			
		urlBar.setLayoutData(data);

		urlBar.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				resTextEvent();
			}
		});
	}
	/*************************************************************/
	//Public functions area:called by main function
	//Public functions area:called by main function
	public void initializeText(){
		iniUrlBar();
	}
	
}
