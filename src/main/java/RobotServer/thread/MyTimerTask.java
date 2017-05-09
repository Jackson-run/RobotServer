package RobotServer.thread;
import RobotServer.DB.Sql;
import java.util.TimerTask;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MyTimerTask extends TimerTask
{
	private String question=null;
	private String answer=null;
	private int status=0;//用户状态
	private String quest=null;//关键字
	private String str0=null;//关键字串
	private String str=null;//问题与答案串
	private String id=null;//用户id号
	private Vector<String> ve=new Vector();
	private Vector<Integer> ve1=new Vector();
	private String reg="[啊吖哦喔嗯噢额呃呢呐吗嘛呀呵哈嘿哎咦哇吧的]";

	private ArrayList<String> list1=new ArrayList<String>();
	private ArrayList<String> list2=new ArrayList<String>();

	private Sql db=null;
	private double cre;
	private int isTrainer=0;
	public MyTimerTask(String q,String a,int n,String id)
	{
		this.question=q;
		this.answer=a;
		this.status=n;
		this.id=id;
		str=question+answer;
	}
	public MyTimerTask(String q,String a,int n,String id,int isTrainer)
	{
		this.question=q;
		this.answer=a;
		this.status=n;
		this.id=id;
		this.isTrainer=isTrainer;
		str=question+answer;
		if (isTrainer==1)
		{
			try
			{
				System.out.println("enter");
				String s=answer.substring(answer.length()-1,answer.length());
				//this.status=Integer.parseInt(s);
				answer=answer.substring(0,answer.length());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}
	public void run()
	{
		System.out.println("insert into MyTimerTask");
		System.out.println("question:"+question+" ,answer:"+answer+" ,status="+status+" ,id="+id);
		if (status>2)
		{
			return;
		}
		db=new Sql();
		quest=question;

		Pattern p=Pattern.compile(reg);
		Matcher m=p.matcher(quest);
		quest=m.replaceAll("");
		if (quest.length()<3)
		{
			return;
		}
		String sql0="select v_content from robot_limit";//首先查看是否有敏感字
		ResultSet rs0=db.selectSql(sql0);
		try
		{
			while (rs0.next())
			{

				int index=quest.indexOf(rs0.getString(1));
				if (index!=-1)
				{
					return;  //表示有则退出
				}
			}
			rs0.close();
			db.close();
		}
		catch (SQLException e0)
		{
			e0.printStackTrace();
		}
		System.out.println("开始insert（）-----------------------");
		insertSql();
		//查看数据库中是否有相同的问题
	/*	db=new Sql();
		String sql="select * from reply_views where v_key like '"+quest+"%' order by i_quanzhi desc";
		System.out.println(sql);
		ResultSet rs=db.selectSql(sql);
		try
		{
			while (rs.next())
			{
				ve.add(rs.getString("te_answer"));
				ve1.add(rs.getInt("i_quanzhi"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		if (ve.size()!=0)		//表示数据库中有数据
		{
			System.out.println("数据库中有相同的数据");
			if (status<ve1.get(ve1.size()))
			{
				insertSql();
			}
			else if(status>=ve1.get(ve1.size()))
			{
				status=(int)(ve1.get(ve1.size())/2);
			}
			System.out.println("status="+status);
		}
		else//库中没有数据，也没有敏感字，也符合条件
		{
			selectCre();
			if (cre>=0.5||isTrainer==1)//采取
			{
				insertSql();
			}
		}
		*/

	}
	//插入语句
	public void insertSql()
	{
		db=new Sql();
		if(isTrainer==1){
			getKey();
			String sql2="insert into robot_normreply(v_content,te_answer,v_keys,v_key,"+
					"i_quanzhi) values('"+question+"','"+answer+"','"+str0+"','"+quest+"','"+status+"')";
			System.out.println(sql2);
			db.update(sql2);
			String sql3="update robot_train set e_isPass='1' where v_userID='"+id+"'and v_TQuestion='"+question+"'";
			System.out.println(sql3);
			db.update(sql3);
		}
		else{
			double doub=selectCre();
			if (doub>=0.5)
			{
				getKey();
				String sql2="insert into robot_normreply(v_content,te_answer,v_keys,v_key,"+
						"i_quanzhi) values('"+question+"','"+answer+"','"+str0+"','"+quest+"','"+status+"')";
				System.out.println(sql2);
				db.update(sql2);
				String sql3="update robot_train set e_isPass='1' where v_userID='"+id+"'and v_TQuestion='"+question+"'";
				System.out.println(sql3);
				db.update(sql3);
			}

		}
	}
	//查询用户的信用度
	public double selectCre()
	{
		db=new Sql();
		String sql="select d_Credit from user_info where v_userID='"+id+"'";
		System.out.println(sql);
		ResultSet rs1=db.selectSql(sql);
		try
		{
			if (rs1.next())
			{
				cre=rs1.getDouble(1);
			}
			rs1.close();
			db.close();
		}
		catch (SQLException r)
		{
			r.printStackTrace();
		}
		return cre;
	}
	public void getKey()
	{
		int j=0;
		int i=2;
		int length=quest.length();
		if (length<4)
		{
			str0=quest+"&null&";
		}
		else
		{
			str0="";
			for (;i<=length ;i=i+2 )
			{
				str0=str0+""+quest.substring(j,i)+"&";
				j=i;
			}
			if (length%2!=0)
			{
				str0=str0+quest.substring(length-2,length)+"&";
			}
		}
	}

}