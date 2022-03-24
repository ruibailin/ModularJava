package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class Image12Label extends Image11Sash{

	//Add static label
	Label dataLabel;
	public Image12Label() {

	}

	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniDataSumLabel(){
		GridData gridData;
		// Label to show data-specific fields.
		dataLabel = new Label(shell, SWT.NONE);
		dataLabel.setText(bundle.getString("Pixel_data_initial"));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		dataLabel.setLayoutData(gridData);
	}
	
	private void setDataSumLabel(){
		String string;
		string = createMsg(
				bundle.getString("Pixel_data_value"),
				new Object[] {
						new Integer(imageData.bytesPerLine),
						new Integer(imageData.scanlinePad),
						depthInfo(imageData.depth),
						(imageData.alphaData != null && imageData.alphaData.length > 0) ?
								bundle.getString("Scroll_for_alpha") : "" });
		dataLabel.setText(string);
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeLabel12(){
		iniDataSumLabel();
	}
	
	public void initializeLabelData(){
		iniDataSumLabel();
	}
	
	public void updateLabel12(){
		setDataSumLabel();
	}
	
	public void updateLabelData(){
		setDataSumLabel();
	}
	/*************************************************************/
}
