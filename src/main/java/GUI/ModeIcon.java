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
    private Color tempColor = Color.black;
    private Color currentColor = Color.BLACK;
    String[] iconName;
    private Color[] colors;

    private HashMap<String, BufferedImage> imgs;

    public ModeIcon(String path, String[] in, Color[] c)  {
        this.setSize(300, 45);
        this.setLocation(35,80);
        this.il = new IconLoader(path);
        this.colors = c;
        this.iconName = in;
//        this.originalImg = IconLoader.getIcons();
//        this.img = recolorImage(this.originalImg, Color.gray);
        this.setBackground(Color.black);

        this.imgs = il.getIcons();
    }

//    public ModeIcon(HashMap<String, BufferedImage> in, Color[] c)  {
//        colors = c;
//        imgs = in;
////        this.originalImg = IconLoader.getIcons();
////        this.img = recolorImage(this.originalImg, Color.gray);
//        this.setBackground(Color.black);
//    }
//    public ModeIcon(String iconName, Color defaultColor)  {
//        this(iconName);
//        this.img = recolorImage(this.originalImg, defaultColor);
//    }

//    public void setColor(String[] iconName, Color color){
//        this.currentColor = color;
//        this.img = il.getIcons(iconName,color);
//        this.paintComponent(this.getGraphics());
//    }

    public void setModeColor(Color[] c){
        assert c.length == 6;
        this.colors =c;
        paintComponent(this.getGraphics());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.clearRect(0,0,this.getWidth(), this.getHeight());

        for (int i = 0; i < iconName.length; i++) {

            this.img = il.getIcons(iconName[i],colors[i]);
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
                        colors[i] = Color.red;
                    }
                    else colors[i] = Color.black;
                }
                repaint();
                idx = (idx + 1) % 6;
            }
        },0,1000);
    }


//
//    public BufferedImage recolorImage(BufferedImage original, Color replaceColor) {
//        int width = original.getWidth();
//        int findColor = Color.black.getRGB();
//        int height = original.getHeight();
//        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = (Graphics2D)newImage.getGraphics();
//        for(int w=0;w<width;w++){
//            for(int h=0;h<height;h++){
//                if(findColor == original.getRGB(w,h)) {
//                    g.setColor(replaceColor);
//                } else {
//                    g.setColor(Color.white);
//                }
//                g.drawLine(w, h, w, h);
//            }
//        }
//        return newImage;
//    }
}
