package RobotClient.robot.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import RobotClient.robot.ground.Info;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */

public class LinkServer extends Thread
{
	private Socket socket=null;
	public LinkServer()
	{
		try
		{
			socket=new Socket("localhost",5525);
		}
		catch (IOException r)
		{}
		catch(Exception e)
		{}
	}
	public void run()
	{
		Info.socket=socket;
		new Thread(new ChatThread(socket)).start();
	}
}