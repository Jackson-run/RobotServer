package RobotClient.robot.thread;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JSplitPane;
import RobotClient.listener.lb_robots;
/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */
public class ShowImage
{
	private String path=null;
	private String introduce=null;
	public ShowImage(String path,String introduce)
	{
		this.path=path;
		this.introduce=introduce;
		show();
	}
	public void show()
	{
		JPanel pane=new JPanel();
		//	pane.setLayout(new BorderLayout());
		ImageIcon icon=new ImageIcon("content/photo/"+path+".png");
		JLabel label0=new JLabel(new ImageIcon("image1/rebot/rebot_introduce.png"));
		JLabel label=new JLabel(icon);
		JTextArea area=new JTextArea();
		JTextArea area1=new JTextArea();
		area1.append("\n\n");
		area.setEditable(false);
		area.append("\n\n\n*************************\n文字介绍:\n  "
				+introduce+"\n*************************");
		area.setFont(new Font("宋体",Font.PLAIN,15));
		area.setEditable(false);
		area.setLineWrap(true);
		JSplitPane split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,area1,label);
		split.setDividerSize(1);
		lb_robots.rightpane.removeAll();
		lb_robots.rightpane.add(split,"North");
		lb_robots.rightpane.add(area,"Center");
		lb_robots.rightpane.updateUI();
	}
}