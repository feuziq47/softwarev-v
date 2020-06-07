package GUI;

import javax.swing.*;
import java.awt.*;

public class SubScreen extends JLabel {
    //private construct
    private SubScreen() {
        this.setText("2020 06 05");
        this.setFont(new Font("궁서", Font.BOLD, 25));
        this.setSize(160,40);
        this.setLocation(390,80);
        //this.setBackground(Color.black);
        //this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

    private static class InnerInstanceClass {
        private static final SubScreen instance = new SubScreen();
    }

    public static SubScreen getInstance() {
        return InnerInstanceClass.instance;
    }

//    public SubScreen(){
//        this.setText("2020 06 05");
//        this.setFont(new Font("궁서", Font.BOLD, 25));
//        this.setSize(160,40);
//        this.setLocation(390,80);
//        //this.setBackground(Color.black);
//        //this.setOpaque(true);
//        this.setHorizontalAlignment(SwingConstants.CENTER);
//        this.setVerticalAlignment(SwingConstants.CENTER);
//    }
}
