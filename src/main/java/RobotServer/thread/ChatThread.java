package RobotServer.thread;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
public class ChatThread implements Runnable
{
	private Socket socket=null;
	private DataInputStream in=null;
	private String str=null;
	public ChatThread(Socket socket)
	{
		this.socket=socket;
		try
		{
			in=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		}
		catch (IOException r)
		{
			r.printStackTrace();
		}
		catch(Exception r)
		{}
	}
	public void run()
	{	
		try
		{
			while (true)
			{
				
				str=in.readUTF();
				new Chat(str,socket).start();
			}
		}
		catch (IOException e)
		{
		//	e.printStackTrace();
		}
	}
}