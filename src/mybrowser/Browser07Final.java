package mybrowser;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;

public class Browser07Final extends Browser06Browser{

	public Browser07Final() {
		// TODO Auto-generated constructor stub
	}
	/*************************************************************/
	//01Shell
	
	/*************************************************************/
	//02ToolItem
	@Override
	public void resToolItemBack(){
		browser.back();
	}
	@Override
	public void resToolItemForward(){
		browser.forward();
	}
	@Override
	public void resToolItemStop(){
		browser.stop();
	}
	@Override
	public void resToolItemRefresh(){
		browser.refresh();
	}
	@Override
	public void resToolItemGo(){
		browser.setUrl(urlBar.getText());
	}
	/*************************************************************/
	//03Canvas
	@Override
	public void resCanvasMouseDownEvent(Event e){
		browser.setUrl(getResourceString("Startup"));
	}
	
	/*************************************************************/
	//04Text
	@Override
	public void resTextEvent(){
		browser.setUrl(urlBar.getText());
	}
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	@Override
	public void resBrowserWindowOpenEvent(WindowEvent event){
		if (icon != null) shell.setImage(icon);
		shell.setLayout(new FillLayout());
		Browser07Final app= new Browser07Final();
		app.setShellDecoration(icon, true);
		event.browser=app.browser;
	}
	@Override
	public void resBrowserWindowShowEvent(WindowEvent event){
		Browser br = (Browser)event.widget;
		browser = br;
	}
	@Override
	public void resBrowserWindowCloseEvent(WindowEvent event){
		shell.close();
	}
	/*************************************************************/
	private void setShellDecoration(Image icon, boolean title) {
		this.icon = icon;
		this.title = title;
	}
}
