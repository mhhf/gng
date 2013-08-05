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
public class RectangleSinScan implements DynamicPolygonInterface {

	private ArrayList<Vector2D> polygon;

	public RectangleSinScan() {
	}

	@Override
	public ArrayList<Vector2D> getPolygon() {
		return polygon;
	}
	
	@Override
	public ArrayList<Vector2D> construct(double t) {
		polygon = new ArrayList<Vector2D>();
		polygon.add( new Vector2D( 120+100*Math.sin(t) , 20  ) );
		polygon.add( new Vector2D( 180+100*Math.sin(t), 20  ) );
		polygon.add( new Vector2D( 180+100*Math.sin(t), 280 ) );
		polygon.add( new Vector2D( 120+100*Math.sin(t) , 280 ) );
		return polygon;
	}
	
}
