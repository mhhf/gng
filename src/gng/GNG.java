/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * TODO: Write tests to test the moving visual field area
 * maybe write a wrapper for the visual area polygon
 */
package gng;

import gng.core.handlers.inputs.InputSpaceManager;
import gng.core.handlers.SetBasedGNGHandler;
import gng.core.Node;
import gng.core.Connection;
import gng.core.handlers.inputs.ImageBasedInputs;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.uib.cipr.matrix.DenseVector;



/**
 * @author mhhf
 */
public class GNG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
		
		/*try {
			initFrame();
		} catch (IOException ex) {
			Logger.getLogger(GNG.class.getName()).log(Level.SEVERE, null, ex);
		}*/
		
		// HEADLERSS
		
		HeadlessSetup h = new HeadlessSetup();
	
        
    }
    
    public static void initFrame() throws IOException{
	// VISUALZER
        WindowObsticlePolygonSetupPanel visualizer = new WindowObsticlePolygonSetupPanel();
        
	    
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