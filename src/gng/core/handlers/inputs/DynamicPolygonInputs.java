/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.handlers.inputs;

import gng.core.handlers.inputs.InputSpaceManager;
import gng.core.handlers.inputs.dynamicPolygons.DynamicPolygonInterface;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import math.BoundingBox;
import math.Vector2D;

/**
 *
 * @author mhhf
 */
public class DynamicPolygonInputs implements InputSpaceManager {
    
    private Image image;
    private DynamicPolygonInterface dynamicPolygon;
    private ArrayList<Vector2D> polygon;
    private int scaleFactor = 1;
    private double t; // local time
    
    public DynamicPolygonInputs() {
    }

    public DynamicPolygonInputs( DynamicPolygonInterface polygon ) {
        this.dynamicPolygon = polygon;
	this.t = 0;
	
	this.constructPolygon();
    }
    
    public void setScaleFactor( int scale ) {
        this.scaleFactor = scale;
    }

    @Override
    public Image getVisualisation() {
        renderPolygon();
        return this.image;
    }

    @Override
    public int getWidth() {
        return 20;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public ArrayList<Vector2D> getInputs() {
        return this.polygon;
    }
    
    public void constructPolygon() {
	    this.polygon = this.dynamicPolygon.construct(t);
	    this.b.calculateBoundingBox(this.polygon);
    }

    private void renderPolygon() {
        BufferedImage img = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.clearRect(0, 0, 600, 600);
	
        int x[] = new int[polygon.size()];
        int y[] = new int[polygon.size()];
        
        for (int i = 0; i < polygon.size(); i++) {
            x[i] = (int) polygon.get(i).x * this.scaleFactor;
            y[i] = (int) polygon.get(i).y * this.scaleFactor;
        }
        
        // test if bigger then 1
        g.fillPolygon( x, y, polygon.size() );
        this.image = Toolkit.getDefaultToolkit().createImage(img.getSource());
    }
    
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

	@Override
	public void update(int i) {
		this.t = i*Math.PI/1000;
		this.constructPolygon();
	}
    
}
