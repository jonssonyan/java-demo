package com.jonssonyan.snake;

import javax.swing.*;
import java.net.URL;

// 存放外部数据
public class Data {
    // 游戏头部的图片
    public static URL headerUrl = Data.class.getResource("/static/header.png");
    public static ImageIcon header = new ImageIcon(headerUrl);
    // 导入一些图片素材
    public static URL upUrl = Data.class.getResource("/static/up.png");
    public static URL downUrl = Data.class.getResource("/static/down.png");
    public static URL leftUrl = Data.class.getResource("/static/left.png");
    public static URL rightUrl = Data.class.getResource("/static/right.png");
    public static URL bodyUrl = Data.class.getResource("/static/body.png");
    public static ImageIcon up = new ImageIcon(upUrl); // 上
    public static ImageIcon down = new ImageIcon(downUrl); // 下
    public static ImageIcon left = new ImageIcon(leftUrl); // 左
    public static ImageIcon right = new ImageIcon(rightUrl); // 右
    public static ImageIcon body = new ImageIcon(bodyUrl); // 身体

    // 食物
    public static URL foodBlueUrl = Data.class.getResource("/static/food_blue.png");
    public static URL foodYellowUrl = Data.class.getResource("/static/food_yellow.png");
    public static URL foodRedUrl = Data.class.getResource("/static/food_red.png");
    public static ImageIcon foodBlue = new ImageIcon(foodBlueUrl); // 蓝色食物，加一个长度
    public static ImageIcon foodYellow = new ImageIcon(foodYellowUrl); // 黄色食物，加两个长度
    public static ImageIcon foodRed = new ImageIcon(foodRedUrl); // 红色食物，加三个长度
}