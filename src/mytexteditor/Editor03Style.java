package mytexteditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Editor03Style extends Editor02StyledText{
	int styleState;
	
	Font curFont, textFont;
	static final int FONT = 1 << 6;
	static final int BOLD = SWT.BOLD;
	static final int ITALIC = SWT.ITALIC;
	static final int FONT_STYLE = BOLD | ITALIC;

	Color textForeground, textBackground, strikeoutColor;
	static final int STRIKEOUT = 1 << 3;
	static final int FOREGROUND = 1 << 4;
	static final int BACKGROUND = 1 << 5;

	static final int BASELINE_UP = 1 << 7;
	static final int BASELINE_DOWN = 1 << 8;
	
	Color  underlineColor;
	static final int UNDERLINE_SINGLE = 1 << 9;
	static final int UNDERLINE_DOUBLE = 1 << 10;
	static final int UNDERLINE_ERROR = 1 << 11;
	static final int UNDERLINE_SQUIGGLE = 1 << 12;
	static final int UNDERLINE_LINK = 1 << 13;
	static final int UNDERLINE = UNDERLINE_SINGLE | UNDERLINE_DOUBLE | UNDERLINE_SQUIGGLE | UNDERLINE_ERROR | UNDERLINE_LINK;

	Color borderColor;
	static final int BORDER_SOLID = 1 << 23;
	static final int BORDER_DASH = 1 << 24;
	static final int BORDER_DOT = 1 << 25;
	static final int BORDER = BORDER_SOLID | BORDER_DASH | BORDER_DOT;
	
	
	public Editor03Style() {
		textFont = null;
		curFont = null;
		
		textForeground = null;
		textBackground = null;
		strikeoutColor = null;
		underlineColor = null;
		borderColor = null;
	}
	

	//Will be override in subclass EditorMenu
	public void updateMenuItemUnderline(){
//		underlineSingleItem.setSelection((styleState & UNDERLINE_SINGLE) != 0);
//		underlineDoubleItem.setSelection((styleState & UNDERLINE_DOUBLE) != 0);
//		underlineErrorItem.setSelection((styleState & UNDERLINE_ERROR) != 0);
//		underlineSquiggleItem.setSelection((styleState & UNDERLINE_SQUIGGLE) != 0);
	}
	
	//Will be override in subclass EditorMenu
	public void updateMenuItemBorder(){
//		borderSolidItem.setSelection((styleState & BORDER_SOLID) != 0);
//		borderDashItem.setSelection((styleState & BORDER_DASH) != 0);
//		borderDotItem.setSelection((styleState & BORDER_DOT) != 0);
	}
	
	//Will be override in subclass EditorToolBar
	public	void updateToolBar(){
		updateToolBarArea();
	}
	
	//Will be override in subclass EditorToolBar
	public void updateToolBarControl(boolean  bold,boolean italic){
		
	}
	/*************************************************************/
	public	void freeStyleRanges(StyleRange[] ranges) {
		StyleRange[] allRanges = styledText.getStyleRanges(0, styledText.getCharCount(), false);
		for (int i = 0; i < ranges.length; i++) {
			StyleRange style = ranges[i];
			boolean disposeFg = true, disposeBg = true;
			boolean disposeStrike= true, disposeUnder= true;
			boolean disposeBorder = true, disposeFont = true;

			for (int j = 0; j < allRanges.length; j++) {
				StyleRange s = allRanges[j];
				if (disposeFont && style.font == s.font) disposeFont = false;
				if (disposeFg && style.foreground == s.foreground) disposeFg = false;
				if (disposeBg && style.background == s.background) disposeBg = false;
				if (disposeStrike && style.strikeoutColor == s.strikeoutColor) disposeStrike = false;
				if (disposeUnder && style.underlineColor == s.underlineColor) disposeUnder = false;
				if (disposeBorder && style.borderColor == s.borderColor) disposeBorder =  false;
			}
			if (disposeFont && style.font != textFont && style.font != null)  style.font.dispose();
			if (disposeFg && style.foreground != textForeground && style.foreground != null) style.foreground.dispose();
			if (disposeBg && style.background != textBackground && style.background != null) style.background.dispose();
			if (disposeStrike && style.strikeoutColor != strikeoutColor && style.strikeoutColor != null) style.strikeoutColor.dispose();
			if (disposeUnder && style.underlineColor != underlineColor && style.underlineColor != null) style.underlineColor.dispose();
			if (disposeBorder && style.borderColor != borderColor && style.borderColor != null) style.borderColor.dispose();
			
			Object data = style.data;
			if (data != null) {
				if (data instanceof Image) ((Image)data).dispose();
				if (data instanceof Control) ((Control)data).dispose();
			}
		}
	}

	public	void freeStyleResource(Resource resource) {
		if (resource == null) return;
		StyleRange[] styles = styledText.getStyleRanges(0, styledText.getCharCount(), false);
		int index = 0;
		while (index < styles.length) {
			if (styles[index].font == resource) break;
			if (styles[index].foreground == resource) break;
			if (styles[index].background == resource) break;
			if (styles[index].strikeoutColor == resource) break;
			if (styles[index].underlineColor == resource) break;
			if (styles[index].borderColor == resource) break;
			index++;
		}
		if (index == styles.length) resource.dispose();
	}
	
	
	public	void freeStyleFontImage(){
		if (textFont != null) textFont.dispose();
		textFont = null;
		if (curFont != null) curFont.dispose();
		curFont = null;
	}
	
	public	void freeStyleColorImage(){
		if (textForeground != null) textForeground.dispose();
		textForeground = null;
		if (textBackground != null) textBackground.dispose();
		textBackground = null;
		if (strikeoutColor != null) strikeoutColor.dispose();
		strikeoutColor = null;
		if (underlineColor != null) underlineColor.dispose();
		underlineColor = null;
		if (borderColor != null) borderColor.dispose();
		borderColor = null;
	}
	
	/*************************************************************/
	void setLink() {
		final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		dialog.setLayout(new GridLayout(2, false));
		dialog.setText(getResourceString("SetLink")); //$NON-NLS-1$
		Label label = new Label(dialog, SWT.NONE);
		label.setText(getResourceString("URL")); //$NON-NLS-1$
		final Text text = new Text(dialog, SWT.SINGLE);
		text.setLayoutData(new GridData(200, SWT.DEFAULT));
		if (link != null) {
			text.setText(link);
			text.selectAll();
		}
		final Button okButton = new Button(dialog, SWT.PUSH);
		okButton.setText(getResourceString("Ok")); //$NON-NLS-1$
		final Button cancelButton = new Button(dialog, SWT.PUSH);
		cancelButton.setText(getResourceString("Cancel")); //$NON-NLS-1$
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == okButton) {
					link = text.getText();
					setStyle(UNDERLINE_LINK);
				}
				dialog.dispose();
			}
		};
		okButton.addListener(SWT.Selection, listener);
		cancelButton.addListener(SWT.Selection, listener);
		dialog.setDefaultButton(okButton);
		dialog.pack();
		dialog.open();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/*************************************************************/
	private int curStyle;
	private StyleRange newRange;
	private void setNewRangeFont(){
		if ((curStyle & FONT) != 0) {
			newRange.font = textFont;
		}
		if ((curStyle & FONT_STYLE) != 0) {
			newRange.fontStyle = curStyle & FONT_STYLE;
		}
	}
	
	private void setNewRangeGround(){
		if ((curStyle & FOREGROUND) != 0) {
			newRange.foreground = textForeground;
		}
		if ((curStyle & BACKGROUND) != 0) {
			newRange.background = textBackground;
		}
	}
	
	private void setNewRangeBaseline(){
		if ((curStyle & BASELINE_UP) != 0)	newRange.rise++;
		if ((curStyle & BASELINE_DOWN) != 0) newRange.rise--;
	}
	
	private void setNewRangeStrikeout(){
		if ((curStyle & STRIKEOUT) == 0) return;
		newRange.strikeout = true;
		newRange.strikeoutColor = strikeoutColor;
	}
	
	private void setNewRangeOutline(){
		if ((curStyle & UNDERLINE) == 0) return; 
		newRange.underline = true;
		newRange.underlineColor = underlineColor;
		switch (curStyle & UNDERLINE) {
			case UNDERLINE_SINGLE:
				newRange.underlineStyle = SWT.UNDERLINE_SINGLE;
				break;
			case UNDERLINE_DOUBLE:
				newRange.underlineStyle = SWT.UNDERLINE_DOUBLE;
				break;
			case UNDERLINE_ERROR:
				newRange.underlineStyle = SWT.UNDERLINE_ERROR;
				break;
			case UNDERLINE_SQUIGGLE:
				newRange.underlineStyle = SWT.UNDERLINE_SQUIGGLE;
				break;
			case UNDERLINE_LINK:
				newRange.underlineColor = null;
				if (link != null && link.length() > 0) {
					newRange.underlineStyle = SWT.UNDERLINE_LINK;
					newRange.data = link;
				} else {
					newRange.underline = false;
				}
				break;
		}
	}
	
	private void setNewRangeBoarder(){
		if ((curStyle & BORDER) == 0) return;
		switch (curStyle & BORDER) {
		case BORDER_DASH:
			newRange.borderStyle = SWT.BORDER_DASH;
			break;
		case BORDER_DOT:
			newRange.borderStyle = SWT.BORDER_DOT;
			break;
		case BORDER_SOLID:
			newRange.borderStyle = SWT.BORDER_SOLID;
			break;
		}
		newRange.borderColor = borderColor;
	}
	
	private void setNewRange(){
		setNewRangeFont();
		setNewRangeGround();
		setNewRangeBaseline();
		setNewRangeStrikeout();
		setNewRangeOutline();
		setNewRangeBoarder();
	}
	/*************************************************************/
	private StyleRange curRange;
	private StyleRange mergedRange;
	private void setMergedRangeFont() {
		//Note: fontStyle is not copied by the constructor
		mergedRange.fontStyle = curRange.fontStyle;
		if ((curStyle & FONT) != 0) {
			mergedRange.font =  newRange.font;
		}
		if ((curStyle & FONT_STYLE) != 0) {
			mergedRange.fontStyle =  curRange.fontStyle ^ newRange.fontStyle;
		}
		if (mergedRange.font != null && ((curStyle & FONT) != 0 || (curStyle & FONT_STYLE) != 0)) {
			boolean change = false;
			FontData[] fds = mergedRange.font.getFontData();
			for (int j = 0; j < fds.length; j++) {
				FontData fd = fds[j];
				if (fd.getStyle() != mergedRange.fontStyle) {
					fds[j].setStyle(mergedRange.fontStyle);
					change = true;
				}
			}
			if (change) {
				mergedRange.font = new Font(display, fds);
			}
		}
	}
	
	private void setMergedRangeGround() {
		if ((curStyle & FOREGROUND) != 0) {
			mergedRange.foreground = newRange.foreground != curRange.foreground ? newRange.foreground : null;
		}
		if ((curStyle & BACKGROUND) != 0) {
			mergedRange.background = newRange.background != curRange.background ? newRange.background : null;
		}
	}

	private void setMergedRangeBaseline() {
		if ((curStyle & BASELINE_UP) != 0) mergedRange.rise++;
		if ((curStyle & BASELINE_DOWN) != 0) mergedRange.rise--;
	}

	private void setMergedRangeStrike() {
		if ((curStyle & STRIKEOUT) != 0) {
			mergedRange.strikeout = !curRange.strikeout || curRange.strikeoutColor != newRange.strikeoutColor;
			mergedRange.strikeoutColor = mergedRange.strikeout ? newRange.strikeoutColor : null;
		}
	}

	private void setMergedRangeUnderline() {
		if ((curStyle & UNDERLINE) != 0) {
			if ((curStyle & UNDERLINE_LINK) != 0) {
				if (link != null && link.length() > 0) {
					mergedRange.underline = !curRange.underline || curRange.underlineStyle != newRange.underlineStyle  || curRange.data != newRange.data;
				} else {
					mergedRange.underline = false;
				}
				mergedRange.underlineColor = null;
			} else {
				mergedRange.underline = !curRange.underline || curRange.underlineStyle != newRange.underlineStyle || curRange.underlineColor != newRange.underlineColor;
				mergedRange.underlineColor = mergedRange.underline ? newRange.underlineColor : null;
			}
			mergedRange.underlineStyle = mergedRange.underline ? newRange.underlineStyle : SWT.NONE;
			mergedRange.data = mergedRange.underline ? newRange.data : null;
		}
	}
	
	private void setMergedRangeBorder() {
		if ((curStyle & BORDER) != 0) {
			if (curRange.borderStyle != newRange.borderStyle || curRange.borderColor != newRange.borderColor) {
				mergedRange.borderStyle = newRange.borderStyle;
				mergedRange.borderColor = newRange.borderColor;
			} else {
				mergedRange.borderStyle = SWT.NONE;
				mergedRange.borderColor = null;
			}
		}
		
	}
	
	private void setMergedRange() {
		setMergedRangeFont();
		setMergedRangeGround();
		setMergedRangeBaseline();	
		setMergedRangeStrike();
		setMergedRangeUnderline();
		setMergedRangeBorder();		

	}
	
	/*************************************************************/
	private StyleRange[] curStyles;
	private StyleRange[] newStyles;
	private int[] curRanges;
	private int[] newRanges;
	
	private void initRanges(int start, int length){
		curRanges = styledText.getRanges(start, length);
		int maxCount = curRanges.length * 2 + 2;
		newRanges = new int[maxCount];
	}
	
	private void initStyles(int start, int length){
		curStyles = styledText.getStyleRanges(start, length, false);		
		int maxCount = curRanges.length * 2 + 2;
		newStyles = new StyleRange[maxCount / 2];
	}
	
	private int fillNewRanges(int start,int length){
		int newRangeStart = start;
		int newRangeLength = length;
		int count = 0;
		for (int i = 0; i < curRanges.length; i+=2) {
			int rangeStart = curRanges[i];
			int rangeLength = curRanges[i + 1];
//			curRange = curStyles[i / 2];
			if (rangeStart > newRangeStart) {
				newRangeLength = rangeStart - newRangeStart;
				newRanges[count] = newRangeStart;
				newRanges[count + 1] = newRangeLength;
//				newStyles[count / 2] = newRange;
				count += 2;
			}
			newRangeStart = rangeStart + rangeLength;
			newRangeLength = (start + length) - newRangeStart;

			/* Create merged style range*/
//			mergedRange = new StyleRange(curRange);
//			curRange = curStyles[i / 2];
//			setMergedRange();

			newRanges[count] = rangeStart;
			newRanges[count + 1] = rangeLength;
//			newStyles[count / 2] = mergedRange;
			count += 2;
		}
		
		if (newRangeLength > 0) {
			newRanges[count] = newRangeStart;
			newRanges[count + 1] = newRangeLength;
//			newStyles[count / 2] = newRange;
			count += 2;
		}
		
		return(count);
	}
	
	private int fillNewStyles(int start,int length){
		int newRangeStart = start;
		int newRangeLength = length;
		int count = 0;
		for (int i = 0; i < curRanges.length; i+=2) {
			int rangeStart = curRanges[i];
			int rangeLength = curRanges[i + 1];
			curRange = curStyles[i / 2];
			if (rangeStart > newRangeStart) {
				newRangeLength = rangeStart - newRangeStart;
//				newRanges[count] = newRangeStart;
//				newRanges[count + 1] = newRangeLength;
				newStyles[count / 2] = newRange;
				count += 2;
			}
			newRangeStart = rangeStart + rangeLength;
			newRangeLength = (start + length) - newRangeStart;

			/* Create merged style range*/
			mergedRange = new StyleRange(curRange);
			curRange = curStyles[i / 2];
			setMergedRange();

//			newRanges[count] = rangeStart;
//			newRanges[count + 1] = rangeLength;
			newStyles[count / 2] = mergedRange;
			count += 2;
		}
		
		if (newRangeLength > 0) {
//			newRanges[count] = newRangeStart;
//			newRanges[count + 1] = newRangeLength;
			newStyles[count / 2] = newRange;
			count += 2;
		}
		return(count);
	}
	
	private void blankNewRanges(int count){
		int maxCount = curRanges.length * 2 + 2;
		if (0 < count && count < maxCount) {			
			int[] tmpRanges = new int[count];
			System.arraycopy(newRanges, 0, tmpRanges, 0, count);
			newRanges = tmpRanges;
		}
	}
	
	private void blankNewStyles(int count){
		int maxCount = curRanges.length * 2 + 2;
		if (0 < count && count < maxCount) {			
			StyleRange[] tmpStyles = new StyleRange[count / 2];
			System.arraycopy(newStyles, 0, tmpStyles, 0, count / 2);
			newStyles = tmpStyles;
		}
	}
	
	private void setNewStyle(int start, int length) {
		if (length == 0) return;
		
		/* Create new style range */
		newRange = new StyleRange();
		setNewRange();

		initRanges(start, length);
		initStyles(start, length);		

		int count;
		count=fillNewRanges(start, length);
		blankNewRanges(count);
		
		count=fillNewStyles(start, length);
		blankNewStyles(count);		
		
		styledText.setStyleRanges(start, length, newRanges, newStyles);
	}
	
	/*************************************************************/
	private void updateStyleState(int changingStyle) {
		if ((curStyle & changingStyle) != 0) {
			if ((curStyle & changingStyle) == (styleState & changingStyle)) {
				styleState &= ~changingStyle;
			} else {
				styleState &= ~changingStyle;
				styleState |= curStyle;
			}
		}
	}
	
	void setStyle(int vStyle) {
		int[] tmpRanges = styledText.getSelectionRanges();
		int i = 0;
		curStyle = vStyle;
		while (i < tmpRanges.length) {
			setNewStyle(tmpRanges[i++], tmpRanges[i++]);
		}
		updateStyleState(FOREGROUND);
		updateStyleState(BACKGROUND);
		updateStyleState(UNDERLINE);
		updateStyleState(STRIKEOUT);
		updateStyleState(BORDER);
	}
	
	/*************************************************************/
	private void showError (String title, String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.CLOSE);
		messageBox.setText(title);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	private StyleRange[] getStyles(InputStream stream) {
		try {
			StyleRange[] tmpStyles = new StyleRange[256];
			int count = 0;
			BufferedReader tmpReader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = tmpReader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ";", false);  //$NON-NLS-1$
				StyleRange tmpRange = new StyleRange();
				tmpRange.start = Integer.parseInt(tokenizer.nextToken());
				tmpRange.length = Integer.parseInt(tokenizer.nextToken());
				tmpRange.fontStyle = Integer.parseInt(tokenizer.nextToken());
				tmpRange.strikeout = tokenizer.nextToken().equals("true");  //$NON-NLS-1$
				tmpRange.underline = tokenizer.nextToken().equals("true");  //$NON-NLS-1$
				if (tokenizer.hasMoreTokens()) {
					int red = Integer.parseInt(tokenizer.nextToken());
					int green = Integer.parseInt(tokenizer.nextToken());
					int blue = Integer.parseInt(tokenizer.nextToken());
					tmpRange.foreground = new Color(display, red, green, blue);
				}
				if (tokenizer.hasMoreTokens()) {
					int red = Integer.parseInt(tokenizer.nextToken());
					int green = Integer.parseInt(tokenizer.nextToken());
					int blue = Integer.parseInt(tokenizer.nextToken());
					tmpRange.background = new Color(display, red, green, blue);
				}
				if (count >= tmpStyles.length) {
					StyleRange[] newStyles =  new StyleRange[tmpStyles.length + 256];
					System.arraycopy(tmpStyles, 0, newStyles, 0, tmpStyles.length);
					tmpStyles = newStyles;
				}
				tmpStyles[count++] = tmpRange;
			}
			if (count < tmpStyles.length) {
				StyleRange[] newStyles = new StyleRange[count];
				System.arraycopy(tmpStyles, 0, newStyles, 0, count);
				tmpStyles = newStyles;
			}
			return tmpStyles;
		} catch (IOException e) {
			showError(getResourceString("Error"), e.getMessage()); //$NON-NLS-1$
		}
		return null;
	}
	
	
	public void updateToolBarArea() {
		styleState = 0;
		link = null;
		boolean bold = false, italic = false;
		Font tmpFont = null;

		int offset = styledText.getCaretOffset();
		StyleRange tmpRange = offset > 0 ? styledText.getStyleRangeAtOffset(offset-1) : null;
		if (tmpRange != null) {
			if (tmpRange.font != null) {
				tmpFont = tmpRange.font;
				FontData[] fds = tmpFont.getFontData();
				for (int i = 0; i < fds.length; i++) {
					int fontStyle = fds[i].getStyle();
					if (!bold && (fontStyle & SWT.BOLD) != 0) bold = true;
					if (!italic && (fontStyle & SWT.ITALIC) != 0) italic = true;
				}
			} else {
				bold = (tmpRange.fontStyle & SWT.BOLD) != 0;
				italic = (tmpRange.fontStyle & SWT.ITALIC) != 0;
			}
			if (tmpRange.foreground != null) {
				styleState |= FOREGROUND;
				if (textForeground != tmpRange.foreground) {
					freeStyleResource(textForeground);
					textForeground = tmpRange.foreground;
				}
			}
			if (tmpRange.background != null) {
				styleState |= BACKGROUND;
				if (textBackground != tmpRange.background) {
					freeStyleResource(textBackground);
					textBackground = tmpRange.background;
				}
			}
			if (tmpRange.underline) {
				switch (tmpRange.underlineStyle) {
					case SWT.UNDERLINE_SINGLE:	styleState |= UNDERLINE_SINGLE; break;
					case SWT.UNDERLINE_DOUBLE: 	styleState |= UNDERLINE_DOUBLE; break;
					case SWT.UNDERLINE_SQUIGGLE:	styleState |= UNDERLINE_SQUIGGLE; break;
					case SWT.UNDERLINE_ERROR: 	styleState |= UNDERLINE_ERROR; break;
					case SWT.UNDERLINE_LINK: 	
						styleState |= UNDERLINE_LINK;
						link = (String)tmpRange.data;
						break;
				}
				if (tmpRange.underlineStyle != SWT.UNDERLINE_LINK) {
					updateMenuItemUnderline();
					freeStyleResource(underlineColor);
					underlineColor = tmpRange.underlineColor;
				}
			}
			if (tmpRange.strikeout) {
				styleState |= STRIKEOUT;
				freeStyleResource(strikeoutColor);
				strikeoutColor = tmpRange.strikeoutColor;
			}
			if (tmpRange.borderStyle != SWT.NONE) {
				switch (tmpRange.borderStyle) {
					case SWT.BORDER_SOLID:	styleState |= BORDER_SOLID; break;
					case SWT.BORDER_DASH:	styleState |= BORDER_DASH; break;
					case SWT.BORDER_DOT:	styleState |= BORDER_DOT; break;
				}
				updateMenuItemBorder();
				freeStyleResource(borderColor);
				borderColor = tmpRange.borderColor;
			}
		}
		
		updateToolBarControl(bold,italic);
		freeStyleResource(textFont);
		textFont = tmpFont;
	}
	
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
	
	void loadProfile(int profile) {
		try {
			switch (profile) {
				case 1: {
					String text = openFile(Editor00Shell.class.getResourceAsStream("text.txt"));  //$NON-NLS-1$
					StyleRange[] styles = getStyles(Editor00Shell.class.getResourceAsStream("styles.txt"));  //$NON-NLS-1$
					styledText.setText(text);
					if (styles != null) styledText.setStyleRanges(styles);
					break;
				}
				case 2: {
					styledText.setText(getResourceString("Profile2"));  //$NON-NLS-1$
					break;
				}
				case 3: {
					String text = openFile(Editor00Shell.class.getResourceAsStream("text4.txt"));  //$NON-NLS-1$
					styledText.setText(text);
					break;
				}
				case 4: {
					styledText.setText(getResourceString("Profile4"));  //$NON-NLS-1$
					break;
				}
			}
			updateToolBarArea();
		} catch (Exception e) {
			showError(getResourceString("Error"), e.getMessage()); //$NON-NLS-1$
		}
	}
	
	Image loadImage(Display display, String fileName) {
		Image image = null; 
		try {
			InputStream sourceStream = getClass().getResourceAsStream(fileName + ".ico");  //$NON-NLS-1$ //$NON-NLS-2$
			ImageData source = new ImageData(sourceStream);
			ImageData mask = source.getTransparencyMask();
			image = new Image(display, source, mask);
			sourceStream.close();
		} catch (IOException e) {
			showError(getResourceString("Error"), e.getMessage()); //$NON-NLS-1$
		}
		return image;
	}
	/*************************************************************/
	public void responseModifyEvent(boolean bold,boolean italic){
		if (newCharCount > 0 && start >= 0) {
			StyleRange tmpStyle = new StyleRange();
			if (textFont != null && !textFont.equals(styledText.getFont())) {
				tmpStyle.font = textFont;
			} else {
				tmpStyle.fontStyle = SWT.NONE;
				if (bold) tmpStyle.fontStyle |= SWT.BOLD;
				if (italic) tmpStyle.fontStyle |= SWT.ITALIC;
			}
			if ((styleState & FOREGROUND) != 0) {
				tmpStyle.foreground = textForeground;
			}
			if ((styleState & BACKGROUND) != 0) {
				tmpStyle.background = textBackground;
			}
			int underlineStyle = styleState & UNDERLINE;
			if (underlineStyle != 0) {
				tmpStyle.underline = true;
				tmpStyle.underlineColor = underlineColor;
				switch (underlineStyle) {
					case UNDERLINE_SINGLE:	tmpStyle.underlineStyle = SWT.UNDERLINE_SINGLE; break;
					case UNDERLINE_DOUBLE:	tmpStyle.underlineStyle = SWT.UNDERLINE_DOUBLE; break;
					case UNDERLINE_SQUIGGLE:	tmpStyle.underlineStyle = SWT.UNDERLINE_SQUIGGLE; break;
					case UNDERLINE_ERROR:	tmpStyle.underlineStyle = SWT.UNDERLINE_ERROR; break;
					case UNDERLINE_LINK: {
						tmpStyle.underlineColor = null;
						if (link != null && link.length() > 0) {
							tmpStyle.underlineStyle = SWT.UNDERLINE_LINK;
							tmpStyle.data = link;
						} else {
							tmpStyle.underline = false;
						}
						break;
					}
				}
			}
			if ((styleState & STRIKEOUT) != 0) {
				tmpStyle.strikeout = true;
				tmpStyle.strikeoutColor = strikeoutColor;
			}
			int borderStyle = styleState & BORDER;
			if (borderStyle != 0) {
				tmpStyle.borderColor = borderColor;
				switch (borderStyle) {
					case BORDER_DASH:	tmpStyle.borderStyle = SWT.BORDER_DASH; break;
					case BORDER_DOT:	tmpStyle.borderStyle = SWT.BORDER_DOT; break;
					case BORDER_SOLID:  tmpStyle.borderStyle = SWT.BORDER_SOLID; break;
				}
			}
			
			int[] tmpRanges = {start, newCharCount};
			StyleRange[] tmpStyles = {tmpStyle}; 
			styledText.setStyleRanges(start, newCharCount, tmpRanges, tmpStyles);
		}
		freeStyleRanges(selectedRanges);
	}
	/*************************************************************/
	public void responseMenuToolSetFont(){
		FontDialog fontDialog = new FontDialog(shell);
		fontDialog.setFontList(styledText.getFont().getFontData());
		FontData data = fontDialog.open();
		if (data != null) {
			Font newFont = new Font(display, data);
			styledText.setFont(newFont);
			if (curFont != null) curFont.dispose();
			curFont = newFont;
			updateToolBar();
		}
	}
	
		

}

