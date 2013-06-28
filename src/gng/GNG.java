/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng;

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
        
        AWTImageDisplayPanel imagePanel = new AWTImageDisplayPanel();
        
        Frame f = new Frame();
        f.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        
        f.add(imagePanel);
        f.setSize(600,622);
        f.setVisible(true);
        
    }
}


class AWTImageDisplayPanel extends Panel implements MouseMotionListener, MouseListener, KeyListener
{

    private int mX, mY;
    private int _x, _y; // position on the Image
    
    // Image Loader
    Image image;
    Image displayImage;
    
    // Array
    //int array[][];
    int state = 0; // 0 - init, 1- Define Start, 2-Define End + Go!
    private int[][] array;
    
    SimpleGNGHandler gngHandler;
    
 
    public AWTImageDisplayPanel() throws IOException {
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        loadImage();
        setBackground(new Color(0xff00ff));
        
        gngHandler = new SimpleGNGHandler(this.convertToInputs(array));
        this.gngHandler.initCycle();
        
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
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);
        int x = (w - imageWidth)/2;
        int y = (h - imageHeight)/2;
        g.drawImage(image, 0, 0, this);
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
        return new Dimension(image.getWidth(this), image.getHeight(this));
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

    private void paintCircle(int x, int y, int r, int c) {
        Graphics g = this.getGraphics();

        if(c == 0) {
            g.setColor(Color.orange);
        } 
        if(c == 1) g.setColor(Color.green);
        if(c == 2) g.setColor(Color.blue);
        if(c == 2) g.setColor(Color.gray);
        g.drawOval(x*2-r*2, y*2-r*2, r*4, r*4);
        
        if(c != 0) {
            g.drawOval(x*2-r*2+1, y*2-r*2, r*4, r*4);
            g.drawOval(x*2-r*2-1, y*2-r*2, r*4, r*4);
            g.drawOval(x*2-r*2, y*2-r*2+1, r*4, r*4);
            g.drawOval(x*2-r*2, y*2-r*2-1, r*4, r*4);
        }
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
    
    private void loadImage() throws IOException {
        String fileName = "/Users/mhhf/NetBeansProjects/GNG/assets/testData/grey01.jpg";
        //Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        BufferedImage img = ImageIO.read(new File(fileName));
        
        ImageRenderer r = new ImageRenderer(img);
        image = r.image;
        array = r.array;
        
        tracker.addImage(image, 0);
        

        try
        {
            tracker.waitForID(0);
        }
        catch(InterruptedException ie)
        {
            System.out.println("interrupt: " + ie.getMessage());
        }
    }
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        
       
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

