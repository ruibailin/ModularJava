package mytexteditor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Editor02StyledText extends Editor01StatusBar{

	StyledText styledText;
	
	String link;

	public Editor02StyledText() {

	}
	/*************************************************************/
	public void initStyledText() {
		
		styledText = new StyledText(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	}
	
	public void updateStatusBarContent() {
		offset = styledText.getCaretOffset();
		index = styledText.getLineAtOffset(offset);
	}
	
	public void resizeStyledText(Point cSize){
		Rectangle rect = shell.getClientArea();
		Point sSize = statusBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		styledText.setBounds(rect.x, rect.y + cSize.y, rect.width, rect.height - cSize.y - (sSize.y + 2 * statusMargin));
	}
	
	public void displayStyledText(Point cSize)
	{
		resizeStyledText(cSize);		//reserve position for tool bar
	}
	/*************************************************************/
	static final int MARGIN = 5;

	public void addControl(Control control) {
		int offset = styledText.getCaretOffset();
		styledText.replaceTextRange(offset, 0, "\uFFFC"); //$NON-NLS-1$
		StyleRange style = new StyleRange();
		Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int ascent = 2 * size.y / 3;
		int descent = size.y - ascent;
		style.metrics = new GlyphMetrics(ascent + MARGIN, descent + MARGIN, size.x + 2 * MARGIN);
		style.data = control;
		int[] ranges = {offset, 1};
		StyleRange[] styles = {style};
		styledText.setStyleRanges(0,0, ranges, styles);
		control.setSize(size);
	}
	/*************************************************************/
	public void addImage(Image image) {
		int offset = styledText.getCaretOffset();
		styledText.replaceTextRange(offset, 0, "\uFFFC"); //$NON-NLS-1$
		StyleRange style = new StyleRange();
		Rectangle rect = image.getBounds();
		style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
		style.data = image;
		int[] ranges = {offset, 1};
		StyleRange[] styles = {style};
		styledText.setStyleRanges(0,0, ranges, styles);
	}
	
	/*************************************************************/
	static final int BULLET_WIDTH = 40;
	void setBullet(int type) {
		Point selection = styledText.getSelection();
		int lineStart = styledText.getLineAtOffset(selection.x);
		int lineEnd = styledText.getLineAtOffset(selection.y);
		StyleRange styleRange = new StyleRange();
		styleRange.metrics = new GlyphMetrics(0, 0, BULLET_WIDTH);
		Bullet bullet = new Bullet(type, styleRange);
		bullet.text = ".";
		for (int lineIndex = lineStart; lineIndex <= lineEnd; lineIndex++) {
			Bullet oldBullet = styledText.getLineBullet(lineIndex);
			styledText.setLineBullet(lineIndex, 1, oldBullet != null ? null : bullet);
		}
	}
	
	/*************************************************************/
	String fileName = null;
	private String openFile(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuffer buffer = new StringBuffer();
		String line;
		String lineDelimiter = styledText.getLineDelimiter();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append(lineDelimiter);
		}
		return buffer.toString();
	}
	
	private void showError (String title, String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.CLOSE);
		messageBox.setText(title);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	public void responseMenuFileOpen() {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterNames(new String [] {getResourceString("Text_Documents")}); //$NON-NLS-1$
		dialog.setFilterExtensions (new String [] {"*.txt"}); //$NON-NLS-1$
        String name = dialog.open();
        if (name == null)  return;
        fileName = name;
        FileInputStream file = null;
        try {
        	file = new FileInputStream(name);
        	styledText.setText(openFile(file));
        } catch (IOException e) {
        	showError(getResourceString("Error"), e.getMessage()); //$NON-NLS-1$
        } finally {
        	try {
        		if (file != null) file.close();
        	} catch (IOException e) {
        		showError(getResourceString("Error"), e.getMessage()); //$NON-NLS-1$
        	}
        }
	}
	/*************************************************************/
	private void saveFile() {
		if (fileName != null) {
			FileWriter file = null;
			try {
				file = new FileWriter(fileName);
		       	file.write(styledText.getText());
		       	file.close();
			} catch (IOException e) {
	        	showError(getResourceString("Error"), e.getMessage());
	        } finally {
	        	try {
	        		if (file != null) file.close();
	        	} catch (IOException e) {
	        		showError(getResourceString("Error"), e.getMessage());
	        	}
	        }
		}
	}
	
	public void responseMenuFileSave() {
		saveFile();
	}
	
	/*************************************************************/
	public void responseMenuFileSaveAs() {
		FileDialog dialog = new FileDialog (shell, SWT.SAVE);
		dialog.setFilterNames(new String [] {getResourceString("Text_Documents")}); //$NON-NLS-1$ 
		dialog.setFilterExtensions(new String [] {"*.txt"}); //$NON-NLS-1$
		if (fileName != null) dialog.setFileName(fileName);
		String name = dialog.open(); 
		if (name != null) {
			fileName = name;
			saveFile();
		}
	}
	/*************************************************************/
	boolean modified = false;
	public void responseMenuFileExit() {
		if(!modified)
			saveFile();
		shell.dispose();
	}
	/*************************************************************/
	public void responseMenuEditCut(){
		styledText.cut();
	}
	
	public void responseMenuEditCopy(){
		styledText.copy();
	}
	
	public void responseMenuEditPaste(){
		styledText.paste();
	}
	
	public void responseMenuEditSelectAll(){
		styledText.selectAll();
	}
	/*************************************************************/
	public void responseMenuToolAlignLeft(){
		styledText.setAlignment(SWT.LEFT);
	}
	
	public void responseMenuToolAlignCenter(){
		styledText.setAlignment(SWT.CENTER);
	}
	
	public void responseMenuToolAlignRight(){
		styledText.setAlignment(SWT.RIGHT);
	}
	/*************************************************************/
	public void responseMenuToolOrientLeft(){
		styledText.setOrientation(SWT.LEFT_TO_RIGHT);
	}
	
	public void responseMenuToolOrientRight(){
		styledText.setOrientation(SWT.RIGHT_TO_LEFT);
	}
	/*************************************************************/
	public void responseToolBarAlignToggle(){
		styledText.invokeAction(ST.TOGGLE_BLOCKSELECTION);
	}
	
	public void responseToolBarAlignLeft(){
		Point selection = styledText.getSelection();
		int lineStart = styledText.getLineAtOffset(selection.x);
		int lineEnd = styledText.getLineAtOffset(selection.y);
		styledText.setLineAlignment(lineStart, lineEnd - lineStart + 1,	SWT.LEFT);
	}
	
	public void responseToolBarAlignCenter(){
		Point selection = styledText.getSelection();
		int lineStart = styledText.getLineAtOffset(selection.x);
		int lineEnd = styledText.getLineAtOffset(selection.y);
		styledText.setLineAlignment(lineStart, lineEnd - lineStart + 1, SWT.CENTER);
	}
	
	public void responseToolBarAlignRight(){
		Point selection = styledText.getSelection();
		int lineStart = styledText.getLineAtOffset(selection.x);
		int lineEnd = styledText.getLineAtOffset(selection.y);
		styledText.setLineAlignment(lineStart, lineEnd - lineStart + 1,	SWT.RIGHT);
	}
	
	public void responseToolBarAlignJustify(boolean select){
		Point selection = styledText.getSelection();
		int lineStart = styledText.getLineAtOffset(selection.x);
		int lineEnd = styledText.getLineAtOffset(selection.y);
		styledText.setLineJustify(lineStart, lineEnd - lineStart + 1, select);
	}

	public void responseInsertImageSelect(){
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		String fileName = fileDialog.open();
		if (fileName != null) {
			try {
				Image image = new Image(display, fileName);
				addImage(image);
			} catch (Exception e) {
				showError(getResourceString("Bad_image"), e.getMessage()); //$NON-NLS-1$
			}
		}
	}
	/*************************************************************/
	@Override
	public void responseControlEvent(ControlEvent event) {
		Point cSize = new Point(0,0);
		resizeStyledText(cSize);
		updateStatusBarContent();
		updateStatusBar();
		resizeStatusBar();
	}
	/*************************************************************/
	/*
	@Override
	public void handleControlEvent() {	
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent event) {
				responseControlEvent(event);
			}
		});
	}
	*/
	/*************************************************************/
	public void responseCaretEvent(CaretEvent event){
		updateStatusBarContent();
		updateStatusBar();
	}


	public void responseMouseUp(Event event){
		if (link != null) {
			int offset = styledText.getCaretOffset();
			StyleRange range = offset > 0 ? styledText.getStyleRangeAtOffset(offset-1) : null;
			if (range != null) {
				if (link == range.data) {
					Shell dialog = new Shell(shell);
					dialog.setLayout(new FillLayout());
					dialog.setText(getResourceString("Browser")); //$NON-NLS-1$
					Browser browser = new Browser(dialog, SWT.MOZILLA);
					browser.setUrl(link);
					dialog.open();
				}
			}
		}
	}
	
	public void responseKeyDown(Event event){
		if (event.keyCode == SWT.INSERT) {
			insert = !insert;
		}
		updateStatusBarContent();
		updateStatusBar();
	}

	int newCharCount, start;
	StyleRange[] selectedRanges;
	void responseVerifyEvent(VerifyEvent event) {
		start = event.start;
		newCharCount = event.text.length();
		int replaceCharCount = event.end - start;

		// mark styles to be disposed
		selectedRanges = styledText.getStyleRanges(start, replaceCharCount, false);
		
		System.out.print("Veryfy: start:"+start+"  "+ "newCharCount:"+newCharCount);
	}
	
	void responseModifyEvent(ModifyEvent event){
		if (newCharCount > 0 && start >= 0) {
			StyleRange style = new StyleRange();
			int[] ranges = {start, newCharCount};
			StyleRange[] styles = {style}; 
			styledText.setStyleRanges(start, newCharCount, ranges, styles);
			System.out.print("Modify: start:"+start+"  "+ "newCharCount:"+newCharCount);
		}
	}
	
	void responsePaintObjectEvent(PaintObjectEvent event){
		GC gc = event.gc;
		StyleRange style = event.style;
		Object data = style.data;
		if (data instanceof Image) {
			Image image = (Image)data;
			int x = event.x;
			int y = event.y + event.ascent - style.metrics.ascent;
			gc.drawImage(image, x, y);
		}
		if (data instanceof Control) {
			Control control = (Control)data;
			Point pt = control.getSize();
			int x = event.x + MARGIN;
			int y = event.y + event.ascent - 2 * pt.y / 3;
			control.setLocation(x, y);
		}
	}
	
	void responseDisposeEvent(Event event){
		StyleRange[] styles = styledText.getStyleRanges(0, styledText.getCharCount(), false);
		for (int i = 0; i < styles.length; i++) {
			Object data = styles[i].data;
			if (data != null) {
				if (data instanceof Image) ((Image)data).dispose();
				if (data instanceof Control) ((Control)data).dispose();
			}
		}
	}
	
	/*************************************************************/
	public void handleCaretEvent(){
		styledText.addCaretListener(new CaretListener() {
			public void caretMoved(CaretEvent event) {
				responseCaretEvent(event);
			}
		});
	}

	public void handleMouseUp(){
		styledText.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				responseMouseUp(event);
			}
		});
	}
	
	public void handleKeyDown(){
		styledText.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				responseKeyDown(event);
			}
		});		
	}

	void handleVerifyEvent(){
		styledText.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent event) {
				responseVerifyEvent(event);
			}
		});
	}
	
	void handleModifyEvent(){
		styledText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent event) {
				responseModifyEvent(event);
			}
		});
	}
	
	void handlePaintObjectEvent(){
		styledText.addPaintObjectListener(new PaintObjectListener() {
			public void paintObject(PaintObjectEvent event) {
				responsePaintObjectEvent(event);
			}
		});
	}
	
	void handleDisposeEvent(){
		styledText.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				responseDisposeEvent(event);
			}
		});
	}
	
	/*************************************************************/
	@Override
	public void handleSystemEvent(){
		handleControlEvent();
		
		handleCaretEvent();
		handleMouseUp();
		handleKeyDown();
		handleVerifyEvent();
		handleModifyEvent();
		handlePaintObjectEvent();
		handleDisposeEvent();
	}
	/*************************************************************/
}
	

