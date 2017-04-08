package RobotServer.DB;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class Sql
{
	private Statement stmt;
	private Connection conn;
	public  PreparedStatement pst;
	private ResultSet rs;
	String db="robot";
	String user="root";
	String password="123456";
	public Sql()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost/"+db,""+user,""+password);
			stmt=conn.createStatement();
		}
		catch (ClassNotFoundException ee)
		{
			System.out.println("找不到驱动连接不上");
			ee.printStackTrace();
		}
		catch (SQLException sql)
		{
			System.out.println("驱动链接不成功");
			sql.getMessage();
		}
	}
	public PreparedStatement Preupdate(String s)
	{
		try{
			pst=conn.prepareStatement(s);
		}catch(SQLException ee)
		{
			ee.printStackTrace();
		}
		return pst;
	}
	//查询数据库
	public ResultSet selectSql(String s)
	{
		try
		{
			rs=stmt.executeQuery(s);
		}
		catch (SQLException r)
		{
			r.printStackTrace();
		}
		return rs;
	}
	//更新数据库
	public void update(String sql)
	{
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (SQLException se)
		{
			System.out.println("see"+se);
			se.printStackTrace();
		}

	}
	public void close()
	{
		try
		{
			stmt.close();
			conn.close();
		}
		catch (SQLException se)
		{
			//System.out.println("se"+se);
			se.printStackTrace();
		}

	}
}