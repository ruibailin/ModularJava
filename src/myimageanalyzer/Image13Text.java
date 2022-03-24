package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;

public class Image13Text extends Image12Label{

	//Add Styled Text
	StyledText dataText;
	
	static final int INDEX_DIGITS = 4;
	static final int ALPHA_CHARS = 5;
	public Image13Text() {
		
	}
	
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area:must be public and need to be override
	public void resTextMouseDown(MouseEvent event){
		debugInfo("Text: MouseEvent \n");
	}
	
	public void resTextKeyPressed(KeyEvent event){
		debugInfo("Text: KeyEvent \n");
	}
	/*************************************************************/
	//Framework functions area:called by public or private functions
	
	/*************************************************************/
	//Private functions area:called by public functions
	private void iniStyledText(){
		// Text to show a dump of the data.
		GridData gridData;
		dataText = new StyledText(shell, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);
		dataText.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		dataText.setFont(fixedWidthFont);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.heightHint = 128;
		gridData.grabExcessVerticalSpace = true;
		dataText.setLayoutData(gridData);


	}
	
	private void insListenerMouse(){
		dataText.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				if (image != null && event.button == 1) {
					resTextMouseDown(event);
				}
			}
		});
	}
	
	private void insListenerKey(){
		dataText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (image != null) {
					resTextKeyPressed(event);
				}
			}
		});
	}
	
	private void setStyledText(String data){

		dataText.setText(data);
		// bold the first column all the way down
		int index = 0;
		while((index = data.indexOf(':', index+1)) != -1) {
			int start = index - INDEX_DIGITS;
			int length = INDEX_DIGITS;
			if (Character.isLetter(data.charAt(index-1))) {
				start = index - ALPHA_CHARS;
				length = ALPHA_CHARS;
			}
			dataText.setStyleRange(new StyleRange(start, length, dataText.getForeground(), dataText.getBackground(), SWT.BOLD));
		}
	}
	/*************************************************************/
	//Public functions area:called by main function
	public void initializeText(){
		iniStyledText();
		insListenerMouse();
		insListenerKey();
	}
	
	public void updateText(String data){
		setStyledText(data);
	}
	
	/*************************************************************/
}
