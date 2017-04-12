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
public class InformDepart_IntroListener extends MouseAdapter{
    public InformDepart_IntroListener() {
        super();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String inform_intro_str = "  信息工程学院成立于2006年，是由原电子信息工程系和原计算机系合并组合而成，学院现有物理学、光电信息科学与工程、电子信息工程、" +
                "计算机科学与技术、软件工程等五个本科专业，在校学生近1800人。\n" +
                "  学院师资力量雄厚，教学管理严谨，拥有一支高学历、高素质、年轻化、有潜力的师资队伍。现有专兼职教师57人，其中教授2人、副高职称9人，博士6人，在读博士8人，硕士48人，" +
                "其中省学科带头人1人，省级学术与技术带头人后备1人，省级优秀团队1个。学院还长期聘请在相关领域的知名教授参与人才培养。目前我院已承担国家自然科学基金3项、国家开放实验室基金1项，" +
                "省级精品课程1门，位居全校前列。";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lb_robots.insertDocument("\n球球:"+sf.format(new Date())+"\n", Color.GREEN);
        lb_robots.insertDocument(inform_intro_str,Color.gray);
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
