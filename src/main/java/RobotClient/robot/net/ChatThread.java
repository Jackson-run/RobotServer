package RobotClient.robot.net;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */

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
				str=in.readUTF();
				System.out.println("接受的信息="+str);
				new Thread(new ClientServer(str)).start();
			}

		}
		catch (IOException e)
		{
			//		e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("未连接上服务器");
		}
	}
}
