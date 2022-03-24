package cveDisplay;

import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CVE01Shell extends CVE00Null{
	
	Display display;
	Shell shell;
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

	public void resShellKeyPressed(KeyEvent event){
		debugInfo("Shell:Key Pressed Event! \n");
	}
	
	public void resShellKeyReleased(KeyEvent event){
		debugInfo("Shell:Key Released Event! \n");
	}
	
	public void resShellMouseEvent(MouseEvent event){
		debugInfo("Shell:Mouse Event! \n");
	}
	
	public void resShellMouseDoubleClick(MouseEvent event){
		debugInfo("Shell:Mouse Double Click Event! \n");
	}
	
	public void resShellMouseDown(MouseEvent event){
		debugInfo("Shell:Mouse Down Event! \n");
	}
	
	public void resShellMouseUp(MouseEvent event){
		debugInfo("Shell:Mouse Up Event! \n");
	}
	
	public void resShellPaintEvent(PaintEvent event){
		debugInfo("Shell:Paint Event! \n");
	}
	/*************************************************************/
	//Framework for install Event Listener
	
	//Shell Event
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
	
	//Key Event
	private void insListenerKey(){
		shell.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				resShellKeyPressed(event);
			}
			public void keyReleased(KeyEvent event) {
				resShellKeyReleased(event);
			}
			
		});
		
	}
	
	//Mouse Event
	private void insListenerMouseMove(){
		shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				resShellMouseEvent(event);
			}
		});
	}
	
	private void insListenerMouse(){

		shell.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent event){
				resShellMouseDoubleClick(event);
			}
			
			public void mouseDown(MouseEvent event){
				resShellMouseDown(event);
			}
			
			public void mouseUp(MouseEvent event){
				resShellMouseUp(event);
			}
			
		});
	}

	//Paint
	private void insListenerPaint(){
		shell.addPaintListener(new PaintListener(){

			@Override
			public void paintControl(PaintEvent event) {
				resShellPaintEvent(event);
			}
			
		});
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
		insListenerKey();
		insListenerMouseMove();
		insListenerMouse();
		insListenerPaint();
	}
	
	public void displayShell() {
		shell.setText(getResourceString("Window_title")); //$NON-NLS-1$
		String ww= getResourceString("Window_W");
		String wh= getResourceString("Window_H");
		shell.setSize(Integer.parseInt(ww), Integer.parseInt(wh));
		shell.open();
	}
	
	public void initializeShellResource() {

	}
	
	public void scheduleShell() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
		
	public void disposeShellResource() {

	}
	
	public void disposeShell(){
		display.dispose();
	}
}
