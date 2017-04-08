package RobotServer.thread;
import RobotServer.DB.Sql;
import  RobotServer.ground.MyException;
import java.util.Timer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CheckTrain implements Runnable
{
	public String quest=null;
	public String userID=null;
	private Sql db=null;
	private int isPass;
	private int quanzhi=0;
	private String str=null;
	private int isTrainer;
	private String reg="[啊吖哦喔嗯噢额呃呢呐吗嘛呀呵哈嘿哎咦哇吧的]";
	private String fu="[……【】￥（）·`？！。；：‘’，“”《》、——]";
	public CheckTrain(String str)
	{
		this.str=str;
	}
	public void run()
	{
		db=new Sql();
		System.out.println("进入存数据:"+str);
		String[] strs=str.split("/");
		String s=strs[0];
		int n=Integer.parseInt(s);//为用户的状态等级
		userID=strs[1];
		String ss=strs[2];

		String s1="";
		String s2="";
		String[] sa=ss.split("#");
		s1=sa[1];
		s2=sa[2];

		System.out.println("n="+n+"  user="+userID+"  str="+str+"  s1="+s1+"     s2="+s2);

		String sql="select e_isTrainer from user_info where v_userID='"+userID+"'";
		ResultSet rs=db.selectSql(sql);
		try
		{
			if (rs.next())
			{
				isTrainer=rs.getInt(1);
			}
			rs.close();
			db.close();
		}
		catch (SQLException t)
		{
			t.printStackTrace();
		}
		f(s1,s2,n);
	}
	public void f(String question,String answer,int n)
	{
		quest=question;
		db=new Sql();
		question=question.replace("\n","");
		System.out.println("question:"+question);

		int len=question.length();
		//将训练者的问题的标点符号，空白，数字部分去除,(中英文符号都去除)
		String regex="[\\w\\s\\d\\p{Punct}]+";
		question=question.replaceAll(regex,"");

		String answers=answer.replaceAll(regex,"");
		quest=quest.replaceAll(regex,"");

		Pattern p=Pattern.compile(fu);
		Matcher m=p.matcher(question);
		question=m.replaceAll("").trim();

		Matcher m1=p.matcher(answers);
		answers=m1.replaceAll("").trim();

		Matcher m2=p.matcher(quest);
		quest=m2.replaceAll("");
		//将语气词去除
		Pattern p1=Pattern.compile(reg);
		Matcher m3=p1.matcher(quest);
		quest=m3.replaceAll("").trim();

		Matcher m4=p1.matcher(answers);
		answers=m4.replaceAll("").trim();

		int alen=answers.length();
		int length=question.length();
		double d=length*1.0/len;
		double ad=alen*1.0/answer.length();
		System.out.println("ad="+ad);
		if (d<0.5||ad<0.2)//则该用户训练的内容不合理，不采用
			isPass=0;
		else if(d>=0.5&&ad>0.2){
			isPass=1;
			quanzhi=n;
		}
		answer=answer.replace("'","‘");

		String str="";	//获取关键字串
		int j=0;
		int i=2;
		if (question.length()<4)
		{
			str=question+"&null&";
		}
		else
		{
			for (;i<=length ;i=i+2 )
			{
				str=str+""+question.substring(j,i)+"&";
				j=i;
			}
			if (length%2!=0)
			{
				str=str+question.substring(length-2,length)+"&";
			}
		}

		try
		{
			System.out.println("isPasss="+isPass);
			fa(isPass);
			Timer timer=new Timer();
			timer.schedule(new MyTimerTask(quest,answer,n,userID,isTrainer),1000);//使用计时器在5分钟之后进行判断
		}
		catch (MyException e)
		{
			//不采取则执行什么操作
			e.getMessage();
		}
		finally
		{
			String sql="insert into robot_train(v_userID,v_TQuestion,te_TAnswer"+
					",i_TStatus,e_isPass,i_Eqcount,i_tTimes) values('"+userID+"','"+quest+"','"+answer+
					"','"+n+"','0','0','0')";
			System.out.println(sql);
			db.update(sql);
		}
		System.out.println("存入训练库完毕");
	}
	public void fa(int num)
	{
		if (num==0)throw new MyException("数为0,不符合要求");
	}
}