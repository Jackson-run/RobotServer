package RobotClient.robot.thread;
import java.awt.Color;
import RobotClient.robot.ground.Info;
import RobotClient.listener.lb_robots;
//import com.wr.RobotClient.robot.RobotServer.net.ClientServer;*/
import java.util.Date;
import java.text.SimpleDateFormat;
import RobotClient.robot.listener.BiaoListener;
import RobotClient.robot.listener.MyActionListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */
public class DealString implements Runnable
{
	private lb_robots main;
	private String str=null;
	private String info=null;//要发送服务器端的字符串
	private String fuhao;//特殊字符
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String fu="[……【】￥（）·`？！。；：‘’，“”《》、——]";
	public DealString(String str)
	{
		this.str=str;
		fuhao="[\\s\\d\\p{Punct}]+";
	}
	public void run()
	{
		System.out.println("进入DealString");
		str=str.replaceAll(fuhao,"");
		Pattern p=Pattern.compile(fu);
		Matcher m=p.matcher(str);
		str=m.replaceAll("");
		String strs=str;
		int len=str.length();
		int j=0;
		str=str.replace("的","");
		int length=str.length();
		if (len<3)
		{
			info=strs;
		}
		else if (len<=10)
		{
			String str1="";
			String str0="";
			String str3="";
			//进行精确查询
			try
			{
				for (int i=2;i<=str.length() ;i++ )
				{
					String str2=str.substring(j,i);
					j=i-1;
					str1=str1+str2+"~";
				}
			}
			catch (Exception e)
			{
				System.out.println("@");
			}
			//进行模糊查询:正向进行
			try
			{
				for (int i=3;i<=str.length() ;i++ )
				{
					String str2=str.substring(0,i);
					str0=str0+str2+"~";
				}
			}
			catch (Exception e1)
			{
				System.out.println("@1");
			}
			//进行模糊查询：逆向查询
			try
			{
				for (int i=0;i<str.length()-1 ;i++ )
				{
					String str2=str.substring(i,str.length());
					str3=str3+str2+"~";
				}
			}
			catch (Exception e2)
			{
				System.out.println("@2");
			}
			info=str1+"/"+str0+"/"+str3;
		}
		else if (len<20)
		{
			String str0="";
			String str1="";
			String str2="";
			if (len%2==0)
			{
				for (int i=2;i<str.length() ;i++ )
				{
					String str3=str.substring(j,i);
					j=i;
					str0=str0+str3+"~";
				}
			}
			else
			{
				for (int i=2;i<str.length() ;i++ )
				{
					String str3=str.substring(j,i);
					j=i;
					str0=str0+str3+"~";
				}
				String str3=str.substring(str.length()-2,str.length());
				str0=str0+str3+"~";
			}
			for (int i=3;i<=str.length() ;i=i+2)
			{
				String str3=str.substring(0,i);
				str1=str1+str3+"~";
			}
			for (int i=0;i<str.length()-2 ;i=i+2)
			{
				String str3=str.substring(i,str.length());
				str2=str2+str3+"~";
			}
			info=str0+"/"+str1+"/"+str2;
		}
		else
		{
			len=(int)(len*0.6);
			str=str.substring(0,len);
			System.out.println("len"+len);
			if (len>=20)
			{
				main.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.blue);
				main.insertDocument("说的太复杂了，都被你弄晕了，还是简单点吧！！！", Color.black);
				info="";
			}
			else
			{
				String str1="";
				String str0="";
				String str3="";
				//进行精确查询
				try
				{
					for (int i=2;i<=len ;i++ )
					{
						String str2=str.substring(j,i);
						j=i-1;
						str1=str1+str2+"~";
					}
				}
				catch (Exception e)
				{
					System.out.println("@");
				}
				//进行模糊查询:正向进行
				try
				{
					for (int i=3;i<=len ;i++ )
					{
						String str2=str.substring(0,i);
						str0=str0+str2+"~";
					}
				}
				catch (Exception e1)
				{
					System.out.println("@1");
				}
				//进行模糊查询：逆向查询
				try
				{
					for (int i=0;i<len-1 ;i++ )
					{
						String str2=str.substring(i,str.length());
						str3=str3+str2+"~";
					}
				}
				catch (Exception e2)
				{
					System.out.println("@2");
				}
				info=str1+"/"+str0+"/"+str3;
			}
		}
		//	wait();
		f();
	}
	public void f()
	{

		if (Info.Isfound&&!info.equals(""))
		{
			System.out.println("远程查找数据库");
			Info.Isfound=false;
			if(BiaoListener.biaoqing.size()>0&&BiaoListener.biaoqing.size()<5)
				info="2*"+BiaoListener.biaoqing.get(0)+"$"+str+"*"+Info.STatus+info;
			else
				info="2*"+str+"*"+Info.STatus+info;
			MyActionListener.sentMessage(info);
		}
	}
}
