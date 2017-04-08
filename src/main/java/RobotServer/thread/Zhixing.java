package RobotServer.thread;
import java.util.Hashtable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import RobotServer.DB.Sql;
public class Zhixing implements Runnable
{
	private Hashtable hash=null;
	private DataOutputStream out=null;

	private String str="";
	private String sql0=null;

	private Sql db=null;
	private Hashtable<Integer,String> hash1=new Hashtable<Integer,String>();
	private Vector<String> vect=new Vector();
	private Vector<Integer> vect1=new Vector();
	private Vector<String> ve=new Vector();
	private String info=null;
	private boolean bool=false;


	private String answers=null;
	private int quan=0;
	private String userID=null;
	private String que=null;//问题，插入记录表中的
	private int choice=0;
	private CheckData c;
	public Zhixing(CheckData c,int choice)
	{
		this.c=c;
		this.choice=choice;
		this.userID=c.userID;
		this.que=c.que;
		this.hash=c.hash;
		this.out=c.out;
		this.str=c.question;

	}

	public void run()
	{
		System.out.println("Zhixing开始执行");
		db=new Sql();
		if (choice==1)
		{
			fa(hash);
			setInfo(info);
		}
		else
		{
			que=str;
			if(selectLimit())//敏感词汇搜索
				return;
			boolean have=false;
			db=new Sql();
			String sqls="select v_content,v_keys,te_answer,i_quanzhi from reply_views where v_key like '"
					+str+"%' order by i_quanzhi desc";

			System.out.println(sqls);

			ResultSet rs=db.selectSql(sqls);
			try
			{
				if (rs.next())
				{
					int i=rs.getInt(4);
					info="YET*"+rs.getString(1)+"#"+rs.getString(3)+i;
					answers=rs.getString(1);
					quan=i;
					have=true;
				}
				rs.close();
				db.close();
			}
			catch (SQLException r)
			{
				r.printStackTrace();
			}
			//找不到记录,进行模糊回答
			if(!have)
			{
				try
				{
					int j=0;
					for (int a=2;a<=str.length() ;a++ )
					{
						String str2=str.substring(j,a);
						j=a-1;
						hash1.put(a,str2);
					}
					fa(hash1);
				}
				catch (Exception e)
				{
					//	bool=false;
				}
			}
			setInfo(info);
		}

		//执行将回答的答案频率进行更新
	}
	//搜索
	public void fa(Hashtable hash2)
	{
		getWord();
		db=new Sql();
		try{
			for (int k=0;k<ve.size();k++ )
			{
				if (hash2.contains(ve.get(k)))
				{
					//模式回答
					sql0="select * from robot_check where v_Qcontent='"+ve.get(k)+"'";
					bool=true;
					break;
				}
			}
		}catch(Exception r)
		{
			System.out.println("r");
		}
		if (!bool)
		{
			//不知道回答
			sql0="select v_Answer,i_Degree from robot_check where i_judge='1'";
		}
		System.out.println(sql0);
		ResultSet rs=db.selectSql(sql0);
		try
		{
			while (rs.next())
			{
				vect.add(rs.getString("v_Answer"));
				vect1.add(rs.getInt("i_Degree"));
			}
		}
		catch (SQLException r)
		{
			r.printStackTrace();
		}
		int x=(int)(Math.random()*vect.size());
		info="NO*"+vect.get(x)+vect1.get(x);
		answers=vect.get(x);
		quan=vect1.get(x);
	}
	//获取模糊回答词
	public void getWord()
	{
		db=new Sql();
		String sql4="select distinct v_Qcontent from robot_check where i_judge='0'";
		try
		{
			ResultSet rs=db.selectSql(sql4);
			while (rs.next())
			{
				ve.add(rs.getString(1));
			}
			rs.close();
			db.close();
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}

	}
	//发送信息
	public void setInfo(String message)
	{
		db=new Sql();
		String sql2="insert into robot_chatcontent(v_userID,v_userContent,v_robotcontent,i_quan) values"+
				" ('"+userID+"','"+que+"','"+answers+"','"+quan+"')";
		db.update(sql2);
		if (!c.stop)
		{
			try
			{
				out.writeUTF(message);
				out.flush();
			}
			catch (IOException r1)
			{
				r1.printStackTrace();
			}
		}
	}

	public boolean selectLimit()
	{
		String sql1="select * from robot_limit where v_content='"+str+"'";
		ResultSet rs0=db.selectSql(sql1);
		try
		{
			if (rs0.next())
			{
				info="NO*"+
						"对不起，您的问题涉及到敏感词汇，请询问关于黄山-徽州之类的问题，谢谢合作"+"2";
				answers="对不起，您的问题涉及到敏感词汇，请询问关于黄山-徽州之类的问题，谢谢合作";
				quan=2;
				setInfo(info);
				return true;
			}
			rs0.close();
			db.close();
		}
		catch (SQLException sw)
		{
			sw.printStackTrace();
		}
		return false;
	}
}