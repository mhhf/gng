package gng.handlers.inputs;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mhhf
 */
public class ImageRenderer extends Image{
    public Image image;
    public int array[][];
    
    public ImageRenderer(BufferedImage img) {
        BufferedImage buf = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
        array = new int[300][300];
        Color c;
        Graphics g = buf.getGraphics();
        int rgb;
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                c = new Color(img.getRGB(x, y));
                
                array[x][y] = c.getBlue();
                
                g.setColor( new Color(img.getRGB(x, y)) );
                g.fillRect(x*2, y*2, 2, 2);
            }
        }
        image = Toolkit.getDefaultToolkit().createImage(buf.getSource());
    }

    @Override
    public int getWidth(ImageObserver io) {
        return image.getWidth(io);
    }

    @Override
    public int getHeight(ImageObserver io) {
        return image.getHeight(io);
    }

    @Override
    public ImageProducer getSource() {
        return image.getSource();
    }

    @Override
    public Graphics getGraphics() {
        return image.getGraphics();
    }

    @Override
    public Object getProperty(String string, ImageObserver io) {
        return image.getProperty(string, io);
    }
    
}
