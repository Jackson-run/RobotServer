package RobotServer.thread;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import RobotServer.DB.Sql;
public class ReadFile
{
	private File file=null;
	private BufferedReader br=null;
	private String line=null;
	private Sql db=null;
	public ReadFile()
	{
		db=new Sql();
		run();
	}
	public void run()
	{
		try
		{
			file=new File("content/敏感词库大全.txt");
			if (file.exists())
			{
				br=new BufferedReader(new FileReader(file));
				while ((line=br.readLine())!=null)
				{

					line=line.replace("|","&");
					String[] s=line.split("&");
					String str=s[0];
					//		System.out.println(str);
					String sql="insert into robot_limit(v_content) values('"+str+"')";
					db.update(sql);
				}//while end
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new ReadFile();
	}
}