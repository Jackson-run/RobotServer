package RobotClient.robot.ground;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import RobotClient.listener.lb_robots;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */

public class ShowMessage
{
	private String strings;
	private SimpleDateFormat sdf;
	public ShowMessage(String strings)
	{
		this.strings=strings;
		sdf=new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		lb_robots.insertDocument("\nSunny:"+sdf.format(new Date())+"\n  ",Color.blue);
		//lb_robots.insertDocument(line, Color.black);
	}
}