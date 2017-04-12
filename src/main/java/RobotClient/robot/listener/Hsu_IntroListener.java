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
public class Hsu_IntroListener extends MouseAdapter{
    public Hsu_IntroListener() {
        super();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String hsustr = "  黄山高专升格为黄山学院。拥有全国第一家徽州文化博物馆。是全国首个全徽派建筑高等院校，在各类毕业生就业率持续名列全省同类院校前列。" +
                "汉语言文学、新闻学、对外汉语、文化产业管理、戏剧影视文学、英语、日语、会计学、国际经济与贸易、市场营销、公共事业管理、财务管理、旅游管理、烹饪与营养教育、" +
                "人力资源管理、酒店管理、数学与应用数学、统计学、物理学、电子信息工程、光信息科学与技术、计算机科学与技术、自动化、机械设计制造及自动化、软件工程、" +
                "化学、制药工程、应用化学、化学工程与工艺、材料科学与工程、生物科学、生物技术、环境科学、园林、林学、环境工程、生物工程、食品科学与工程、建筑学、土木工程、" +
                "城市规划、学前教育、小学教育、应用心理学、社会体育、美术学、艺术设计、动画、音乐学、广播电视编导、思想政治教育。";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lb_robots.insertDocument("\n球球:"+sf.format(new Date())+"\n", Color.GREEN);
        lb_robots.insertDocument(hsustr,Color.gray);
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
