/*
 * Polygon Based GHG Handler
 * 
 * Handels the input space based on an polygon, which contains the positive inputs,
 * In the following, I will call the points, laying inside the polygon as "white"
 * and we will considder the white points as positive inputs for the gng algorithm
 * 
 */
package gng.core.handlers;

import gng.core.AbstractGNGHandler;
import gng.core.Connection;
import gng.core.Node;
import gng.core.handlers.inputs.InputSpaceManager;
import java.util.ArrayList;
import math.BoundingBox;
import math.Vector2D;
import no.uib.cipr.matrix.DenseVector;

/**
 *
 * @author mhhf
 */
public class PolygonBasedGNGHandler extends AbstractGNGHandler {
    
    // the polygon containing the white points
    ArrayList<Vector2D> polygon = new ArrayList();
    
    // Center of Mass point of the polygon
    Vector2D comPoint;

    public Vector2D getComPoint() {
        return comPoint;
    }
    
    // maximal distance of a white point to the comPoint
    public double polygonRadius;
    public InputSpaceManager inputManager;
    
    
    /**
     * 
     * @param width
     * @param height
     * @param list: polygon
     */
    public PolygonBasedGNGHandler( InputSpaceManager inputManager ){
	this.inputManager = inputManager;
        this.polygon = inputManager.getInputs();
        this.initCycle();
        
    } // Constructor
    
    
    @Override
    public ArrayList iterate(int i) {
	this.inputManager.update(i);
	this.polygon = this.inputManager.getInputs();
	
	return super.iterate(i);
    }

    
    /**
     * returns a random white point
     * Pseudocode:
     *  Do, while no point is found:
     *      get a random point in the circle of the comPoint with the polygonRadius radius
     *      check if the point is inside the polygon, if so, then the point is found
     */
    @Override
    public DenseVector getRandomInput() {
        boolean found = false;
        Vector2D tmpPoint;
       
        do{
            /**
            tmpPoint = new Vector2D( 
                    this.comPoint.x + Math.cos( Math.random() * Math.PI * 2 ) * (Math.random() * polygonRadius),
                    this.comPoint.y + Math.sin( Math.random() * Math.PI * 2 ) * (Math.random() * polygonRadius)
                    );
            */
            tmpPoint = new Vector2D(
                    this.inputManager.b.x + Math.random() * this.inputManager.b.w,
                    this.inputManager.b.y + Math.random() * this.inputManager.b.h
                    );
            if ( this.inputManager.pointInPolygon(tmpPoint) ) { found = true; }
        } while (!found);
                
        double[] d = { tmpPoint.x, tmpPoint.y };
        return new DenseVector( d );
    }

    public Node getNearestNode(Vector2D v){
    
	    double[] d = {v.x,v.y};
	    DenseVector w = new DenseVector(d);
	    
	    return this.getNearestNodes(w)[0];
    }
    
    @Override
    public DenseVector getRandomNegativeInput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // check what this is doing and comment it
    @Override
    public boolean validateNodeForUpdate(Node node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    

	public int getIterations() {
		return this.iterator;
	}
    
}
