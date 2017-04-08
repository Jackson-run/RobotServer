package RobotServer.thread;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import RobotServer.DB.Sql;

public class ReadPhoto
{
	private PreparedStatement pst=null;
	private Sql db=null;
	private FileInputStream fis=null;
	private File file;
	public ReadPhoto()
	{
		db=new Sql();
		try
		{
			file=new File("photo/宿舍.png");
			fis=new FileInputStream(file);
			String sql="update robot_place set b_photo=? where i_id='16'";
			pst=db.Preupdate(sql);
			try
			{
				byte[] b=new byte[fis.available()];
				fis.read(b);
				pst.setBytes(1,b);
			}
			catch (Exception e)
			{
			}
			//byte[] b=new byte[1024];
			//	while(fis.read(b)!=-1)
			pst.executeUpdate();
			pst.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new ReadPhoto();
	}

}