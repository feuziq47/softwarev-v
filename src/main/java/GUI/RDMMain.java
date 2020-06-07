package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RDMMain extends JFrame{
    private Button[] buttons;


    public RDMMain(){
        background panel = new background();
        panel.setLayout(null);
        panel.setSize(600, 400);
        panel.setLocation(50, 40);
        panel.setBackground(Color.cyan);
        panel.setOpaque(false);

        int[] btnX = {24, 646, 24, 646};
        int[] btnY = {100, 100, 310, 310};
        String[] btnName = {"A","B","C","D"};
        this.setBackground(Color.black);
        buttons = new Button[4];
        for(int btnIndex = 0;btnIndex<4;btnIndex++){
            Button btn = new Button();
            btn.setName(btnName[btnIndex]);
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
        //String[] iconName = {"timeKeeping.png","stopWatch.png","timer.png","alarm.png","decisionMaker.png","worldTime.png"};
        String[] iconName = {"1.png","2.png","3.png","4.png","5.png","6.png"};
        String path = "C:\\Users\\kdje0\\Documents\\Git\\softwarev-v\\src\\main\\java\\GUI\\icon\\";
        //IconLoader il = new IconLoader(path);
        //HashMap<String, BufferedImage> icons = il.getIcons();

        ModeIcon mi = new ModeIcon(path, iconName, iconColors);

        panel.add(mi);
        mi.test();


//        try{
//            Thread.sleep(2000);
//        }catch(InterruptedException e){
//            e.printStackTrace();;
//        }

//        for(int i=0;i< icons.length;i++){
//            icons[i] = new WatchIcon(iconNames[i], iconColors[i]);
//            icons[i].setSize(40, 40);
//            icons[i].setLocation(50 + 45 * i, 80);
//            icons[i].setVisible(true);
//            panel.add(icons[i]);
//        }

//        JLabel time = new JLabel("12 : 00 : 00");
//        time.setFont(new Font("궁서", Font.BOLD, 90));
//        time.setSize(550,100);
//        time.setLocation(20,200);
//        time.setHorizontalAlignment(SwingConstants.CENTER);
//        time.setVerticalAlignment(SwingConstants.CENTER);
//        panel.add(time);

//        JLabel date = new JLabel("<html><font color = 'red'>2020 06 05</font></html>");
//        date.setFont(new Font("궁서", Font.BOLD, 25));
//        date.setSize(150,40);
//        date.setLocation(390,80);
//        date.setBackground(Color.black);
//        date.setOpaque(true);
//        date.setHorizontalAlignment(SwingConstants.CENTER);
//        date.setVerticalAlignment(SwingConstants.CENTER);

        SubScreen ss = SubScreen.getInstance();
        panel.add(ss);

        MainScreen ms = new MainScreen();
        panel.add(ms);
        //ms.displayTime(LocalTime.now());
        ms.test();

        this.setTitle("RDM Watch");
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
