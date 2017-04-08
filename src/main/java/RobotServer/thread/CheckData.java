package RobotServer.thread;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import java.io.DataOutputStream;

import RobotServer.DB.Sql;
public class CheckData implements Runnable
{
	public String question=null;
	public Hashtable<Integer,String> hash=new Hashtable<Integer,String>();
	public DataOutputStream out=null;
	public String userID=null;
	public int UserTatus=0;
	public boolean stop=false;
	public String que=null;//问题，插入记录表中的

	private Vector<String> vect=null;
	private Vector<String> vect1=new Vector<String>();
	private Vector<String> vect2=new Vector<String>();
	private ReadFile rf=null;
	private Thread t1,t2,t3,t4,t5,t6,t7,t8;
	private int count=0;
	private Sql db=null;
	private String sql0,sql1,sql2,sql3,sql4;

	private String biao=null;
	public String ip=null;
	public CheckData(String userID,String quest,DataOutputStream out)
	{
		this.userID=userID;
		this.question=quest;
		this.out=out;
		rf=new ReadFile();
	}
	public CheckData(String ip,String biao,String userID,String quest,DataOutputStream out)
	{
		this.ip=ip;
		this.biao=biao;
		this.userID=userID;
		this.question=quest;
		this.out=out;
		rf=new ReadFile();
	}
	public void run()
	{

		System.out.println("进入CheckData");
		try
		{
			int indexs=question.indexOf("*");
			que=question.substring(0,indexs);
			question=question.substring(indexs+1,question.length());

			String sa=question.substring(0,1);
			UserTatus=Integer.parseInt(sa);
			question=question.substring(1,question.length());
			String[] strs=question.split("/");
			//精确查询
			String[] str0=strs[0].split("~");
			for (int i=0;i<str0.length ;i++ )
			{
				hash.put(i,str0[i]);
			}
			//正向模糊查询
			String[] str1=strs[1].split("~");
			for (int i=0;i<str1.length ;i++ )
			{
				vect1.add(str1[i]);
			}
			String[] str2=strs[2].split("~");
			for (int i=0;i<str2.length ;i++ )
			{
				vect2.add(str2[i]);
			}
			db=new Sql();
			if (biao!=null)
			{
				String sqls="select i_Degree from robot_check where v_Qcontent='"+biao+"',and i_judge='4'";
				ResultSet rss=db.selectSql(sqls);
				try
				{
					if (rss.next())
					{
						UserTatus=rss.getInt(1);
					}
				}
				catch (SQLException ee)
				{
					ee.printStackTrace();
				}
			}
			sql0="select v_content,v_keys,te_answer,i_quanzhi from reply_views";
			sql1="select v_content,v_keys,te_answer,i_quanzhi from reply_views order by t_addTime desc";
			sql2="select v_content,v_keys,te_answer,i_quanzhi from reply_views order by i_useTimes desc";
			sql3="select v_content,v_keys,te_answer,i_quanzhi from reply_views order by i_quanzhi desc";
			sql4="select v_content,v_keys,te_answer,i_quanzhi from reply_views order by i_quanzhi asc";

			new Thread(new PlaceSelect(this,vect1)).start();
			new Thread(new PlaceSelect(this,vect2)).start();
			t2=new Thread(new DimSelect(this,vect2));
			t1=new Thread(new DimSelect(this,vect1));
			t3=new Thread(new SelectData(this,sql0));
			t4=new Thread(new SelectData(this,sql1));
			t5=new Thread(new SelectData(this,sql2));
			t6=new Thread(new SelectData(this,sql3));
			t2.start();
			t1.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();

			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
			if (stop)
			{
				return;
			}
			Thread td=new Thread(new Zhixing(this,1));
			System.out.println("END");
			td.start();
		}
		catch (Exception ee)
		{
			new Thread(new Zhixing(this,0)).start();
		}

	}
	public synchronized void update(int id)
	{
		stop=true;
		db=new Sql();
		if (count<2&&id!=0)
		{
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
			String sql2="insert into robot_chatcontent(v_userID,v_userContent,v_robotcontent,i_quan) values"+
					" ('"+userID+"','"+que+"','"+answers+"','"+quan+"')";
			db.update(sql2);
		}

	}
	public synchronized void setCount()
	{
		count++;
	}
}