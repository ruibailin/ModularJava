package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;

public class Image05Combo extends Image04Group{
	//Add Combo Control
	Combo backgroundCombo;
	
	Combo imageTypeCombo, compressionCombo;
	Label compressionRatioLabel;
	
	Combo scaleXCombo, scaleYCombo, alphaCombo;
	Button incrementalCheck, transparentCheck, maskCheck, backgroundCheck;
	Button previousButton, nextButton, animateButton;
	
	int compression; // used to modify the compression ratio of the image
	float xscale = 1, yscale = 1; // used to scale the image
	int alpha = 255; // used to modify the alpha value of the image
	
	boolean transparent = true; // used to display an image with transparency
	boolean showMask = false; // used to display an icon mask or transparent image mask
	boolean showBackground = false; // used to display the background of an animated image
	
	public Image05Combo() {

	}
	
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override function area
	public void resComboChangeBackgroud(SelectionEvent event){
		debugInfo("Combo: ChangeBackgroud! \n");
//		String background = backgroundCombo.getText();
	}
	
	public void resComboSelectImageType(SelectionEvent event){
		debugInfo("Combo: SelectImageType! \n");
		int index = imageTypeCombo.getSelectionIndex();
		switch(index) {
		case 0:
			compressionCombo.setEnabled(true);
			compressionRatioLabel.setEnabled(true);
			if (compressionCombo.getItemCount() == 100) break;
			compressionCombo.removeAll();
			for (int i = 0; i < 100; i++) {
				compressionCombo.add(String.valueOf(i + 1));
			}
			compressionCombo.select(compressionCombo.indexOf("75"));
			break;
		case 1:
			compressionCombo.setEnabled(true);
			compressionRatioLabel.setEnabled(true);
			if (compressionCombo.getItemCount() == 10) break;
			compressionCombo.removeAll();
			for (int i = 0; i < 4; i++) {
				compressionCombo.add(String.valueOf(i));
			}
			compressionCombo.select(0);
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			compressionCombo.setEnabled(false);
			compressionRatioLabel.setEnabled(false);
			break;
		default:
			break;
		}
	}
	
	public void resComboScaleXChange(SelectionEvent event){
		debugInfo("Combo: ScaleXChange! \n");
		try {
			xscale = Float.parseFloat(scaleXCombo.getText());
		} catch (NumberFormatException e) {
			xscale = 1;
			scaleXCombo.select(scaleXCombo.indexOf("1"));
		}
//		if (image != null) {
//			resizeScrollBars();
//			imageCanvas.redraw();
//		}
	}
	
	public void resComboScaleYChange(SelectionEvent event){
		debugInfo("Combo: ScaleYChange! \n");
		try {
			yscale = Float.parseFloat(scaleYCombo.getText());
		} catch (NumberFormatException e) {
			yscale = 1;
			scaleYCombo.select(scaleYCombo.indexOf("1"));
		}
//		if (image != null) {
//		resizeScrollBars();
//		imageCanvas.redraw();
//		}
	}
	
	public void resComboAlphaChange(SelectionEvent event){
		try {
			alpha = Integer.parseInt(alphaCombo.getText());
		} catch (NumberFormatException e) {
			alphaCombo.select(alphaCombo.indexOf("255"));
			alpha = 255;
		}
	}
	
	public void resComboTransparentCheck(SelectionEvent event){
		debugInfo("Combo: TransparentCheck! \n");
		transparent = ((Button)event.widget).getSelection();
//		if (image != null) {
//			imageCanvas.redraw();
//		}
	}
	
	public void resComboMaskCheck(SelectionEvent event){
		debugInfo("Combo: MaskCheck! \n");
		showMask = ((Button)event.widget).getSelection();
//		if (image != null) {
//			imageCanvas.redraw();
//		}
	}
	
	public void resComboPrevious(SelectionEvent event){
		debugInfo("Combo: Previous! \n");
	}
	
	public void resComboNext(SelectionEvent event){
		debugInfo("Combo: Next! \n");
	}
	
	public void resComboAnimate(SelectionEvent event){
		debugInfo("Combo: Animate! \n");
	}
	
	/*************************************************************/
	private void iniBackgroundCombo(){
		backgroundCombo = new Combo(backgroundGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		backgroundCombo.setItems(new String[] {
			bundle.getString("None"),
			bundle.getString("White"),
			bundle.getString("Black"),
			bundle.getString("Red"),
			bundle.getString("Green"),
			bundle.getString("Blue")});
		backgroundCombo.select(backgroundCombo.indexOf(bundle.getString("White")));
		backgroundCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboChangeBackgroud(event);
			}
		});
	}
	/*************************************************************/
	private void iniSaveCombo(){
		imageTypeCombo = new Combo(saveGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] types = {"JPEG", "PNG", "GIF", "ICO", "TIFF", "BMP"};
		for (int i = 0; i < types.length; i++) {
			imageTypeCombo.add(types[i]);
		}
		imageTypeCombo.select(imageTypeCombo.indexOf("JPEG"));
		imageTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboSelectImageType(event);
			}
		});
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		imageTypeCombo.setLayoutData(gridData);
		
		compressionRatioLabel = new Label(saveGroup, SWT.NONE);
		compressionRatioLabel.setText(bundle.getString("Compression"));
		gridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		compressionRatioLabel.setLayoutData(gridData);
		
		compressionCombo = new Combo(saveGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < 100; i++) {
			compressionCombo.add(String.valueOf(i + 1));
		}
		compressionCombo.select(compressionCombo.indexOf("75"));
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		compressionCombo.setLayoutData(gridData);

	}
	
	private void iniScaleXCombo(){
		// Combo to change the x scale.
		String[] xvalues = {
			"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1",
			"1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2",
			"3", "4", "5", "6", "7", "8", "9", "10",};

		scaleXCombo = new Combo(scaleXGroup, SWT.DROP_DOWN);
		for (int i = 0; i < xvalues.length; i++) {
			scaleXCombo.add(xvalues[i]);
		}
		scaleXCombo.select(scaleXCombo.indexOf("1"));
		scaleXCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboScaleXChange(event);
			}
		});
	}
	
	private void iniScaleYCombo(){
		String[] yvalues = {
				"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1",
				"1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2",
				"3", "4", "5", "6", "7", "8", "9", "10",};
		
		scaleYCombo = new Combo(scaleYGroup, SWT.DROP_DOWN);
		for (int i = 0; i < yvalues.length; i++) {
			scaleYCombo.add(yvalues[i]);
		}
		scaleYCombo.select(scaleYCombo.indexOf("1"));
		scaleYCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboScaleYChange(event);
			}
		});
	}
	
	private void iniAlphaCombo(){
		alphaCombo = new Combo(alphaGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i <= 255; i += 5) {
			alphaCombo.add(String.valueOf(i));
		}
		alphaCombo.select(alphaCombo.indexOf("255"));
		alphaCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboAlphaChange(event);
			}
		});
	}
	
	private void iniDisplayCombo(){
		// Check box to request incremental display.
		incrementalCheck = new Button(displayGroup, SWT.CHECK);
		incrementalCheck.setText(bundle.getString("Incremental"));
		incrementalCheck.setSelection(incremental);
		incrementalCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				incremental = ((Button)event.widget).getSelection();
			}
		});
		
		// Check box to request transparent display.
		transparentCheck = new Button(displayGroup, SWT.CHECK);
		transparentCheck.setText(bundle.getString("Transparent"));
		transparentCheck.setSelection(transparent);
		transparentCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboTransparentCheck(event);
			}
		});

		// Check box to request mask display.
		maskCheck = new Button(displayGroup, SWT.CHECK);
		maskCheck.setText(bundle.getString("Mask"));
		maskCheck.setSelection(showMask);
		maskCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboMaskCheck(event);
			}
		});

		// Check box to request background display.
		backgroundCheck = new Button(displayGroup, SWT.CHECK);
		backgroundCheck.setText(bundle.getString("Background"));
		backgroundCheck.setSelection(showBackground);
		backgroundCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				showBackground = ((Button)event.widget).getSelection();
			}
		});

	}
	
	private void iniAnimateCombo(){
		previousButton = new Button(animateGroup, SWT.PUSH);
		previousButton.setText(bundle.getString("Previous"));
		previousButton.setEnabled(false);
		previousButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboPrevious(event);
			}
		});

		// Push button to display the next image in a multi-image file.
		nextButton = new Button(animateGroup, SWT.PUSH);
		nextButton.setText(bundle.getString("Next"));
		nextButton.setEnabled(false);
		nextButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboNext(event);
			}
		});

		// Push button to toggle animation of a multi-image file.
		animateButton = new Button(animateGroup, SWT.PUSH);
		animateButton.setText(bundle.getString("Animate"));
		animateButton.setEnabled(false);
		animateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resComboAnimate(event);
			}
		});
	}
	/*************************************************************/
	private void iniComboItem(){
		iniBackgroundCombo();
		iniSaveCombo();
		iniScaleXCombo();
		iniScaleYCombo();
		iniAlphaCombo();
		iniDisplayCombo();
		iniAnimateCombo();
	}
	/*************************************************************/

	public void initializeCombo(){
		iniComboItem();
	}
}
