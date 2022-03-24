package myimageanalyzer;

import org.eclipse.swt.events.ShellEvent;

public class Image02Thread extends Image01Shell{
	
	boolean animate = false; // used to animate a multi-image file
	Thread animateThread; // draws animated images
	boolean incremental = false; // used to incrementally display an image
	Thread incrementalThread; // draws incremental images
	
	public Image02Thread() {

	}
	/*************************************************************/	
	//Static functions area
	

	/*************************************************************/	
	//Override function area for Thread procedure
	@Override
	public void resShellClosedEvent(ShellEvent event){
		super.resShellClosedEvent(event);
		debugInfo("Thread:Shell Closed Event! \n");
		animate = false; // stop any animation in progress
		if (animateThread != null) {
			// wait for the thread to die before disposing the shell.
			while (animateThread.isAlive()) {
				if (!display.readAndDispatch()) display.sleep();
			}
		}
		event.doit = true;
	}
	
	public void runThreadAnimateProc(){
		debugInfo("Thread:Animate Thread Run! \n");
	}
	
	public void runThreadIncrementalProc(){
		debugInfo("Thread:Incremental Thread Run! \n");
	}
	/*************************************************************/
	//Framework for initialize thread
	private void iniAnimateThread(){
		animate = true;
		animateThread = new Thread(bundle.getString("Animation")) {
			public void run() {
				runThreadAnimateProc();
			}
		};
		animateThread.setDaemon(true);
		animateThread.start();
	}
	
	private void iniIncrementalThread(){
		incremental = true;
		incrementalThread = new Thread("Incremental") {
			public void run() {
				runThreadIncrementalProc();
			}
		};
		incrementalThread.setDaemon(true);
		incrementalThread.start();
	}
	
	/*************************************************************/
	//functions called by main function
	public void initializeThread() {
		iniAnimateThread();
		iniIncrementalThread();
	}
	//in order to make use of old name
	public void initializeShellThread() {
		iniAnimateThread();
		iniIncrementalThread();
	}
}
