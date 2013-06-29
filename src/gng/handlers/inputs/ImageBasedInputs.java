/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.handlers.inputs;

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
public class ImageBasedInputs implements InputSpaceVisualizer {
    
    private Image image;
    private int array[][];

    public ImageBasedInputs(String location) throws IOException {
        loadImage( location );    
    }
    
    public void ImageBasedInputs (String location) throws IOException {
        
    }

    @Override
    public Image getVisualisation() {
        return this.image;
    }
    
    // load the image file and parse the input array
    private void loadImage(String fileName) throws IOException {
        BufferedImage img = ImageIO.read(new File(fileName));
        
        ImageRenderer r = new ImageRenderer(img);
        this.image = r.image;
        this.array = r.array;
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
    public ArrayList<DenseVector> getArray() {
        return this.convertToInputs(array);
    }
    
    private ArrayList<DenseVector> convertToInputs(int[][] array) {
        ArrayList <DenseVector> inputs = new ArrayList();
        double[] dbl = new double[3];
        
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                dbl[0] = i;
                dbl[1] = j;
                dbl[2] = array[i][j]/255.0;
                inputs.add( new DenseVector( dbl ));
            }
        }
        
        return inputs;
    }
}