package RobotServer.thread;
import java.util.Hashtable;
import java.util.Vector;
import RobotServer.ground.ReadFile;

public class Judge extends Thread
{
	private String str=null;
	private ReadFile rf=null;
	private Hashtable<Integer,String> hash=null;
	private Hashtable<Integer,String> hash1=new Hashtable<Integer,String>();
	private Vector<String> ve1=new Vector();
	private String string=null;
	public Judge(String str)
	{
		this.str=str;
		rf=new ReadFile();
	}

	public void run()
	{
		//	System.out.println("进行脏话查询");
		String filename="content/c.txt";
		hash=rf.readfile2(filename);
		int n=hash.size();
		for (int i=0;i<n ;i++ )
		{
			String[] ss=(hash.get(i)).split("&");
			hash1.put(i,ss[0]);
			ve1.add(ss[1]);
		}

		int len=str.length();
		int length=len;
		int i=0;
		for (i=0;i<hash1.size() ;i++ ){
			str=str.replace(hash1.get(i),"");
			length=str.length();
			if(length!=len)
				break;
		}
		if (length!=len)//说脏话
		{
			String[] sa=(ve1.get(i)).split("%");
			if (sa.length>1)
			{
				int na=sa.length;
				int x=(int)(Math.random()*na);
				string="^"+sa[x];

			}
			else
				string="^"+sa[0];
		}
		else//正常话语
			string="$";
		//	System.out.println(string);
	}
	public String f()
	{
		return string;
	}
	public static void main(String[] args)
	{
//		new Judge();
	}
}