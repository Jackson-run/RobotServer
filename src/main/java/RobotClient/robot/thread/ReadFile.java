package RobotClient.robot.thread;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.text.SimpleDateFormat;
import RobotClient.robot.ground.Info;
import RobotClient.listener.lb_robots;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */
public class ReadFile extends Thread
{
	private File file=null;
	private BufferedReader br=null;
	private String string=null;
	private Hashtable<Integer,String> hash1=new Hashtable<Integer,String>();
	private Hashtable<String,Integer> hash=new Hashtable<String,Integer>();
	private Hashtable<Integer,String> hash2=new Hashtable<Integer,String>();
	private lb_robots main;
	private String line=null;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期的格式
	public ReadFile(String string)
	{
		//	this.main=main;
		this.string=string;//用户提出的问题

		System.out.println("string"+string);
	}
	public void run()
	{
		int i=0;
		int j=0;
		try
		{
			file=new File("content/"+Info.userID+"_jilu"+".txt");
			if (file.exists())
			{
				br=new BufferedReader(new FileReader(file));
				while ((line=br.readLine())!=null)
				{
					System.out.println("line="+line);
					try
					{
						line=line.substring(2,line.length());
						if (line.startsWith("我"))
						{
							int index=line.indexOf("  ");
							line=line.substring(index+2,line.length());
							i++;
							hash1.put(i,line);
							hash.put(line,i);
						}
						else if (line.startsWith("蓝宝"))
						{
							int index=line.indexOf("  ");
							line=line.substring(index+2,line.length());
							j++;
							hash2.put(j,line);
						}
					}
					catch (Exception r)
					{
						r.printStackTrace();
					}
				}//while end
			}
		}
		catch (IOException e)
		{
			//		e.printStackTrace();
		}
		boolean bool=hash1.contains(string);
		if (bool)
		{
			int k=hash.get(string);
			System.out.println("在本地找到相关的记录");
			Info.Yetcount=0;
			lb_robots.insertDocument("\n蓝宝"+sdf.format(new Date())+"\n  ",Color.blue);
			lb_robots.insertDocument(hash2.get(k), Color.black);
			Info.Isfound=false;
		}
		else
		{
			System.out.println("没有找到相关记录");
			//	notifyAll();
			Info.Isfound=true;
		}

	}
}