/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.handlers.inputs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import no.uib.cipr.matrix.DenseVector;


/**
 *
 * @author mhhf
 */
public class ImageBasedInputs implements InputSpaceManager {
    
    // image for the visualisation
    private Image image;
    
    // set of input vectors
    private ArrayList<DenseVector> inputSet;

    public ImageBasedInputs(String location) throws IOException {
        loadImage( location );    
    }

    @Override
    public Image getVisualisation() {
        return this.image;
    }
    
    // Load an image and parse it
    private void loadImage(String fileName) throws IOException {
        
        // load Image from a file
        BufferedImage img = ImageIO.read(new File(fileName));
        
        // parse the image and the input set
        this.parseImage( img );
        
        //ImageRenderer r = new ImageRenderer(img);
        //this.image = r.image;
        //this.array = r.array;
    }
    
    // Parse the Image
    // 1. render the visualisation Image 
    // 2. generate the input set
    private void parseImage( BufferedImage input ) {
        BufferedImage output = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
        this.inputSet = new ArrayList<DenseVector>();
        Color c;
        Graphics g = output.getGraphics();
        int rgb;
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                c = new Color(input.getRGB(x, y));
                
                double d[] = { x, y, c.getBlue()/255.0 };
                this.inputSet.add( new DenseVector( d ) );
                //this.array[x][y] = c.getBlue();
                
                g.setColor( new Color(input.getRGB(x, y)) );
                g.fillRect(x*2, y*2, 2, 2);
            }
        }
        this.image = Toolkit.getDefaultToolkit().createImage(output.getSource());
    }
    
   
    @Override
    public int getWidth() {
        return this.image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return this.image.getHeight(null);
    }

    @Override
    public ArrayList<DenseVector> getInputs() {
        return this.inputSet;
    }

	@Override
	public void update(int i) {
		// do nothing
	}
}