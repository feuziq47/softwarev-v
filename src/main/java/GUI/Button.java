package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Button extends JLabel {
    private Button t;
    private ActionListener eventClick;
    private ActionListener eventClickHold;
    private int holdTime;
    private boolean shape = true;

    public Button(){
        this.setLayout(null);
        this.setBackground(Color.white);
        this.t = this;
        this.holdTime = 1000;
        this.addMouseListener(new ML());
    }

    public void setShape(boolean shape){
        this.shape = shape;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        g2.setStroke(new BasicStroke(4));

        int x = 2;
        if(this.shape == false) {
            x = -2;
        }
        g2.fillRoundRect(x, 2, this.getWidth(), this.getHeight() - 4, 15, 15);
    }

    public void setClickListener(ActionListener l){
        this.eventClick = l;
    }

    public void setClickHoldListener(ActionListener l){
        this.eventClickHold = l;
    }

    class ML implements MouseListener{
        private long mousePressedTime;

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Mouse Clicked");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            this.mousePressedTime = System.currentTimeMillis();
            setBackground(Color.black);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            long timeDifference = System.currentTimeMillis() - this.mousePressedTime;
            int uniqueId = (int) System.currentTimeMillis();
            if(timeDifference >= holdTime) {
                if(t.eventClickHold != null){
                    String commandName = "ClickHold";
                    ActionEvent event = new ActionEvent(this, uniqueId, commandName);
                    t.eventClickHold.actionPerformed(event);
                }
            } else {
                if(t.eventClick != null){
                    String commandName = "Click";
                    ActionEvent event = new ActionEvent(this, uniqueId, commandName);
                    t.eventClick.actionPerformed(event);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("Mouse Entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("Mouse Exited");
        }
    }
}
