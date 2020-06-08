package GUI;

import javax.swing.*;
import java.awt.*;

public class SubScreen extends JLabel {
    private SubScreen() {
        this.setText("YYYY MM DD X");
        this.setFont(new Font("맑은고딕", Font.BOLD, 20));
        this.setSize(200,60);
        this.setLocation(370,70);
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
        System.out.println("Sub display call : " + msg);
    }

    private static class InnerInstanceClass {
        private static final SubScreen instance = new SubScreen();
    }

    public static SubScreen getInstance() {
        return InnerInstanceClass.instance;
    }
}
