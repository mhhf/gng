/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng;

import gng.core.Connection;
import gng.core.Node;
import gng.core.handlers.SetBasedGNGHandler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;

/**
 *
 * @author mhhf
 */
public class GraphVisualizer {
    
    ArrayList<Node> nodes;
    ArrayList<Connection> connections;
    Graphics g;
    Image visualisation;

    GraphVisualizer(ArrayList<Node> nodes, ArrayList<Connection> connections, Graphics g) {
        this.nodes = nodes;
        this.connections = connections;
        this.g = g;
    }
    
    private void paintDot(int x, int y, int color) {
        if(color == 0) this.g.setColor(new Color(0xff0000));
        if(color == 1) this.g.setColor(new Color(0x00ff00));
        if(color == 2) this.g.setColor(new Color(0x0000ff));
        this.g.fillRect(x*2, y*2, 2, 2);
    }
    
    private void paintCross(int x, int y, int c, boolean small) {
        
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
    
    // p1 = inputPoint, nearest, nearest2
    public Image drawGraph( ArrayList points ) {
        BufferedImage output = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
        this.g = output.getGraphics();
        
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
     
        this.visualisation = Toolkit.getDefaultToolkit().createImage(output.getSource());
        
        return this.visualisation;
    }

    void initGraphics(Graphics g) {
        this.g = g;
    }
    
}
