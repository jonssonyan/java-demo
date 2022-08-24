package com.jonssonyan.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * 客户端界面
 */
public class ClientView {
    protected JFrame frame;
    //配置面板
    protected JPanel settingPanel, messagePanel; //消息面板
    //分隔面板
    protected JSplitPane centerSplitPanel;
    //左边用户面板
    protected JScrollPane userPanel, messageBoxPanel; //右边消息框
    //消息编辑框
    protected JTextArea messageTextArea;
    //用户名输入框
    protected JTextField nameTextField, ipTextField; //服务器IP地址输入框
    //端口输入框
    protected JTextField portTextField;
    //消息编辑框
    protected JTextField messageTextField;
    //To..标签
    protected JLabel messageToLabel;
    //连接按钮
    protected JButton connectButton, disconnectButton, //断开按钮
            sendButton; //发送按钮
    //动态变化的用户列表
    protected JList userList;

    //Model
    protected DefaultListModel<String> listModel;

    //构造函数
    public ClientView() {
        initUI();
    }

    //UI初始化函数
    private void initUI() {

        //设置客户端窗口标题、大小以及布局
        frame = new JFrame("客户端");
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        //设置面板初始参数
        ipTextField = new JTextField("127.0.0.1");
        portTextField = new JTextField("8888");
        nameTextField = new JTextField("小兰");
        connectButton = new JButton("连接");
        disconnectButton = new JButton("断开");

        //配置面板
        settingPanel = new JPanel();
        //设置布局为一行八列
        settingPanel.setLayout(new GridLayout(1, 8));
        //为配置面板添加组件
        settingPanel.add(new JLabel("         名字:"));
        settingPanel.add(nameTextField);
        settingPanel.add(new JLabel("  服务器IP:"));
        settingPanel.add(ipTextField);
        settingPanel.add(new JLabel("  端口号:"));
        settingPanel.add(portTextField);
        settingPanel.add(connectButton);
        settingPanel.add(disconnectButton);
        //设置配置面板标题
        settingPanel.setBorder(new TitledBorder("客户端配置"));

        //在线用户面板
        listModel = new DefaultListModel<>();
        userList = new JList(listModel);
        userPanel = new JScrollPane(userList);
        //设置在线用户面板标题
        userPanel.setBorder(new TitledBorder("在线用户"));

        //接收消息面板
        messageTextArea = new JTextArea();
        //设置该区域不可编辑
        messageTextArea.setEditable(false);
        //设置字体默认颜色为蓝色
        messageTextArea.setForeground(Color.blue);
        //设置为带滑动条的文本框
        messageBoxPanel = new JScrollPane(messageTextArea);
        //设置标题
        messageBoxPanel.setBorder(new TitledBorder("接收消息"));

        //发送消息组件
        //默认为发送给所有人
        messageToLabel = new JLabel("To:所有人  ");
        messageTextField = new JTextField();
        sendButton = new JButton("发送");
        //将组件放置在面板上
        messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageToLabel, "West");
        messagePanel.add(messageTextField, "Center");
        messagePanel.add(sendButton, "East");
        messagePanel.setBorder(new TitledBorder("发送消息"));

        //将中间在线用户面板与接收消息面板组合起来
        centerSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPanel, messageBoxPanel);
        //设置分隔线离左边100px
        centerSplitPanel.setDividerLocation(100);

        frame.add(settingPanel, "North");
        frame.add(centerSplitPanel, "Center");
        frame.add(messagePanel, "South");
        frame.setVisible(true);
        //设置按钮以及文本框的默认状态
        serviceUISetting(false);
    }

    public void serviceUISetting(boolean connected) {
        portTextField.setEnabled(!connected);
        ipTextField.setEnabled(!connected);
        disconnectButton.setEnabled(connected);
        connectButton.setEnabled(!connected);
        sendButton.setEnabled(connected);
        messageTextField.setEnabled(connected);
        nameTextField.setEnabled(!connected);
    }
}