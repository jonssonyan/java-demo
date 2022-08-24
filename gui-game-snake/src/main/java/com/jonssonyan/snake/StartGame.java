package com.jonssonyan.snake;

import javax.swing.*;

public class StartGame {
    public static void main(String[] args) {
        // 1.绘制一个静态窗口并设置窗口标题
        JFrame jFrame = new JFrame("贪吃蛇小游戏");
        jFrame.setBounds(10, 10, 900, 720); // 设置起始坐标和窗口大小
        jFrame.setResizable(false); // 设置不可以调整窗口大小
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置可以关闭窗口
        // 2.绘制面板
        jFrame.add(new GamePanel());
        jFrame.setVisible(true); // 让窗口可以展现出来
    }
}
