package cveDisplay;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class CVE03Client extends CVE02Frame{
	private  static final String IP_ADDR = "127.0.0.1";//服务器地址   
	private  static final int PORT = 8000;//服务器端口号    
	private static final String magic="Received!";
	private  Socket clientSocket;
    // 用于读取服务端数据    
    private DataInputStream clientinput;  
    // 用于响应服务端数据    
    private DataOutputStream clientout;   
    
	/**************************************************/   
    private void init() 
    {
    	try 
    	{
            InetAddress ip_addr=InetAddress.getByName(IP_ADDR);
    		//创建一个流套接字并将其连接到指定主机上的指定端口号  
            clientSocket = new Socket(ip_addr, PORT);
    	}
        catch (Exception e) 
        {    
        System.out.println("IVE client fails to init socket!" + e.getMessage());
        }
    }
    /**************************************************/ 
    private void bind()
    {
    	try
    	{
            clientinput = new DataInputStream(clientSocket.getInputStream());  
            clientout = new DataOutputStream(clientSocket.getOutputStream());    
    	}
        catch (Exception e) 
        {    
            System.out.println("IVE client fails to bind socket!: " + e.getMessage());
        }    
    }
    /**************************************************/ 
    private void close() 
    {    
        try 
        {    
            System.out.println("IVE client to close IO stream!");
            clientout.close();    
            clientinput.close();    

            if(clientSocket == null)
        		return;
            System.out.println("IVE client to close socket!");
            clientSocket.close(); 
            clientSocket = null;
        } 
        catch (Exception e) 
        {    
            System.out.println("IVE client fails to close socket!: " + e.getMessage());
        }    
    }  
    /**************************************************/  
    private void recvBuffer(byte[] buf, int off, int size) 
    {    
    	if(clientSocket == null)
    		return;
        System.out.println("Client begins to recieve\n");
        try 
        {    
            int rec_len;
            //这里要注意和服务端输出流的写方法对应,包括客户端向服务端写的方法，否则会抛 EOFException
            rec_len = clientinput.read(buf,off,size);
            if(rec_len != size)
                System.out.println("Recieved wrong\n");
            else
                System.out.println(".");    

            byte[] sbuf=magic.getBytes();
            clientout.write(sbuf,0,magic.length());    //Send feedback
        } 
        catch (Exception e) 
        {    
            System.out.println("Client fails to recieve a buffer " + e.getMessage());    
        } 
    }    
    
    /**************************************************/
    private void recvFrame(byte[] frame, int width, int height) 
    {    
    	if(clientSocket == null)
    		return;
    	System.out.println("Client begins to recieve\n");
    	try 
        {    
            byte[] sbuf=magic.getBytes();

            int rec_len, i,j;
            j=0;
            for(i=0;i<height;i++)
            {
            	rec_len = clientinput.read(frame,j,width);     //size must be width
                if(rec_len != width)
                    System.out.println("Recieved wrong\n");
                else
                    System.out.println(".");    
                clientout.write(sbuf,0,magic.length());    //Send feedback
            	j += width;
            }
        } 
        catch (Exception e) 
        {    
            System.out.println("Client fails to recieve a frame " + e.getMessage());    
        } 
    }
    
    /**************************************************/ 
    private void sendBuffer(byte[] buf, int off, int size) 
    {      
        System.out.println("Client begins to send\n");  
        try
        {
        	clientout.write(buf,off,size);
        	
        	byte[] rbuf=magic.getBytes();
        	int rec_len;
            rec_len=clientinput.read(rbuf,0,magic.length());
        	if(rec_len < 1)
                System.out.println("Handshake wrong\n");
            else
                System.out.println("."); 
        } 
        catch (Exception e) 
        {  
            System.out.println("Client fails to send a buffer " + e.getMessage());    
         }
    }    
    
    /**************************************************/ 
    private void sendFrame(byte[] frame, int width, int heigth) 
    {      
        System.out.println("Client begins to send\n");  
        try
        {
        	byte[] rbuf=magic.getBytes();
        	int rec_len;
        	int i,j;
            j=0;
            for(i=0;i<heigth;i++)
            {
            	clientout.write(frame,j,width);
            	rec_len=clientinput.read(rbuf,0,magic.length());
            	if(rec_len < 1)
            		System.out.println("Handshake wrong\n");
            	else
            		System.out.println(".");
            	j += width;
            }
        } 
        catch (Exception e) 
        {  
            System.out.println("Client fails to send a frame " + e.getMessage());   
         }
    }        
    
    /**************************************************/ 
    @Override
	public void initializeFrame() {

		frame= new byte[FRAME_WIDTH*FRAME_HIGTH];
		post_x=0;
		post_x=0;
		
		init();
		bind();
		recvFrame(frame,FRAME_WIDTH,FRAME_HIGTH);
		close();
	}
}
