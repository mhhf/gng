/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * TODO: Write tests to test the moving visual field area
 * maybe write a wrapper for the visual area polygon
 */
package gng;

import gng.core.handlers.inputs.InputSpaceVisualizer;
import gng.core.handlers.SetBasedGNGHandler;
import gng.core.Node;
import gng.core.Connection;
import gng.core.handlers.inputs.ImageBasedInputs;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;



/**
 * @author mhhf
 */
public class GNG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // Init the display panel
        //ImageSetupPanel visualizer = new ImageSetupPanel();
        
        StaticPolygonSetupPanel visualizer = new StaticPolygonSetupPanel();
        
        // Handle closing events
        Frame f = new Frame();
        f.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        
        f.add(visualizer);
        f.setSize(600,622);
        f.setVisible(true);  
    }
}