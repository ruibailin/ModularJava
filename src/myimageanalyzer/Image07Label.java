package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class Image07Label extends Image06File{

	Label typeLabel;
	
	public Image07Label() {

	}
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area
	
	/*************************************************************/
	private void iniImageTypeLabel(){
		// Label to show the image file type.
		typeLabel = new Label(shell, SWT.NONE);
		typeLabel.setText(bundle.getString("Type_initial"));
		typeLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
	}
	
	/*************************************************************/
	//functions called by main function
	public void initializeLabel(){
		iniImageTypeLabel();
	}

	/*************************************************************/
}
