/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.handlers.inputs.dynamicPolygons;

import java.util.ArrayList;
import math.Vector2D;

/**
 *
 * @author mhhf
 */
public interface DynamicPolygonInterface {

	public ArrayList<Vector2D> construct(double t);
	public ArrayList<Vector2D> getPolygon();
	
}
