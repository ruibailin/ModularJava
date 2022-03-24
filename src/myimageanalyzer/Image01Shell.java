package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Image01Shell extends Image00Null{
	
	Display display;
	Shell shell;

	Color whiteColor, blackColor, redColor, greenColor, blueColor, canvasBackground;
	Font fixedWidthFont;
	Cursor crossCursor;
	
	public Image01Shell() {
		
	}
	/*************************************************************/	
	//Static functions area

	/*************************************************************/
	//Override function area for event response procedure
	public void resShellResizedEvent(ControlEvent event){
		debugInfo("Shell:Control Resized Event! \n");
	}
	
	public void resShellClosedEvent(ShellEvent event){
		debugInfo("Shell:Shell Closed Event! \n");
	}
	
	public void resShellDisposedEvent(DisposeEvent event){
		debugInfo("Shell:Widget Disposed Event! \n");
	}

	/*************************************************************/
	//Framework for install Event Listener
	private void insListenerControl(){
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent event) {
				resShellResizedEvent(event);
			}
		});
	}
	
	private void insListenerShell(){
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent event) {
				resShellClosedEvent(event);
			}
		});
	}
	
	private void insListenerDispose(){
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				resShellDisposedEvent(event);
			}
		});
	}
	/*************************************************************/
	//Initialize and dispose other resource
	private void iniShellColor(){
		whiteColor = new Color(display, 255, 255, 255);
		blackColor = new Color(display, 0, 0, 0);
		redColor = new Color(display, 255, 0, 0);
		greenColor = new Color(display, 0, 255, 0);
		blueColor = new Color(display, 0, 0, 255);
	}
	
	private void relShellColor(){
		whiteColor.dispose();
		blackColor.dispose();
		redColor.dispose();
		greenColor.dispose();
		blueColor.dispose();
	}
	
	private void iniShellFont(){
		fixedWidthFont = new Font(display, "courier", 10, 0);
	}
	
	private void relShellFont(){
		fixedWidthFont.dispose();		
	}
	
	private void iniShellCursor(){
		crossCursor = display.getSystemCursor(SWT.CURSOR_CROSS);
	}
	
	private void relShellCursor(){
		
	}
	/*************************************************************/
	//functions called by main function
	public void initializeShell(Display dis,Shell she) {
		initializeNullResource();
		display=dis;
		shell=she;
	}
	
	public void installShellListener() {
		insListenerControl();
		insListenerShell();
		insListenerDispose();
	}
	
	
	public void displayShell() {
		shell.setText(getResourceString("Window_title")); //$NON-NLS-1$
		shell.setSize(1000, 700);
		shell.open();
	}
	
	public void initializeShellResource() {
		iniShellColor();
		iniShellFont();
		iniShellCursor();
	}
	
	public void scheduleShell() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
		
	public void disposeShellResource() {
		relShellColor();
		relShellFont();
		relShellCursor();
	}
	
	public void disposeShell(){
		display.dispose();
	}
	/*************************************************************/
	
}
