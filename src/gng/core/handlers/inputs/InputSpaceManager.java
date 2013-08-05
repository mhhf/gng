/*
 * Interface for converting the input space to an image for a visualisation
 */
package gng.core.handlers.inputs;

import java.awt.Image;
import java.util.ArrayList;
import math.BoundingBox;
import math.Vector2D;

/**
 *
 * @author mhhf
 */
public interface InputSpaceManager {
	
	public BoundingBox b = new BoundingBox();
	
    public void update( int i );
    
    public Image getVisualisation();

    public int getWidth();

    public int getHeight();

    public ArrayList getInputs();
    
    public boolean pointInPolygon( Vector2D vec );
    
    public boolean linesCrossed(double x1, double y1, double x2, double y2);
    
}
