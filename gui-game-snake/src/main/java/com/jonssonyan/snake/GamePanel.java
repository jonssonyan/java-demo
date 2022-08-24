package com.jonssonyan.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    int length; // 蛇的长度
    String fx; // 方向 R：右 L：左 U：上 D：下
    int[] snakeX = new int[600]; // 蛇的X轴坐标
    int[] snakeY = new int[500]; // 蛇的Y轴坐标
    boolean isStart; // 游戏是否开始的表示，默认未开始
    boolean isFail; // 是否失败标识
    int score; // 分数
    int num; // 食物颜色编号

    int foodX;
    int foodY;
    Random random = new Random();
    int seconds = 100;
    Timer timer = new Timer(seconds, this); // 设置一个定时器

    // 构造器
    public GamePanel() {
        init();
        // 获取键盘监听时间
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start(); // 让时间动起来
    }

    public void init() {
        length = 3; // 初始化蛇长度为3
        fx = "R"; // 初始化蛇头方向是往右
        // 头部坐标
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
        isStart = false;
        isFail = false;
        // 随机初始化食物
        foodX = 25 + 25 * random.nextInt(34);
        foodY = 75 + 25 * random.nextInt(24);
        score = 0; // 默认分数为0
        seconds = 100;
    }

    // 面板：画界面 画蛇
    // Graphics：画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.white); // 设置背景颜色
        Data.header.paintIcon(this, g, 25, 11); // 绘制游戏头部图片
        g.fillRect(25, 75, 850, 600); // 绘制游戏区域
        // 画一条静态的小蛇
        if ("R".equals(fx))
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        if ("L".equals(fx))
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        if ("U".equals(fx))
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        if ("D".equals(fx))
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        // 蛇的长度用length进行控制
        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        if (num == 0)
            Data.foodBlue.paintIcon(this, g, foodX, foodY);
        if (num == 1)
            Data.foodYellow.paintIcon(this, g, foodX, foodY);
        if (num == 2)
            Data.foodRed.paintIcon(this, g, foodX, foodY);
        // 游戏未开始时话一个字符串
        if (!isStart) {
            g.setColor(Color.white); // 设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40)); // 设置字体
            g.drawString("按下空格开始游戏", 300, 300);
        }
        // 游戏未开始时话一个字符串
        if (!isStart) {
            g.setColor(Color.white); // 设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40)); // 设置字体
            g.drawString("按下空格开始游戏", 300, 300);
        }
        // 游戏失败时话一个字符串
        if (isFail) {
            g.setColor(Color.red); // 设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40)); // 设置字体
            g.drawString("游戏结束，按下空格重新开始游戏", 150, 300);
        }
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度：" + length, 750, 35);
        g.drawString("分数：" + score, 750, 55);
    }

    // 定时器，监听时间，帧：执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        // 如果游戏处于开始状态并且游戏没有结束
        if (isStart && !isFail) {
            // 右移动
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            // 通过控制方向让小蛇移动
            if ("U".equals(fx)) {
                // 头部移动
                snakeY[0] -= 25;
                // 边界判断
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            } else if ("D".equals(fx)) {
                // 头部移动
                snakeY[0] += 25;
                // 边界判断
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            } else if ("R".equals(fx)) {
                // 头部移动
                snakeX[0] += 25;
                // 边界判断
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if ("L".equals(fx)) {
                // 头部移动
                snakeX[0] -= 25;
                // 边界判断
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            }
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                // 长度加一
                length++;
                // 增加分数
                if (num == 0)
                    score += 10;
                if (num == 1)
                    score += 20;
                if (num == 2)
                    score += 30;
                // 帧数加时
                if (seconds > 50) {
                    timer.setDelay(seconds -= 1);
                }
                // 重新随机生成一个颜色食物
                foodX = 25 + 25 * random.nextInt(34);
                foodY = 75 + 25 * random.nextInt(24);
                num = random.nextInt(3);
            }
            // 失败判定
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                    break;
                }
            }
            // 刷新界面
            repaint();
        }
        //
        timer.start(); // 让时间动起来
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // 键盘按下未释放
        int keyCode = e.getKeyCode();
        // 如果按下的是空格
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFail) {
                // 重新开始游戏
                init();
                isStart = true;
            } else {
                // 游戏开始或者暂停
                isStart = !isStart;
            }
            // 刷新界面
            repaint();
        }
        if (keyCode == KeyEvent.VK_UP) {
            fx = "U";
        } else if (keyCode == KeyEvent.VK_DOWN) {
            fx = "D";
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            fx = "R";
        } else if (keyCode == KeyEvent.VK_LEFT) {
            fx = "L";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 键盘释放
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 键盘按下并释放 敲击
    }
}