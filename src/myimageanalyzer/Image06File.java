package myimageanalyzer;

import java.text.MessageFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Image06File extends Image05Combo{
	
	//Add file operation function
	static final String[] OPEN_FILTER_EXTENSIONS = new String[] {
		"*.bmp;*.gif;*.ico;*.jfif;*.jpeg;*.jpg;*.png;*.tif;*.tiff",
		"*.bmp", "*.gif", "*.ico", "*.jpg;*.jpeg;*.jfif", "*.png", "*.tif;*.tiff" };
	static final String[] OPEN_FILTER_NAMES = new String[] {
		bundle.getString("All_images") + " (bmp, gif, ico, jfif, jpeg, jpg, png, tif, tiff)",
		"BMP (*.bmp)", "GIF (*.gif)", "ICO (*.ico)", "JPEG (*.jpg, *.jpeg, *.jfif)",
		"PNG (*.png)", "TIFF (*.tif, *.tiff)" };
	static final String[] SAVE_FILTER_EXTENSIONS = new String[] {
		"*.bmp", "*.bmp", "*.gif", "*.ico", "*.jpg", "*.png", "*.tif", "*.bmp" };
	static final String[] SAVE_FILTER_NAMES = new String[] {
		"Uncompressed BMP (*.bmp)", "RLE Compressed BMP (*.bmp)", "GIF (*.gif)",
		"ICO (*.ico)", "JPEG (*.jpg)", "PNG (*.png)",
		"TIFF (*.tif)", "OS/2 BMP (*.bmp)" };
	
	String lastPath; // used to seed the file dialog
	String currentName; // the current image file or URL name
	String fileName; // the current image file
	int fileType;
	

	public Image06File() {

	}
	/*************************************************************/	
	//Static functions area
	
	static String createMsg(String msg, Object[] args) {
		MessageFormat formatter = new MessageFormat(msg);
		return formatter.format(args);
	}
	
	static String createMsg(String msg, Object arg) {
		MessageFormat formatter = new MessageFormat(msg);
		return formatter.format(new Object[]{arg});
	}
	
	/*
	 * Return a String describing how to analyze the bytes
	 * in the hex dump.
	 */
	static String depthInfo(int depth) {
		Object[] args = {new Integer(depth), ""};
		switch (depth) {
			case 1:
				args[1] = createMsg(bundle.getString("Multi_pixels"), 
				                    new Object[] {new Integer(8), " [01234567]"});
				break;
			case 2:
				args[1] = createMsg(bundle.getString("Multi_pixels"),
				                    new Object[] {new Integer(4), "[00112233]"});
				break;
			case 4:
				args[1] = createMsg(bundle.getString("Multi_pixels"),
				                    new Object[] {new Integer(2), "[00001111]"});
				break;
			case 8:
				args[1] = bundle.getString("One_byte");
				break;
			case 16:
				args[1] = createMsg(bundle.getString("Multi_bytes"), new Integer(2));
				break;
			case 24:
				args[1] = createMsg(bundle.getString("Multi_bytes"), new Integer(3));
				break;
			case 32:
				args[1] = createMsg(bundle.getString("Multi_bytes"), new Integer(4));
				break;
			default:
				args[1] = bundle.getString("Unsupported_lc");
		}
		return createMsg(bundle.getString("Depth_info"), args);
	}

	/*************************************************************/
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
	
	/*
	 * Return the specified file's image type, based on its extension.
	 * Note that this is not a very robust way to determine image type,
	 * and it is only to be used in the absence of any better method.
	 */
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
	
	private void dealJPGFile(){
		imageTypeCombo.select(0);
		compressionCombo.setEnabled(true);
		compressionRatioLabel.setEnabled(true);
		if (compressionCombo.getItemCount() == 100) return;
		compressionCombo.removeAll();
		for (int i = 0; i < 100; i++) {
			compressionCombo.add(String.valueOf(i + 1));
		}
		compressionCombo.select(compressionCombo.indexOf("75"));
	}
	
	private void dealPNGFile(){
		imageTypeCombo.select(1);
		compressionCombo.setEnabled(true);
		compressionRatioLabel.setEnabled(true);
		if (compressionCombo.getItemCount() == 10) return;
		compressionCombo.removeAll();
		for (int i = 0; i < 4; i++) {
			compressionCombo.add(String.valueOf(i));
		}
		compressionCombo.select(0);
	}
	
	private void showFileType(String filename) {
		String ext = filename.substring(filename.lastIndexOf('.') + 1);
		if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jfif")) {
			dealJPGFile();
			return;
		}
		if (ext.equalsIgnoreCase("png")) {
			dealPNGFile();
			return;
		} 
		if (ext.equalsIgnoreCase("bmp")) {
			imageTypeCombo.select(5);
		}
		if (ext.equalsIgnoreCase("gif")) {
			imageTypeCombo.select(2);
		}
		if (ext.equalsIgnoreCase("ico")) {
			imageTypeCombo.select(3);
		}
		if (ext.equalsIgnoreCase("tif") || ext.equalsIgnoreCase("tiff")) {
			imageTypeCombo.select(4);
		}
		compressionCombo.setEnabled(false);
		compressionRatioLabel.setEnabled(false);	
	}
	
	private void selectFileOpenDialog(){
		// Get the user to choose an image file.
		FileDialog fileChooser = new FileDialog(shell, SWT.OPEN);
		if (lastPath != null) 
			fileChooser.setFilterPath(lastPath);
		fileChooser.setFilterExtensions(OPEN_FILTER_EXTENSIONS);
		fileChooser.setFilterNames(OPEN_FILTER_NAMES);
		fileName = fileChooser.open();
		lastPath = fileChooser.getFilterPath();
	}
	
	private void selectFileSaveDialog(){
		// Get the user to choose a file name and type to save.
		FileDialog fileChooser = new FileDialog(shell, SWT.SAVE);
		fileChooser.setFilterPath(lastPath);
		if (fileName != null) fileChooser.setFileName(fileName);
		fileChooser.setFilterExtensions(SAVE_FILTER_EXTENSIONS);
		fileChooser.setFilterNames(SAVE_FILTER_NAMES);
		fileName = fileChooser.open();
		lastPath = fileChooser.getFilterPath();
		fileType = fileChooser.getFilterIndex();
	}

	private void selectFileSaveAsDialog(){
		// Get the user to choose a file name and type to save.
		FileDialog fileChooser = new FileDialog(shell, SWT.SAVE);
		fileChooser.setFilterPath(lastPath);
		if (fileName != null) {
			String name = fileName;
			int nameStart = name.lastIndexOf(java.io.File.separatorChar);
			if (nameStart > -1) {
				name = name.substring(nameStart + 1);
			}
			fileChooser.setFileName(name.substring(0, name.indexOf(".")) + "." + imageTypeCombo.getText().toLowerCase());
		}
		fileChooser.setFilterExtensions(SAVE_FILTER_EXTENSIONS);
		fileChooser.setFilterNames(SAVE_FILTER_NAMES);
		switch (imageTypeCombo.getSelectionIndex()) {
		case 0:
			fileChooser.setFilterIndex(4);
			break;
		case 1:
			fileChooser.setFilterIndex(5);
			break;
		case 2:
			fileChooser.setFilterIndex(2);
			break;
		case 3:
			fileChooser.setFilterIndex(3);
			break;
		case 4:
			fileChooser.setFilterIndex(6);
			break;
		case 5:
			fileChooser.setFilterIndex(0);
			break;
		}
		fileName = fileChooser.open();
		lastPath = fileChooser.getFilterPath();
		fileType = fileChooser.getFilterIndex();
	}
	/*************************************************************/

	@Override
	public void menuFileOpen(){
		animate = false; // stop any animation in progress
		selectFileOpenDialog();
		
		if (fileName == null)
			return;
		showFileType(fileName);
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		shell.setCursor(waitCursor);
	}
	
	@Override
	public void menuFileOpenURL(){
		super.menuFileOpenURL();
		debugInfo("File: Open URL! \n");
	}
	
	@Override 
	public void menuFileReopen() {
		if (currentName == null) return;
		animate = false; // stop any animation in progress
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		shell.setCursor(waitCursor);
	}
	
	@Override 
	public void menuFileLoad(){
		animate = false; // stop any animation in progress
		selectFileOpenDialog();
	}
	
	@Override
	public void menuFileSave(){
		animate = false; // stop any animation in progress
		selectFileSaveDialog();
	}
	
	@Override
	public void menuFileSaveAs(){
		animate = false; // stop any animation in progress
		selectFileSaveAsDialog();
		if (fileName == null)
			return;

		// Figure out what file type the user wants saved.
		if (fileType == -1) {
			/* The platform file dialog does not support user-selectable file filters.
			 * Determine the desired type by looking at the file extension. 
			 */
			fileType = determineFileType(fileName);
			if (fileType == SWT.IMAGE_UNDEFINED) {
				MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
				box.setMessage(createMsg(bundle.getString("Unknown_extension"), 
					fileName.substring(fileName.lastIndexOf('.') + 1)));
				box.open();
				return;
			}
		}
		
		if (new java.io.File(fileName).exists()) {
			MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			box.setMessage(createMsg(bundle.getString("Overwrite"), fileName));
			if (box.open() == SWT.CANCEL)
				return;
		}
		
		Cursor waitCursor = display.getSystemCursor(SWT.CURSOR_WAIT);
		shell.setCursor(waitCursor);
	}

	@Override
	public void menuFileSaveMaskAs() {
		super.menuFileSaveMaskAs();
		debugInfo("File: Save Mask As! \n");
	}
	
	@Override
	public void menuFilePrint(){
		super.menuFilePrint();
		debugInfo("File: Print! \n");
	}
	
	@Override
	public void menuAlphaCompose(int alpha){
		super.menuAlphaCompose(alpha);
		debugInfo("File: Alpha! " + alpha + "\n");
	}
	/*************************************************************/
}
