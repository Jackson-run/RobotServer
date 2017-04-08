package RobotClient.robot.ground;
import java.util.Vector;
import java.util.Timer;
import java.net.Socket;
//import client.LoginAction;
public class Info
{
	public static Socket socket;
	public static String userID="2002";//获取用户的ID
	public static String string;//获取上次用户的问题
	public static String answer;//边输入问题变搜索到的答案
	public static boolean bool;//判断text中的文字在数据库中是否搜索到
	public static int count;//记录提问者重复的次数
	public static int count2;//正常
	public static int count3;//悲伤
	public static int count4;//兴奋
	public static int count5;//愤怒
	public static int count6;//捣乱

	public static String Chat;//用户发的内容
	public static int Isbad;//是否为脏话
	public static String dialog;//模糊回答
	public static int Yetcount=0;//模糊回答的次数
	public static boolean isLearn=true;//是否启动自学习
	public static String quest;//记录上次反问的问题
	public static boolean Isfound=false;
	public static int TCount;
	public static int STatus;//用户的状态等级记录
	public static String ID;//用于更新的题号id
	public static int ComponentCount;

}