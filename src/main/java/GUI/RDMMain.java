package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class RDMMain extends JFrame{
    private Button[] buttons;


    public RDMMain() throws IOException {
        background panel = new background();
        panel.setLayout(null);
        panel.setSize(600, 400);
        panel.setLocation(50, 40);
        panel.setBackground(Color.cyan);
        panel.setOpaque(false);

        int[] btnX = {24, 646, 24, 646};
        int[] btnY = {100, 100, 310, 310};
        this.setBackground(Color.black);
        buttons = new Button[4];
        for(int btnIndex = 0;btnIndex<4;btnIndex++){
            Button btn = new Button();
            btn.setSize(30, 60);
            btn.setLocation(btnX[btnIndex], btnY[btnIndex]);
            btn.setBackground(Color.red);
            btn.setVisible(true);
            btn.setShape((btnIndex % 2) == 0);
            btn.setHorizontalAlignment(JLabel.CENTER);
            this.add(btn);
            buttons[btnIndex] = btn;
        }

        Color c = new Color(0, 0, 0);
        Color[] iconColors = {c,c,c,c,c,c};
        String[] iconName = {"background.png"};
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String path = "C:\\Users\\kdje0\\Documents\\Git\\softwarev-v\\src\\main\\java\\GUI\\icon\\";
        IconLoader il = new IconLoader(path);
        HashMap<String, BufferedImage> icons = il.getIcons();
        ModeIcon mi = new ModeIcon(icons,iconColors);
        //mi.setImg(bi);
        mi.setSize(300, 45);
        mi.setLocation(35,80);
        mi.setVisible(true);

        panel.add(mi);
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();;
        }
//        for(int i=0;i< icons.length;i++){
//            icons[i] = new WatchIcon(iconNames[i], iconColors[i]);
//            icons[i].setSize(40, 40);
//            icons[i].setLocation(50 + 45 * i, 80);
//            icons[i].setVisible(true);
//            panel.add(icons[i]);
//        }



        this.setTitle("Digital Watch");
        this.setSize(700, 500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);
        this.setResizable(false);
        this.add(panel);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        RDMMain m = new RDMMain();
    }
}
