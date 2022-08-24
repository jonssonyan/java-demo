package com.jonssonyan.main;

import com.jonssonyan.entity.User;
import com.jonssonyan.view.ClientView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端
 */
public class Client extends ClientView {

    private User user;
    // 所有在线用户
    private ConcurrentHashMap<String, User> onlineUsers = new ConcurrentHashMap<>();
    //默认发送对象
    private String sendTarget = "ALL";

    //Socket
    private Socket socket;
    //输出流
    private PrintWriter writer;
    //输入流
    private BufferedReader reader;

    // 负责接收消息的线程
    private MessageThread messageThread;

    //Status
    //判断是否连接到服务端
    private boolean isConnected;

    //构造函数
    public Client() {

        //写消息的文本框中按回车键时事件
        messageTextField.addActionListener(e -> send());

        //单击发送按钮时事件
        sendButton.addActionListener(e -> send());

        //单击连接按钮时事件
        connectButton.addActionListener(e -> {
            if (!isConnected) {
                connect();
            }
        });

        //单击断开按钮时事件
        disconnectButton.addActionListener(e -> {
            if (isConnected) {
                disconnect();
            }
        });

        //关闭窗口时事件
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isConnected) {
                    disconnect();
                }
                System.exit(0);
            }
        });

        //为在线用户添加点击事件
        userList.addListSelectionListener(e -> {
            //获取被点击的用户的序号
            int index = userList.getSelectedIndex();
            if (index < 0) return;

            if (index == 0) {  //默认为所有人
                sendTarget = "ALL";
                messageToLabel.setText("To: 所有人");
            } else {
                //获取被点击用户的名字
                String name = listModel.getElementAt(index);
                if (onlineUsers.containsKey(name)) {
                    sendTarget = onlineUsers.get(name).description();
                    //将To..标签改为To 用户名
                    messageToLabel.setText("To: " + name);
                } else {
                    sendTarget = "ALL";
                    messageToLabel.setText("To: 所有人");
                }
            }
        });
    }

    //连接
    private void connect() {
        int port;

        try {
            //获取端口号
            port = Integer.parseInt(portTextField.getText().trim());
        } catch (NumberFormatException e) {
            showErrorMessage("端口号必须为整数！");
            return;
        }
        //判断端口号是否符合
        if (port < 1024 || port > 65535) {
            showErrorMessage("端口号必须在1024～65535之间");
            return;
        }
        //获取用户名
        String username = nameTextField.getText().trim();
        //判断用户名是否为空
        if (username.equals("")) {
            showErrorMessage("名字不能为空！");
            return;
        }
        //获取IP地址
        String ip = ipTextField.getText().trim();
        //判断IP地址是否为空
        if (ip.equals("")) {
            showErrorMessage("IP地址不能为空！");
            return;
        }

        try {
            listModel.addElement("所有人");

            user = new User(username, ip);
            //根据指定IP地址以及端口号建立线程
            socket = new Socket(ip, port);
            //输入流
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //输出流
            writer = new PrintWriter(socket.getOutputStream());
            //获取客户端所在的IP地址
            String myIP = socket.getLocalAddress().toString().substring(1);
            //发送用户登录信息
            sendMessage("LOGIN@" + username + "%" + myIP);
            //创建接收消息的线程
            messageThread = new MessageThread();
            Thread thread = new Thread(messageThread);
            thread.start();
            isConnected = true;
        } catch (Exception e) {
            isConnected = false;
            logMessage("客户端连接失败");
            //移除在线面板上所有用户
            listModel.removeAllElements();
            e.printStackTrace();
            return;
        }

        //将连接成功的消息显示到消息面板上
        logMessage("客户端连接成功");
        //设置按钮的状态
        serviceUISetting(isConnected);
    }

    //消息发送
    private void send() {
        if (!isConnected) {
            showErrorMessage("未连接到服务器！");
            return;
        }
        //获取发送框内容
        String message = messageTextField.getText().trim();
        if (message.equals("")) {
            showErrorMessage("消息不能为空！");
            return;
        }

        String to = sendTarget;
        try {
            //向服务器发送消息
            //MSG@+“接收消息用户名 %IP地址”+“发送者用户名 %IP地址”+@+message
            sendMessage("MSG@" + to + "@" + user.description() + "@" + message);
            logMessage("我->" + to + ": " + message);
        } catch (Exception e) {
            e.printStackTrace();
            logMessage("（发送失败）我->" + to + ": " + message);
        }

        //发送完毕把输入框置空
        messageTextField.setText(null);
    }

    //断开连接
    private synchronized void disconnect() {
        try {
            //向服务器发送断开连接的消息
            sendMessage("LOGOUT");

            messageThread.close();
            listModel.removeAllElements();
            onlineUsers.clear();

            reader.close();
            writer.close();
            socket.close();
            isConnected = false;
            serviceUISetting(false);

            sendTarget = "ALL";
            messageToLabel.setText("To: 所有人");

            logMessage("已断开连接...");
        } catch (Exception e) {
            e.printStackTrace();
            isConnected = true;
            serviceUISetting(true);
            showErrorMessage("服务器断开连接失败！");
        }
    }

    private void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    private void logMessage(String msg) {
        messageTextArea.append(msg + "\r\n");
    }

    private void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //接收消息的线程
    private class MessageThread implements Runnable {
        private boolean isRunning = false;

        public MessageThread() {
            isRunning = true;
        }

        @Override
        public void run() {
            //不断接收消息
            while (isRunning) {
                try {
                    String message = reader.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(message, "@");
                    String command = tokenizer.nextToken();

                    switch (command) {
                        case "CLOSE":
                            logMessage("服务器已关闭，正在断开连接...");
                            disconnect();
                            isRunning = false;
                            return;
                        case "ERROR":
                            String error = tokenizer.nextToken();
                            logMessage("服务器返回错误，错误类型：" + error);
                            break;
                        case "LOGIN":
                            String status = tokenizer.nextToken();
                            if (status.equals("SUCCESS")) {
                                logMessage("登录成功！" + tokenizer.nextToken());
                            } else if (status.equals("FAIL")) {
                                logMessage("登录失败，断开连接！原因：" + tokenizer.nextToken());
                                disconnect();
                                isRunning = false;
                                return;
                            }
                            break;
                        case "USER":
                            String type = tokenizer.nextToken();
                            switch (type) {
                                case "ADD": {
                                    String userDescription = tokenizer.nextToken();
                                    User newUser = new User(userDescription);
                                    onlineUsers.put(newUser.getUsername(), newUser);
                                    listModel.addElement(newUser.getUsername());

                                    logMessage("新用户（" + newUser.description() + "）上线！");

                                    break;
                                }
                                case "DELETE": {
                                    String userDescription = tokenizer.nextToken();
                                    User deleteUser = new User(userDescription);
                                    onlineUsers.remove(deleteUser.getUsername());
                                    listModel.removeElement(deleteUser.getUsername());

                                    logMessage("用户（" + deleteUser.description() + "）下线！");

                                    if (sendTarget.equals(deleteUser.description())) {
                                        sendTarget = "ALL";
                                        messageToLabel.setText("To: 所有人");
                                    }

                                    break;
                                }
                                case "LIST":
                                    int num = Integer.parseInt(tokenizer.nextToken());
                                    for (int i = 0; i < num; i++) {
                                        String userDescription = tokenizer.nextToken();
                                        User newUser = new User(userDescription);
                                        onlineUsers.put(newUser.getUsername(), newUser);
                                        listModel.addElement(newUser.getUsername());

                                        logMessage("获取到用户（" + newUser.description() + "）在线！");
                                    }
                                    break;
                            }
                            break;
                        case "MSG":
                            StringBuilder buffer = new StringBuilder();
                            String to = tokenizer.nextToken();
                            String from = tokenizer.nextToken();
                            String content = tokenizer.nextToken();

                            buffer.append(from);
                            if (to.equals("ALL")) {
                                buffer.append("（群发）");
                            }
                            buffer.append(": ").append(content);
                            logMessage(buffer.toString());
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logMessage("接收消息异常！");
                }
            }
        }

        public void close() {
            isRunning = false;
        }
    }

    // 主函数
    public static void main(String[] args) {
        new Client();
    }
}
