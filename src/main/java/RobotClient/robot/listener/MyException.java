package RobotClient.robot.listener;
public class MyException extends ArithmeticException
{
	public MyException(){}
	public MyException(String error)
	{
		super(error);
	}
}