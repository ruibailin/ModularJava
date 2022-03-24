package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ScrollBar;

public class Image10Canvas extends Image09Label{
	//Add Canvas to display  palette
	Canvas paletteCanvas;
	int paletteWidth = 140; // recalculated and used as a width hint
	int py = 0; // used to scroll the palette
	
	public Image10Canvas() {

	}

	/*************************************************************/
	//Override functions area
	
	
	/*************************************************************/
	public void resPaletteCanvasPaintEvent(PaintEvent event){
		debugInfo("Canvas10: respaletteCanvasPaintEvent \n");
//		if (image != null)
//			paintPalette(event);
	}
	
	public void resPaletteCanvasScrollVertically(SelectionEvent event){
		debugInfo("Canvas10: respaletteCanvasScrollVertically \n");
	}
	
	/*************************************************************/
	private void iniPaletteCanvas(){
		GridData gridData;
		// Canvas to show the image's palette.
		paletteCanvas = new Canvas(shell, SWT.BORDER | SWT.V_SCROLL | SWT.NO_REDRAW_RESIZE);
		paletteCanvas.setFont(fixedWidthFont);
		paletteCanvas.getVerticalBar().setVisible(true);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		GC gc = new GC(paletteLabel);
		paletteWidth = gc.stringExtent(bundle.getString("Max_length_string")).x;
		gc.dispose();
		gridData.widthHint = paletteWidth;
		gridData.heightHint = 16 * 11; // show at least 16 colors
		paletteCanvas.setLayoutData(gridData);
		paletteCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {
				resPaletteCanvasPaintEvent(event);
			}
		});
	}
	
	private void iniPaletteCanvasScrollBar(){
		ScrollBar vertical;
		// Set up scroll bar for the palette's canvas.
		vertical = paletteCanvas.getVerticalBar();
		vertical.setVisible(true);
		vertical.setMinimum(0);
		vertical.setIncrement(10);
		vertical.setEnabled(false);
		vertical.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				resPaletteCanvasScrollVertically(event);
			}
		});
	}
	
	/*************************************************************/
	public void initializeCanvas2(){
		iniPaletteCanvas();
		iniPaletteCanvasScrollBar();		
	}
	
	public void initializeCanvasPalette(){
		iniPaletteCanvas();
		iniPaletteCanvasScrollBar();		
	}
}
