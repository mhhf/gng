/*
 * Calculate how much of the positice space is filled up
 */
package gng.core.metrics;

import gng.GraphVisualizer;
import gng.core.Connection;
import gng.core.Node;
import gng.core.handlers.PolygonBasedGNGHandler;
import gng.core.handlers.inputs.InputSpaceManager;
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
    
    
    private final InputSpaceManager inputManager;
    private final PolygonBasedGNGHandler gngHandler;
    
    
    public SpaceMetric(InputSpaceManager inputManager, PolygonBasedGNGHandler gngHandler) {
        this.inputManager = inputManager;
        this.gngHandler = gngHandler;
        
    }
    
    public double calculateNegative(){
	    double crossed = 0;
	    
	    for(Connection con: this.gngHandler.getConnections()){
		    if( this.gngHandler.inputManager.linesCrossed(
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
		if( gngHandler.inputManager.pointInPolygon(point,true) ){
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
   
    
}
