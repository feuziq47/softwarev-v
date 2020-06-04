package GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

public class IconLoader {
    public static HashMap<String, BufferedImage> icons;

    public IconLoader(String path) {
        File iconDir = new File(path);
        icons = new HashMap<String, BufferedImage>();
        File[] iconList = iconDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("png");
            }
        });

        assert iconList != null;
        for (File f : iconList) {
            try{
                System.out.println(f.getName());
                System.out.println(ImageIO.read(new File(f.getPath())));
                //System.out.println(f.getPath());
                //icons.put(f.getName().split(".")[0],ImageIO.read(f));
                icons.put(f.getName(),ImageIO.read(new File(f.getPath())));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

//    public BufferedImage getIcons(String iName, Color color) {
//        //return recolorImage(icons.get(iName),color);
//        return icons.get(iName);
//    }

    public HashMap<String, BufferedImage> getIcons() {
        //return recolorImage(icons.get(iName),color);
        return icons;
    }

    private static BufferedImage recolorImage(BufferedImage original, Color replaceColor) {
        assert original !=null;
        int width = original.getWidth();
        int findColor = Color.black.getRGB();
        int height = original.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        for(int w=0;w<width;w++){
            for(int h=0;h<height;h++){
                if(findColor == original.getRGB(w,h)) {
                    g.setColor(replaceColor);
                } else {
                    g.setColor(Color.white);
                }
                g.drawLine(w, h, w, h);
            }
        }
        return newImage;
    }
}
