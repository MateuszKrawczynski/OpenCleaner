import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame {
    public JPanel topPanel = new JPanel();
    public JPanel leftPanel = new JPanel();
    public JPanel centerPanel = new JPanel();
    MainFrame(){
        this.setSize(new Dimension(1910,700));
        this.setTitle("Open Cleaner");
        this.setIconImage(Main.globalAppIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        topPanel.setPreferredSize(new Dimension(100,75));
        topPanel.setBackground(new Color(1, 40, 130));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING,30,5));
        this.add(topPanel,BorderLayout.NORTH);

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Open Cleaner");
        titleLabel.setIcon(Main.globalAppIcon);
        titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
        titleLabel.setVerticalTextPosition(JLabel.CENTER);
        titleLabel.setFont(new Font("Calibri",Font.BOLD,30));
        titleLabel.setForeground(Color.white);
        titleLabel.setIconTextGap(25);
        topPanel.add(titleLabel);

        JLabel authorLabel = new JLabel();
        authorLabel.setText("by Mateusz Krawczyński");
        authorLabel.setForeground(Color.WHITE);
        authorLabel.setFont(new Font("Calibri", Font.ITALIC,16));
        topPanel.add(authorLabel);

        leftPanel.setPreferredSize(new Dimension(280,100));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setLayout(new FlowLayout());
        this.add(leftPanel,BorderLayout.WEST);
        this.add(centerPanel);

       leftPanel.add(new menuButton("Customized clean",2));
       leftPanel.add(new menuButton("Analize your files",4));
       leftPanel.add(new menuButton("Organize your files",6));
       leftPanel.add(new menuButton("About this software",9));

        this.setVisible(true);
    }
}
