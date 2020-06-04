package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ModeIcon extends JLabel {
    private IconLoader il;
    private boolean selected;
    private BufferedImage originalImg;
    private BufferedImage img;
    private Color tempColor = Color.black;
    private Color currentColor = Color.BLACK;
    String[] iconName;
    private Color[] colors;

    private HashMap<String, BufferedImage> imgs;

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public ModeIcon(String path, String[] in, Color[] c)  {
        il = new IconLoader(path);
        colors = c;
        iconName = in;
//        this.originalImg = IconLoader.getIcons();
//        this.img = recolorImage(this.originalImg, Color.gray);
        this.setBackground(Color.black);

        imgs = il.getIcons();
    }
    public ModeIcon(HashMap<String, BufferedImage> in, Color[] c)  {
        colors = c;
        imgs = in;
//        this.originalImg = IconLoader.getIcons();
//        this.img = recolorImage(this.originalImg, Color.gray);
        this.setBackground(Color.black);
    }
//    public ModeIcon(String iconName, Color defaultColor)  {
//        this(iconName);
//        this.img = recolorImage(this.originalImg, defaultColor);
//    }

//    public void setColor(String[] iconName, Color color){
//        this.currentColor = color;
//        this.img = il.getIcons(iconName,color);
//        this.paintComponent(this.getGraphics());
//    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.clearRect(0,0,this.getWidth(), this.getHeight());

        //g2.clearRect(0,0,this.getWidth(), this.getHeight());
//        if(this.img != null)
//            g2.drawImage(this.img, 0, 0,this.getWidth(),this.getHeight(), this);
//        else {
//            g2.drawImage(this.originalImg, 0, 0,this.getWidth(),this.getHeight(), this);
//        }
        int i = 0;
        for (BufferedImage bi:imgs.values()) {
            System.out.println(bi);
            //g2.drawImage(bi, 20 + 55 * i, 0, 40,40, this);
            g2.drawImage(bi, 10 + 50 * i, 0, 40,40, this);
            i++;
        }
//        for (int i = 0; i < iconName.length; i++) {
//
////            this.img = il.getIcons(iconName[i],colors[i]);
////            assert this.img != null;
////            System.out.println(this.img);
////            g2.drawImage(this.img, 50 + 45 * i, 80, 400,400, this);
//
//            //this.img = imgs.get(iconName[0]);
//
////            assert this.img != null;
////            System.out.println(this.img);
////            //g2.drawImage(this.img, 50 + 45 * i, 80, 400,400, this);
////            g2.drawImage(this.img, 0, 0,this.getWidth(),this.getHeight(), this);
//        }
//        g2.drawRoundRect(60,60,30, 30,20, 20);
//
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
