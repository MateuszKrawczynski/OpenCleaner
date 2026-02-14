import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;

public class CheckBox extends JCheckBox {
    CheckBox(String text,int id,int ver){
        this.setText(text);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setFocusPainted(false);
        this.setSize(new Dimension(60,60));
        this.setFont(new Font("Calibri",Font.BOLD,16));
        ImageIcon unchecked = new ImageIcon("img/unchecked_checkbox.png");
        ImageIcon clicked = new ImageIcon("img/clicked_checkbox.png");
        ImageIcon checked = new ImageIcon("img/checked_checkbox.png");
        this.setPressedIcon(clicked);
        this.setSelectedIcon(checked);
        this.setIcon(unchecked);
        this.addActionListener(e -> {
            String editing = "";
            String finals = "";
            try{
                if (ver == 1){
                FileReader fr = new FileReader("customization.txt");



                editing = fr.readAllAsString();
                if (isSelected()){
                    for(int i = 0;i<editing.length();i++){
                        if (id == i){finals += "1"; }
                        else { finals += editing.charAt(i); }
                    }
                }
                else{
                    for(int i = 0;i<editing.length();i++){
                        if (id == i){finals += "0"; }
                        else { finals += editing.charAt(i); }
                    }
                }
                FileWriter fw = new FileWriter("customization.txt");

                fw.write(finals);
                fw.close();}
                else if (ver == 2){
                    FileReader fr = new FileReader("customization2.txt");



                    editing = fr.readAllAsString();
                    if (isSelected()){
                        for(int i = 0;i<editing.length();i++){
                            if (id == i){finals += "1"; }
                            else { finals += editing.charAt(i); }
                        }
                    }
                    else{
                        for(int i = 0;i<editing.length();i++){
                            if (id == i){finals += "0"; }
                            else { finals += editing.charAt(i); }
                        }
                    }
                    FileWriter fw = new FileWriter("customization2.txt");

                    fw.write(finals);
                    fw.close();}
            } catch (Exception e2){}
        });
    }
    public boolean getStatus(){
        if (this.isSelected()){ return true; }
        else{ return false; }
    }
}
