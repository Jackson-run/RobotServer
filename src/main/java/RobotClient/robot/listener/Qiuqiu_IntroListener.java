package RobotClient.robot.listener;

import RobotClient.listener.lb_robots;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wr on 2017/4/12.
 * versions 1.0
 */
public class Qiuqiu_IntroListener extends MouseAdapter{
    public Qiuqiu_IntroListener() {
        super();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String qq_intro_str = "  Hi,我是无所不能的Sunny，来自美丽的安徽黄山，闲着无聊找我聊天哦！" +
                "我的爸爸是黄山学院的信工软件小组，Sunny致力于解决黄山旅游与黄山学院的师生，Sunny可以回答你的" +
                "关于黄山旅游及相关问题，黄山学院的师生也可以问我哦，我对你们学校可熟悉了，" +
                "Sunny的数据库还不完善，Sunny以后可是要解决全国的驴友的好朋友哦！(*^__^*) 嘻嘻……";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lb_robots.insertDocument("\nSunny:"+sf.format(new Date())+"\n", Color.GREEN);
        lb_robots.insertDocument(qq_intro_str,Color.gray);
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
