package mybrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

public class Browser05Label extends Browser04Text{

	//Add status Label
	Label status;
	ProgressBar progressBar;
	public Browser05Label() {

	}
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniStatusLabel(){
		status = new Label(shell, SWT.NONE);
		progressBar = new ProgressBar(shell, SWT.NONE);
		
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, 5);
		data.right = new FormAttachment(progressBar, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment(100, -5);
		status.setLayoutData(data);
		status.setText("");
		
		data = new FormData();
		data.right = new FormAttachment(100, -5);
		data.bottom = new FormAttachment(100, -5);
		progressBar.setLayoutData(data);
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeLabel(){
		iniStatusLabel();

	}
	
}
