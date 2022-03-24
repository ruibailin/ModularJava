package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class Image09Label extends Image08Canvas{

	Label sizeLabel, depthLabel, transparentPixelLabel,timeToLoadLabel;
	Label screenSizeLabel, backgroundPixelLabel;
	Label locationLabel, disposalMethodLabel, delayTimeLabel,repeatCountLabel;
	Label paletteLabel;
	public Image09Label() {

	}

	/*************************************************************/
	//Static functions area
	static int visibleDelay(int ms) {
		if (ms < 20) return ms + 30;
		if (ms < 30) return ms + 10;
		return ms;
	}
	
	/*
	 * Return a String describing the specified
	 * transparent or background pixel.
	 */
	static String pixelInfo(int pixel) {
		if (pixel == -1) {
			return pixel + " (" + bundle.getString("None_lc") + ")";
		}
		return pixel + " (0x" + Integer.toHexString(pixel) + ")";
	}
	
	/*
	 * Return a String describing the specified disposal method.
	 */
	static String disposalString(int disposalMethod) {
		switch (disposalMethod) {
			case SWT.DM_FILL_NONE: return bundle.getString("None_lc");
			case SWT.DM_FILL_BACKGROUND: return bundle.getString("Background_lc");
			case SWT.DM_FILL_PREVIOUS: return bundle.getString("Previous_lc");
		}
		return bundle.getString("Unspecified_lc");
	}
	/*************************************************************/
	//Override functions area
	
	/*************************************************************/
	private void iniImageDataLabel(){
		
		// Label to show the image size.
		sizeLabel = new Label(shell, SWT.NONE);
		sizeLabel.setText(bundle.getString("Size_initial"));
		sizeLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the image depth.
		depthLabel = new Label(shell, SWT.NONE);
		depthLabel.setText(bundle.getString("Depth_initial"));
		depthLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the transparent pixel.
		transparentPixelLabel = new Label(shell, SWT.NONE);
		transparentPixelLabel.setText(bundle.getString("Transparent_pixel_initial"));
		transparentPixelLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the time to load.
		timeToLoadLabel = new Label(shell, SWT.NONE);
		timeToLoadLabel.setText(bundle.getString("Time_to_load_initial"));
		timeToLoadLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Separate the animation fields from the rest of the fields.
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

	}
	
	private void iniScreenDataLabel(){
		// Label to show the logical screen size for animation.
		screenSizeLabel = new Label(shell, SWT.NONE);
		screenSizeLabel.setText(bundle.getString("Animation_size_initial"));
		screenSizeLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the background pixel.
		backgroundPixelLabel = new Label(shell, SWT.NONE);
		backgroundPixelLabel.setText(bundle.getString("Background_pixel_initial"));
		backgroundPixelLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
	}
	
	private void iniProcessImageLabel(){
		// Label to show the image location (x, y).
		locationLabel = new Label(shell, SWT.NONE);
		locationLabel.setText(bundle.getString("Image_location_initial"));
		locationLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the image disposal method.
		disposalMethodLabel = new Label(shell, SWT.NONE);
		disposalMethodLabel.setText(bundle.getString("Disposal_initial"));
		disposalMethodLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the image delay time.
		delayTimeLabel = new Label(shell, SWT.NONE);
		delayTimeLabel.setText(bundle.getString("Delay_initial"));
		delayTimeLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Label to show the background pixel.
		repeatCountLabel = new Label(shell, SWT.NONE);
		repeatCountLabel.setText(bundle.getString("Repeats_initial"));
		repeatCountLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		// Separate the animation fields from the palette.
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
	}
	
	private void iniPalleteDataLabel(){
		// Label to show if the image has a direct or indexed palette.
		paletteLabel = new Label(shell, SWT.NONE);
		paletteLabel.setText(bundle.getString("Palette_initial"));
		paletteLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
	}
	/*************************************************************/
	private void setImageDataLabel(){
		String msg,string;
		msg = createMsg(bundle.getString("Analyzer_on"), fileName);
		shell.setText(msg);
		
		if (imageDataArray.length > 1) {
			string = createMsg(bundle.getString("Type_index"), 
			                   new Object[] {fileTypeString(imageData.type),
			                                 new Integer(imageDataIndex + 1),
			                                 new Integer(imageDataArray.length)});
		} else {
			string = createMsg(bundle.getString("Type_string"), fileTypeString(imageData.type));
		}
		typeLabel.setText(string);

		string = createMsg(bundle.getString("Size_value"), 
					 new Object[] {new Integer(imageData.width),
							   new Integer(imageData.height)});
		sizeLabel.setText(string);

		string = createMsg(bundle.getString("Depth_value"),
				new Object[] {new Integer(imageData.depth), new Integer(display.getDepth())});
		depthLabel.setText(string);

		string = createMsg(bundle.getString("Transparent_pixel_value"), pixelInfo(imageData.transparentPixel));
		transparentPixelLabel.setText(string);

		string = createMsg(bundle.getString("Time_to_load_value"), new Long(loadTime));
		timeToLoadLabel.setText(string);
		
	}

	private void setScreenDataLabel(){
		String string;
		string = createMsg(bundle.getString("Animation_size_value"), 
                new Object[] {new Integer(loader.logicalScreenWidth),
					new Integer(loader.logicalScreenHeight)});
		screenSizeLabel.setText(string);

		string = createMsg(bundle.getString("Background_pixel_value"), pixelInfo(loader.backgroundPixel));
		backgroundPixelLabel.setText(string);
	}

	private void setProcessImageLabel(){
		String string;
		string = createMsg(bundle.getString("Image_location_value"), 
                new Object[] {new Integer(imageData.x), new Integer(imageData.y)});
		locationLabel.setText(string);

		string = createMsg(bundle.getString("Disposal_value"),
                new Object[] {new Integer(imageData.disposalMethod),
				      disposalString(imageData.disposalMethod)});
		disposalMethodLabel.setText(string);

		int delay = imageData.delayTime * 10;
		int delayUsed = visibleDelay(delay);
		if (delay != delayUsed) {
			string = createMsg(bundle.getString("Delay_value"), 
                 new Object[] {new Integer(delay), new Integer(delayUsed)});
		} else {
			string = createMsg(bundle.getString("Delay_used"), new Integer(delay));
		}
		delayTimeLabel.setText(string);

		if (loader.repeatCount == 0) {
				string = createMsg( bundle.getString("Repeats_forever"), new Integer(loader.repeatCount));
		} else {
			string = createMsg(bundle.getString("Repeats_value"), new Integer(loader.repeatCount));
		}
		repeatCountLabel.setText(string);
	}

	private void setPalleteDataLabel(){
		String string;
		if (imageData.palette.isDirect) {
			string = bundle.getString("Palette_direct");
		} else {
			string = createMsg(bundle.getString("Palette_value"), new Integer(imageData.palette.getRGBs().length));
		}
		paletteLabel.setText(string);
	}

	/*************************************************************/
	//functions called by main function
	public void initializeLabel2(){
		iniImageDataLabel();
		iniScreenDataLabel();
		iniProcessImageLabel();
		iniPalleteDataLabel();
	}
	
	public void updateLabel(){
		setImageDataLabel();
		setScreenDataLabel();
		setProcessImageLabel();
		setPalleteDataLabel();
	}
}
