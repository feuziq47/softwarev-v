package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RDMMain extends JFrame{
    private Button[] buttons;
    private final String[] btnName = {"AD","RE","MO","ST"};


    private RDMMain(){
        Background panel = Background.getInstance();

        int[] btnX = {24, 646, 24, 646};
        int[] btnY = {100, 100, 310, 310};
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

        ModeIcon mi = ModeIcon.getInstance();

        panel.add(mi);
        //mi.test();

        SubScreen ss = SubScreen.getInstance();
        panel.add(ss);

        MainScreen ms = MainScreen.getInstance();
        panel.add(ms);
        //ms.test();


        this.setTitle("RDM Watch");
        this.setSize(700, 500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);
        this.setResizable(false);
        this.add(panel);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private static class InnerInstanceClass {
        private static final RDMMain instance = new RDMMain();
    }

    public static RDMMain getInstance() {
        return RDMMain.InnerInstanceClass.instance;
    }

    public static void main(String[] args){
        RDMMain m = RDMMain.getInstance();
    }
}
