package RobotClient.robot.ground;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import RobotClient.mainpanelistener.lb_robots;

public class ShowMessage
{
	private String strings;
	private SimpleDateFormat sdf;
	public ShowMessage(String strings)
	{
		this.strings=strings;
		sdf=new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		lb_robots.insertDocument("\n球球:"+sdf.format(new Date())+"\n  ",Color.blue);
		//lb_robots.insertDocument(line, Color.black);
	}
}