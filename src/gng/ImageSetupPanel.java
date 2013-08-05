/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng;

import gng.core.handlers.SetBasedGNGHandler;
import gng.core.handlers.inputs.ImageBasedInputs;
import gng.core.handlers.inputs.InputSpaceManager;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mhhf
 */
public class ImageSetupPanel  extends Panel implements KeyListener{
    
     // DEFINITION
    
    // visualizer
    InputSpaceManager inputManager;
    
    // GNG Handeler
    SetBasedGNGHandler gngHandler;
    
    // Graph Visualizer
    GraphVisualizer graphVisualizer;
    private Image graphVisualisation;
    
 
    public ImageSetupPanel() throws IOException {
        addKeyListener(this);
        
        setBackground(new Color(0xff00ff));
        
        // init visualizer
        inputManager = new ImageBasedInputs("/Users/mhhf/NetBeansProjects/GNG/assets/testData/grey01.jpg");
        
        // init Handler
        gngHandler = new SetBasedGNGHandler(inputManager.getInputs());
        
        // init Graph Visualizer
        graphVisualizer = new GraphVisualizer( 
                gngHandler.getNodes(), 
                gngHandler.getConnections(), 
                this.getGraphics()
                );
    }
    
    @Override
    public void paint( Graphics g ) {
        super.paint(g);
        g.drawImage(inputManager.getVisualisation(), 0, 0, this);
        this.getGraphics().drawImage(this.graphVisualisation, 0, 0, this);
    }
   
    // TODO: WTF doas this?
    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        ArrayList points;
        
        if(ke.getKeyChar() == '1') {
            points = this.gngHandler.cycle(100);
        } else if(ke.getKeyChar() == '2') {
            points = this.gngHandler.cycle(500);
        } else if(ke.getKeyChar() == '3') {
            points = this.gngHandler.cycle(2000);
        } else {
            points = this.gngHandler.cycle();
        }
        
        this.graphVisualisation = this.graphVisualizer.drawGraph(points);
        this.paint(this.getGraphics());
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
