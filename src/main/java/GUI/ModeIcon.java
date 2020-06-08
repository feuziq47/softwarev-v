package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.TimerTask;

public class ModeIcon extends JLabel {
    private IconLoader il;
    private BufferedImage img;
    String[] iconName;
    private Color[] iconColors;
    String path;

    private HashMap<String, BufferedImage> imgs;

    private ModeIcon()  {
        this.setSize(300, 45);
        this.setLocation(35,80);

        Color c = new Color(0, 0, 0);

        this.iconColors = new Color[]{c, c, c, c, c, c};

        this.iconName = new String[]{"0timeKeeping.png", "1stopWatch.png", "2timer.png", "3alarm.png", "4decisionMaker.png", "5worldTime.png"};
        this.path = System.getProperty("user.dir") + "/src/main/java/GUI/icon/";
        this.il = new IconLoader(path);
        this.imgs = il.getIcons();
    }

    private static class InnerInstanceClass {
        private static final ModeIcon instance = new ModeIcon();
    }

    public static ModeIcon getInstance() {
        return ModeIcon.InnerInstanceClass.instance;
    }

    /**
     * 모드 아이콘 색상을 바꿔준다.
     * @param c : 길이가 6인 Color 색상 배열
     */
    public void setModeColor(Color[] c){
        assert c.length == 6;
        this.iconColors =c;
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.clearRect(0,0,this.getWidth(), this.getHeight());

        for (int i = 0; i < iconName.length; i++) {

            this.img = il.getIcons(iconName[i],iconColors[i]);
            assert this.img != null;
            //System.out.println(this.img);

            g2.drawImage(this.img, 10 + 50 * i, 0, 40,40, this);

        }

    }

    public void test(){
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int idx = 0;
            @Override
            public void run() {

                for (int i = 0; i < 6; i++) {
                    if (i == idx){
                        iconColors[i] = Color.red;
                    }
                    else iconColors[i] = Color.black;
                }
                repaint();
                idx = (idx + 1) % 6;
            }
        },0,1000);
    }
}
