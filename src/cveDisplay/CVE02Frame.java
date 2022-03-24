package cveDisplay;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class CVE02Frame extends CVE01Shell{
	static int FRAME_WIDTH=720;
	static int FRAME_HIGTH=576;
	byte[] frame;
	int post_x;
	int post_y;
	
	/*************************************************************/
	@Override
	public void resShellPaintEvent(PaintEvent event){
		GC gc = event.gc;
		int i,j,k;
		int yuv_y;
		
		k=0;
    	for(i=0;i<FRAME_HIGTH;i++)
    	{
        	for(j=0;j<FRAME_WIDTH;j++)
        	{
        		yuv_y=frame[k];
        		yuv_y = yuv_y&0x00ff;
        		Color color=new Color(display,yuv_y,yuv_y,yuv_y);
        		gc.setForeground(color);
        		gc.drawPoint(post_x+j, post_y+i);
        		color.dispose();
        		k++;
        	}
    	}
	}
	

	/*************************************************************/
	//functions called by main function
	public void initializeFrame() {

		frame= new byte[FRAME_WIDTH*FRAME_HIGTH];
		post_x=0;
		post_x=0;
    	int i,j,k;
    	k=0;
    	byte val=0;
    	for(i=0;i<FRAME_HIGTH;i++)
    	{
        	for(j=0;j<FRAME_WIDTH;j++)
        	{
        		frame[k]=val;
        		k++;
        	}
        	val++;
    	}
	}
	
	public void releaseFrame() {

	}
}
