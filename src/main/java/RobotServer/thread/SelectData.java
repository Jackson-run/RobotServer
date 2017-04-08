package RobotServer.thread;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Hashtable;
import java.io.DataOutputStream;
import RobotServer.DB.Sql;
public class SelectData implements Runnable
{
	private Sql db=null;
	private ResultSet rs=null;
	private Vector<String> ve=null;
	private Vector<String> ve1=null;
	private Vector<Integer> ve2=null;
	private Vector<String> ve3=null;//问题内容
	private Vector<Vector> vect=null;
	private Hashtable hash=null;
	private String info=null;
	private DataOutputStream out=null;
	private boolean mohu=false;
	private String sql=null;
	private Vector<String> qve=null;
	private int status=0;
	private CheckData c;
	public SelectData(CheckData c,String sql)
	{
		this.c=c;
		this.status=c.UserTatus;
		this.sql=sql;
		this.hash=c.hash;
		this.out=c.out;
	}
	public void run()
	{
		db=new Sql();
		ve3=new Vector<String>();
		ve=new Vector<String>();
		ve1=new Vector<String>();
		ve2=new Vector<Integer>();
		vect=new Vector();
		System.out.println("进入SelectData");
		try
		{
			rs=db.selectSql(sql);
			int i=0;
			while(rs.next())
			{
				ve3.add(rs.getString("v_content"));
				ve.add(rs.getString("v_keys"));
				ve1.add(rs.getString("te_answer"));
				ve2.add(rs.getInt("i_quanzhi"));
				i++;
				if (i%300==0)
				{
					vect.add(ve3);
					vect.add(ve);
					vect.add(ve1);
					vect.add(ve2);
					vect.add(qve);
					System.out.println("从数据库取数据暂停");
					//		Compare com=new Compare(hash,vect,1,out);
					Compare com=new Compare(c,vect);
					com.start();
					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException ee)
					{
						ee.printStackTrace();
					}
					if (com.Bool())
					{
						return;
					}
					qve=com.qa();
					ve.clear();
					ve1.clear();
					ve2.clear();
					ve3.clear();
					vect.clear();
					System.out.println("继续");
				}
			}
			rs.close();
			db.close();
			vect.add(ve3);
			vect.add(ve);
			vect.add(ve1);
			vect.add(ve2);
			vect.add(qve);
			//		new Compare(hash,vect,0,out).start();
			new Compare(c,vect).start();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
				db.close();
			}
			catch (SQLException r)
			{
				r.printStackTrace();
			}
		}
		//	}
	}
}