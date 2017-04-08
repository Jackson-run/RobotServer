package RobotServer.thread;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import RobotServer.DB.Sql;
public class ReadImage extends Thread
{
	private Sql db=null;
	private int ID=0;
	private byte[] buf;
	private Socket socket=null;
	private String ip=null;
	private int id=0;
	public ReadImage(int id,String ip)
	{
		this.id=id;
		this.ip=ip;
		System.out.println("读取图片并发送");

		//	show();
	}
	public void run()
	{
		read();
		send();
	}
	public void read()
	{
		db=new Sql();
		String sql="select b_photo from robot_place where i_id='"+id+"'";
		ResultSet rs=db.selectSql(sql);
		try
		{
			if (rs.next())
			{
				buf=rs.getBytes(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void show()
	{
		try{
			BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(new File("1.png")));
			bout.write(buf,0,buf.length);
		}catch(IOException e)
		{}
	}
	public void send()
	{
		try
		{
			socket=new Socket(ip,3333);
			BufferedOutputStream out=new BufferedOutputStream(socket.getOutputStream());
			out.write(buf,0,buf.length);
			out.flush();
			socket.close();
		}
		catch (IOException ee)
		{
			ee.printStackTrace();
		}
	}
}