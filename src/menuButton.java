import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class menuButton extends JButton {
    menuButton(String text, int id){
        this.setVisible(true);
        this.setPreferredSize(new Dimension(240,60));
        this.setBackground(new Color(181, 43, 0));
        this.setForeground(Color.WHITE);
        this.setFocusable(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setFont(new Font("Bahnschrift",Font.PLAIN,20));
        this.setText(text);
        this.addActionListener(e -> ButtonAction.exec(id));
    }
}
