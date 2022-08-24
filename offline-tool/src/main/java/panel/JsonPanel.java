package panel;

import util.JsonFormatUtil;

import javax.swing.*;
import java.awt.*;

public class JsonPanel extends Panel {
    JTextArea jTextArea1 = new JTextArea(42, 35);
    JTextArea jTextArea2 = new JTextArea(42, 65);
    JButton jButton = new JButton("转换 >>");
    JsonFormatUtil jsonFormatUtil = new JsonFormatUtil();

    public JsonPanel() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        final JScrollPane jScrollPane1 = new JScrollPane(jTextArea1);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jScrollPane1.setBackground(new Color(255, 251, 240));
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jTextArea1.setFont(new Font("Consolas", Font.PLAIN, 15));
        this.add(jScrollPane1);

        jButton.setFont(new Font("宋体", Font.BOLD, 20));
        this.add(jButton);
        jButton.addActionListener(e -> {
            if ("".equals(jTextArea1.getText())) {
                JOptionPane.showMessageDialog(JsonPanel.this, "请输入json字符串");
                return;
            }
            String json = jsonFormatUtil.formatJson(jTextArea1.getText());
            jTextArea2.setText(json);
        });

        JScrollPane jScrollPane2 = new JScrollPane(jTextArea2);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jScrollPane2.setBackground(new Color(255, 251, 240));
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jTextArea2.setFont(new Font("Consolas", Font.PLAIN, 15));
        this.add(jScrollPane2);
    }
}

