package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class Image03Menu extends Image02Thread{

	//Add Menu
	Menu menuBar;
	Menu fileMenu,alphaMenu;
	
	static final int ALPHA_CONSTANT = 0;
	static final int ALPHA_X = 1;
	static final int ALPHA_Y = 2;
	
	public Image03Menu() {

	}

	/*************************************************************/
	//Override function area for Menu response procedure
	public void menuFileOpen(){
		debugInfo("Menu: Open File! \n");
	}
	public void menuFileOpenURL(){
		debugInfo("Menu: Open URL! \n");
	}
	public void menuFileReopen(){
		debugInfo("Menu: Reopen! \n");
	}
	public void menuFileLoad(){
		debugInfo("Menu: Load! \n");
	}
	public void menuFileSave(){
		debugInfo("Menu: Save! \n");
	}
	public void menuFileSaveAs(){
		debugInfo("Menu: Save AS! \n");
	}
	public void menuFileSaveMaskAs(){
		debugInfo("Menu: Save Mask As! \n");
	}
	public void menuFilePrint(){
		debugInfo("Menu: Print! \n");
	}
	public void menuFileClose(){
		debugInfo("Menu: Close! " + "\n");
	}
	
	public void menuAlphaCompose(int alpha){
		debugInfo("Menu: Alpha! " + alpha + "\n");
	}
	/*************************************************************/
	private void createMenuBar(){
		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText(bundle.getString("File"));
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(fileMenu);
		
		item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText(bundle.getString("Alpha"));
		alphaMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(alphaMenu);
	}
	
	private void createFileMenu(){
		// File -> Open File...
		MenuItem item;
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("OpenFile"));
		item.setAccelerator(SWT.MOD1 + 'O');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileOpen();
			}
		});
		
		// File -> Open URL...
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("OpenURL"));
		item.setAccelerator(SWT.MOD1 + 'U');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileOpenURL();
			}
		});
		
		// File -> Reopen
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("Reopen"));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileReopen();
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);

		// File -> Load File... (natively)
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("LoadFile"));
		item.setAccelerator(SWT.MOD1 + 'L');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileLoad();
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Save
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("Save"));
		item.setAccelerator(SWT.MOD1 + 'S');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileSave();
			}
		});
		
		// File -> Save As...
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("Save_as"));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileSaveAs();
			}
		});
		
		// File -> Save Mask As...
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("Save_mask_as"));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileSaveMaskAs();
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Print
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("Print"));
		item.setAccelerator(SWT.MOD1 + 'P');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFilePrint();
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);

		// File -> Exit
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText(bundle.getString("Exit"));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFileClose();
			}
		});
	}
	
	private void createAlphaMenu(){
		MenuItem item;
		// Alpha -> K
		item = new MenuItem(alphaMenu, SWT.PUSH);
		item.setText("K");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuAlphaCompose(ALPHA_CONSTANT);
			}
		});
		
		// Alpha -> (K + x) % 256
		item = new MenuItem(alphaMenu, SWT.PUSH);
		item.setText("(K + x) % 256");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuAlphaCompose(ALPHA_X);
			}
		});

		// Alpha -> (K + y) % 256
		item = new MenuItem(alphaMenu, SWT.PUSH);
		item.setText("(K + y) % 256");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuAlphaCompose(ALPHA_Y);
			}
		});
	}
	/*************************************************************/
	//functions called by main function
	public void initializeMenu(){
		createMenuBar();
		createFileMenu();
		createAlphaMenu();
	}
	
	
}
