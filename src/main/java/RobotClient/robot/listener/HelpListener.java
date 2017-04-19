package RobotClient.robot.listener;

import RobotClient.listener.lb_robots;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wr on 2017/4/10.
 * versions 1.0
 */
public class HelpListener extends MouseAdapter{
    public HelpListener() {
        super();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String helpstr = "  Hi,我是无所不能的Sunny，你可以问我黄山旅游，黄山学院，或者闲着无聊找我聊天哦！\nSunny还很笨/(ㄒoㄒ)/~~，不要为难Sunny哦(；′⌒`)!\n" +
                "  我可以告诉你黄山实时的门票价格哦，还有黄山旅游攻略哦，如果想问宝宝问题可以直接说“黄山”，“黄山门票”等等。。。人家喜欢耿直的蓝孩子(*^__^*) 嘻嘻……\n " +
                " 如果宝宝不知道记得原谅宝宝哦，都怪那个润润把我弄得那么笨！！记住我哦！卖个萌(*^__^*) 嘻嘻……";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lb_robots.insertDocument("\nSunny:"+sf.format(new Date())+"\n",Color.GREEN);
        lb_robots.insertDocument(helpstr,Color.gray);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }


}
