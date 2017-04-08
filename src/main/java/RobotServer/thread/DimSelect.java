package RobotServer.thread;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.DataOutputStream;
import java.util.Vector;

import RobotServer.DB.Sql;

import java.util.concurrent.ExecutorService;
public class DimSelect implements Runnable
{
	private Vector<String> vect=null;//处理后要匹配的字符串
	private Vector<String> ve1=new Vector();//获得答案
	private Vector<Integer> ve2=new Vector();//获得的权值
	private Vector<Integer> ve3=new Vector();//获得的关键字串

	private Vector<Integer> ve4=new Vector();
	private Vector<String> vector=new Vector();
	private DataOutputStream out=null;
	private Sql db=null;
	private String info=null;
	private int tatus=0;//用户状态值
	private ExecutorService exec=null;
	private CheckData c=null;
	private int count=0;//计数
	private int ID=0;

	public DimSelect(Vector vect,DataOutputStream out)
	{
		this.vect=vect;
		this.out=out;
	}
	public DimSelect(int Tatus,Vector vect,DataOutputStream out)
	{
		this.tatus=tatus;
		this.vect=vect;
		this.out=out;
	}
	public DimSelect(CheckData c,Vector vect)
	{
		this.c=c;
		this.tatus=c.UserTatus;
		this.vect=vect;
		this.out=c.out;
	}
	public void run()
	{
		System.out.println("进入DimSelect");
		db=new Sql();
		//首先查看是否有敏感词汇
		String str0="";
		for (int i=0;i<vect.size() ;i++ )
		{
			str0=str0+"v_content like '"+vect.get(i)+"%' or ";
		}
		String sql1="select * from robot_limit where ";
		sql1=sql1+str0.substring(0,str0.length()-4);
		ResultSet rs0=db.selectSql(sql1);
		try
		{
			if (rs0.next())
			{
				info="No*对不起，您的问题涉及到敏感词汇，请询问关于黄山-徽州之类的问题，谢谢合作"+"2";
				try
				{
					out.writeUTF(info);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				return;
			}
			rs0.close();
			db.close();
		}
		catch (SQLException sw)
		{
			sw.printStackTrace();
		}
		//无，就正常搜索
		db=new Sql();
		String sql="select i_ID,v_content,te_answer,i_quanzhi from reply_views where ";
		String str="";
		for (int i=0;i<vect.size() ;i++ )
		{
			str=str+"v_key like '"+vect.get(i)+"%' or ";
		}

		sql=sql+str.substring(0,str.length()-4)+" order by i_quanzhi desc";
		System.out.println(sql);
		ResultSet rs=db.selectSql(sql);
		int a=0;
		try
		{
			while (rs.next())
			{
				ve1.add(rs.getString(3));
				ve2.add(rs.getInt(4));
				ve3.add(rs.getInt(1));//ID
				a++;
			}
			rs.close();
			db.close();
		}
		catch (SQLException r)
		{
			r.printStackTrace();
		}
		if(ve1.size()!=0){
			if (ve1.size()>2&&ve1.size()<5)
			{
				if (tatus==0)//状态0表示刚开始初始化,或者是状态为最高
				{
					//选择权重值最高的那个
					info="YES*"+ve1.get(0)+ve2.get(0);
					ID=ve3.get(0);
				}
				else
				{
					try{
						int n=ve2.indexOf(tatus);
						info="YES*"+ve1.get(n)+tatus;
						ID=ve3.get(n);
					}catch(Exception e)
					{
						int x=(int)(Math.random()*(ve2.size()));
						info="YES*"+ve1.get(x)+ve2.get(x);
						ID=ve3.get(x);
					}
				}
			}
			else if(ve1.size()==1)
			{
				info="YES*"+ve1.get(0)+ve2.get(0);
				ID=ve3.get(0);
			}
			try
			{
				if (!c.stop&&info!=null)
				{
					c.stop=true;
					System.out.println("DimSelect.info"+info);
					c.setCount();
					System.out.println("ID"+ID);
					c.update(ID);
					out.writeUTF(info);
					out.flush();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}