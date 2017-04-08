package RobotServer.thread;
import java.util.Hashtable;
import java.util.Vector;
import RobotServer.ground.ReadFile;
import java.io.IOException;
import java.io.DataOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import RobotServer.DB.Sql;
public class Compare extends Thread
{
	private Hashtable<Integer,String> hash=null;
	private Vector<String> vect=null;//关键字串

	private Vector<String> answer=null;//数据库中的答案
	private Vector<String> ve=new Vector();//符合条件的答案

	private Vector<Integer> quanzhi=null;//权值
	private Vector<Integer> quan=new Vector();
	private Vector<Integer> ve1=new Vector();

	private Vector<String> qve=null;//收集模糊访问的问题
	private Vector<String> ques=null;
	private int flag=0;
	private String info=null;
	private boolean bool=false;
	private DataOutputStream out=null;
	private ReadFile rf=null;
	private Sql db;
	private int status=0;//用户的状态
	private int id=0;
	private int count=0;
	private CheckData c;

	public Compare(CheckData c,Vector<Vector> vector)
	{
		this.c=c;
		this.status=c.UserTatus;
		this.hash=c.hash;
		this.out=c.out;
		ques=vector.get(0);
		vect=vector.get(1);
		answer=vector.get(2);
		quanzhi=vector.get(3);
		qve=vector.get(4);
		db=new Sql();
		rf=new ReadFile();
	}
	public void run()
	{
		int len=0;
		System.out.println("Compare线程开始运行");
		for (int i=0;i<vect.size() ;i++ )
		{
			String[] ss=(vect.get(i)).split("&");
			double dou=0.0;
			double[] alike=com(ss);
			if (alike[0]>=0.6&&alike[1]>=0.6)
			{
				bool=true;
				String str0="select * from reply_views where v_content like '"+ques.get(i)+"%' order by i_quanzhi desc";
				ResultSet rs=db.selectSql(str0);
				try
				{
					while (rs.next())
					{
						ve.add(rs.getString("te_answer"));
						quan.add(rs.getInt("i_quanzhi"));
						ve1.add(rs.getInt("i_ID"));
					}
					rs.close();
					db.close();
				}
				catch (SQLException ee)
				{
					ee.printStackTrace();
				}
				if (status==0)
				{
					info="YES*"+ve.get(0)+quan.get(0);
					id=ve1.get(0);
				}
				else
				{
					try{
						int n=quan.indexOf(status);
						info="YES*"+ve.get(n)+status;
						id=ve1.get(n);
					}catch(Exception e1){
						int x=(int)(Math.random()*(quan.size()));
						info="YES*"+ve.get(x)+quan.get(x);
						bool=true;
						id=ve1.get(x);
					}
				}
				System.out.println("Compare.info="+info);
				getInfo(info);
				return;
			}
		}//end for
	}
	//数据库的内容进行比较
	public double[] com(String[] s)
	{
		int count=0;
		int len=s.length;
		double[] ds=new double[2];
		for (int i=0;i<len ;i++ )
		{
			boolean b=hash.contains(s[i]);
			if (b)
			{
				count++;
			}
		}
		double d=count*1.0/len;
		ds[0]=d;
		if (hash.size()%2==0)
		{
			int length=hash.size()/2;
			d=count*1.0/length;
		}
		else
		{
			int length=hash.size()/2+1;
			d=count*1.0/length;
		}
		ds[1]=d;

		return ds;
	}
	//发送消息
	public void getInfo(String str)
	{
		if (!c.stop)
		{
			c.stop=true;
			try
			{
				c.setCount();
				c.update(id);
				out.writeUTF(str);
				out.flush();
			}
			catch (IOException r)
			{
				r.printStackTrace();
			}
		}
	}
	public boolean Bool()
	{
		return bool;
	}
	public Vector qa()
	{
		return qve;
	}
}