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
    
    private BoundingBox boundingBox;
    
    
    /**
     * 
     * @param width
     * @param height
     * @param list: polygon
     */
    public PolygonBasedGNGHandler( ArrayList<Vector2D> list ){
        this.polygon = list;
        this.boundingBox = new BoundingBox(list);
        
        this.init();
        
    } // Constructor
    
    /**
     * Init out of the constructor for DEBUG reasons, implement it back for a release
     */
    public void init(){
        
        // calculate the center of mass and the maximal radius of the polygon
        this.calculateCOMPoint();
        this.calculateCOMRadius();
        
        // init the first cycle
        this.initCycle();
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
                    this.boundingBox.x + Math.random() * this.boundingBox.w,
                    this.boundingBox.y + Math.random() * this.boundingBox.h
                    );
            if ( this.pointInPolygon(tmpPoint) ) { found = true; }
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
    
    /**
     * Calculates the center of mass of the polygon
     */
    public Vector2D calculateCOMPoint() {
        double x = 0;
        double y = 0;
        
        for( Vector2D point: this.polygon ){
            x += point.x;
            y += point.y;
        }// each polygon
        
        this.comPoint = new Vector2D( x/this.polygon.size(), y/this.polygon.size() );
        
        return this.comPoint;
    }// calculateCOMPoint 
    
    
    /**
     * Calculates the white point, with  the biggest distance to the comPoint
     */
    public double calculateCOMRadius() {
        
        double maxDistance = -1;
        
        for( Vector2D point: this.polygon ){
            if( maxDistance == -1 && maxDistance < Math.sqrt( Math.pow((this.comPoint.x - point.x), 2) + Math.pow((this.comPoint.y - point.y), 2) ) ) {
                maxDistance = Math.sqrt( Math.pow((this.comPoint.x - point.x), 2) + Math.pow((this.comPoint.y - point.y), 2) );
            }// if new point is found
        }// each polygon
        
        this.polygonRadius = maxDistance;
        
        return maxDistance;
    } // calculateCOMRadius
    
    
    /**
     * for the point in polygon test, i will considder the simple like crossing test
     * it can be found here: http://erich.realtimerendering.com/ptinpoly/
     * later i will write a faster aproach, considdering my need for the polygon size
     */
    public boolean pointInPolygon( Vector2D point ) {
        
        // LOCAL PARAMETER
        int assumedMaxPolygonSize = 10000;
        
        int intersectionCounter = 0;
        
        // TOOD: test for empty polygon or < 3, then return false 
        for(int i = 0; i<this.polygon.size(); i++){
            if( linesIntersect( 
                    this.polygon.get(i).x, this.polygon.get(i).y,
                    this.polygon.get( (i + 1) % (this.polygon.size())  ).x, this.polygon.get( (i + 1) % (this.polygon.size()) ).y,
                    point.x, point.y,
                    point.x + assumedMaxPolygonSize, point.y
                    ) ) {
                intersectionCounter ++;
            }
        } // for
        
        // creteria for the point, to be inside the polygon.
        return intersectionCounter % 2 == 1;
    } // isPintInPolygon
    
    
    public boolean linesCrossed(double x1, double y1, double x2, double y2){
	    
	    for(int i=0; i<this.polygon.size(); i++){
		    if(this.linesIntersect(x1, y1, x2, y2, 
			    this.polygon.get(i).x,
			    this.polygon.get(i).y, 
			    this.polygon.get( (i+1) % this.polygon.size() ).x ,
			    this.polygon.get( (i+1) % this.polygon.size() ).y
			    )){
			    return true;
		    }
	    }
	    
	    return false;
    }
    
    /**
     * Test if two lines intersect
     * Algorithm taken from here: 
     * http://www.java-gaming.org/index.php?topic=22590.0
     * 
     * TODO SPEED: find a faster aproach
     * 
     * @return 
     */
    private boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
            
            // Return false if either of the lines have zero length
            if (x1 == x2 && y1 == y2 ||
                    x3 == x4 && y3 == y4){
                return false;
            }
            // Fastest method, based on Franklin Antonio's "Faster Line Segment Intersection" topic "in Graphics Gems III" book (http://www.graphicsgems.org/)
            double ax = x2-x1;
            double ay = y2-y1;
            double bx = x3-x4;
            double by = y3-y4;
            double cx = x1-x3;
            double cy = y1-y3;

            double alphaNumerator = by*cx - bx*cy;
            double commonDenominator = ay*bx - ax*by;
            if (commonDenominator > 0){
                if (alphaNumerator < 0 || alphaNumerator > commonDenominator){
                    return false;
                }
            }else if (commonDenominator < 0){
                if (alphaNumerator > 0 || alphaNumerator < commonDenominator){
                    return false;
                }
            }
            double betaNumerator = ax*cy - ay*cx;
            if (commonDenominator > 0){
                if (betaNumerator < 0 || betaNumerator > commonDenominator){
                    return false;
                }
            }else if (commonDenominator < 0){
                if (betaNumerator > 0 || betaNumerator < commonDenominator){
                    return false;
                }
            }
            if (commonDenominator == 0){
                // This code wasn't in Franklin Antonio's method. It was added by Keith Woodward.
                // The lines are parallel.
                // Check if they're collinear.
                double y3LessY1 = y3-y1;
                double collinearityTestForP3 = x1*(y2-y3) + x2*(y3LessY1) + x3*(y1-y2);   // see http://mathworld.wolfram.com/Collinear.html
                // If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2 is parallel with p3-p4
                if (collinearityTestForP3 == 0){
                    // The lines are collinear. Now check if they overlap.
                    if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 ||
                        x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4 ||
                        x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2){
                    if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 ||
                            y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4 ||
                            y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2){
                        return true;
                    }
                    }
                }
                return false;
            }
            return true;
        }// linesIntersect

	public int getIterations() {
		return this.iterator;
	}
    
}
