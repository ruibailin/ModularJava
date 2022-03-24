package mybrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class Browser02ToolItem extends Browser01Shell{

	//Add ToolItem
	ToolBar	toolBar;
	ToolItem itemBack, itemForward;
	ToolItem itemGo,itemStop,itemRefresh;
	public Browser02ToolItem() {

	}
	/*************************************************************/	
	//Static functions area
	static void debugInfo(String msg){
		System.out.print(msg);
	}
	/*************************************************************/
	//Override functions area:must be public and need to be override
	public void resToolItemBack(){
		debugInfo("ToolItem:Back! \n");
	}
	
	public void resToolItemForward(){
		debugInfo("ToolItem:Forward! \n");
	}
	
	public void resToolItemStop(){
		debugInfo("ToolItem:Stop! \n");
	}
	
	public void resToolItemRefresh(){
		debugInfo("ToolItem:Refresh! \n");
	}
	
	public void resToolItemGo(){
		debugInfo("ToolItem:Go! \n");
	}
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniToolBar(){
		FormData data;
		toolBar = new ToolBar(shell, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(0, 5);
		toolBar.setLayoutData(data);
	}
	
	private void iniToolItem(){
		itemBack = new ToolItem(toolBar, SWT.PUSH);
		itemBack.setText(getResourceString("Back"));
		itemBack.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				resToolItemBack();
			}
		});
				
				
		itemForward = new ToolItem(toolBar, SWT.PUSH);
		itemForward.setText(getResourceString("Forward"));
		itemForward.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				resToolItemForward();
			}
		});
		
		itemStop = new ToolItem(toolBar, SWT.PUSH);
		itemStop.setText(getResourceString("Stop"));
		itemStop.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				resToolItemStop();
			}
		});
		
		itemRefresh = new ToolItem(toolBar, SWT.PUSH);
		itemRefresh.setText(getResourceString("Refresh"));
		itemRefresh.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				resToolItemRefresh();
			}
		});
		itemGo = new ToolItem(toolBar, SWT.PUSH);
		itemGo.setText(getResourceString("Go"));
		itemGo.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				resToolItemGo();
			}
		});
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeToolItem(){
		iniToolBar();
		iniToolItem();
	}
	/*************************************************************/
}
