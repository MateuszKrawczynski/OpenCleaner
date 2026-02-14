import javax.swing.*;
import java.io.File;
import java.io.FileWriter;



public class Main {
    public static ImageIcon globalAppIcon = new ImageIcon("img/icon.png");
    public static MainFrame mainframe;
    public static void main(String[] args){
        mainframe = new MainFrame();
            if (!(new File("customization.txt").exists())){
                try{
                new File("customization.txt").setWritable(true);
                new File("customization.txt").createNewFile();
                FileWriter writer = new FileWriter("customization.txt");

                writer.write("0000000000000000");writer.close();
                new File("customization2.txt").setWritable(true);
                new File("customization2.txt").createNewFile();
                FileWriter writer2 = new FileWriter("customization2.txt");

                writer2.write("000000");writer2.close();} catch (Exception e){}

            }
        ButtonAction.exec(9);

        }
    }

