package RobotClient.robot.listener;

import RobotClient.listener.lb_robots;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wr on 2017/4/12.
 * Versions 1.0
 */
public class AboutUsListener extends MouseAdapter{
    public AboutUsListener() {
        super();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String aboutus_str = "  我们来自黄山学院信息工程学院软件小组，实验室主要以项目、软件开发为驱动，学习 Java、ASP.net、" +
                "JavaScript 等开发语言和 J2EE、Android 开发平台和 Mysql、Oracle 等" +
                "数据库，立足最新计算机技术研习，并适当参加国内计算机、软件类作品竞赛。" +
                "目前小组获得全国竞赛二等奖 10 余项、全国三等奖 20 余" +
                "项、省级一等奖 20 余项等；指导学生申报科研项目 10 余项。\n" +
                "  实验室负责沈来信老师从事计算机与软件开发 16 年余，同济大" +
                "学博士研究生，副教授职称，熟悉多门开发语言与数据库技术，主持" +
                "与核心参与省部级项目 6 项；发表核心论文 30 余篇；崇尚技术研发" +
                "与研究工作。";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lb_robots.insertDocument("\nSunny:"+sf.format(new Date())+"\n", Color.GREEN);
        lb_robots.insertDocument(aboutus_str,Color.gray);
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
