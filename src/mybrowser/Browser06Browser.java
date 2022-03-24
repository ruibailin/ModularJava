package mybrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Label;

public class Browser06Browser extends Browser05Label{
	//Add a browser
	Browser browser;
	boolean busy;
	public Browser06Browser() {

	}
	
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	public void resBrowserWindowOpenEvent(WindowEvent event){
		debugInfo("Browser:OpenEvent! \n");
	}
	
	public void resBrowserWindowShowEvent(WindowEvent event){
		debugInfo("Browser:ShowEvent! \n");
	}
	
	public void resBrowserWindowCloseEvent(WindowEvent event){
		debugInfo("Browser:CloseEvent! \n");
	}

	public void resBrowserProgressChangedEvent(ProgressEvent event) {
		if (event.total == 0) return;                            
		int ratio = event.current * 100 / event.total;
		if (progressBar != null) progressBar.setSelection(ratio);
		busy = event.current != event.total;
		if (!busy) {
			index = 0;
			if (canvas != null) canvas.redraw();
		}
	}
	public void resBrowserProgressCompletedEvent(ProgressEvent event) {
		if (progressBar != null) progressBar.setSelection(0);
		busy = false;
		index = 0;
		if (canvas != null) {
			itemBack.setEnabled(browser.isBackEnabled());
			itemForward.setEnabled(browser.isForwardEnabled());
			canvas.redraw();
		}
	}
	
	public void resBrowserLocationChangedEvent(LocationEvent event){
		busy = true;
		if (event.top && urlBar != null) urlBar.setText(event.location);
	}
	
	public void resBrowserLocationChangingEvent(LocationEvent event){
		
	}

	public void resBrowserTitleChangedEvent(TitleEvent event){
		shell.setText(event.title+" - "+getResourceString("window.title"));

	}
	/*************************************************************/
	//Framework functions area:called by public or private functions
	private void iniBrowserListener(){
		browser.addOpenWindowListener(new OpenWindowListener() {
			public void open(WindowEvent event) {
				resBrowserWindowOpenEvent(event);
			}
		});
		browser.addVisibilityWindowListener(new VisibilityWindowListener() {
			public void hide(WindowEvent event) {
			}
			public void show(WindowEvent event) {
				resBrowserWindowShowEvent(event);
			}
		});
		
		browser.addCloseWindowListener(new CloseWindowListener() {
			public void close(WindowEvent event) {
				resBrowserWindowCloseEvent(event);
			}
		});
		
		browser.addStatusTextListener(new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				status.setText(event.text);	
			}
		});
		
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
				resBrowserProgressChangedEvent(event);
			}
			public void completed(ProgressEvent event) {
				resBrowserProgressCompletedEvent(event);
			}
		});
		
		browser.addLocationListener(new LocationListener() {
			public void changed(LocationEvent event) {
				resBrowserLocationChangedEvent(event);
			}
			public void changing(LocationEvent event) {
				resBrowserLocationChangingEvent(event);
			}
		});
	
		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				resBrowserTitleChangedEvent(event);
			}
		});
	
	}
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniBrowser(){
		try {
			browser = new Browser(shell, SWT.BORDER);
		} catch (SWTError e) {
			error = e;
			/* Browser widget could not be instantiated */
			shell.setLayout(new FillLayout());
			Label label = new Label(shell, SWT.CENTER | SWT.WRAP);
			label.setText(getResourceString("BrowserNotCreated"));
			shell.layout(true);
			return;
		}
		browser.setData("org.eclipse.swt.examples.browserexample.BrowserApplication", this);
		browser.setUrl(getResourceString("Startup"));
		
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(urlBar, 5, SWT.DEFAULT);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(status, -5, SWT.DEFAULT);
		browser.setLayoutData(data);
	}
		
	private void setToolItemState(){
		itemBack.setEnabled(browser.isBackEnabled());
		itemForward.setEnabled(browser.isForwardEnabled());
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeBrowser(){
		iniBrowser();
		iniBrowserListener();
		setToolItemState();
	}
	/*************************************************************/
}
