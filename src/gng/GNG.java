/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * TODO: Write tests to test the moving visual field area
 * maybe write a wrapper for the visual area polygon
 */
package gng;

import gng.handlers.inputs.ImageBasedInputs;
import gng.handlers.inputs.InputSpaceVisualizer;
import gng.handlers.SetBasedGNGHandler;
import gng.core.Node;
import gng.core.Connection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    
    // Image Loader
    InputSpaceVisualizer inputManager;
    // Image displayImage;
    
    // Array
    //int array[][];
    int state = 0; // 0 - init, 1- Define Start, 2-Define End + Go!
    private int[][] array;
    
    SetBasedGNGHandler gngHandler;
    
 
    public AWTImageDisplayPanel() throws IOException {
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        
        setBackground(new Color(0xff00ff));
        inputManager = new ImageBasedInputs("/Users/mhhf/NetBeansProjects/GNG/assets/testData/grey01.jpg");
        gngHandler = new SetBasedGNGHandler(inputManager.getArray());
    }
    
    
    public void mouseMoved(MouseEvent me) {
        mX = (int) me.getPoint().getX();
        mY = (int) me.getPoint().getY();
        _x = (int) Math.abs(mX/2);
        _y = (int) Math.abs(mY/2);
        //System.out.println(mX+" "+mY);
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = this.inputManager.getWidth();
        int imageHeight = this.inputManager.getHeight();
        int x = (w - imageWidth)/2;
        int y = (h - imageHeight)/2;
        g.drawImage(inputManager.getVisualisation(), 0, 0, this);
    }
    
    
    public void paintDot(int x, int y, int color) {
        Graphics g = this.getGraphics();
        if(color == 0) g.setColor(new Color(0xff0000));
        if(color == 1) g.setColor(new Color(0x00ff00));
        if(color == 2) g.setColor(new Color(0x0000ff));
        g.fillRect(x*2, y*2, 2, 2);
    }
    
    @Override
    public Dimension getPreferredSize() {
        //return new Dimension(this.visualizer.getWidth(this), this.visualizer.getHeight(this));
        return null;
    }
    
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
    public void mouseEntered(MouseEvent me) {
        
    }
    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    
    public void paintCross(int x, int y, int c, boolean small) {
        
        if(small) {
            for (int i = -2; i <= 2; i++) {
                this.paintDot(x+i, y+i, c);
                this.paintDot(x+i, y-i, c);
            }
        } else{ 
            for (int i = -3; i <= 3; i++) {
                this.paintDot(x+i, y, c);
                this.paintDot(x, y-i, c);
            }
        }
    } 
    
    
    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getKeyChar() == ' ') {   
            this.drawGraph(this.gngHandler.cycle());
        } else if(ke.getKeyChar() == '1') {
            this.drawGraph(this.gngHandler.cycle(100));
        } else if(ke.getKeyChar() == '2') {
            this.drawGraph(this.gngHandler.cycle(500));
        } else if(ke.getKeyChar() == '3') {
            this.drawGraph(this.gngHandler.cycle(1000));
        }
        
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
    // p1 = inputPoint, nearest, nearest2
    public void drawGraph( ArrayList points ){
        
        this.paint(this.getGraphics());
        
        ArrayList <Node> nodes = gngHandler.getNodes();
        ArrayList <Connection> connections = gngHandler.getConnections();
        
        Graphics g = this.getGraphics();
        g.setColor(new Color(0x02d7ff));
        for(Connection conn:connections) {
            g.drawLine(
                    (int) conn.n1.getVector().get(0)*2,
                    (int) conn.n1.getVector().get(1)*2,
                    (int) conn.n2.getVector().get(0)*2,
                    (int) conn.n2.getVector().get(1)*2
                    );
        }
        
        for (Node node:nodes) {
            //System.out.println(node.getVector().get(0)+" "+ (int) node.getVector().get(0));
            this.paintCross((int) node.getVector().get(0), (int) node.getVector().get(1), 1, true);
        }
        
        this.paintCross(
                (int)((DenseVector) points.get(0)).get(0), 
                (int)((DenseVector) points.get(0)).get(1), 
                0, 
                false
                );
        
        this.paintCross(
                (int)((Node) points.get(1)).getVector().get(0), 
                (int)((Node) points.get(1)).getVector().get(1), 
                2, 
                false
                );
        
        this.paintCross(
                (int)((Node) points.get(2)).getVector().get(0), 
                (int)((Node) points.get(2)).getVector().get(1), 
                2, 
                false
                );
        
        g.setColor(Color.red);
        g.drawLine(
                (int)((Node) points.get(1)).getVector().get(0)*2, 
                (int)((Node) points.get(1)).getVector().get(1)*2,
                (int)((Node) points.get(2)).getVector().get(0)*2, 
                (int)((Node) points.get(2)).getVector().get(1)*2
                );
        
    }
    
}

