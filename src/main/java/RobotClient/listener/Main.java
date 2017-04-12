package RobotClient.listener;
import javax.swing.*;
import java.awt.event.*;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */

public class Main extends JFrame
{
	lb_robots r;
	public Main()
	{
		r=new lb_robots(500,600);
		add(r);
		setSize(750,600);
		
		this. addWindowStateListener(new WindowStateListener()
		{
			public void windowStateChanged(WindowEvent e)
			{
				System.out.println(e.getNewState());
				if(e.getNewState()==6){
					change();
				}
				else if(e.getNewState()==0)
				{
					change();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	} 
	public void change()
	{
		
	//	r.vertical.setResizeWeight(0.7);
	//	r.all_horizon.setResizeWeight(0.75);
		//	
	}
	public static void main(String[] args)
	{
		new Main();
	}
}