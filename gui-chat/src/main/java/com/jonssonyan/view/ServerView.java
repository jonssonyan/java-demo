package com.jonssonyan.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * 服务器界面
 */
public class ServerView {
    protected JFrame frame;
    //配置面板
    protected JPanel settingPanel, messagePanel; //消息面板
    //分隔面板
    protected JSplitPane centerSplitPanel;
    //左边用户面板
    protected JScrollPane userPanel, logPanel; //右边消息框
    //服务器日志
    protected JTextArea logTextArea;
    //人数上限
    protected JTextField maxClientTextField, portTextField; //端口号
    //广播消息输入框
    protected JTextField serverMessageTextField;
    //启动按钮
    protected JButton startButton, stopButton, //停止按钮
            sendButton; //发送按钮
    //动态变化的用户列表
    protected JList userList;

    //Model
    protected DefaultListModel<String> listModel;

    //构造函数
    public ServerView() {
        initUI();
    }

    //UI初始化函数
    @SuppressWarnings("unchecked")
    private void initUI() {

        //设置服务端窗口标题、默认大小以及布局
        frame = new JFrame("服务器");
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        //服务器配置面板（设置默认参数）
        maxClientTextField = new JTextField("50");
        portTextField = new JTextField("8888");
        startButton = new JButton("启动");
        stopButton = new JButton("停止");

        settingPanel = new JPanel();
        //设置布局为一行六列
        settingPanel.setLayout(new GridLayout(1, 6));
        settingPanel.add(new JLabel("人数上限"));
        settingPanel.add(maxClientTextField);
        settingPanel.add(new JLabel("端口号"));
        settingPanel.add(portTextField);
        settingPanel.add(startButton);
        settingPanel.add(stopButton);
        //设置标题
        settingPanel.setBorder(new TitledBorder("服务器配置"));

        //在线用户面板
        listModel = new DefaultListModel<String>();

        userList = new JList(listModel);
        userPanel = new JScrollPane(userList);
        userPanel.setBorder(new TitledBorder("在线用户"));

        //服务器日志面板
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        //设置默认字体颜色为蓝色
        logTextArea.setForeground(Color.blue);

        logPanel = new JScrollPane(logTextArea);
        logPanel.setBorder(new TitledBorder("服务器日志"));

        //发送消息组件
        serverMessageTextField = new JTextField();
        sendButton = new JButton("发送");

        messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(serverMessageTextField, "Center");
        messagePanel.add(sendButton, "East");
        messagePanel.setBorder(new TitledBorder("广播消息"));

        //将中间在线用户面板与接收消息面板组合起来
        centerSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPanel, logPanel);
        //设置分隔线离左边100px
        centerSplitPanel.setDividerLocation(100);

        frame.add(settingPanel, "North");
        frame.add(centerSplitPanel, "Center");
        frame.add(messagePanel, "South");
        frame.setVisible(true);
        //设置按钮以及文本框的默认状态
        serviceUISetting(false);
    }

    protected void serviceUISetting(boolean started) {
        stopButton.setEnabled(started);
        maxClientTextField.setEnabled(!started);
        startButton.setEnabled(!started);
        portTextField.setEnabled(!started);
        sendButton.setEnabled(started);
        serverMessageTextField.setEnabled(started);
    }
}