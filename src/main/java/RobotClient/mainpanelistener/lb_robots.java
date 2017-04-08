package RobotClient.mainpanelistener;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.text.*;
import RobotClient.robot.listener.BiaoListener;
import RobotClient.robot.listener.MyActionListener;
import RobotClient.robot.ground.Info;
import RobotClient.robot.net.LinkServer;
import com.sun.xml.internal.ws.api.model.JavaMethod;

public class lb_robots extends JPanel{
	private static final long serialVersionUID = 1L;
	public JButton b,biaoqing;
	public static JTextPane pane,text;
	public JScrollPane jsp;
	private JScrollPane scroll;
	private JLabel label,label2;
	private JButton qq_introd_btn;//球球简介button
	private JButton hsu_introd_btn;//黄山学院简介button
	private JButton aboutus_btn;//关于我们Button
	private JButton help_btn;//获取帮助
	private JButton infor_depart_btn;//信息工程学院button
	private JPanel introdu_pane;//机器人介绍面板
	private JPanel hsu_pane;//黄山学院介绍面板
	private JPanel buttompane=new JPanel();
	public static JPanel rightpane=new JPanel();
	public static JPanel rightpane1=new JPanel();
	private JPanel North=new JPanel();
	private JTextArea area;
	public JSplitPane vertical=new JSplitPane(JSplitPane.VERTICAL_SPLIT,false);//垂直分割条
	private int width,height;
	public lb_robots(int width, int height)
	{
		this.width=width;
		this.height=height;
		if (Info.socket==null)
		{
			new LinkServer().start();
		}
		BorderLayout bl=new BorderLayout();
		setLayout(bl);
		bl.setHgap(6);
		initA();
		initB();
		initC();

		setSize(width,height);
		setSplitPane();
		setVisible(true);
	}

	public void initA()//设置下面部分
	{
		final ImageIcon icon1=new ImageIcon("../java/RobotClient/image1/robot/lb_rebot1.png");
		final ImageIcon icon2=new ImageIcon("../java/RobotClient/image1/robot/lb_rebot0.png");
		ImageIcon icon3=new ImageIcon("../java/RobotClient/image1/expression/Look_1.png");
		biaoqing=new JButton(icon3);
		biaoqing.setHorizontalTextPosition(SwingConstants.CENTER);
		biaoqing.setContentAreaFilled(false);
		biaoqing.setBorderPainted(false);
		biaoqing.setText("表情");
		biaoqing.addMouseListener(new BiaoListener(this));
		b=new JButton(icon1);
		b.setFont(new Font("楷体_gb2312",Font.BOLD,15));
		b.setText("发送");
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setContentAreaFilled(false);
		b.setBorder(null);
		b.addMouseListener(new MouseAdapter(){
			public void  mouseEntered(MouseEvent e){
				b.setIcon(icon2);
			}
			public void mouseExited(MouseEvent e){
				b.setIcon(icon1);
			}
			public void mouseClicked(MouseEvent e) {}
		});
		b.addActionListener(new MyActionListener(this));
	}
	public void initB()//设置右边部分
	{
		help_btn = new JButton("help≡[。。]≡");
		introdu_pane = new JPanel();
		hsu_pane = new JPanel();
		hsu_pane.setLayout(new BorderLayout());
		introdu_pane.setLayout(new BorderLayout());
		qq_introd_btn = new JButton("球球简介☺");
		hsu_introd_btn = new JButton("黄山学院简介");
		aboutus_btn = new JButton("关于我们");
		infor_depart_btn = new JButton("我们大信工");
		area=new JTextArea(5,10);
		area.append("\n姓名：球球\n\n" +
				"专业：软件工程\n\n主人：帅气的润润\n\n爱好：有事问球球，宝宝啥都懂\n\n@version 1.0\n\n@author Jackson_Run\n");
		area.setFont(new Font("宋体",Font.PLAIN,18));
		area.setEditable(false);
		area.setLineWrap(true);
		label=new JLabel(new ImageIcon("../java/RobotClient/image1/robot/rebot_intrduce.png"));
		label2=new JLabel(new ImageIcon("../java/RobotClient/image1/robot/rebot_hei.png"));
		introdu_pane.add(hsu_introd_btn,BorderLayout.NORTH);
		hsu_pane.add(infor_depart_btn,BorderLayout.NORTH);
		hsu_pane.add(area,BorderLayout.CENTER);
		hsu_pane.add(help_btn,BorderLayout.SOUTH);
		introdu_pane.add(hsu_pane,BorderLayout.CENTER);

		rightpane.setLayout(new BorderLayout());
		rightpane.add(qq_introd_btn,BorderLayout.NORTH);
		rightpane.add(introdu_pane,BorderLayout.CENTER);
		rightpane.add(aboutus_btn,BorderLayout.SOUTH);
		/*rightpane.add(area,BorderLayout.CENTER);
		rightpane.add(label2,BorderLayout.SOUTH);*/
		rightpane.setVisible(true);
	}
	public void initC()//设置文本框部分
	{
		pane=new JTextPane();
		text=new JTextPane();
		pane.setFont(new Font("宋体",Font.PLAIN,15));
		scroll=new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp=new JScrollPane(pane);
		text.addCaretListener(new MyActionListener(this));
		text.addKeyListener(new MyActionListener(this));
		pane.setEditable(false);

		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		insertDocument("球球:"+sf.format(new Date())+"\n",Color.BLUE);
		insertDocument("  hi,我是机器人球球,很高兴认识你",Color.black);
	}
	public void setSplitPane()
	{
		North.setLayout(new BorderLayout());
		//North.add(biaoqing,"West");
		buttompane.setLayout(new BorderLayout());
		buttompane.add(North,"North");
		buttompane.add(scroll,"Center");
		buttompane.add(b,"East");

		vertical.setTopComponent(jsp);
		vertical.setBottomComponent(buttompane);
		vertical.setDividerLocation((int)(this.getHeight()*0.7));
		vertical.setDividerSize(1);
		add(vertical,"Center");
		add(rightpane,"East");
		//add(rightpane1,"West");
	}
	//文本框加监控
	public static void insertDocument(String text,Color textColor)
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, textColor);//设置文字颜色
		StyleConstants.setFontSize(set, 15);//设置字体大小
		Document doc = pane.getStyledDocument();
		pane.setCaretPosition(doc.getLength());
		try
		{
			doc.insertString(doc.getLength(), text, set);//插入文字
		}
		catch (BadLocationException e)
		{}
	}
}
