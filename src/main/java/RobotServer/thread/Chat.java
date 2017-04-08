package RobotServer.thread;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Vector;
import java.sql.SQLException;
import java.sql.ResultSet;
import RobotServer.DB.Sql;
public class Chat extends Thread
{
	private DataOutputStream out=null;
	private Socket socket;
	private Sql db=null;
	private String str=null;//接收到客户端发送的字符串
	private String info=null;//发送消息的字符串
	private Vector<String> ve=new Vector();
	private int ID=0;
	private String userID=null;
	private String ip=null;//客户端ip地址字符串表示形式
	public Chat(String str,Socket socket)
	{
		this.str=str;
		this.socket=socket;
		try
		{
			InetAddress inet=socket.getInetAddress();//获取ip地址类
			ip=inet.getHostAddress();//获取客户端IP地址字符串表示形式
			//	System.out.println("ip:"+ip);
			out=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

		}
		catch (IOException r)
		{
			r.printStackTrace();
		}
	}
	public void run()
	{
		db=new Sql();
		int index=str.indexOf("*");
		userID=str.substring(0,index);
		str=str.substring(index+1,str.length());
		System.out.println("接收到信息="+str);
		if (str.startsWith("0*"))//最初建立查询
		{
			str=str.substring(2,str.length());
			String sql0="select * from robot_check where v_Qcontent='"+str+"' and i_judge>0";
			ResultSet rs0=db.selectSql(sql0);
			String answer="";
			int degree=0;
			String string="SN*";
			try
			{
				if (rs0.next())
				{
					answer=rs0.getString("v_Answer");
					degree=rs0.getInt("i_Degree");
					string="ZA*";
					info=string+answer+degree;
				}
				rs0.close();
			}
			catch (SQLException se)
			{
				se.printStackTrace();
			}
			if (!string.equals("ZA*"))
			{
				String sql="select * from reply_views where v_content='"+str+"'";
				ResultSet rs=db.selectSql(sql);
				try
				{
					if (rs.next()){
						answer=rs.getString("te_answer");
						degree=rs.getInt("i_quanzhi");
						ID=rs.getInt("i_ID");
						info=string+answer+degree+"$"+ID;
					}
					else{
						info=string+"false";
					}
					rs.close();
					db.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				out.writeUTF(info);
				out.flush();
			}
			catch (IOException r)
			{
				r.printStackTrace();
			}

		}
		else if (str.startsWith("1*"))//训练内容
		{
			try
			{
				str=str.substring(2,str.length());
				new Thread(new CheckTrain(str)).start();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		else if (str.startsWith("2*"))//初次查询失败后建立的查询
		{
			str=str.substring(2,str.length());
			try
			{
				int ax=str.indexOf("$");
				String biaoqing=str.substring(0,ax);
				str=str.substring(ax+1,str.length());
				new Thread(new CheckData(ip,biaoqing,userID,str,out)).start();
			}
			catch (Exception r)
			{
				new Thread(new CheckData(userID,str,out)).start();
			}
			//	new Thread(new CheckData(userID,str,out)).start();
		}

		else if (str.startsWith("3*"))	//过滤掉符号的回答
		{
			db=new Sql();
			str=str.substring(2,str.length());
			String shu=str.substring(0,1);
			str=str.substring(1,str.length());
			String sql=null;

			if (shu.equals("2"))//为奇怪的符号，语气词的回答
			{
				sql="select v_Answer from robot_check where i_judge='2'";
			}
			else if (shu.equals("6"))//重复字段的回答
			{
				sql="select v_Answer from robot_check where i_judge='3'";
			}
			ResultSet rs1=db.selectSql(sql);
			try
			{
				while (rs1.next())
				{
					ve.add(rs1.getString(1));
				}
				rs1.close();
				db.close();
			}
			catch (SQLException r)
			{
				r.printStackTrace();
			}
			finally
			{
				int x=(int)(Math.random()*(ve.size()));
				info="NO*"+ve.get(x)+shu;
				try
				{
					out.writeUTF(info);
					out.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				//将内容存入聊天信息中
				try{
					int su=Integer.parseInt(shu);
					db=new Sql();
					String sql2="insert into robot_chatcontent(v_userID,i_quan,v_userContent,v_robotContent) values"+
							" ('"+userID+"','"+su+"','"+str+"','"+ve.get(x)+"')";
					System.out.println(sql2);
					db.update(sql2);
				}catch(Exception r)
				{
					r.printStackTrace();
				}
			}
		}
		else if (str.startsWith("4*"))
		{
			db=new Sql();
			str=str.substring(2,str.length());
			int indexa=str.indexOf("*");
			String str3=str.substring(0,indexa);
			int id=Integer.parseInt(str3);
			String qu=str.substring(indexa+1,str.length());
			String sql0="update robot_normreply set i_useTimes=i_useTimes+1 where i_ID='"+id+"'";
			db.update(sql0);
			String sql1="select te_answer,i_quanzhi from reply_views where i_ID='"+id+"'";
			ResultSet rs0=db.selectSql(sql1);
			String answers="";
			int quan=0;
			try
			{
				if (rs0.next())
				{
					answers=rs0.getString(1);
					quan=rs0.getInt(2);
				}
				rs0.close();
			}
			catch (SQLException r)
			{
				r.printStackTrace();
			}
			String sql2="insert into robot_chatcontent(v_userID,i_quan,v_userContent,v_robotContent) values"+
					" ('"+userID+"','"+quan+"','"+qu+"','"+answers+"')";
			db.update(sql2);
		}
		else if (str.startsWith("5*"))
		{
			str=str.substring(2,str.length());
			System.out.println("str="+str);

			db=new Sql();
			String one=str.substring(0,1);
			str=str.substring(1,str.length());
			if (one.equals("1"))
			{
				String sqls="select v_Answer,i_Degree from robot_check where v_Qcontent='"+str+"' and i_judge='4'";
				System.out.println(sqls);
				ResultSet rss=db.selectSql(sqls);
				try
				{
					if (rss.next())
					{
						String answer1=rss.getString(1);
						int de=rss.getInt(2);
						info="YES*"+answer1+de;
					}

					rss.close();
					db.close();
				}
				catch (SQLException r1)
				{
					r1.printStackTrace();
				}

			}
			else if (one.equals("2"))
			{
				String sqls="select v_answer,i_Degree from robot_check where i_judge='-1'";
				ResultSet rss=db.selectSql(sqls);
				Vector<String> vect1=new Vector();
				Vector<Integer> vect2=new Vector();
				try
				{
					if (rss.next())
					{
						vect1.add(rss.getString(1));
					}
					rss.close();
					db.close();
				}
				catch (SQLException r1)
				{
					r1.printStackTrace();
				}
				int x=(int)(Math.random()*(vect1.size()));
				String answers=vect1.get(x);
				info="YES*"+answers+"2";
			}
			try
			{

				out.writeUTF(info);
				out.flush();
			}
			catch (IOException r)
			{
				r.printStackTrace();
			}
		}
	}
}