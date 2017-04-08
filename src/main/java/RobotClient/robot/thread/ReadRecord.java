package RobotClient.robot.thread;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Hashtable;
import RobotClient.robot.ground.Info;
import RobotClient.mainpanelistener.lb_robots;
import RobotClient.robot.net.ClientServer;
import RobotClient.robot.ground.WriteFile;
import RobotClient.robot.listener.MyActionListener;
public class ReadRecord extends Thread
{
	private File file=null;
	private BufferedReader br=null;
	private String str=null;
	private Hashtable<Integer,String> hash=new Hashtable<Integer,String>();
	private lb_robots main;
	private String line=null;
	private WriteFile wf;
	public ReadRecord(String str)
	{
		this.str=str;//用户提出的问题
		System.out.println("string"+str);

	}
	public void run()
	{
		int i=0;
		try
		{
			file=new File("content/"+Info.userID+"_xunlian"+".txt");
			if (file.exists())
			{
				br=new BufferedReader(new FileReader(file));
				while ((line=br.readLine())!=null)
				{
					System.out.println("line="+line);
					hash.put(i,line);
					i++;
				}//while end
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		boolean bool=hash.contains(str);
		if (bool)
		{
			System.out.println("已经训练过");
		}
		else
		{
			wf=new WriteFile();
			wf.writefile(str,"xunlian");
			System.out.println("第一次训练");
			str="1*"+Info.STatus+"/"+Info.userID+"/"+str+"/";
			//new Thread(new ClientServer(str,main)).start();
			MyActionListener.sentMessage(str);
		}
	}
}