package panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MyFrame extends JFrame {
    Container contentPane = this.getContentPane();
    TimePanel timePanel = new TimePanel();
    JsonPanel jsonPanel = new JsonPanel();

    public MyFrame(String title) {
        // 设置软件图标
        Image image;
        try {
            image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/MilkTea.png")));
            this.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setBackground(new Color(255, 255, 255));
        jTabbedPane.setFont(new Font("宋体", Font.BOLD, 20));
        jTabbedPane.add("时间戳转换", timePanel);
        jTabbedPane.add("JSON格式化", jsonPanel);
        contentPane.add(jTabbedPane);
        this.setTitle(title);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - 1125) / 2, (dimension.height - 900) / 2, 1125, 900);
        this.setMinimumSize(new Dimension(1000, 900));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

