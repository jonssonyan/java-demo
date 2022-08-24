package panel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimePanel extends Panel {
    JLabel jLabel1 = new JLabel("时间戳：");
    JLabel jLabel2 = new JLabel("北京时间：");
    JButton jButton1 = new JButton("转换 >>");
    Choice choice1 = new Choice();
    JLabel jLabel3 = new JLabel("北京时间：");
    JLabel jLabel4 = new JLabel("时间戳：");
    JButton jButton2 = new JButton("转换 >>");
    Choice choice2 = new Choice();
    JTextField jTextField1 = new JTextField(20);
    JTextField jTextField2 = new JTextField(20);
    JTextField jTextField3 = new JTextField(20);
    JTextField jTextField4 = new JTextField(20);

    JLabel jLabel5 = new JLabel("当前时间戳(13位)：");
    JLabel jLabel6 = new JLabel("当前北京时间：");
    JTextField jTextField5 = new JTextField(20);
    JTextField jTextField6 = new JTextField(20);
    JButton jButton3 = new JButton("刷 新");

    Panel panel1 = new Panel();
    Panel panel2 = new Panel();
    Panel panel3 = new Panel();

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    public TimePanel() {
        GridLayout gridLayout = new GridLayout(3, 1);
        this.setLayout(gridLayout);

        // panel3当前日期和时间戳
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
        jLabel5.setFont(new Font("宋体", Font.BOLD, 20));
        panel3.add(jLabel5);
        jTextField5.setFont(new Font("宋体", Font.BOLD, 20));
        panel3.add(jTextField5);
        jLabel6.setFont(new Font("宋体", Font.BOLD, 20));
        panel3.add(jLabel6);
        jTextField6.setFont(new Font("宋体", Font.BOLD, 20));
        panel3.add(jTextField6);
        jButton3.addActionListener(e -> new Thread(() -> {
            jTextField5.setText(String.valueOf(System.currentTimeMillis()));
            jTextField6.setText(simpleDateFormat.format(System.currentTimeMillis()));
        }).start());
        jTextField5.setText(String.valueOf(System.currentTimeMillis()));
        jTextField6.setText(simpleDateFormat.format(System.currentTimeMillis()));
        jButton3.setFont(new Font("宋体", Font.BOLD, 20));
        panel3.add(jButton3);
        this.add(panel3);

        // panel1将时间戳转换为时间
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        jLabel1.setFont(new Font("宋体", Font.BOLD, 20));
        panel1.add(jLabel1);
        jTextField1.setFont(new Font("宋体", Font.BOLD, 20));
        panel1.add(jTextField1);
        choice1.setFont(new Font("宋体", Font.BOLD, 20));
        choice1.add("毫秒(ms)");
        choice1.add("秒(s)");
        panel1.add(choice1);
        jButton1.setFont(new Font("宋体", Font.BOLD, 20));
        panel1.add(jButton1);
        jLabel2.setFont(new Font("宋体", Font.BOLD, 20));
        panel1.add(jLabel2);
        jTextField2.setFont(new Font("宋体", Font.BOLD, 20));
        panel1.add(jTextField2);
        this.add(panel1);

        jButton1.addActionListener(e -> {
            if ("".equals(jTextField1.getText())) {
                JOptionPane.showMessageDialog(TimePanel.this, "请输入日期");
                return;
            }
            int index = choice1.getSelectedIndex();
            String format = "";
            if (index == 0) {
                try {
                    format = simpleDateFormat.format(Long.parseLong(jTextField1.getText()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TimePanel.this, "时间戳格式错误！");
                    return;
                }
            }
            if (index == 1) {
                try {
                    format = simpleDateFormat.format(Long.parseLong(jTextField1.getText()) * 1000);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TimePanel.this, "时间戳格式错误！");
                    return;
                }
            }
            jTextField2.setText(format);
        });

        // panel2将时间转换为时间戳
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        jLabel3.setFont(new Font("宋体", Font.BOLD, 20));
        panel2.add(jLabel3);
        jTextField3.setFont(new Font("宋体", Font.BOLD, 20));
        panel2.add(jTextField3);
        jButton2.setFont(new Font("宋体", Font.BOLD, 20));
        panel2.add(jButton2);
        choice2.setFont(new Font("宋体", Font.BOLD, 20));
        choice2.add("毫秒(ms)");
        choice2.add("秒(s)");
        panel2.add(choice2);
        jLabel4.setFont(new Font("宋体", Font.BOLD, 20));
        panel2.add(jLabel4);
        jTextField4.setFont(new Font("宋体", Font.BOLD, 20));
        panel2.add(jTextField4);
        this.add(panel2);
        jButton2.addActionListener(e -> {
            if ("".equals(jTextField3.getText())) {
                JOptionPane.showMessageDialog(TimePanel.this, "请输入日期");
                return;
            }
            int index = choice2.getSelectedIndex();
            String format = "";
            if (index == 0) {
                try {
                    format = String.valueOf(simpleDateFormat.parse(jTextField3.getText()).getTime());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(TimePanel.this, "日期格式错误！");
                    return;
                }
            }
            if (index == 1) {
                try {
                    format = String.valueOf(simpleDateFormat.parse(jTextField3.getText()).getTime() / 1000);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(TimePanel.this, "日期格式错误！");
                    return;
                }
            }
            jTextField4.setText(format);
        });
    }
}

