package GUI;

import javax.swing.*;
import java.awt.*;

public class SubScreen extends JLabel {
    private SubScreen() {
        this.setText("YYYY MM DD X");
        this.setFont(new Font("궁서", Font.BOLD, 20));
        this.setSize(160,40);
        this.setLocation(390,80);
        //this.setBackground(Color.black);
        //this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }
    
    /**
     * 스트링 객체를 받아 화면에 출력해준다.
     * @param msg : 화면에 출력할 메시지
     */
    public void display(String msg){
        this.setText(msg);
    }

    private static class InnerInstanceClass {
        private static final SubScreen instance = new SubScreen();
    }

    public static SubScreen getInstance() {
        return InnerInstanceClass.instance;
    }
}
