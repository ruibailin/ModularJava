package mytexteditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class Editor04Menu extends Editor03Style{
	Menu menuBar,menuPopUp;
	Menu fileMenu,editMenu,toolMenu;
	
	Menu underlineMenu;	//Pull down menu with ToolBar
	MenuItem underlineSingleItem, underlineDoubleItem, underlineErrorItem, underlineSquiggleItem;
	
	Menu borderMenu; //Pull down menu with ToolBar
	MenuItem borderSolidItem, borderDashItem, borderDotItem;

	static final boolean SAMPLE_TEXT = false;
	
	public Editor04Menu() {

	}

	//Will be override in subclass
	public	void enableAlignmentItem(boolean enabled){
//		leftAlignmentItem.setEnabled(enabled);
//		centerAlignmentItem.setEnabled(enabled);
//		rightAlignmentItem.setEnabled(enabled);
//		justifyAlignmentItem.setEnabled(enabled);
//		blockSelectionItem.setEnabled(!enabled);
	}
	
	@Override
	public void updateMenuItemUnderline(){
		underlineSingleItem.setSelection((styleState & UNDERLINE_SINGLE) != 0);
		underlineDoubleItem.setSelection((styleState & UNDERLINE_DOUBLE) != 0);
		underlineErrorItem.setSelection((styleState & UNDERLINE_ERROR) != 0);
		underlineSquiggleItem.setSelection((styleState & UNDERLINE_SQUIGGLE) != 0);
	}
	
	@Override
	public void updateMenuItemBorder(){
		borderSolidItem.setSelection((styleState & BORDER_SOLID) != 0);
		borderDashItem.setSelection((styleState & BORDER_DASH) != 0);
		borderDotItem.setSelection((styleState & BORDER_DOT) != 0);
	}
	/*************************************************************/
	private void initMenuBar(){
		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setText(getResourceString("File_menuitem")); //$NON-NLS-1$
		fileItem.setMenu(fileMenu);	
		
		MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
		editMenu = new Menu(shell, SWT.DROP_DOWN);
		editItem.setText(getResourceString("Edit_menuitem")); //$NON-NLS-1$
		editItem.setMenu(editMenu);
		
		MenuItem toolItem = new MenuItem(menuBar, SWT.CASCADE);
		toolMenu = new Menu(shell, SWT.DROP_DOWN);
		toolItem.setText(getResourceString("Tool_menuitem")); //$NON-NLS-1$
		toolItem.setMenu(toolMenu);
	}
	
	private void initFileMenu(){
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText(getResourceString("Open_menuitem")); //$NON-NLS-1$
		openItem.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent event) {
				responseMenuFileOpen();
			}					
		});
		
		final MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText(getResourceString("Save_menuitem")); //$NON-NLS-1$
		saveItem.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent event) {
				responseMenuFileSave();
			}											
		});
		
		fileMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent event){
				saveItem.setEnabled(fileName != null);
			}			 			
		});
		
		MenuItem saveAsItem = new MenuItem(fileMenu, SWT.PUSH);
		saveAsItem.setText(getResourceString("SaveAs_menuitem")); //$NON-NLS-1$
		saveAsItem.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent event) {
				responseMenuFileSaveAs();
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText(getResourceString("Exit_menuitem")); //$NON-NLS-1$
		exitItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseMenuFileExit();
			}
		});	

	}
	
	/*************************************************************/
	Image iCopy, iCut, iPaste;
	private void initEditMenuIcon(){
		iCut = loadImage(display, "cut"); //$NON-NLS-1$
		iCopy = loadImage(display, "copy"); //$NON-NLS-1$
		iPaste = loadImage(display, "paste"); //$NON-NLS-1$
	}
	
	public void freeEditMenuIcon(){
		iCut.dispose();
		iCut = null;
		iCopy.dispose();
		iCopy = null;
		iPaste.dispose();
		iPaste = null;
	}
	
	private void initEditMenu(){
		final MenuItem cutItem = new MenuItem(editMenu, SWT.PUSH);
		cutItem.setText(getResourceString("Cut_menuitem")); //$NON-NLS-1$
		cutItem.setImage(iCut);
		cutItem.setAccelerator(SWT.MOD1 | 'x');
		cutItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseMenuEditCut();
			}
		});

		final MenuItem copyItem = new MenuItem(editMenu, SWT.PUSH);
		copyItem.setText(getResourceString("Copy_menuitem")); //$NON-NLS-1$
		copyItem.setImage(iCopy);
		copyItem.setAccelerator(SWT.MOD1 | 'c');
		copyItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseMenuEditCopy();
			}
		});

		MenuItem pasteItem = new MenuItem(editMenu, SWT.PUSH);
		pasteItem.setText(getResourceString("Paste_menuitem")); //$NON-NLS-1$
		pasteItem.setImage(iPaste);
		pasteItem.setAccelerator(SWT.MOD1 | 'v');
		pasteItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseMenuEditPaste();
			}
		});

		new MenuItem(editMenu, SWT.SEPARATOR);
		final MenuItem selectAllItem = new MenuItem(editMenu, SWT.PUSH);
		selectAllItem.setText(getResourceString("SelectAll_menuitem")); //$NON-NLS-1$
		selectAllItem.setAccelerator(SWT.MOD1 | 'a');
		selectAllItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseMenuEditSelectAll();
			}
		});

		editMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent event) {
				int selectionCount = styledText.getSelectionCount();
				cutItem.setEnabled(selectionCount > 0);
				copyItem.setEnabled(selectionCount > 0);
				selectAllItem.setEnabled(selectionCount < styledText.getCharCount());
			}
		});
	}
	
	/*************************************************************/
	private void initToolMenu(){
		MenuItem wrapItem = new MenuItem(toolMenu, SWT.CHECK);
		wrapItem.setText(getResourceString("Wrap_menuitem")); //$NON-NLS-1$
		wrapItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				MenuItem item = (MenuItem) event.widget;
				boolean enabled = item.getSelection();
				styledText.setWordWrap(enabled);
				toolMenu.getItem(2).setEnabled(enabled);		//Justfy Menu Item
				toolMenu.getItem(4).setEnabled(enabled);		//Alignment Menu Item
				enableAlignmentItem(enabled);
			}
		});

		MenuItem justifyItem = new MenuItem(toolMenu, SWT.CHECK);
		justifyItem.setText(getResourceString("Justify_menuitem")); //$NON-NLS-1$
		justifyItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				MenuItem item = (MenuItem) event.widget;
				styledText.setJustify(item.getSelection());
				updateToolBar();//Will be override in subclass
			}
		});
		justifyItem.setEnabled(false);
		
		MenuItem setFontItem = new MenuItem(toolMenu, SWT.PUSH);
		setFontItem.setText(getResourceString("SetFont_menuitem")); //$NON-NLS-1$
		setFontItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseMenuToolSetFont();
			}
		});
		
		MenuItem alignmentItem = new MenuItem(toolMenu, SWT.CASCADE);
		alignmentItem.setText(getResourceString("Alignment_menuitem")); //$NON-NLS-1$
		Menu alignmentMenu = new Menu(shell, SWT.DROP_DOWN);
		alignmentItem.setMenu(alignmentMenu);
		
		final MenuItem leftAlignmentItem = new MenuItem(alignmentMenu, SWT.RADIO);
		leftAlignmentItem.setText(getResourceString("Left_menuitem")); //$NON-NLS-1$
		leftAlignmentItem.setSelection(true);
		leftAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.setAlignment(SWT.LEFT);
				updateToolBar();
			}
		});
		alignmentItem.setEnabled(false);
		final MenuItem centerAlignmentItem = new MenuItem(alignmentMenu, SWT.RADIO);
		centerAlignmentItem.setText(getResourceString("Center_menuitem")); //$NON-NLS-1$
		centerAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.setAlignment(SWT.CENTER);
				updateToolBar();
			}
		});
		MenuItem rightAlignmentItem = new MenuItem(alignmentMenu, SWT.RADIO);
		rightAlignmentItem.setText(getResourceString("Right_menuitem")); //$NON-NLS-1$
		rightAlignmentItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.setAlignment(SWT.RIGHT);
				updateToolBar();
			}
		});
		
		MenuItem editOrientationItem = new MenuItem(toolMenu, SWT.CASCADE);
		editOrientationItem.setText(getResourceString("Orientation_menuitem")); //$NON-NLS-1$
		Menu editOrientationMenu = new Menu(shell, SWT.DROP_DOWN);
		editOrientationItem.setMenu(editOrientationMenu);

		MenuItem leftToRightItem = new MenuItem(editOrientationMenu, SWT.RADIO);
		leftToRightItem.setText(getResourceString("LeftToRight_menuitem")); //$NON-NLS-1$
		leftToRightItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				styledText.setOrientation(SWT.LEFT_TO_RIGHT);
			}
		});
		leftToRightItem.setSelection(true);
		MenuItem rightToLeftItem = new MenuItem(editOrientationMenu, SWT.RADIO);
		rightToLeftItem.setText(getResourceString("RightToLeft_menuitem")); //$NON-NLS-1$
		rightToLeftItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.setOrientation(SWT.RIGHT_TO_LEFT);
			}
		});
		
		new MenuItem(toolMenu, SWT.SEPARATOR);
		MenuItem insertObjectItem = new MenuItem(toolMenu, SWT.CASCADE);
		insertObjectItem.setText(getResourceString("InsertObject_menuitem")); //$NON-NLS-1$
		Menu insertObjectMenu = new Menu(shell, SWT.DROP_DOWN);
		insertObjectItem.setMenu(insertObjectMenu);

		MenuItem insertControlItem = new MenuItem(insertObjectMenu, SWT.CASCADE);
		insertControlItem.setText(getResourceString("Controls_menuitem")); //$NON-NLS-1$
		Menu controlChoice = new Menu(shell, SWT.DROP_DOWN);
		insertControlItem.setMenu(controlChoice);

		MenuItem buttonItem = new MenuItem(controlChoice, SWT.PUSH);
		buttonItem.setText(getResourceString("Button_menuitem")); //$NON-NLS-1$
		MenuItem comboItem = new MenuItem(controlChoice, SWT.PUSH);
		comboItem.setText(getResourceString("Combo_menuitem")); //$NON-NLS-1$

		buttonItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Button button = new Button(styledText, SWT.PUSH);
				button.setText(getResourceString("Button_menuitem")); //$NON-NLS-1$
				addControl(button);
			}
		});
		comboItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Combo combo = new Combo(styledText, SWT.NONE);
				combo.setText(getResourceString("Combo_menuitem")); //$NON-NLS-1$
				addControl(combo);
			}
		});

		MenuItem insertImageItem = new MenuItem(insertObjectMenu, SWT.PUSH);
		insertImageItem.setText(getResourceString("Image_menuitem")); //$NON-NLS-1$

		insertImageItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				responseInsertImageSelect();
			}
		});

		if (SAMPLE_TEXT) {
			new MenuItem(toolMenu, SWT.SEPARATOR);
			MenuItem loadProfileItem = new MenuItem(toolMenu, SWT.CASCADE);
			loadProfileItem.setText(getResourceString("LoadProfile_menuitem")); //$NON-NLS-1$
			Menu loadProfileMenu = new Menu(shell, SWT.DROP_DOWN);
			loadProfileItem.setMenu(loadProfileMenu);
			SelectionAdapter adapter = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					int profile = Integer.parseInt((String) event.widget.getData());
					loadProfile(profile);
				}
			};
	
			MenuItem profileItem = new MenuItem(loadProfileMenu, SWT.PUSH);
			profileItem.setText(getResourceString("Profile1_menuitem")); //$NON-NLS-1$
			profileItem.setData("1"); //$NON-NLS-1$
			profileItem.addSelectionListener(adapter);
			profileItem = new MenuItem(loadProfileMenu, SWT.PUSH);
			profileItem.setText(getResourceString("Profile2_menuitem")); //$NON-NLS-1$
			profileItem.setData("2"); //$NON-NLS-1$
			profileItem.addSelectionListener(adapter);
			profileItem = new MenuItem(loadProfileMenu, SWT.PUSH);
			profileItem.setText(getResourceString("Profile3_menuitem")); //$NON-NLS-1$
			profileItem.setData("3"); //$NON-NLS-1$
			profileItem.addSelectionListener(adapter);
			profileItem = new MenuItem(loadProfileMenu, SWT.PUSH);
			profileItem.setText(getResourceString("Profile4_menuitem")); //$NON-NLS-1$
			profileItem.setData("4"); //$NON-NLS-1$
			profileItem.addSelectionListener(adapter);
		}
	}
	/*************************************************************/
	private void initUnderlineMenu(){
		underlineMenu = new Menu(shell, SWT.POP_UP);
		underlineSingleItem = new MenuItem(underlineMenu, SWT.RADIO);
		underlineSingleItem.setText(getResourceString("Single_menuitem")); //$NON-NLS-1$
		underlineSingleItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (underlineSingleItem.getSelection()) {
					setStyle(UNDERLINE_SINGLE);
				}
			}
		});
		underlineSingleItem.setSelection(true);

		underlineDoubleItem = new MenuItem(underlineMenu, SWT.RADIO);
		underlineDoubleItem.setText(getResourceString("Double_menuitem")); //$NON-NLS-1$
		underlineDoubleItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (underlineDoubleItem.getSelection()) {
					setStyle(UNDERLINE_DOUBLE);
				}
			}
		});

		underlineSquiggleItem = new MenuItem(underlineMenu, SWT.RADIO);
		underlineSquiggleItem.setText(getResourceString("Squiggle_menuitem")); //$NON-NLS-1$
		underlineSquiggleItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (underlineSquiggleItem.getSelection()) {
					setStyle(UNDERLINE_SQUIGGLE);
				}
			}
		});

		underlineErrorItem = new MenuItem(underlineMenu, SWT.RADIO);
		underlineErrorItem.setText(getResourceString("Error_menuitem")); //$NON-NLS-1$
		underlineErrorItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (underlineErrorItem.getSelection()) {
					setStyle(UNDERLINE_ERROR);
				}
			}
		});

		MenuItem underlineColorItem = new MenuItem(underlineMenu, SWT.PUSH);
		underlineColorItem.setText(getResourceString("Color_menuitem")); //$NON-NLS-1$
		underlineColorItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				ColorDialog dialog = new ColorDialog(shell);
				RGB rgb = underlineColor != null ? underlineColor.getRGB() : null;
				dialog.setRGB(rgb);
				RGB newRgb = dialog.open();
				if (newRgb != null) {
					if (!newRgb.equals(rgb)) {
						freeStyleResource(underlineColor);
						underlineColor = new Color(display, newRgb);					
					}
					if (underlineSingleItem.getSelection()) setStyle(UNDERLINE_SINGLE);
					else if (underlineDoubleItem.getSelection()) setStyle(UNDERLINE_DOUBLE);
					else if (underlineErrorItem.getSelection()) setStyle(UNDERLINE_ERROR);
					else if (underlineSquiggleItem.getSelection()) setStyle(UNDERLINE_SQUIGGLE);
				}
			}
		});

	}
	
	private void initBorderMenu(){
		borderMenu = new Menu(shell, SWT.POP_UP);
		borderSolidItem = new MenuItem(borderMenu, SWT.RADIO);
		borderSolidItem.setText(getResourceString("Solid")); //$NON-NLS-1$
		borderSolidItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				if (borderSolidItem.getSelection()) {
					setStyle(BORDER_SOLID);
				}
			}
		});
		borderSolidItem.setSelection(true);
		
		borderDashItem = new MenuItem(borderMenu, SWT.RADIO);
		borderDashItem.setText(getResourceString("Dash")); //$NON-NLS-1$
		borderDashItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				if (borderDashItem.getSelection()) {
					setStyle(BORDER_DASH);
				}
			}
		});
		
		borderDotItem = new MenuItem(borderMenu, SWT.RADIO);
		borderDotItem.setText(getResourceString("Dot")); //$NON-NLS-1$
		borderDotItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				if (borderDotItem.getSelection()) {
					setStyle(BORDER_DOT);
				}
			}
		});
		
		MenuItem borderColorItem = new MenuItem(borderMenu, SWT.PUSH);
		borderColorItem.setText(getResourceString("Color_menuitem")); //$NON-NLS-1$
		borderColorItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				ColorDialog dialog = new ColorDialog(shell);
				RGB rgb = borderColor != null ? borderColor.getRGB() : null;
				dialog.setRGB(rgb);
				RGB newRgb = dialog.open();
				if (newRgb != null) {
					if (!newRgb.equals(rgb)) {
						freeStyleResource(borderColor);
						borderColor = new Color(display, newRgb);
					}
					if (borderDashItem.getSelection()) setStyle(BORDER_DASH);
					else if (borderDotItem.getSelection()) setStyle(BORDER_DOT);
					else if (borderSolidItem.getSelection()) setStyle(BORDER_SOLID);
				}
			}
		});

	}
	/*************************************************************/
	void createMenuBar(){
	
		initMenuBar();
		initFileMenu();
		initEditMenuIcon();
		initEditMenu();
		initToolMenu();
		
		initUnderlineMenu();
		initBorderMenu();
		
	}
	/*************************************************************/
	void createMemuPopup() {
		menuPopUp = new Menu (styledText);
		final MenuItem cutItem = new MenuItem (menuPopUp, SWT.PUSH);
		cutItem.setText (getResourceString("Cut_menuitem")); //$NON-NLS-1$
		cutItem.setImage(iCut);
		cutItem.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				responseMenuEditCut();
			}
		});
		final MenuItem copyItem = new MenuItem (menuPopUp, SWT.PUSH);
		copyItem.setText (getResourceString("Copy_menuitem")); //$NON-NLS-1$
		copyItem.setImage(iCopy);
		copyItem.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				responseMenuEditCopy();
			}
		});
		final MenuItem pasteItem = new MenuItem (menuPopUp, SWT.PUSH);
		pasteItem.setText (getResourceString("Paste_menuitem")); //$NON-NLS-1$
		pasteItem.setImage(iPaste);
		pasteItem.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				responseMenuEditPaste();
			}
		});
		new MenuItem (menuPopUp, SWT.SEPARATOR);
		final MenuItem selectAllItem = new MenuItem (menuPopUp, SWT.PUSH);
		selectAllItem.setText (getResourceString("SelectAll_menuitem")); //$NON-NLS-1$
		selectAllItem.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				responseMenuEditSelectAll();
			}
		});
		menuPopUp.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent event) {
				int selectionCount = styledText.getSelectionCount();
				cutItem.setEnabled(selectionCount > 0);
				copyItem.setEnabled(selectionCount > 0);
				selectAllItem.setEnabled(selectionCount < styledText.getCharCount());
			}
		});
		styledText.setMenu(menuPopUp);
	}
	/*************************************************************/
}
