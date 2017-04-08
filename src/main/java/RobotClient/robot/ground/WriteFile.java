package RobotClient.robot.ground;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Hashtable;

public class WriteFile
{
	private File file=null;
	private BufferedWriter bwrite=null;
	private BufferedReader bread=null;
//	private String str=null;
	private String line=null;
	private boolean b=false;
	private Hashtable<Integer,String> hash=null;
	private Vector<String> ve=new Vector<String>();
	public WriteFile()
	{
		//this.str=str;
	}
	public boolean writefile(String str,String name)
	{
		try
		{
			String names="F:/政治/"+Info.userID+"_"+name+".txt";
			file=new File(names);
			if (!file.exists())
			{
				file.createNewFile();	
			}
			bwrite=new BufferedWriter(new FileWriter(names,true));
			bwrite.write(str);
			bwrite.newLine();
			bwrite.flush();
			b=true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return b;
	}
	public String readfile(String filename)
	{
		try
		{
			file=new File(filename);
			bread=new BufferedReader(new FileReader(file));
			while ((line=bread.readLine())!=null)
			{
				ve.add(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		int n=ve.size();
		int x=(int)(Math.random()*n);
		return ve.get(x);
	}
	public Hashtable readfile2(String filename)
	{
		int i=0;
		hash=new Hashtable<Integer,String>();
		try
		{
			file=new File(filename);
			bread=new BufferedReader(new FileReader(file));
			while ((line=bread.readLine())!=null)
			{
				hash.put(i,line);
				i++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return hash;
	}
}