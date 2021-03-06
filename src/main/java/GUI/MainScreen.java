package GUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class MainScreen extends JLabel {
    static public final int timeAll = 100;
    static public final int timeH = 101;
    static public final int timeM = 102;
    static public final int timeS = 103;

    private String cityName;
    private String hh = "00";
    private String mm= "00";
    private String ss= "00";

    private MainScreen(){
        this.setFont(new Font("맑은고딕", Font.BOLD, 60));
        this.setSize(540,200);
        this.setLocation(30,150);
        this.setText("XX : XX : XX");
        //this.setBackground(Color.black);
        //this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

    private static class InnerInstanceClass {
        private static final MainScreen instance = new MainScreen();
    }

    public static MainScreen getInstance() {
        return MainScreen.InnerInstanceClass.instance;
    }

    public void displayTime(LocalTime time){
        assert time != null;
        this.hh = String.format("%02d", time.getHour());
        this.mm = String.format("%02d", time.getMinute());
        this.ss = String.format("%02d", time.getSecond());

        this.setText(hh + " : " + mm + " : " + ss);
        //super.update(this.getGraphics());
    }

    /**
     * 스트링 객체를 받아 화면에 출력해준다.
     * @param msg : 화면에 출력할 메시지
     */
    public void display(String msg){
        System.out.println("Main display call : " + msg);
        this.setText(msg);
        repaint();
    }

    public void highlight(int mode){
        assert mode == MainScreen.timeAll || mode == MainScreen.timeH ||
                mode == MainScreen.timeM || mode == MainScreen.timeS;

        switch (mode){
            case MainScreen.timeAll:
                this.setText(String.format("<html><font color='red'>%s : %s : %s</font></html>", hh, mm, ss));
                break;
            case MainScreen.timeH:
                this.setText(String.format("<html><font color='red'>%s</font> : %s : %s</html>", hh, mm, ss));
                break;
            case MainScreen.timeM:
                this.setText(String.format("<html>%s : <font color='red'>%s</font> : %s</html>", hh, mm, ss));
                break;
            case MainScreen.timeS:
                this.setText(String.format("<html>%s : %s : <font color='red'>%s</font></html>", hh, mm, ss));
                break;
        }
    }

    public void test(){
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            private int idx = 0;
            private int[] arr = {MainScreen.timeAll,MainScreen.timeH, MainScreen.timeM, MainScreen.timeS };
            @Override
            public void run() {
                //displayTime(LocalTime.now());
                highlight(arr[idx % 4]);
                idx += 1;

            }
        },0,1000);
    }
}
