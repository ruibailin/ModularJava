package mytexteditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class Editor05CoolBar extends Editor04Menu{
	
	CoolBar coolBar;
	//Child control of CoolBar:
	//CoolItem 1,2,3,4
	//ToolBar 1,3
	//Composite 2,4
	
	//CoolItem 1 & ToolBar 1
	ToolItem boldControl, italicControl;
	
	//CoolItem 2 & Composite 2
	Combo fontNameControl, fontSizeControl;
	
	//CoolItem 3 & ToolBar 3
	ToolItem blockSelectionItem;
	ToolItem leftAlignmentItem, centerAlignmentItem, rightAlignmentItem, justifyAlignmentItem;

	//Image for CoolItem 1
	Image iBold, iItalic, iUnderline, iStrikeout, iBorderStyle ,iTextForeground, iTextBackground,iBaselineUp, iBaselineDown,iLink;

	//Image for CoolItem 3
	Image iBlockSelection, iLeftAlignment, iRightAlignment, iCenterAlignment, iJustifyAlignment,iBulletList, iNumberedList;
	
	//Image for CoolItem 4
	Image iSpacing, iIndent;
	
	static final boolean USE_BASELINE = false;

	static final String[] FONT_SIZES = new String[] {
			"6",		//$NON-NLS-1$
			"8", 		//$NON-NLS-1$
			"9", 		//$NON-NLS-1$
			"10", 		//$NON-NLS-1$
			"11", 		//$NON-NLS-1$
			"12",	 	//$NON-NLS-1$
			"14",		//$NON-NLS-1$
			"24",		//$NON-NLS-1$
			"36",		//$NON-NLS-1$
			"48" 		//$NON-NLS-1$
	};
	public Editor05CoolBar() {

	}
	
	@Override
	public	void enableAlignmentItem(boolean enabled){
		leftAlignmentItem.setEnabled(enabled);
		centerAlignmentItem.setEnabled(enabled);
		rightAlignmentItem.setEnabled(enabled);
		justifyAlignmentItem.setEnabled(enabled);
		blockSelectionItem.setEnabled(!enabled);
	}
	
	@Override
	public void updateToolBarControl(boolean  bold,boolean italic){
		boldControl.setSelection(bold);
		italicControl.setSelection(italic);
	}
	
	
	private  void updateToolBarFontStyle(){
		Font font = textFont;
		FontData fontData = font != null ? font.getFontData()[0] : styledText.getFont().getFontData()[0];
		int index = 0;
		int count = fontNameControl.getItemCount();
		String fontName = fontData.getName();
		while (index < count) {
			if (fontNameControl.getItem(index).equals(fontName)) {
				fontNameControl.select(index);
				break;
			}
			index++;
		}
		index = 0;
		count = fontSizeControl.getItemCount();
		int fontSize = fontData.getHeight();
		while (index < count) {
			int size = Integer.parseInt(fontSizeControl.getItem(index));
			if (fontSize == size) {
				fontSizeControl.select(index);
				break;
			}
			if (size > fontSize) {
				fontSizeControl.add (String.valueOf(fontSize), index);
				fontSizeControl.select(index);
				break;
			}
			index++;
		}
	}
	
	private  void updateAlignmentItem(){
		int lineIndex = styledText.getLineAtOffset(offset);
		int alignment = styledText.getLineAlignment(lineIndex);
		leftAlignmentItem.setSelection((alignment & SWT.LEFT) != 0);
		centerAlignmentItem.setSelection((alignment & SWT.CENTER) != 0);
		rightAlignmentItem.setSelection((alignment & SWT.RIGHT) != 0);
		boolean justify = styledText.getLineJustify(lineIndex);
		justifyAlignmentItem.setSelection(justify);
	}
	
	@Override
	public	void updateToolBar(){
		updateToolBarArea();
		updateToolBarFontStyle();
		updateAlignmentItem();
	}
	/*************************************************************/
	void initToolBarImage(){
		//Group 1 Tool Item
		iBold = loadImage(display, "bold"); //$NON-NLS-1$
		iItalic = loadImage(display, "italic"); //$NON-NLS-1$
		iUnderline = loadImage(display, "underline"); //$NON-NLS-1$
		iStrikeout = loadImage(display, "strikeout"); //$NON-NLS-1$
		iBorderStyle = loadImage(display, "resize"); //$NON-NLS-1$
		iTextForeground = loadImage(display, "textForeground"); //$NON-NLS-1$
		iTextBackground = loadImage(display, "textBackground"); //$NON-NLS-1$
		iBaselineUp = loadImage(display, "font_big"); //$NON-NLS-1$
		iBaselineDown = loadImage(display, "font_sml"); //$NON-NLS-1$
		iLink = new Image(display, getClass().getResourceAsStream("link_obj.gif")); //$NON-NLS-1$
		
		//Group 3 Tool Item
		iBlockSelection = loadImage(display, "fullscrn"); //$NON-NLS-1$
		iLeftAlignment = loadImage(display, "left"); //$NON-NLS-1$
		iRightAlignment = loadImage(display, "right"); //$NON-NLS-1$
		iCenterAlignment = loadImage(display, "center"); //$NON-NLS-1$
		iJustifyAlignment = loadImage(display, "justify"); //$NON-NLS-1$
		iBulletList = loadImage(display, "para_bul"); //$NON-NLS-1$
		iNumberedList = loadImage(display, "para_num"); //$NON-NLS-1$
		
	}
	
	void freeToolBarImage() {
		//Group 1 Tool Item
		iBold.dispose();  	iBold = null;
		iItalic.dispose(); 		iItalic = null;
		iUnderline.dispose(); 		iUnderline = null;
		iStrikeout.dispose(); 		iStrikeout = null;
		iBorderStyle.dispose();		iBorderStyle = null;
		iTextForeground.dispose();		iTextForeground = null;
		iTextBackground.dispose();		iTextBackground = null;
		iBaselineUp.dispose();			iBaselineUp = null;
		iBaselineDown.dispose();		iBaselineDown = null;
		iLink.dispose();				iLink = null;
		
		//Group 3 Tool Item
		iBlockSelection.dispose();		iBlockSelection = null;
		iLeftAlignment.dispose();		iLeftAlignment = null;
		iRightAlignment.dispose();		iRightAlignment = null;
		iCenterAlignment.dispose();		iCenterAlignment = null;
		iJustifyAlignment.dispose();	iJustifyAlignment = null;
		iBulletList.dispose();			iBulletList = null;
		iNumberedList.dispose();		iNumberedList = null;
	}
	/*************************************************************/
	private String[] getFontNames() {
		FontData[] fontNames = display.getFontList(null, true);
		String[] names = new String[fontNames.length];
		int count = 0;
		mainfor:
		for (int i = 0; i < fontNames.length; i++) {
			String fontName = fontNames[i].getName();
			if (fontName.startsWith("@")) //$NON-NLS-1$
				continue;
			for (int j = 0; j < count; j++) {
				if (names[j].equals(fontName)) continue mainfor;
			}
			names[count++] = fontName;
		}
		if (count < names.length) {
			String[] newNames = new String[count];
			System.arraycopy(names, 0, newNames, 0, count);
			names = newNames;
		}
		return names;
	}
	
	private void adjustFontSize (int increment) {
		int newIndex = fontSizeControl.getSelectionIndex() + increment;
		if (0 <= newIndex && newIndex < fontSizeControl.getItemCount()) {
			String name = fontNameControl.getText();
			int size = Integer.parseInt(fontSizeControl.getItem(newIndex));
			freeStyleResource(textFont);
			textFont = new Font(display, name, size, SWT.NORMAL);
			setStyle(FONT);
			updateToolBar();
		}
	}
	
	private Point resizeToolBar(){
		Rectangle rect = shell.getClientArea();
		Point cSize = coolBar.computeSize(rect.width, SWT.DEFAULT);
		coolBar.setBounds(rect.x, rect.y, cSize.x, cSize.y);
		return(cSize);
	}
	
	/*************************************************************/
	@Override
	public void responseControlEvent(ControlEvent event) {
		Point cSize = resizeToolBar();
		resizeStyledText(cSize);
		updateStatusBarContent();
		updateStatusBar();
		resizeStatusBar();
	}

	/*************************************************************/
	private ToolBar styleToolBar;
	private void initStyleCoolBarItem(){
		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
		styleToolBar = new ToolBar(coolBar, SWT.FLAT);
		coolItem.setControl(styleToolBar);
	}
	private void initStyleToolBarItem(){
		
		boldControl = new ToolItem(styleToolBar, SWT.CHECK);
		boldControl.setImage(iBold);
		boldControl.setToolTipText(getResourceString("Bold")); //$NON-NLS-1$
		boldControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setStyle(BOLD);
			}
		});

		italicControl = new ToolItem(styleToolBar, SWT.CHECK);
		italicControl.setImage(iItalic);
		italicControl.setToolTipText(getResourceString("Italic")); //$NON-NLS-1$
		italicControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setStyle(ITALIC);
			}
		});

	
		final ToolItem underlineControl = new ToolItem(styleToolBar, SWT.DROP_DOWN);
		underlineControl.setImage(iUnderline);
		underlineControl.setToolTipText(getResourceString("Underline")); //$NON-NLS-1$
		underlineControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = underlineControl.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					underlineMenu.setLocation(display.map(underlineControl.getParent(), null, pt));
					underlineMenu.setVisible(true);
				} else {
					if (underlineSingleItem.getSelection()) setStyle(UNDERLINE_SINGLE);
					else if (underlineDoubleItem.getSelection()) setStyle(UNDERLINE_DOUBLE);
					else if (underlineErrorItem.getSelection()) setStyle(UNDERLINE_ERROR);
					else if (underlineSquiggleItem.getSelection()) setStyle(UNDERLINE_SQUIGGLE);
				}
			}
		});

		ToolItem strikeoutControl = new ToolItem(styleToolBar, SWT.DROP_DOWN);
		strikeoutControl.setImage(iStrikeout);
		strikeoutControl.setToolTipText(getResourceString("Strikeout")); //$NON-NLS-1$
		strikeoutControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (event.detail == SWT.ARROW) {
					ColorDialog dialog = new ColorDialog(shell);
					RGB rgb = strikeoutColor != null ? strikeoutColor.getRGB() : null;
					dialog.setRGB(rgb);
					RGB newRgb = dialog.open();
					if (newRgb == null) return;
					if (!newRgb.equals(rgb)) {
						freeStyleResource(strikeoutColor);
						strikeoutColor = new Color(display, newRgb);
					}
				}
				setStyle(STRIKEOUT);
			}
		});


		final ToolItem borderControl = new ToolItem(styleToolBar, SWT.DROP_DOWN);
		borderControl.setImage(iBorderStyle);
		borderControl.setToolTipText(getResourceString("Box")); //$NON-NLS-1$
		borderControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = borderControl.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					borderMenu.setLocation(display.map(borderControl.getParent(), null, pt));
					borderMenu.setVisible(true);
				} else {
					if (borderDashItem.getSelection()) setStyle(BORDER_DASH);
					else if (borderDotItem.getSelection()) setStyle(BORDER_DOT);
					else if (borderSolidItem.getSelection()) setStyle(BORDER_SOLID);
				}
			}
		});

		ToolItem foregroundItem = new ToolItem(styleToolBar, SWT.DROP_DOWN);
		foregroundItem.setImage(iTextForeground);
		foregroundItem.setToolTipText(getResourceString("TextForeground")); //$NON-NLS-1$
		foregroundItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {		
				if (event.detail == SWT.ARROW || textForeground == null) {
					ColorDialog dialog = new ColorDialog(shell);
					RGB rgb = textForeground != null ? textForeground.getRGB() : null;
					dialog.setRGB(rgb);
					RGB newRgb = dialog.open();
					if (newRgb == null) return;
					if (!newRgb.equals(rgb)) {
						freeStyleResource(textForeground);
						textForeground = new Color(display, newRgb);					
					}
				}
				setStyle(FOREGROUND);				
			}
		});

		ToolItem backgroundItem = new ToolItem(styleToolBar, SWT.DROP_DOWN);
		backgroundItem.setImage(iTextBackground);
		backgroundItem.setToolTipText(getResourceString("TextBackground")); //$NON-NLS-1$
		backgroundItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {			
				if (event.detail == SWT.ARROW || textBackground == null) {
					ColorDialog dialog = new ColorDialog(shell);
					RGB rgb = textBackground != null ? textBackground.getRGB() : null;
					dialog.setRGB(rgb);
					RGB newRgb = dialog.open();
					if (newRgb == null) return;
					if (!newRgb.equals(rgb)) {
						freeStyleResource(textBackground);
						textBackground = new Color(display, newRgb);
					}
				}
				setStyle(BACKGROUND);
			}
		});

		ToolItem baselineUpItem = new ToolItem(styleToolBar, SWT.PUSH);
		baselineUpItem.setImage(iBaselineUp);
		String tooltip = "IncreaseFont"; //$NON-NLS-1$
		if (USE_BASELINE) tooltip = "IncreaseBaseline"; //$NON-NLS-1$
		baselineUpItem.setToolTipText(getResourceString(tooltip));
		baselineUpItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (USE_BASELINE) {
					setStyle(BASELINE_UP);
				} else {
					adjustFontSize(1);
				}
			}
		});

		ToolItem baselineDownItem = new ToolItem(styleToolBar, SWT.PUSH);
		baselineDownItem.setImage(iBaselineDown);
		tooltip = "DecreaseFont"; //$NON-NLS-1$
		if (USE_BASELINE) tooltip = "DecreaseBaseline"; //$NON-NLS-1$
		baselineDownItem.setToolTipText(getResourceString(tooltip));
		baselineDownItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (USE_BASELINE) {
					setStyle(BASELINE_DOWN);
				} else {
					adjustFontSize(-1);
				}
			}
		});
		ToolItem linkItem = new ToolItem(styleToolBar, SWT.PUSH);
		linkItem.setImage(iLink);
		linkItem.setToolTipText(getResourceString("Link")); //$NON-NLS-1$
		linkItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setLink();
			}
		});
	}
	/*************************************************************/
	private Composite fontComposite;
	private void initFontCoolBarItem(){
		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
		fontComposite = new Composite(coolBar, SWT.NONE);
		coolItem.setControl(fontComposite);
	}
	private void initFontCompositeItem(){
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 1;
		fontComposite.setLayout(layout);
		fontNameControl = new Combo(fontComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		fontNameControl.setItems(getFontNames());
		fontNameControl.setVisibleItemCount(12);
		fontSizeControl = new Combo(fontComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		fontSizeControl.setItems(FONT_SIZES);
		fontSizeControl.setVisibleItemCount(8);
		SelectionAdapter adapter = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String name = fontNameControl.getText();
				int size = Integer.parseInt(fontSizeControl.getText());
				freeStyleResource(textFont);
				textFont = new Font(display, name, size, SWT.NORMAL);
				setStyle(FONT);
			}
		};
		fontSizeControl.addSelectionListener(adapter);
		fontNameControl.addSelectionListener(adapter);

	}
	/*************************************************************/
	private ToolBar alignmentToolBar;
	private void initAlignCoolBarItem(){
		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
		alignmentToolBar = new ToolBar(coolBar, SWT.FLAT);
		coolItem.setControl(alignmentToolBar);
	}

	private void initAlignToolBarItem(){
		blockSelectionItem = new ToolItem(alignmentToolBar, SWT.CHECK);
		blockSelectionItem.setImage(iBlockSelection);
		blockSelectionItem.setToolTipText(getResourceString("BlockSelection")); //$NON-NLS-1$
		blockSelectionItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.invokeAction(ST.TOGGLE_BLOCKSELECTION);
			}
		});
		
		leftAlignmentItem = new ToolItem(alignmentToolBar, SWT.RADIO);
		leftAlignmentItem.setImage(iLeftAlignment);
		leftAlignmentItem.setToolTipText(getResourceString("AlignLeft")); //$NON-NLS-1$
		leftAlignmentItem.setSelection(true);
		leftAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Point selection = styledText.getSelection();
				int lineStart = styledText.getLineAtOffset(selection.x);
				int lineEnd = styledText.getLineAtOffset(selection.y);
				styledText.setLineAlignment(lineStart, lineEnd - lineStart + 1,	SWT.LEFT);
			}
		});
		leftAlignmentItem.setEnabled(false);

		centerAlignmentItem = new ToolItem(alignmentToolBar, SWT.RADIO);
		centerAlignmentItem.setImage(iCenterAlignment);
		centerAlignmentItem.setToolTipText(getResourceString("Center_menuitem")); //$NON-NLS-1$
		centerAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Point selection = styledText.getSelection();
				int lineStart = styledText.getLineAtOffset(selection.x);
				int lineEnd = styledText.getLineAtOffset(selection.y);
				styledText.setLineAlignment(lineStart, lineEnd - lineStart + 1, SWT.CENTER);
			}
		});
		centerAlignmentItem.setEnabled(false);

		rightAlignmentItem = new ToolItem(alignmentToolBar, SWT.RADIO);
		rightAlignmentItem.setImage(iRightAlignment);
		rightAlignmentItem.setToolTipText(getResourceString("AlignRight")); //$NON-NLS-1$
		rightAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Point selection = styledText.getSelection();
				int lineStart = styledText.getLineAtOffset(selection.x);
				int lineEnd = styledText.getLineAtOffset(selection.y);
				styledText.setLineAlignment(lineStart, lineEnd - lineStart + 1,	SWT.RIGHT);
			}
		});
		rightAlignmentItem.setEnabled(false);

		justifyAlignmentItem = new ToolItem(alignmentToolBar, SWT.CHECK);
		justifyAlignmentItem.setImage(iJustifyAlignment);
		justifyAlignmentItem.setToolTipText(getResourceString("Justify")); //$NON-NLS-1$
		justifyAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Point selection = styledText.getSelection();
				int lineStart = styledText.getLineAtOffset(selection.x);
				int lineEnd = styledText.getLineAtOffset(selection.y);
				styledText.setLineJustify(lineStart, lineEnd - lineStart + 1, justifyAlignmentItem.getSelection());
			}
		});
		justifyAlignmentItem.setEnabled(false);

		ToolItem bulletListItem = new ToolItem(alignmentToolBar, SWT.PUSH);
		bulletListItem.setImage(iBulletList);
		bulletListItem.setToolTipText(getResourceString("BulletList")); //$NON-NLS-1$
		bulletListItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setBullet(ST.BULLET_DOT);}
		});

		ToolItem numberedListItem = new ToolItem(alignmentToolBar, SWT.PUSH);
		numberedListItem.setImage(iNumberedList);
		numberedListItem.setToolTipText(getResourceString("NumberedList")); //$NON-NLS-1$
		numberedListItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setBullet(ST.BULLET_NUMBER | ST.BULLET_TEXT);
			}
		});

	}
	/*************************************************************/
	private Composite formatComposite;
	private void initFormatCoolBarItem(){
		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
		formatComposite = new Composite(coolBar, SWT.NONE);
		coolItem.setControl(formatComposite);
	}
	
	private void initFormatCompositeItem(){
		GridLayout layout = new GridLayout(4, false);
		layout.marginHeight = 1;
		formatComposite.setLayout(layout);
		
		Label label = new Label(formatComposite, SWT.NONE);
		label.setText(getResourceString("Indent")); //$NON-NLS-1$
		Spinner indent = new Spinner(formatComposite, SWT.BORDER);
		indent.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Spinner spinner = (Spinner) event.widget;
				styledText.setIndent(spinner.getSelection());
			}
		});
		
		label = new Label(formatComposite, SWT.NONE);
		label.setText(getResourceString("Spacing")); //$NON-NLS-1$
		Spinner spacing = new Spinner(formatComposite, SWT.BORDER);
		spacing.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Spinner spinner = (Spinner) event.widget;
				styledText.setLineSpacing(spinner.getSelection());
			}
		});

	}
	/*************************************************************/
	private void showCoolBarItem(){
		CoolItem[] coolItems = coolBar.getItems();
		for (int i = 0; i < coolItems.length; i++) {
			CoolItem item = coolItems[i];
			Control control = item.getControl();
			Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			item.setMinimumSize(size);
			size = item.computeSize(size.x, size.y);
			item.setPreferredSize(size);
			item.setSize(size);
		}
	}
	
	private void initCoolBarListener(){
		coolBar.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent event) {
				responseControlEvent(event);
			}
		});
	}
	/*************************************************************/
	public void createCoolBar() {
		
		coolBar = new CoolBar(shell, SWT.FLAT);

		initStyleCoolBarItem();
		initStyleToolBarItem();
		
		initFontCoolBarItem();
		initFontCompositeItem();
		
		initAlignCoolBarItem();
		initAlignToolBarItem();

		initFormatCoolBarItem();
		initFormatCompositeItem();

		showCoolBarItem();
		initCoolBarListener();
	}
	
	public void displayCoolBar(){
		Point cSize = resizeToolBar();
		resizeStyledText(cSize);
		updateStatusBarContent();
		updateStatusBar();
		resizeStatusBar();
	}
}
