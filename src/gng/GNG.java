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
 *
 * @author mhhf
 */
public class GNG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // Init the display panel
        AWTImageDisplayPanel visualizer = new AWTImageDisplayPanel();
        
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


class AWTImageDisplayPanel extends Panel implements MouseMotionListener, MouseListener, KeyListener
{

    private int mX, mY;
    private int _x, _y; // position on the Image
    
    
    // DEFINITION
    
    // visualizer
    InputSpaceVisualizer inputManager;
    
    // GNG Handeler
    SetBasedGNGHandler gngHandler;
    
    // Graph Visualizer
    GraphVisualizer graphVisualizer;
    private Image graphVisualisation;
    
 
    public AWTImageDisplayPanel() throws IOException {
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        
        setBackground(new Color(0xff00ff));
        
        // init visualizer
        inputManager = new ImageBasedInputs("/Users/mhhf/NetBeansProjects/GNG/assets/testData/grey01.jpg");
        
        // init Handler
        gngHandler = new SetBasedGNGHandler(inputManager.getInputSet());
        
        // init Graph Visualizer
        graphVisualizer = new GraphVisualizer( 
                gngHandler.getNodes(), 
                gngHandler.getConnections(), 
                this.getGraphics() );
    }
    
    
    @Override
    public void mouseMoved(MouseEvent me) {
        mX = (int) me.getPoint().getX();
        mY = (int) me.getPoint().getY();
        _x = (int) Math.abs(mX/2);
        _y = (int) Math.abs(mY/2);
    }
    
    
    @Override
    public void paint( Graphics g ) {
        super.paint(g);
        g.drawImage(inputManager.getVisualisation(), 0, 0, this);
        this.getGraphics().drawImage(this.graphVisualisation, 0, 0, this);
    }
   
    @Override
    public Dimension getPreferredSize() {
        //return new Dimension(this.visualizer.getWidth(this), this.visualizer.getHeight(this));
        return null;
    }
   
    //TODO: WTF doas this?
    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
       
    }
    @Override
    public void mousePressed(MouseEvent me) {
        
    }
    @Override
    public void mouseReleased(MouseEvent me) {
       
    }
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}
    
    @Override
    public void mouseClicked(MouseEvent me) {}

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

