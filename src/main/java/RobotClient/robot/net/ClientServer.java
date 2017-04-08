package RobotClient.robot.net;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import RobotClient.mainpanelistener.lb_robots;
import RobotClient.robot.ground.Info;
import RobotClient.robot.ground.WriteFile;
import java.awt.Color;
import RobotClient.robot.thread.ShowImage;
public class ClientServer implements Runnable
{
	/*
	*处理接收服务端信息并显示
	*/
	private WriteFile wf=null;
	private SimpleDateFormat sdf=null;
	private String line=null;
	private String shu="";//
	public ClientServer(String line)
	{
		this.line=line;
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		wf=new WriteFile();
	}
	public void run()
	{
		try{
			if (line.startsWith("ZA*"))//表示在对用户状态测试，返回状态值
			{
				line=line.substring(3,line.length());
				shu=line.substring(line.length()-1,line.length());//将状态提取出来
				line=line.substring(0,line.length()-1);
				Info.bool=true;
				Info.answer=line;
			}
			else if (line.startsWith("SN*"))//
			{
				line=line.substring(3,line.length());
				if (!line.equals("false"))
				{
					int index=line.indexOf("$");
					shu=line.substring(index-1,index);//将状态提取出来
					Info.ID=line.substring(index+1,line.length());
					line=line.substring(0,index-1);
					Info.bool=true;
					Info.answer=line;
				}
				else
				{
					Info.bool=false;
				}
			}
			else if (line.startsWith("YES*"))//找到答案
			{
				line=line.substring(4,line.length());
				System.out.println(line);
				Info.dialog=null;
				//	try{
				shu=line.substring(line.length()-1,line.length());//将状态提取出来
				line=line.substring(0,line.length()-1);
				//	}catch(Exception e){}
				//显示答案
				System.out.println("line:");
				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ",Color.blue);
				lb_robots.insertDocument(line, Color.black);
				//写入文件
				wf.writefile("  我:"+sdf.format(new Date())+"  "+Info.Chat,"jilu");
				String sline="  球球:"+sdf.format(new Date())+"  "+line;
				wf.writefile(sline,"jilu");
			}
			else if (line.startsWith("YET*"))//模糊反问
			{
				line=line.substring(4,line.length());

				shu=line.substring(line.length()-1,line.length());//将状态提取出来
				line=line.substring(0,line.length()-1);

				int i=line.indexOf("#");
				String lines=line.substring(0,i);
				Info.dialog=line.substring(i+1,line.length());
				Info.quest=lines;
				line=lines+"?";
				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
				lb_robots.insertDocument(line, Color.black);
			}
			else if (line.startsWith("NO*"))//没有找到答案
			{
				line=line.substring(3,line.length());

				shu=line.substring(line.length()-1,line.length());//将状态提取出来
				line=line.substring(0,line.length()-1);
				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
				lb_robots.insertDocument(line, Color.black);
				Info.dialog=null;
			}
			else if (line.startsWith("P*"))//找到地名显示图片
			{
				line=line.substring(2,line.length());
				shu=line.substring(line.length()-1,line.length());//将状态提取出来
				line=line.substring(0,line.length()-1);
				String[] stringA=line.split("/");
				String path=stringA[0];
				String introduce=stringA[1];
				new ShowImage(path,introduce);
				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
				lb_robots.insertDocument(introduce, Color.black);
			}
			if (Info.TCount<4)
			{
				System.out.println(shu);
				if (shu.equals("7"))
				{
					Info.count2++;
				}
				else if (shu.equals("6"))
				{
					Info.count++;
				}
				else if (shu.equals("5"))
				{
					Info.count3++;
				}
				else if (shu.equals("4"))
				{
					Info.count4++;
				}
				else if (shu.equals("3"))
				{
					Info.count5++;
				}
				else if (shu.equals("2"))
				{
					Info.count6++;
				}
			}
		}catch(Exception r)
		{}
	}
}