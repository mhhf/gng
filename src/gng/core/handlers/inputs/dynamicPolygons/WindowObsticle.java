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
public class WindowObsticle{

	private ArrayList<Vector2D> window;
	private ArrayList<Vector2D> area;
	private ArrayList<Vector2D> obsticle;
	private double t;
	
	public WindowObsticle() {
	}

	
	public void construct(double t) {
		window = new ArrayList<Vector2D>();
		window.add( new Vector2D( 120+100*Math.sin(t) , 20  ) );
		window.add( new Vector2D( 180+100*Math.sin(t), 20  ) );
		window.add( new Vector2D( 180+100*Math.sin(t), 280 ) );
		window.add( new Vector2D( 120+100*Math.sin(t) , 280 ) );
		
		area = new ArrayList<Vector2D>();
		area.add( new Vector2D( 20, 20  ) );
		area.add( new Vector2D( 280, 20  ) );
		area.add( new Vector2D( 280, 280 ) );
		area.add( new Vector2D( 20 , 280 ) );
		
		obsticle = new ArrayList<Vector2D>();
		obsticle.add( new Vector2D( 100 , 20  ) );
		obsticle.add( new Vector2D( 180, 20  ) );
		obsticle.add( new Vector2D( 180, 120 ) );
		obsticle.add( new Vector2D( 100, 150 ) );
		
	}

	
	public ArrayList<Vector2D> getWindow() {
		return window;
	}
	
	public ArrayList<Vector2D> getArea() {
		return area;
	}
	
	public ArrayList<Vector2D> getObsticle() {
		return obsticle;
	}
	
}
