package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Sash;

public class Image11Sash extends Image10Canvas{
	//Add Sash to UI
	Sash sash;
	public Image11Sash() {

	}
	
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	public void resSashDragedEvent(SelectionEvent event){
		debugInfo("Sash: SashDragedEvent \n");
	}
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniSashItem(){
		GridData gridData;
		// Sash to see more of image or image data.
		sash = new Sash(shell, SWT.HORIZONTAL);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		sash.setLayoutData(gridData);
	}
	
	private void iniSashListener(){
		sash.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent event) {
				resSashDragedEvent(event);
			}
		});
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeSash(){
		iniSashItem();
		iniSashListener();
	}
	
	
	/*************************************************************/
}
