package RobotServer.thread;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.DataOutputStream;
import java.util.Vector;
import RobotServer.DB.Sql;
public class PlaceSelect implements Runnable
{
	private CheckData c=null;
	private Vector vect=null;
	private DataOutputStream out=null;
	private Sql db=null;
	private String answer=null;
	private String Qcontent=null;
	private String Question=null;
	private int id=0;
	public PlaceSelect(CheckData c,Vector vect)
	{
		this.c=c;
		this.vect=vect;
		this.out=c.out;
		this.Question=c.que;
	}
	public void run()
	{
		db=new Sql();
		System.out.println("进入PlaceSelect");
		String str2=Question.substring(Question.length()-2,Question.length());
		String str1=Question.substring(Question.length()-4,Question.length());
		String str0=Question.substring(Question.length()-3,Question.length());
		if (str2.equals("在哪")||str1.equals("什么地方")||str2.equals("哪里"))
		{
			String sql="select i_id,v_place,v_information from robot_place where ";
			String str="";
			for (int i=0;i<vect.size() ;i++ )
			{
				str=str+"v_place like '"+vect.get(i)+"%' or ";
			}
			sql=sql+str.substring(0,str.length()-4);
			System.out.println(sql);
			ResultSet rs=db.selectSql(sql);
			try
			{
				if (rs.next())
				{
					id=rs.getInt(1);
					Qcontent=rs.getString(2);
					answer=rs.getString(3);
				}
				rs.close();
				db.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			if (answer!=null)
			{
				String info="P*"+Qcontent+"/"+answer+"6";
				send(info);
			}
		}
	}
	public void send(String info)
	{

		if (!c.stop)
		{
			c.stop=true;
			try
			{
				System.out.println("PlaceSelect*"+info);
				out.writeUTF(info);
				out.flush();
				new ReadImage(id,c.ip).start();
			}
			catch (IOException r)
			{
				r.printStackTrace();
			}
		}
	}
}