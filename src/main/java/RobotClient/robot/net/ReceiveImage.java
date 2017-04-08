package RobotClient.robot.net;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class ReceiveImage
{
	private ServerSocket ss=null;
	private Socket socket=null;
	private File file=null;
	private String path="content/new1.png";
	private BufferedOutputStream bos;
	private BufferedInputStream bis=null;
	public ReceiveImage()
	{
		try
		{
			ss=new ServerSocket(5552);
			socket=ss.accept();
			System.out.println("接收图片");
			file=new File(path);
			bos=new BufferedOutputStream(new FileOutputStream(path));
			bis=new BufferedInputStream(socket.getInputStream());
			byte[] buf=new byte[1024];
			int i=0;
			while ((i=bis.read(buf,0,1024))!=-1)
			{
				bos.write(buf,0,i);
				bos.flush();
			}
			bis.close();
			bos.close();
			System.out.println("接收完毕");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new ReceiveImage();
	}
}