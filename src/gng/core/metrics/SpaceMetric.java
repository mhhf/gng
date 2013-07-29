/*
 * Calculate how much of the positice space is filled up
 */
package gng.core.metrics;

import gng.GraphVisualizer;
import gng.core.Connection;
import gng.core.Node;
import gng.core.handlers.PolygonBasedGNGHandler;
import gng.core.handlers.inputs.InputSpaceVisualizer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import math.Vector2D;

/**
 *
 * @author mhhf
 */
public class SpaceMetric {
    
    
    private final InputSpaceVisualizer inputManager;
    private final PolygonBasedGNGHandler gngHandler;
    private final GraphVisualizer graphVisualizer;
    
    public SpaceMetric(InputSpaceVisualizer inputManager, PolygonBasedGNGHandler gngHandler, GraphVisualizer graphVisualizer) {
        this.inputManager = inputManager;
        this.gngHandler = gngHandler;
        this.graphVisualizer = graphVisualizer;
    }
    
    public double calculateNegative(){
	    double crossed = 0;
	    
	    for(Connection con: this.gngHandler.getConnections()){
		    if( this.gngHandler.linesCrossed(
			    con.n1.getX(), 
			    con.n1.getY(),
			    con.n2.getX(),
			    con.n2.getY()) ){
			    crossed++;
		    }
	    }
	    
	    return 1 - crossed/this.gngHandler.getConnections().size();
    }
    
    public double calculatePositive(){
	int fieldSize = 450;
	int stepSize = 10;
	int steps = (int) Math.floor(fieldSize / stepSize);
	double error = 0;

	Node node;
	Vector2D point;
	
	for (int i = 0; i < steps; i++) {
	    for (int j = 0; j < steps; j++) {
		point = new Vector2D(i,j);
		if( gngHandler.pointInPolygon(point) ){
		    node = gngHandler.getNearestNode( point );
		    error += Math.sqrt( Math.pow( node.getX() - point.x , 2 ) 
			    + Math.pow( node.getY() - point.y, 2 )   );
		}
	   }
	}
	
	// Randomized
	/*for (int i = 0; i< steps; i++) {
		point = new Vector2D(Math.floor(fieldSize * Math.random()), Math.floor(fieldSize * Math.random()));
		if( gngHandler.pointInPolygon(point) ){
		    node = gngHandler.getNearestNode( point );
		    error += Math.sqrt( Math.pow( node.getX() - point.x , 2 ) 
			    + Math.pow( node.getY() - point.y, 2 )   );
		}
	}*/

	return error / ( steps * steps );
    }
    
    public double[] calculate_b(){
        BufferedImage img = new BufferedImage(450, 450, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(inputManager.getVisualisation(), 0, 0, null);
        Color c;
        double totalPositive = 0;
        double totalNegative = 0;
        double coveredNegative = 0;
        double  covered = 0;
        
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                c = new Color(img.getRGB(x, y));
                if( c.getBlue()>200 ) totalPositive++;
                else totalNegative++;
            }
        }
        
	coveredNegative = totalNegative;
        g.drawImage(graphVisualizer.drawFilledGraph( gngHandler.getComPoint() ), 0, 0, null);
        
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                c = new Color(img.getRGB(x, y));
                if( c.getBlue()<200 && c.getRed()>200 ) covered++;
                if( c.getRed()<100 ) coveredNegative--;
            }
        }
        
        double[] result = {totalPositive/covered, 100* ( coveredNegative/totalNegative )};
        return result;
    }
    
}
