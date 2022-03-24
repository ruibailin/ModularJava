package mytexteditor;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Main00Shell extends Editor00Shell {

	public Main00Shell() {

	}

		public static void main(String[] args) {

		    final Display display = new Display();

		    final Shell shell = new Shell(

		        display,

		        SWT.SHELL_TRIM | SWT.NO_REDRAW_RESIZE);

		    final Random rand =

		        new Random(System.currentTimeMillis());

		    final Color[] colors = new Color[16];

		    for (int i=0;i<colors.length; ++i) 
		    {

		        colors[i] = new Color (display, rand.nextInt(256),rand.nextInt(256), rand.nextInt(256));

		    }

		    shell.addListener(SWT.Paint, new Listener() {

		        public void handleEvent(Event event) {

		            GC gc = event.gc;

		            gc.setBackground(colors[rand.nextInt(colors.length)]);

		            gc.fillRectangle(shell.getClientArea());

		        }});

		    shell.setText("Clipping Region");

		    shell.setSize(250, 150);

		    shell.open();

		    while (!shell.isDisposed()) {

		        if (!display.readAndDispatch())

		            display.sleep();

		    }

		    for (int i=0; i<colors.length; ++i) {

		        colors[i].dispose();

		    }

		    display.dispose();

		}


}
