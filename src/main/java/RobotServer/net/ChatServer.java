package RobotServer.net;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import RobotServer.thread.ChatThread;
public class ChatServer extends Thread
{
	private ServerSocket ss = null;
	private Socket socket=null;
	public static int count=0; 
	public ChatServer()
	{
		try
		{
			ss=new ServerSocket(5525);
			System.out.println("等待连接请求。。。。");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void run()
	{
		try
		{			
			while (true)
			{
				socket=ss.accept();
				new Thread(new ChatThread(socket)).start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new ChatServer().start();
	}
}