package RobotClient.robot.thread;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.Date;
import RobotClient.robot.ground.WriteFile;
import RobotClient.robot.listener.MyActionListener;
import RobotClient.robot.listener.MyException;
import RobotClient.mainpanelistener.lb_robots;
import RobotClient.robot.ground.Info;
import java.awt.Color;
public class JudgeThread
{
	private String str=null;
	private String reg="[啊吖哦喔嗯噢额呃呢呐吗嘛呀啧哇咋]";
	private String fu="[……【】￥（）·`？！。；：‘’，“”《》、——]";
	private String fuhao="[\\s\\d\\w\\p{Punct}]+";

	Pattern p=Pattern.compile(fu);
	private WriteFile wf=new WriteFile();
	private SimpleDateFormat sdf=null;
	public JudgeThread(String str)
	{
		this.str=str;
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		run();
	}
	public void run()
	{
		Info.TCount++;

		if (str.startsWith("#"))
		{
			int flag=1;
			String strs=str.replace("#","");
			if (str.length()-strs.length()!=2)
			{
				flag=0;
			}
			else
			{
				String[] ss=str.split("#");
				if (ss[1].equals("")||ss[1]==null)
					flag=0;
				else{
					String str0=str.replaceAll(fuhao,"");
					Matcher m=p.matcher(str0);
					str0=m.replaceAll("");
					if (str0.length()==0)
					{
						flag=0;
					}
				}
			}
			try{
				f(flag);
				String str1=str.substring(1,str.length());

				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
				lb_robots.insertDocument("谢谢，学习了！", Color.black);

				int index=str1.indexOf("#");
				String chat=str1.substring(0,index);
				String str2=str1.substring(index+1,str1.length());
				System.out.println("chat="+chat);
				//将训练的内容写入txt文件中
				wf.writefile("  我:"+sdf.format(new Date())+"  "+chat,"jilu");
				wf.writefile("  球球:"+sdf.format(new Date())+"  "+str2,"jilu");
				Info.bool=false;
				new ReadRecord(str).start();
			}
			catch(Exception r)
			{
				System.out.println("训练失败");
				sd(str);
			}
		}
		//不是训练
		else if(!str.equals(Info.string))
		{
			Info.string=str;

			if (Info.dialog!=null)			//进行反问后的判断
			{
				String s0=str.replaceAll(fuhao,"");
				Matcher m=p.matcher(s0);
				s0=m.replaceAll("");
				if (s0.startsWith("是的")||s0.startsWith("是")||s0.startsWith("恩")||s0.startsWith("嗯"))
				{
					lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
					lb_robots.insertDocument(Info.dialog, Color.black);
				}
				else
				{
					sd(str);
				}
				Info.dialog=null;
			}
			else if (Info.bool)//初次建立查询是否成功
			{

				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
				lb_robots.insertDocument(Info.answer, Color.black);
				wf.writefile("  我:"+sdf.format(new Date())+"  "+str,"jilu");
				wf.writefile("  球球:"+sdf.format(new Date())+"  "+Info.answer,"jilu");
				Info.bool=false;
				Info.dialog=null;
				String str1="4*"+Info.ID+"*"+str;
				MyActionListener.sentMessage(str1);
			}
			else
			{
				System.out.println("初次建立视图失败");
				String s=str.replaceAll(fuhao,"");
				Matcher m=p.matcher(s);
				s=m.replaceAll("");
				System.out.println("s="+s);
				if (s.length()==0)
				{
					str="3*"+"2"+str;
					MyActionListener.sentMessage(str);
				}
				else
				{
					String s1="";
					s1=s;
					Pattern p1=Pattern.compile(reg);//对语气词的捕捉
					Matcher c=p1.matcher(s1);
					s1=c.replaceAll("");
					System.out.println("s1="+s1);
					if (s1.length()==0&&s.length()>=2)//将信息发送给服务器端
					{
						lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
						lb_robots.insertDocument(str+",什么呀", Color.black);
						Info.count6++;
					}
					else
					{
						str=str.replaceAll(fuhao,"");
						Matcher ms=p.matcher(str);
						str=ms.replaceAll("");
						Info.Chat=str;
						new ReadFile(str).start();//查找本地文件和反问数据库
						try
						{
							Thread.sleep(100);
						}
						catch (InterruptedException e)
						{
						}
						new Thread(new DealString(str)).start();
					}
				}//else end
			}//end else
		}
		else		//重复的回答也有服务器来完成
		{
			System.out.println("重复");
			str="3*"+"6"+str;
			MyActionListener.sentMessage(str);

		}
		lb_robots.text.setText("");
		//判断用户的状态
		if (Info.TCount==4)
		{
			if (Info.count2>1)
			{
				Info.STatus=7;//正常
			}
			else if (Info.count>1)
			{
				Info.STatus=6;//无聊
			}
			else if (Info.count3>1)
			{
				Info.STatus=5;//悲伤
			}
			else if (Info.count4>1)
			{
				Info.STatus=4;//兴奋
			}
			else if (Info.count5>1)
			{
				Info.STatus=3;//愤怒
			}
			else if(Info.count6>1)
			{
				Info.STatus=2;//捣乱
			}
		}
	}
	public void sd(String str)
	{
		if (Info.bool)//初次建立查询是否成功
		{
			lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
			lb_robots.insertDocument(Info.answer, Color.black);
			wf.writefile("  我:"+sdf.format(new Date())+"  "+str,"jilu");
			wf.writefile("  球球:"+sdf.format(new Date())+"  "+Info.answer,"jilu");
			Info.bool=false;
			Info.dialog=null;
			String str1="4*"+Info.ID;
			MyActionListener.sentMessage(str1);
		}
		else{
			System.out.println("初次建立视图失败");
			new ReadFile(str).start();//查找本地文件和反问数据库
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{}
			new Thread(new DealString(str)).start();
		}
	}
	public void f(int n)
	{
		if (n==0) throw new MyException("不符合条件");
	}

}