package RobotClient.robot.listener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.Vector;
import RobotClient.listener.lb_robots;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */

public class BiaoListener extends MouseAdapter
{
	private static final long serialVersionUID = -7111651161565795064L;
	private lb_robots main;
	private JPopupMenu popup=new JPopupMenu();
	private ImageIcon[] icon=new ImageIcon[40];
	private JButton[] buttons=new JButton[40];
	public final static String[] str={"可爱","酷","生气","无辜","特馋","馋","小兵","闭嘴","哼小曲"
			,"眨眼","憨笑","兴奋","瞎眼","呲牙","撅嘴","发呆","困","不高兴","微笑","想睡觉","气愤","恐怖",
			"可怜","奸笑","左哼哼","右哼哼","无聊","惊讶","得意","花痴","大笑","无视","怒瞪","狡猾"
			,"瞪大眼","睡觉","饿","大骂"};
	public static Vector biaoqing=new Vector();
	public static Vector<Integer> postion=new Vector();
	public BiaoListener(final lb_robots main)
	{
		this.main=main;
		popup.setLayout(new GridLayout(4,10));

		for (int i=1;i<=38 ;i++ )
		{
			icon[i]=new ImageIcon("../java/RobotClient/image1/expression/Look_"+i+".png");
		}
		for (int i=1;i<=38 ;i++ )
		{
			buttons[i]=new JButton();
			setButtons(buttons[i]);
			buttons[i].setIcon(icon[i]);
			popup.add(buttons[i]);
			buttons[i].addMouseListener(new MyMouseAdapter(i));

		}
	}
	public void setButtons(JButton but)
	{
		but.setContentAreaFilled(false);
		but.setBorder(null);
	}
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("点击");
		popup.setVisible(true);
		popup.show(e.getComponent(),e.getX(),e.getY());
		System.out.println(e.getX()+e.getY());

	}
	public void mouseEntered(MouseEvent e)
	{
		if (e.getSource()==main.biaoqing)
		{
			main.biaoqing.setBorderPainted(true);
			main.biaoqing.setToolTipText("表情");
		}
	}
	public void mouseExited(MouseEvent e)
	{
		if (e.getSource()==main.biaoqing)
		{
			main.biaoqing.setBorderPainted(false);
		}
	}
	class MyMouseAdapter extends MouseAdapter
	{
		public int i=0;
		public MyMouseAdapter(int i){
			this.i=i;
		}
		public void mouseClicked(MouseEvent ee)
		{
			if (ee.getSource()==buttons[i])
			{
				JLabel jl=new JLabel();
				JButton bb=new JButton();
				ImageIcon icon=new ImageIcon("../java/RobotClient/image1/expression/Look_"+i+".png");
				jl.setIcon(icon);
				jl.setName(""+str[i-1]);
				String quest=main.text.getText();
				postion.add(quest.length());
				biaoqing.add(str[i-1]);
				main.text.insertComponent(jl);
				popup.setVisible(false);
			}
		}
		public void mouseEntered(MouseEvent ee)
		{
			buttons[i].setToolTipText(str[i-1]);
		}
	}
}