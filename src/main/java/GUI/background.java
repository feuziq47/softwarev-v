package GUI;

import javax.swing.*;
import java.awt.*;

public class background extends JPanel {
    public background() {
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        g2.setStroke(new BasicStroke(4));

        //main border
        g2.drawRoundRect(2,2,this.getWidth()-4, this.getHeight()-30,80, 80);

        // left top
        g2.drawRoundRect(30,70,320, 60,20, 20);

        //right top
        g2.drawRoundRect(370,70,200, 60,20, 20);
        //bottom
        g2.drawRoundRect(30,150,540, 200,20, 20);
    }
}
