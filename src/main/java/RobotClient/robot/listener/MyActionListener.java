package RobotClient.robot.listener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextPane;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;

import java.awt.Component;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import RobotClient.robot.ground.Info;
import RobotClient.robot.net.LinkServer;
import RobotClient.robot.thread.JudgeThread;
import RobotClient.mainpanelistener.lb_robots;

public class MyActionListener implements ActionListener,KeyListener,CaretListener
{
	private lb_robots main=null;
	private String regex="[……【】￥（）·`？！。；：‘’，“”《》、——]";
	private String fuhao="[\\s\\d\\w\\p{Punct}]+";//特殊字符
	public static SimpleDateFormat sdf=null;

	private Vector ve1=BiaoListener.biaoqing;
	public static Vector ve=new Vector();
	private String[] biaos=BiaoListener.str;
	Pattern p=Pattern.compile(regex);
	public MyActionListener(lb_robots main)
	{
		this.main=main;

		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			for (int i=0;i<biaos.length ;i++ )
			{
				ve.add(biaos[i]);
			}
		}
		catch (Exception ee)
		{}
	}
	public void actionPerformed(ActionEvent e)//发送
	{
		if (e.getSource()==main.b)
		{
			String str = main.text.getText();//从文本框获取问题
			str=str.replaceAll("[\\s\n]+","");
			corp(str);
		}
	}
	public void caretUpdate(CaretEvent e)
	{
		JTextPane t=(JTextPane)e.getSource();
		int pos=e.getDot();
		int n=t.getComponentCount();
		if (n==0)
		{
			try{
				BiaoListener.biaoqing.clear();
				BiaoListener.postion.clear();
			}catch(Exception ee)
			{}
		}
		if (Info.ComponentCount>n)
		{
			try{
				BiaoListener.postion.remove(pos-1);
				BiaoListener.biaoqing.remove(BiaoListener.biaoqing.size()-1);

			}catch(Exception r){}
		}
		Info.ComponentCount=n;
		String str=t.getText();
		System.out.println("str="+str);
		Matcher m=p.matcher(str);
		String strs=m.replaceAll("").trim();//过滤符号
		strs=strs.replaceAll(fuhao,"");
		strs="0*"+strs;
		try{
			sentMessage(strs);
		}catch(Exception ee){}
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==e.VK_ENTER)
		{
			String str=main.text.getText();//从文本框获取问题
			str=str.replaceAll("[\\s\n]+","");
			corp(str);
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void corp(String str)
	{
		if (str!=null&&!str.equals(""))
		{
			main.insertDocument("\n我:"+sdf.format(new Date())+"\n  ", Color.RED);
			try
			{
				if (ve1.size()!=0)//表示有表情图片
				{
					if(BiaoListener.postion.get(0)!=1&&BiaoListener.postion.get(0)<str.length())
						main.insertDocument(str.substring(0,BiaoListener.postion.get(0)),Color.black);
					else if(BiaoListener.postion.get(0)>=str.length())
						main.insertDocument(str.substring(0,str.length()),Color.black);
					for(int i=0;i<ve1.size();i++)
					{
						int n=ve.indexOf(ve1.get(i));
						main.pane.insertIcon(new ImageIcon("image1/expression/Look_"+(n+1)+".png"));
						try
						{
							main.insertDocument(str.substring(BiaoListener.postion.get(i)
									,BiaoListener.postion.get(i+1)),Color.black);
						}
						catch (Exception e1)
						{
							main.insertDocument(str.substring(BiaoListener.postion.get(i)
									,str.length()),Color.black);
						}
					}
				}
				else
				{
					main.insertDocument(str,Color.BLACK);
				}
			}
			catch (Exception r)
			{
				System.out.println("r");
			}
			System.out.println("获取文本："+str);
			new JudgeThread(str);
		}
		else if (ve1.size()!=0)//表示只有表情
		{
			main.insertDocument("\n我:"+sdf.format(new Date())+"\n  ", Color.RED);
			for(int i=0;i<ve1.size();i++)
			{
				int n=ve.indexOf(ve1.get(i));
				main.pane.insertIcon(new ImageIcon("image1/expression/Look_"+(n+1)+".png"));
			}
			if (ve1.size()<5)
			{
				String info="5*"+"1"+ve1.get(0);
				sentMessage(info);
			}
			else//表情丰富
			{
				String info="5*"+"2"+ve1.get(0);
				sentMessage(info);
			}
			BiaoListener.biaoqing.clear();
			BiaoListener.postion.clear();
			main.text.setText("");
		}
	}
	public static void sentMessage(String question)
	{
		String str0=question;
		question=Info.userID+"*"+question;
		System.out.println("question="+question);
		try
		{
			DataOutputStream out=new DataOutputStream(
					new BufferedOutputStream(Info.socket.getOutputStream()));
			out.writeUTF(question);
			out.flush();
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			System.out.println("str0"+str0);
			if (!str0.startsWith("0"))
			{
				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.BLUE);
				lb_robots.insertDocument("服务器正在更新，请稍后在使用......",Color.BLACK);
			}
		}
		catch(Exception e)
		{
			System.out.println("str0"+str0);
			if (!str0.startsWith("0"))
			{
				lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ", Color.BLUE);
				lb_robots.insertDocument("服务器正在更新，请稍后......",Color.BLACK);
			}
		}
	}
}