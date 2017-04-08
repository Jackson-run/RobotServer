package RobotClient.robot.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import RobotClient.robot.ground.Info;
public class LinkServer extends Thread
{
	private Socket socket=null;
	public LinkServer()
	{
		try
		{
			socket=new Socket("127.0.0.1",5525);
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