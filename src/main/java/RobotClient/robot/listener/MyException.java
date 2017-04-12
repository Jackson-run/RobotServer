package RobotClient.robot.listener;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */

public class MyException extends ArithmeticException
{
	public MyException(){}
	public MyException(String error)
	{
		super(error);
	}
}