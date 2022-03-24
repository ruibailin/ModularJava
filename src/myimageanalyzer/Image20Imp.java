package myimageanalyzer;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.ImageLoaderListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;

public class Image20Imp extends Image14Label{
	//Implement all Functions which should be overrided
	public Image20Imp() {

	}
	/*************************************************************/
	//01Shell
	@Override
	public void resShellResizedEvent(ControlEvent event){
		if (image == null || shell.isDisposed())
			return;
		resizeScrollBars();
	}
	
	@Override
	public void resShellClosedEvent(ShellEvent event){
		super.resShellClosedEvent(event);
		debugInfo("Thread:Shell Closed Event! \n");
		animate = false; // stop any animation in progress
		if (animateThread != null) {
			// wait for the thread to die before disposing the shell.
			while (animateThread.isAlive()) {
				if (!display.readAndDispatch()) display.sleep();
			}
		}
		event.doit = true;
	}
	
	@Override
	public void resShellDisposedEvent(DisposeEvent event){
		// Clean up.
		if (image != null)
			image.dispose();
		whiteColor.dispose();
		blackColor.dispose();
		redColor.dispose();
		greenColor.dispose();
		blueColor.dispose();
		fixedWidthFont.dispose();
	}
	/*************************************************************/
	//02Thread
	@Override
	public void runThreadAnimateProc(){
		// Pre-animation widget setup.
		preAnimation();
		
		// Animate.
		try {
			animateLoop();
		} catch (final SWTException e) {
			display.syncExec(new Runnable() {
				public void run() {
					showErrorDialog(createMsg(bundle.getString("Creating_image"), 
							    new Integer(imageDataIndex+1)),
							    currentName, e);
				}
			});
		}
		
		// Post animation widget reset.
		postAnimation();
	}
	
	@Override
	public void runThreadIncrementalProc(){
		while (incrementalEvents != null) {
			// Synchronize so we don't try to remove when the vector is null.
			synchronized (this) {
				if (incrementalEvents != null) {
					if (incrementalEvents.size() > 0) {
						ImageLoaderEvent event = (ImageLoaderEvent) incrementalEvents.remove(0);
						if (image != null) image.dispose();
						image = new Image(display, event.imageData);
						imageData = event.imageData;
						imageCanvasGC.drawImage(
							image,
							0,
							0,
							imageData.width,
							imageData.height,
							imageData.x,
							imageData.y,
							imageData.width,
							imageData.height);
					} else {
						Thread.yield();
					}
				}
			}
		}
		display.wake();
	}
	/*************************************************************/
	//03Menu
	/*************************************************************/
	//04Group
	/*************************************************************/
	//05Combo
	@Override
	public void resComboChangeBackgroud(SelectionEvent event){
		String background = backgroundCombo.getText();
		if (background.equals(bundle.getString("White"))) {
			imageCanvas.setBackground(whiteColor);
		} else if (background.equals(bundle.getString("Black"))) {
			imageCanvas.setBackground(blackColor);
		} else if (background.equals(bundle.getString("Red"))) {
			imageCanvas.setBackground(redColor);
		} else if (background.equals(bundle.getString("Green"))) {
			imageCanvas.setBackground(greenColor);
		} else if (background.equals(bundle.getString("Blue"))) {
			imageCanvas.setBackground(blueColor);
		} else {
			imageCanvas.setBackground(null);
		}
	}
	
	@Override
	public void resComboSelectImageType(SelectionEvent event){
		super.resComboSelectImageType(event);

	}
	
	@Override
	public void resComboScaleXChange(SelectionEvent event){
		super.resComboScaleXChange(event);
		if (image != null) {
			resizeScrollBars();
			imageCanvas.redraw();
		}
	}
	
	@Override
	public void resComboScaleYChange(SelectionEvent event){
		super.resComboScaleYChange(event);
		if (image != null) {
		resizeScrollBars();
		imageCanvas.redraw();
		}
	}
	
	@Override
	public void resComboAlphaChange(SelectionEvent event){
		super.resComboAlphaChange(event);
	}
	
	@Override
	public void resComboTransparentCheck(SelectionEvent event){
		super.resComboTransparentCheck(event);
		if (image != null) {
			imageCanvas.redraw();
		}
	}
	
	@Override
	public void resComboMaskCheck(SelectionEvent event){
		super.resComboMaskCheck(event);
		if (image != null) {
			imageCanvas.redraw();
		}
	}
	
	@Override
	public void resComboPrevious(SelectionEvent event){
		previous();
	}
	
	@Override
	public void resComboNext(SelectionEvent event){
		next();
	}
	
	@Override
	public void resComboAnimate(SelectionEvent event){
		animate = !animate;
		if (animate && image != null && imageDataArray.length > 1) {
			animateThread = new Thread(bundle.getString("Animation")) {
				public void run() {
					// Pre-animation widget setup.
					preAnimation();
					
					// Animate.
					try {
						animateLoop();
					} catch (final SWTException e) {
						display.syncExec(new Runnable() {
							public void run() {
								showErrorDialog(createMsg(bundle.getString("Creating_image"), 
										    new Integer(imageDataIndex+1)),
										    currentName, e);
							}
						});
					}
					
					// Post animation widget reset.
					postAnimation();
				}
			};
			animateThread.setDaemon(true);
			animateThread.start();
		}
	}
	/*************************************************************/
	//06File
	@Override
	public void menuFileOpen(){
		super.menuFileOpen();
		
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		imageCanvas.setCursor(waitCursor);
		ImageLoader oldLoader = loader;
		try {
			loader = new ImageLoader();
			if (incremental) {
				// Prepare to handle incremental events.
				loader.addImageLoaderListener(new ImageLoaderListener() {
					public void imageDataLoaded(ImageLoaderEvent event) {
						resImageLoaderEvent(event);
					}
				});
				initializeThread();
			}
			// Read the new image(s) from the chosen file.
			long startTime = System.currentTimeMillis();
			imageDataArray = loader.load(fileName);
			loadTime = System.currentTimeMillis() - startTime;
			if (imageDataArray.length > 0) {
				// Cache the filename.
				currentName = fileName;
				
				// If there are multiple images in the file (typically GIF)
				// then enable the Previous, Next and Animate buttons.
				previousButton.setEnabled(imageDataArray.length > 1);
				nextButton.setEnabled(imageDataArray.length > 1);
				animateButton.setEnabled(imageDataArray.length > 1 && loader.logicalScreenWidth > 0 && loader.logicalScreenHeight > 0);
	
				// Display the first image in the file.
				imageDataIndex = 0;
				displayImage(imageDataArray[imageDataIndex]);
			}
		} catch (SWTException e) {
			showErrorDialog(bundle.getString("Loading_lc"), fileName, e);
			loader = oldLoader;
		} catch (SWTError e) {
			showErrorDialog(bundle.getString("Loading_lc"), fileName, e);
			loader = oldLoader;
		} catch (OutOfMemoryError e) {
			showErrorDialog(bundle.getString("Loading_lc"), fileName, e);
			loader = oldLoader;
		} finally {
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	
	@Override
	public void menuFileReopen(){
		super.menuFileReopen();
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		imageCanvas.setCursor(waitCursor);
		try {
			loader = new ImageLoader();
			ImageData[] newImageData;
			if (fileName == null) {
				URL url = new URL(currentName);
				InputStream stream = url.openStream();
				long startTime = System.currentTimeMillis();
				newImageData = loader.load(stream);
				loadTime = System.currentTimeMillis() - startTime;
				stream.close();
			} else {
				long startTime = System.currentTimeMillis();
				newImageData = loader.load(fileName);
				loadTime = System.currentTimeMillis() - startTime;
			}
			imageDataIndex = 0;
			displayImage(newImageData[imageDataIndex]);

		} catch (Exception e) {
			showErrorDialog(bundle.getString("Reloading_lc"), currentName, e);
		} catch (OutOfMemoryError e) {
			showErrorDialog(bundle.getString("Reloading_lc"), currentName, e);
		} finally {	
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	
	@Override
	public void menuFileLoad(){
		super.menuFileLoad();
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		imageCanvas.setCursor(waitCursor);
		try {
			// Read the new image from the chosen file.
			long startTime = System.currentTimeMillis();
			Image newImage = new Image(display, fileName);
			loadTime = System.currentTimeMillis() - startTime; // don't include getImageData in load time
			imageData = newImage.getImageData();

			// Cache the filename.
			currentName = fileName;
			
			// Fill in array and loader data.
			loader = new ImageLoader();
			imageDataArray = new ImageData[] {imageData};
			loader.data = imageDataArray;
				
			// Display the image.
			imageDataIndex = 0;
			displayImage(imageData);
		} catch (SWTException e) {
			showErrorDialog(bundle.getString("Loading_lc"), fileName, e);
		} catch (SWTError e) {
			showErrorDialog(bundle.getString("Loading_lc"), fileName, e);
		} catch (OutOfMemoryError e) {
			showErrorDialog(bundle.getString("Loading_lc"), fileName, e);
		} finally {
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	
	@Override
	public void menuFileSave(){
		if (image == null) return;
		animate = false; // stop any animation in progress

		// If the image file type is unknown, we can't 'Save',
		// so we have to use 'Save As...'.
		if (imageData.type == SWT.IMAGE_UNDEFINED || fileName == null) {
			menuFileSaveAs();
			return;
		}

		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		shell.setCursor(waitCursor);
		imageCanvas.setCursor(waitCursor);
		try {
			// Save the current image to the current file.
			loader.data = new ImageData[] {imageData};
			if (imageData.type == SWT.IMAGE_JPEG) loader.compression = compressionCombo.indexOf(compressionCombo.getText()) + 1;
			if (imageData.type == SWT.IMAGE_PNG) loader.compression = compressionCombo.indexOf(compressionCombo.getText());
			loader.save(fileName, imageData.type);
		} catch (SWTException e) {
			showErrorDialog(bundle.getString("Saving_lc"), fileName, e);
		} catch (SWTError e) {
			showErrorDialog(bundle.getString("Saving_lc"), fileName, e);
		} finally {
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	
	@Override
	public void menuFileSaveAs(){
		super.menuFileSaveAs();
		
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		imageCanvas.setCursor(waitCursor);
		try {
			// Save the current image to the specified file.
			boolean multi = false;
			if (loader.data.length > 1) {
				MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				box.setMessage(createMsg(bundle.getString("Save_all"), new Integer(loader.data.length)));
				int result = box.open();
				if (result == SWT.CANCEL) return;
				if (result == SWT.YES) multi = true;
			}
			/* If the image has transparency but the user has transparency turned off,
			 * turn it off in the saved image. */
			int transparentPixel = imageData.transparentPixel;
			if (!multi && transparentPixel != -1 && !transparent) {
				imageData.transparentPixel = -1;
			}
			
			if (!multi) loader.data = new ImageData[] {imageData};
			loader.compression = compressionCombo.indexOf(compressionCombo.getText());
			loader.save(fileName, fileType);
			
			/* Restore the previous transparency setting. */
			if (!multi && transparentPixel != -1 && !transparent) {
				imageData.transparentPixel = transparentPixel;
			}

			// Update the shell title and file type label,
			// and use the new file.
			shell.setText(createMsg(bundle.getString("Analyzer_on"), fileName));
			typeLabel.setText(createMsg(bundle.getString("Type_string"), fileTypeString(fileType)));

		} catch (SWTException e) {
			showErrorDialog(bundle.getString("Saving_lc"), fileName, e);
		} catch (SWTError e) {
			showErrorDialog(bundle.getString("Saving_lc"), fileName, e);
		} finally {
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	
	@Override
	public void menuFileSaveMaskAs(){
		if (image == null || !showMask) return;
		if (imageData.getTransparencyType() == SWT.TRANSPARENCY_NONE) return;
		animate = false; // stop any animation in progress

		// Get the user to choose a file name and type to save.
		FileDialog fileChooser = new FileDialog(shell, SWT.SAVE);
		fileChooser.setFilterPath(lastPath);
		if (fileName != null) fileChooser.setFileName(fileName);
		fileChooser.setFilterExtensions(SAVE_FILTER_EXTENSIONS);
		fileChooser.setFilterNames(SAVE_FILTER_NAMES);
		String filename = fileChooser.open();
		lastPath = fileChooser.getFilterPath();
		if (filename == null)
			return;

		// Figure out what file type the user wants saved.
		int filetype = fileChooser.getFilterIndex();
		if (filetype == -1) {
			/* The platform file dialog does not support user-selectable file filters.
			 * Determine the desired type by looking at the file extension. 
			 */
			filetype = determineFileType(filename);
			if (filetype == SWT.IMAGE_UNDEFINED) {
				MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
				box.setMessage(createMsg(bundle.getString("Unknown_extension"), 
					filename.substring(filename.lastIndexOf('.') + 1)));
				box.open();
				return;
			}
		}
		
		if (new java.io.File(filename).exists()) {
			MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			box.setMessage(createMsg(bundle.getString("Overwrite"), filename));
			if (box.open() == SWT.CANCEL)
				return;
		}
		
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		shell.setCursor(waitCursor);
		imageCanvas.setCursor(waitCursor);
		try {
			// Save the mask of the current image to the specified file.
			ImageData maskImageData = imageData.getTransparencyMask();
			loader.data = new ImageData[] {maskImageData};
			loader.save(filename, filetype);
			
		} catch (SWTException e) {
			showErrorDialog(bundle.getString("Saving_lc"), filename, e);
		} catch (SWTError e) {
			showErrorDialog(bundle.getString("Saving_lc"), filename, e);
		} finally {
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	
	@Override
	public void menuFilePrint(){
		if (image == null) return;

		try {
			// Ask the user to specify the printer.
			PrintDialog dialog = new PrintDialog(shell, SWT.NONE);
			if (printerData != null) dialog.setPrinterData(printerData);
			printerData = dialog.open();
			if (printerData == null) return;
			
			Printer printer = new Printer(printerData);
			Point screenDPI = display.getDPI();
			Point printerDPI = printer.getDPI();
			int scaleFactor = printerDPI.x / screenDPI.x;
			Rectangle trim = printer.computeTrim(0, 0, 0, 0);
			if (printer.startJob(currentName)) {
				if (printer.startPage()) {
					GC gc = new GC(printer);
					int transparentPixel = imageData.transparentPixel;
					if (transparentPixel != -1 && !transparent) {
						imageData.transparentPixel = -1;
					}
					Image printerImage = new Image(printer, imageData);
					gc.drawImage(
						printerImage,
						0,
						0,
						imageData.width,
						imageData.height,
						-trim.x,
						-trim.y,
						scaleFactor * imageData.width,
						scaleFactor * imageData.height);
					if (transparentPixel != -1 && !transparent) {
						imageData.transparentPixel = transparentPixel;
					}
					printerImage.dispose();
					gc.dispose();
					printer.endPage();
				}
				printer.endJob();
			}
			printer.dispose();
		} catch (SWTError e) {
			MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
			box.setMessage(bundle.getString("Printing_error") + e.getMessage());
			box.open();
		}
	}
	
	@Override
	public void menuFileClose(){
		shell.close();
	}
	
	@Override
	public void menuAlphaCompose(int alpha_op){
		if (image == null) return;
		animate = false; // stop any animation in progress
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		shell.setCursor(waitCursor);
		imageCanvas.setCursor(waitCursor);
		try {
			if (alpha_op == ALPHA_CONSTANT) {
				imageData.alpha = alpha;
			} else {
				imageData.alpha = -1;
				switch (alpha_op) {
					case ALPHA_X: 
						for (int y = 0; y < imageData.height; y++) {
						for (int x = 0; x < imageData.width; x++) {
							imageData.setAlpha(x, y, (x + alpha) % 256);
						}
						}
						break;
					case ALPHA_Y: 
						for (int y = 0; y < imageData.height; y++) {
						for (int x = 0; x < imageData.width; x++) {
							imageData.setAlpha(x, y, (y + alpha) % 256);
						}
						}
						break;
					default: break;
				}					
			}			
			displayImage(imageData);
		} finally {
			shell.setCursor(null);
			imageCanvas.setCursor(crossCursor);
		}
	}
	/*************************************************************/
	//07Label
	
	/*************************************************************/
	//08Canvas
	@Override
	public void resImageLoaderEvent(ImageLoaderEvent event){
		super.resImageLoaderEvent(event);
	}
	
	@Override
	public void resImageCanvasPaintEvent(PaintEvent event){
		super.resImageCanvasPaintEvent(event);
	}
	
	@Override
	public void resImageCanvasMouseEvent(MouseEvent event){
		if (image != null) {
			showColorAt(event.x, event.y);
		}
	}
	
	@Override
	public void resImageCanvasScrollHorizontally(SelectionEvent event){
		scrollHorizontally((ScrollBar)event.widget);
	}
	
	@Override
	public void resImageCanvasScrollVertically(SelectionEvent event){
		scrollVertically((ScrollBar)event.widget);
	}
	/**************************************************************/
	//09Label
	 
	/*************************************************************/
	//10Canavs
	/*************************************************************/
	@Override
	public void resPaletteCanvasPaintEvent(PaintEvent event){
		if (image != null)
			paintPalette(event);
	}
	@Override
	public void resPaletteCanvasScrollVertically(SelectionEvent event){
		scrollPalette((ScrollBar)event.widget);
	}
	/*************************************************************/
	//11Sash
	@Override
	public void resSashDragedEvent(SelectionEvent event){
		if (event.detail != SWT.DRAG) {
			((GridData)paletteCanvas.getLayoutData()).heightHint = SWT.DEFAULT;
			Rectangle paletteCanvasBounds = paletteCanvas.getBounds();
			int minY = paletteCanvasBounds.y + 20;
			Rectangle dataLabelBounds = dataLabel.getBounds();
			int maxY = statusLabel.getBounds().y - dataLabelBounds.height - 20;
			if (event.y > minY && event.y < maxY) {
				Rectangle oldSash = sash.getBounds();
				sash.setBounds(event.x, event.y, event.width, event.height);
				int diff = event.y - oldSash.y;
				Rectangle bounds = imageCanvas.getBounds();
				imageCanvas.setBounds(bounds.x, bounds.y, bounds.width, bounds.height + diff);
				bounds = paletteCanvasBounds;
				paletteCanvas.setBounds(bounds.x, bounds.y, bounds.width, bounds.height + diff);
				bounds = dataLabelBounds;
				dataLabel.setBounds(bounds.x, bounds.y + diff, bounds.width, bounds.height);
				bounds = dataText.getBounds();
				dataText.setBounds(bounds.x, bounds.y + diff, bounds.width, bounds.height - diff);
				//shell.layout(true);
			}
		}
	}
	
	/*************************************************************/
	//13Text
	public void resTextMouseDown(MouseEvent event){
		if (image != null && event.button == 1) {
			showColorForData();
		}
	}
	
	public void resTextKeyPressed(KeyEvent event){
		if (image != null) {
			showColorForData();
		}
	}
	/*************************************************************/
	//For 02Thread
	/*
	 * Loop through all of the images in a multi-image file
	 * and display them one after another.
	 */
	private void animateLoop() {
		// Create an off-screen image to draw on, and a GC to draw with.
		// Both are disposed after the animation.
		Image offScreenImage = new Image(display, loader.logicalScreenWidth, loader.logicalScreenHeight);
		GC offScreenImageGC = new GC(offScreenImage);
		
		try {
			// Use syncExec to get the background color of the imageCanvas.
			display.syncExec(new Runnable() {
				public void run() {
					canvasBackground = imageCanvas.getBackground();
				}
			});

			// Fill the off-screen image with the background color of the canvas.
			offScreenImageGC.setBackground(canvasBackground);
			offScreenImageGC.fillRectangle(
				0,
				0,
				loader.logicalScreenWidth,
				loader.logicalScreenHeight);
					
			// Draw the current image onto the off-screen image.
			offScreenImageGC.drawImage(
				image,
				0,
				0,
				imageData.width,
				imageData.height,
				imageData.x,
				imageData.y,
				imageData.width,
				imageData.height);

			int repeatCount = loader.repeatCount;
			while (animate && (loader.repeatCount == 0 || repeatCount > 0)) {
				if (imageData.disposalMethod == SWT.DM_FILL_BACKGROUND) {
					// Fill with the background color before drawing.
					Color bgColor = null;
					int backgroundPixel = loader.backgroundPixel;
					if (showBackground && backgroundPixel != -1) {
						// Fill with the background color.
						RGB backgroundRGB = imageData.palette.getRGB(backgroundPixel);
						bgColor = new Color(null, backgroundRGB);
					}
					try {
						offScreenImageGC.setBackground(bgColor != null ? bgColor : canvasBackground);
						offScreenImageGC.fillRectangle(
							imageData.x,
							imageData.y,
							imageData.width,
							imageData.height);
					} finally {
						if (bgColor != null) bgColor.dispose();
					}
				} else if (imageData.disposalMethod == SWT.DM_FILL_PREVIOUS) {
					// Restore the previous image before drawing.
					offScreenImageGC.drawImage(
						image,
						0,
						0,
						imageData.width,
						imageData.height,
						imageData.x,
						imageData.y,
						imageData.width,
						imageData.height);
				}
									
				// Get the next image data.
				imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
				imageData = imageDataArray[imageDataIndex];
				image.dispose();
				image = new Image(display, imageData);
				
				// Draw the new image data.
				offScreenImageGC.drawImage(
					image,
					0,
					0,
					imageData.width,
					imageData.height,
					imageData.x,
					imageData.y,
					imageData.width,
					imageData.height);
				
				// Draw the off-screen image to the screen.
				imageCanvasGC.drawImage(offScreenImage, 0, 0);
				
				// Sleep for the specified delay time before drawing again.
				try {
					Thread.sleep(visibleDelay(imageData.delayTime * 10));
				} catch (InterruptedException e) {
				}
				
				// If we have just drawn the last image in the set,
				// then decrement the repeat count.
				if (imageDataIndex == imageDataArray.length - 1) repeatCount--;
			}
		} finally {
			offScreenImage.dispose();
			offScreenImageGC.dispose();
		}
	}
	
	private void preAnimation() {
		display.syncExec(new Runnable() {
			public void run() {
				// Change the label of the Animate button to 'Stop'.
				animateButton.setText(bundle.getString("Stop"));
				
				// Disable anything we don't want the user
				// to select during the animation.
				previousButton.setEnabled(false);
				nextButton.setEnabled(false);
				backgroundCombo.setEnabled(false);
				scaleXCombo.setEnabled(false);
				scaleYCombo.setEnabled(false);
				alphaCombo.setEnabled(false);
				incrementalCheck.setEnabled(false);
				transparentCheck.setEnabled(false);
				maskCheck.setEnabled(false);
				// leave backgroundCheck enabled
			
				// Reset the scale combos and scrollbars.
				resetScaleCombos();
				resetScrollBars();
			}
		});
	}

	/*
	 * Post animation reset.
	 */
	private void postAnimation() {
		display.syncExec(new Runnable() {
			public void run() {
				// Enable anything we disabled before the animation.
				previousButton.setEnabled(true);
				nextButton.setEnabled(true);
				backgroundCombo.setEnabled(true);
				scaleXCombo.setEnabled(true);
				scaleYCombo.setEnabled(true);
				alphaCombo.setEnabled(true);
				incrementalCheck.setEnabled(true);
				transparentCheck.setEnabled(true);
				maskCheck.setEnabled(true);
			
				// Reset the label of the Animate button.
				animateButton.setText(bundle.getString("Animate"));
			
				if (animate) {
					// If animate is still true, we finished the
					// full number of repeats. Leave the image as-is.
					animate = false;
				} else {
					// Redisplay the current image and its palette.
					displayImage(imageDataArray[imageDataIndex]);
				}
			}
		});
	}


	/*************************************************************/
	//05Combo
	/*
	 * Called when the Previous button is pressed.
	 * Display the previous image in a multi-image file.
	 */
	private void previous() {
		if (image != null && imageDataArray.length > 1) {
			if (imageDataIndex == 0) {
				imageDataIndex = imageDataArray.length;
			}
			imageDataIndex = imageDataIndex - 1;
			displayImage(imageDataArray[imageDataIndex]);
		}	
	}

	/*
	 * Called when the Next button is pressed.
	 * Display the next image in a multi-image file.
	 */
	private void next() {
		if (image != null && imageDataArray.length > 1) {
			imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
			displayImage(imageDataArray[imageDataIndex]);
		}	
	}

	/*************************************************************/
	/*
	 * Open an error dialog displaying the specified information.
	 */
	private void showErrorDialog(String operation, String filename, Throwable e) {
		MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
		String message = createMsg(bundle.getString("Error"), new String[] {operation, filename});
		String errorMessage = "";
		if (e != null) {
			if (e instanceof SWTException) {
				SWTException swte = (SWTException) e;
				errorMessage = swte.getMessage();
				if (swte.throwable != null) {
					errorMessage += ":\n" + swte.throwable.toString();
				}
			} else if (e instanceof SWTError) {
				SWTError swte = (SWTError) e;
				errorMessage = swte.getMessage();
				if (swte.throwable != null) {
					errorMessage += ":\n" + swte.throwable.toString();
				}
			} else {
				errorMessage = e.toString();
			}
		}
		box.setMessage(message + errorMessage);
		box.open();
	}


	private void displayImage(ImageData newImageData) {
		resetScaleCombos();
		if (incremental && incrementalThread != null) {
			// Tell the incremental thread to stop drawing.
			synchronized (this) {
				incrementalEvents = null;
			}
			
			// Wait until the incremental thread is done.
			while (incrementalThread.isAlive()) {
				if (!display.readAndDispatch()) display.sleep();
			}
		}
					
		// Dispose of the old image, if there was one.
		if (image != null) image.dispose();

		try {
			// Cache the new image and imageData.
			image = new Image(display, newImageData);
			imageData = newImageData;

		} catch (SWTException e) {
			showErrorDialog(bundle.getString("Creating_from") + " ", currentName, e);
			image = null;
			return;
		}

		// Update the widgets with the new image info.
		String string = createMsg(bundle.getString("Analyzer_on"), currentName);
		shell.setText(string);

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

		string = createMsg(bundle.getString("Animation_size_value"), 
		                      new Object[] {new Integer(loader.logicalScreenWidth),
								new Integer(loader.logicalScreenHeight)});
		screenSizeLabel.setText(string);

		string = createMsg(bundle.getString("Background_pixel_value"), pixelInfo(loader.backgroundPixel));
		backgroundPixelLabel.setText(string);

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

		if (imageData.palette.isDirect) {
			string = bundle.getString("Palette_direct");
		} else {
			string = createMsg(bundle.getString("Palette_value"), new Integer(imageData.palette.getRGBs().length));
		}
		paletteLabel.setText(string);

		string = createMsg(
				bundle.getString("Pixel_data_value"),
				new Object[] {
						new Integer(imageData.bytesPerLine),
						new Integer(imageData.scanlinePad),
						depthInfo(imageData.depth),
						(imageData.alphaData != null && imageData.alphaData.length > 0) ?
								bundle.getString("Scroll_for_alpha") : "" });
		dataLabel.setText(string);

		String data = dataHexDump(dataText.getLineDelimiter());
		dataText.setText(data);
		
		// bold the first column all the way down
		int index = 0;
		while((index = data.indexOf(':', index+1)) != -1) {
			int start = index - INDEX_DIGITS;
			int length = INDEX_DIGITS;
			if (Character.isLetter(data.charAt(index-1))) {
				start = index - ALPHA_CHARS;
				length = ALPHA_CHARS;
			}
			dataText.setStyleRange(new StyleRange(start, length, dataText.getForeground(), dataText.getBackground(), SWT.BOLD));
		}

		statusLabel.setText("");

		// Redraw both canvases.
		resetScrollBars();
		paletteCanvas.redraw();
		imageCanvas.redraw();
	}
	
	// Reset the scale combos to 1.
	private void resetScaleCombos() {
		xscale = 1; yscale = 1;
		scaleXCombo.select(scaleXCombo.indexOf("1"));
		scaleYCombo.select(scaleYCombo.indexOf("1"));
	}
	
	// Reset the scroll bars to 0.
	private void resetScrollBars() {
		if (image == null) return;
		ix = 0; iy = 0; py = 0;
		resizeScrollBars();
		imageCanvas.getHorizontalBar().setSelection(0);
		imageCanvas.getVerticalBar().setSelection(0);
		paletteCanvas.getVerticalBar().setSelection(0);
	}
	
	private void resizeScrollBars() {
		// Set the max and thumb for the image canvas scroll bars.
		ScrollBar horizontal = imageCanvas.getHorizontalBar();
		ScrollBar vertical = imageCanvas.getVerticalBar();
		Rectangle canvasBounds = imageCanvas.getClientArea();
		int width = Math.round(imageData.width * xscale);
		if (width > canvasBounds.width) {
			// The image is wider than the canvas.
			horizontal.setEnabled(true);
			horizontal.setMaximum(width);
			horizontal.setThumb(canvasBounds.width);
			horizontal.setPageIncrement(canvasBounds.width);
		} else {
			// The canvas is wider than the image.
			horizontal.setEnabled(false);
			if (ix != 0) {
				// Make sure the image is completely visible.
				ix = 0;
				imageCanvas.redraw();
			}
		}
		int height = Math.round(imageData.height * yscale);
		if (height > canvasBounds.height) {
			// The image is taller than the canvas.
			vertical.setEnabled(true);
			vertical.setMaximum(height);
			vertical.setThumb(canvasBounds.height);
			vertical.setPageIncrement(canvasBounds.height);
		} else {
			// The canvas is taller than the image.
			vertical.setEnabled(false);
			if (iy != 0) {
				// Make sure the image is completely visible.
				iy = 0;
				imageCanvas.redraw();
			}
		}

		// Set the max and thumb for the palette canvas scroll bar.
		vertical = paletteCanvas.getVerticalBar();
		if (imageData.palette.isDirect) {
			vertical.setEnabled(false);
		} else { // indexed palette
			canvasBounds = paletteCanvas.getClientArea();
			int paletteHeight = imageData.palette.getRGBs().length * 10 + 20; // 10 pixels each index + 20 for margins.
			vertical.setEnabled(true);
			vertical.setMaximum(paletteHeight);
			vertical.setThumb(canvasBounds.height);
			vertical.setPageIncrement(canvasBounds.height);
		}
	}

	/*
	 * Called when the image canvas' horizontal scrollbar is selected.
	 */
	private void scrollHorizontally(ScrollBar scrollBar) {
		if (image == null) return;
		Rectangle canvasBounds = imageCanvas.getClientArea();
		int width = Math.round(imageData.width * xscale);
		int height = Math.round(imageData.height * yscale);
		if (width > canvasBounds.width) {
			// Only scroll if the image is bigger than the canvas.
			int x = -scrollBar.getSelection();
			if (x + width < canvasBounds.width) {
				// Don't scroll past the end of the image.
				x = canvasBounds.width - width;
			}
			imageCanvas.scroll(x, iy, ix, iy, width, height, false);
			ix = x;
		}
	}
	
	/*
	 * Called when the image canvas' vertical scrollbar is selected.
	 */
	private void scrollVertically(ScrollBar scrollBar) {
		if (image == null) return;
		Rectangle canvasBounds = imageCanvas.getClientArea();
		int width = Math.round(imageData.width * xscale);
		int height = Math.round(imageData.height * yscale);
		if (height > canvasBounds.height) {
			// Only scroll if the image is bigger than the canvas.
			int y = -scrollBar.getSelection();
			if (y + height < canvasBounds.height) {
				// Don't scroll past the end of the image.
				y = canvasBounds.height - height;
			}
			imageCanvas.scroll(ix, y, ix, iy, width, height, false);
			iy = y;
		}
	}

	/*
	 * Called when the palette canvas' vertical scrollbar is selected.
	 */
	private void scrollPalette(ScrollBar scrollBar) {
		if (image == null) return;
		Rectangle canvasBounds = paletteCanvas.getClientArea();
		int paletteHeight = imageData.palette.getRGBs().length * 10 + 20;
		if (paletteHeight > canvasBounds.height) {
			// Only scroll if the palette is bigger than the canvas.
			int y = -scrollBar.getSelection();
			if (y + paletteHeight < canvasBounds.height) {
				// Don't scroll past the end of the palette.
				y = canvasBounds.height - paletteHeight;
			}
			paletteCanvas.scroll(0, y, 0, py, paletteWidth, paletteHeight, false);
			py = y;
		}
	}

	/*
	 * Return a String containing a line-by-line dump of
	 * the data in the current imageData. The lineDelimiter
	 * parameter must be a string of length 1 or 2.
	 */
	private String dataHexDump(String lineDelimiter) {
		final int MAX_DUMP = 1024 * 1024;
		if (image == null) return "";
		boolean truncated = false;
		char[] dump = null;
		byte[] alphas = imageData.alphaData;
		try {
			int length = imageData.height * (6 + 3 * imageData.bytesPerLine + lineDelimiter.length());
			if (alphas != null && alphas.length > 0) {
				length += imageData.height * (6 + 3 * imageData.width + lineDelimiter.length()) + 6 + lineDelimiter.length();
			}
			dump = new char[length];
		} catch (OutOfMemoryError e) {
			/* Too much data to dump - truncate. */
			dump = new char[MAX_DUMP];
			truncated = true;
		}
		int index = 0;
		try {
			for (int i = 0; i < imageData.data.length; i++) {
				if (i % imageData.bytesPerLine == 0) {
					int line = i / imageData.bytesPerLine;
					dump[index++] = Character.forDigit(line / 1000 % 10, 10);
					dump[index++] = Character.forDigit(line / 100 % 10, 10);
					dump[index++] = Character.forDigit(line / 10 % 10, 10);
					dump[index++] = Character.forDigit(line % 10, 10);
					dump[index++] = ':';
					dump[index++] = ' ';
				}
				byte b = imageData.data[i];
				dump[index++] = Character.forDigit((b & 0xF0) >> 4, 16);
				dump[index++] = Character.forDigit(b & 0x0F, 16);
				dump[index++] = ' ';
				if ((i + 1) % imageData.bytesPerLine == 0) {
					dump[index++] = lineDelimiter.charAt(0);
					if (lineDelimiter.length() > 1) {
						dump[index++] = lineDelimiter.charAt(1);
					}
				}
			}
			if (alphas != null && alphas.length > 0) {
				dump[index++] = lineDelimiter.charAt(0);
				if (lineDelimiter.length() > 1) {
					dump[index++] = lineDelimiter.charAt(1);
				}
				System.arraycopy(new char[]{'A','l','p','h','a',':'}, 0, dump, index, 6);
				index +=6;
				dump[index++] = lineDelimiter.charAt(0);
				if (lineDelimiter.length() > 1) {
					dump[index++] = lineDelimiter.charAt(1);
				}
				for (int i = 0; i < alphas.length; i++) {
					if (i % imageData.width == 0) {
						int line = i / imageData.width;
						dump[index++] = Character.forDigit(line / 1000 % 10, 10);
						dump[index++] = Character.forDigit(line / 100 % 10, 10);
						dump[index++] = Character.forDigit(line / 10 % 10, 10);
						dump[index++] = Character.forDigit(line % 10, 10);
						dump[index++] = ':';
						dump[index++] = ' ';
					}
					byte b = alphas[i];
					dump[index++] = Character.forDigit((b & 0xF0) >> 4, 16);
					dump[index++] = Character.forDigit(b & 0x0F, 16);
					dump[index++] = ' ';
					if ((i + 1) % imageData.width == 0) {
						dump[index++] = lineDelimiter.charAt(0);
						if (lineDelimiter.length() > 1) {
							dump[index++] = lineDelimiter.charAt(1);
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {}
		String result = "";
		try {
			result = new String(dump);
		} catch (OutOfMemoryError e) {
			/* Too much data to display in the text widget - truncate. */
			result = new String(dump, 0, MAX_DUMP);
			truncated = true;
		}
		if (truncated) result += "\n ...data dump truncated at " + MAX_DUMP + "bytes...";
		return result;
	}
	
	/*************************************************************/
	//For 06File
	private int determineFileType(String filename) {
		String ext = filename.substring(filename.lastIndexOf('.') + 1);
		if (ext.equalsIgnoreCase("bmp")) {
			return showBMPDialog();
		}
		if (ext.equalsIgnoreCase("gif"))
			return SWT.IMAGE_GIF;
		if (ext.equalsIgnoreCase("ico"))
			return SWT.IMAGE_ICO;
		if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jfif"))
			return SWT.IMAGE_JPEG;
		if (ext.equalsIgnoreCase("png"))
			return SWT.IMAGE_PNG;
		if (ext.equalsIgnoreCase("tif") || ext.equalsIgnoreCase("tiff"))
			return SWT.IMAGE_TIFF;
		return SWT.IMAGE_UNDEFINED;
	}
	
	/*
	 * Open a dialog asking the user for more information on the type of BMP file to save.
	 */
	private int showBMPDialog() {
		final int [] bmpType = new int[1];
		bmpType[0] = SWT.IMAGE_BMP;
		SelectionListener radioSelected = new SelectionAdapter () {
			public void widgetSelected (SelectionEvent event) {
				Button radio = (Button) event.widget;
				if (radio.getSelection()) bmpType[0] = ((Integer)radio.getData()).intValue();
			}
		};
		// need to externalize strings
		final Shell dialog = new Shell(shell, SWT.DIALOG_TRIM);

		dialog.setText(bundle.getString("Save_as_type"));
		dialog.setLayout(new GridLayout());
		
		Label label = new Label(dialog, SWT.NONE);
		label.setText(bundle.getString("Save_as_type_label"));
		
		Button radio = new Button(dialog, SWT.RADIO);
		radio.setText(bundle.getString("Save_as_type_no_compress"));
		radio.setSelection(true);
		radio.setData(new Integer(SWT.IMAGE_BMP));
		radio.addSelectionListener(radioSelected);

		radio = new Button(dialog, SWT.RADIO);
		radio.setText(bundle.getString("Save_as_type_rle_compress"));
		radio.setData(new Integer(SWT.IMAGE_BMP_RLE));
		radio.addSelectionListener(radioSelected);
		
		radio = new Button(dialog, SWT.RADIO);
		radio.setText(bundle.getString("Save_as_type_os2"));
		radio.setData(new Integer(SWT.IMAGE_OS2_BMP));
		radio.addSelectionListener(radioSelected);

		label = new Label(dialog, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button ok = new Button(dialog, SWT.PUSH);
		ok.setText(bundle.getString("OK"));
		GridData data = new GridData();
		data.horizontalAlignment = SWT.CENTER;
		data.widthHint = 75;
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialog.close();
			}
		});
		
		dialog.pack();
		dialog.open();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		return bmpType[0];
	}	
	/*************************************************************/
	//08Canvas
	/*
	 * Called when the mouse moves in the image canvas.
	 * Show the color of the image at the point under the mouse.
	 */
	private void showColorAt(int mx, int my) {
		int x = mx - imageData.x - ix;
		int y = my - imageData.y - iy;
		showColorForPixel(x, y);
	}

	
	/*
	 * Set the status label to show color information
	 * for the specified pixel in the image.
	 */
	private void showColorForPixel(int x, int y) {
		if (x >= 0 && x < imageData.width && y >= 0 && y < imageData.height) {
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
		} else {
			statusLabel.setText("");
		}
	}
	
	/*************************************************************/
	//10Canavs
	private void paintPalette(PaintEvent event) {
		GC gc = event.gc;
		gc.fillRectangle(paletteCanvas.getClientArea());
		if (imageData.palette.isDirect) {
			// For a direct palette, display the masks.
			int y = py + 10;
			int xTab = 50;
			gc.drawString("rMsk", 10, y, true);
			gc.drawString(toHex4ByteString(imageData.palette.redMask), xTab, y, true);
			gc.drawString("gMsk", 10, y+=12, true);
			gc.drawString(toHex4ByteString(imageData.palette.greenMask), xTab, y, true);
			gc.drawString("bMsk", 10, y+=12, true);
			gc.drawString(toHex4ByteString(imageData.palette.blueMask), xTab, y, true);
			gc.drawString("rShf", 10, y+=12, true);
			gc.drawString(Integer.toString(imageData.palette.redShift), xTab, y, true);
			gc.drawString("gShf", 10, y+=12, true);
			gc.drawString(Integer.toString(imageData.palette.greenShift), xTab, y, true);
			gc.drawString("bShf", 10, y+=12, true);
			gc.drawString(Integer.toString(imageData.palette.blueShift), xTab, y, true);
		} else {
			// For an indexed palette, display the palette colors and indices.
			RGB[] rgbs = imageData.palette.getRGBs();
			if (rgbs != null) {
				int xTab1 = 40, xTab2 = 100;
				for (int i = 0; i < rgbs.length; i++) {
					int y = (i+1) * 10 + py;
					gc.drawString(String.valueOf(i), 10, y, true);
					gc.drawString(toHexByteString(rgbs[i].red) + toHexByteString(rgbs[i].green) + toHexByteString(rgbs[i].blue), xTab1, y, true);
					Color color = new Color(display, rgbs[i]);
					gc.setBackground(color);
					gc.fillRectangle(xTab2, y+2, 10, 10);
					color.dispose();
				}
			}
		}
	}
	
	/*
	 * Return the specified byte value as a hex string,
	 * preserving leading 0's.
	 */
	private String toHexByteString(int i) {
		if (i <= 0x0f)
			return "0" + Integer.toHexString(i);
		return Integer.toHexString(i & 0xff);
	}

	/*
	 * Return the specified 4-byte value as a hex string,
	 * preserving leading 0's.
	 * (a bit 'brute force'... should probably use a loop...)
	 */
	private String toHex4ByteString(int i) {
		String hex = Integer.toHexString(i);
		if (hex.length() == 1)
			return "0000000" + hex;
		if (hex.length() == 2)
			return "000000" + hex;
		if (hex.length() == 3)
			return "00000" + hex;
		if (hex.length() == 4)
			return "0000" + hex;
		if (hex.length() == 5)
			return "000" + hex;
		if (hex.length() == 6)
			return "00" + hex;
		if (hex.length() == 7)
			return "0" + hex;
		return hex;
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
	//13Text
	/*
	 * Called when a mouse down or key press is detected
	 * in the data text. Show the color of the pixel at
	 * the caret position in the data text.
	 */
	private void showColorForData() {
		int delimiterLength = dataText.getLineDelimiter().length();
		int charactersPerLine = 6 + 3 * imageData.bytesPerLine + delimiterLength;
		int position = dataText.getCaretOffset();
		int y = position / charactersPerLine;
		if ((position - y * charactersPerLine) < 6 || ((y + 1) * charactersPerLine - position) <= delimiterLength) {
			statusLabel.setText("");
			return;
		}
		int dataPosition = position - 6 * (y + 1) - delimiterLength * y;
		int byteNumber = dataPosition / 3;
		int where = dataPosition - byteNumber * 3;
		int xByte = byteNumber % imageData.bytesPerLine;
		int x = -1;
		int depth = imageData.depth;
		if (depth == 1) { // 8 pixels per byte (can only show 3 of 8)
			if (where == 0) x = xByte * 8;
			if (where == 1) x = xByte * 8 + 3;
			if (where == 2) x = xByte * 8 + 7;
		}
		if (depth == 2) { // 4 pixels per byte (can only show 3 of 4)
			if (where == 0) x = xByte * 4;
			if (where == 1) x = xByte * 4 + 1;
			if (where == 2) x = xByte * 4 + 3;
		}
		if (depth == 4) { // 2 pixels per byte
			if (where == 0) x = xByte * 2;
			if (where == 1) x = xByte * 2;
			if (where == 2) x = xByte * 2 + 1;
		}
		if (depth == 8) { // 1 byte per pixel
			x = xByte;
		}
		if (depth == 16) { // 2 bytes per pixel
			x = xByte / 2;
		}
		if (depth == 24) { // 3 bytes per pixel
			x = xByte / 3;
		}
		if (depth == 32) { // 4 bytes per pixel
			x = xByte / 4;
		}
		if (x != -1) {
			showColorForPixel(x, y);
		} else {
			statusLabel.setText("");
		}
	}
	
	/*************************************************************/
	//05Combo
	
	
}
