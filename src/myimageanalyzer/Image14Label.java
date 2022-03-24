package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class Image14Label extends Image13Text{

	//Add status label to show a point's information
	Label statusLabel;
	public Image14Label() {

	}

	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniPointStatusLabel(){
		GridData gridData;
		// Label to show status and cursor location in image.
		statusLabel = new Label(shell, SWT.NONE);
		statusLabel.setText("");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		statusLabel.setLayoutData(gridData);
	}
	
	private void setPointStatusLabel(int x,int y){
		statusLabel.setText("");
		if(x < 0)	return;
		if(y < 0)	return;
		if( x>= imageData.width) return;
		if(y >= imageData.height) return;
		int pixel = imageData.getPixel(x, y);
		RGB rgb = imageData.palette.getRGB(pixel);
		boolean hasAlpha = false;
		int alphaValue = 0;
		if (imageData.alphaData != null && imageData.alphaData.length > 0) {
			hasAlpha = true;
			alphaValue = imageData.getAlpha(x, y);
		}
		String rgbMessageFormat = bundle.getString(hasAlpha ? "RGBA" : "RGB");
		Object[] rgbArgs = {
				Integer.toString(rgb.red),
				Integer.toString(rgb.green),
				Integer.toString(rgb.blue),
				Integer.toString(alphaValue)
		};
		Object[] rgbHexArgs = {
				Integer.toHexString(rgb.red),
				Integer.toHexString(rgb.green),
				Integer.toHexString(rgb.blue),
				Integer.toHexString(alphaValue)
		};
		Object[] args = {
				new Integer(x),
				new Integer(y),
				new Integer(pixel),
				Integer.toHexString(pixel),
				createMsg(rgbMessageFormat, rgbArgs),
				createMsg(rgbMessageFormat, rgbHexArgs),
				(pixel == imageData.transparentPixel) ? bundle.getString("Color_at_transparent") : ""};
		statusLabel.setText(createMsg(bundle.getString("Color_at"), args));
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeLabel14(){
		iniPointStatusLabel();
	}
	public void initializeLabelPoint(){
		iniPointStatusLabel();
	}
	
	public void updateLabel14(int x,int y){
		setPointStatusLabel(x,y);
	}
	
	public void updateLabePoint(int x,int y){
		setPointStatusLabel(x,y);
	}
	
	/*************************************************************/
}
