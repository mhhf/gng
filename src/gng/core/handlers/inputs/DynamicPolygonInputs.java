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

	@Override
	public void update(int i) {
		this.t = i*Math.PI/1000;
		this.constructPolygon();
	}
    
}
