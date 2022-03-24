package myimageanalyzer;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ScrollBar;

public class Image08Canvas extends Image07Label {
	//Add Canvas to display image and pallete
	Canvas imageCanvas;
	
	Cursor crossCursor;
	GC imageCanvasGC;
	PrinterData printerData;

	int ix = 0, iy = 0; // used to scroll the image and palette
	
	ImageLoader loader; // the loader for the current image file
	ImageData[] imageDataArray; // all image data read from the current file
	int imageDataIndex; // the index of the current image data
	ImageData imageData; // the currently-displayed image data
	Image image; // the currently-displayed image
	Vector<ImageLoaderEvent>  incrementalEvents; // incremental image events
	long loadTime = 0; // the time it took to load the current image
	
	public Image08Canvas() {

	}
	
	/*************************************************************/	
	//Static functions area
	/*
	 * Return a String describing the specified image file type.
	 */
	public String fileTypeString(int filetype) {
		if (filetype == SWT.IMAGE_BMP)
			return "BMP";
		if (filetype == SWT.IMAGE_BMP_RLE)
			return "RLE" + imageData.depth + " BMP";
		if (filetype == SWT.IMAGE_OS2_BMP)
			return "OS/2 BMP";
		if (filetype == SWT.IMAGE_GIF)
			return "GIF";
		if (filetype == SWT.IMAGE_ICO)
			return "ICO";
		if (filetype == SWT.IMAGE_JPEG)
			return "JPEG";
		if (filetype == SWT.IMAGE_PNG)
			return "PNG";
		if (filetype == SWT.IMAGE_TIFF)
			return "TIFF";
		return bundle.getString("Unknown_ac");
	}
	/*************************************************************/
	//Override functions area
	@Override
	public void runThreadIncrementalProc(){
		// Draw the first ImageData increment.
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
//						yield();
					}
				}
			}
		}
		display.wake();
	}

	public void resImageLoaderEvent(ImageLoaderEvent event){
		// Synchronize so that we do not try to add while
		// the incremental drawing thread is removing.
		synchronized (this) {
			incrementalEvents.addElement(event);
		}
	}
	
	public void resImageCanvasPaintEvent(PaintEvent event){
		debugInfo("Canvas08:resImageCanvasPaintEvent \n");
		
		if (image == null) {
			Rectangle bounds = imageCanvas.getBounds();
			event.gc.fillRectangle(0, 0, bounds.width, bounds.height);
		} else {
			debugInfo("Canvas08:resImageCanvasPaintEvent \n");
			paintImage(event);
		}
	}
	
	public void resImageCanvasMouseEvent(MouseEvent event){
		debugInfo("Canvas08:resImageCanvasMouseEvent \n");
	}
	
	public void resImageCanvasScrollHorizontally(SelectionEvent event){
		debugInfo("Canvas08:resImageCanvasScrollHorizontally \n");
	}
	
	public void resImageCanvasScrollVertically(SelectionEvent event){
		debugInfo("Canvas08:resImageCanvasScrollVertically \n");
	}
	
		
	/*************************************************************/
	private void paintImage(PaintEvent event){
		GC gc = event.gc;
		Image paintImage = image;
		
		/* If the user wants to see the transparent pixel in its actual color,
		 * then temporarily turn off transparency.
		 */
		int transparentPixel = imageData.transparentPixel;
		if (transparentPixel != -1 && !transparent) {
			imageData.transparentPixel = -1;
			paintImage = new Image(display, imageData);
		}
		
		/* Scale the image when drawing, using the user's selected scaling factor. */
		int w = Math.round(imageData.width * xscale);
		int h = Math.round(imageData.height * yscale);
		
		/* If any of the background is visible, fill it with the background color. */
		Rectangle bounds = imageCanvas.getBounds();
		if (imageData.getTransparencyType() != SWT.TRANSPARENCY_NONE) {
			/* If there is any transparency at all, fill the whole background. */
			gc.fillRectangle(0, 0, bounds.width, bounds.height);
		} else {
			/* Otherwise, just fill in the backwards L. */
			if (ix + w < bounds.width) gc.fillRectangle(ix + w, 0, bounds.width - (ix + w), bounds.height);
			if (iy + h < bounds.height) gc.fillRectangle(0, iy + h, ix + w, bounds.height - (iy + h));
		}
		
		/* Draw the image */
		gc.drawImage(
			paintImage,
			0,
			0,
			imageData.width,
			imageData.height,
			ix + imageData.x,
			iy + imageData.y,
			w,
			h);
		
		/* If there is a mask and the user wants to see it, draw it. */
		if (showMask && (imageData.getTransparencyType() != SWT.TRANSPARENCY_NONE)) {
			ImageData maskImageData = imageData.getTransparencyMask();
			Image maskImage = new Image(display, maskImageData);
			gc.drawImage(
				maskImage,
				0,
				0,
				imageData.width,
				imageData.height,
				w + 10 + ix + imageData.x,
				iy + imageData.y,
				w,
				h);
			maskImage.dispose();
		}
		
		/* If transparency was temporarily disabled, restore it. */
		if (transparentPixel != -1 && !transparent) {
			imageData.transparentPixel = transparentPixel;
			paintImage.dispose();
		}
	}
		
	/*************************************************************/
	private void iniImageCanvas(){
		GridData gridData;
		// Canvas to show the image.
		imageCanvas = new Canvas(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		imageCanvas.setBackground(whiteColor);
		imageCanvas.setCursor(crossCursor);
		gridData = new GridData();
		gridData.verticalSpan = 15;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		imageCanvas.setLayoutData(gridData);
		imageCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {
				resImageCanvasPaintEvent(event);
			}
		});
		imageCanvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				resImageCanvasMouseEvent(event);
			}
		});
	}
	
	private void iniImageCanvasScrollBar(){
		// Set up the image canvas scroll bars.
		ScrollBar horizontal = imageCanvas.getHorizontalBar();
		horizontal.setVisible(true);
		horizontal.setMinimum(0);
		horizontal.setEnabled(false);
		horizontal.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resImageCanvasScrollHorizontally(event);
			}
		});
		ScrollBar vertical = imageCanvas.getVerticalBar();
		vertical.setVisible(true);
		vertical.setMinimum(0);
		vertical.setEnabled(false);
		vertical.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resImageCanvasScrollVertically(event);
			}
		});
	}

	/*************************************************************/
	public void initializeCanvas(){
		iniImageCanvas();
		iniImageCanvasScrollBar();
	}
	
	public void initializeCanvasImage(){
		iniImageCanvas();
		iniImageCanvasScrollBar();
	}
	/*************************************************************/
	
}
