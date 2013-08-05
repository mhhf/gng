/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.handlers.inputs;

import gng.core.handlers.inputs.InputSpaceManager;
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
public class StaticPolygonInputs implements InputSpaceManager {

    // rectangle
    public static ArrayList<Vector2D> POLYGON_TYPE1 = new ArrayList<Vector2D>();
    // square
    public static ArrayList<Vector2D> POLYGON_TYPE2 = new ArrayList<Vector2D>();
    // star
    public static ArrayList<Vector2D> POLYGON_TYPE3 = new ArrayList<Vector2D>();
    // U-Shape
    public static ArrayList<Vector2D> POLYGON_TYPE4 = new ArrayList<Vector2D>();
    
    static {
	// Rectangle
        StaticPolygonInputs.POLYGON_TYPE1.add( new Vector2D( 20 , 20  ) );
        StaticPolygonInputs.POLYGON_TYPE1.add( new Vector2D( 80, 20  ) );
        StaticPolygonInputs.POLYGON_TYPE1.add( new Vector2D( 80, 280 ) );
        StaticPolygonInputs.POLYGON_TYPE1.add( new Vector2D( 20 , 280 ) );
        
	// square
        StaticPolygonInputs.POLYGON_TYPE2.add( new Vector2D( 50 , 50  ) );
        StaticPolygonInputs.POLYGON_TYPE2.add( new Vector2D( 350, 50  ) );
        StaticPolygonInputs.POLYGON_TYPE2.add( new Vector2D( 350, 350 ) );
        StaticPolygonInputs.POLYGON_TYPE2.add( new Vector2D( 50 , 350 ) );
        
	// Star
        for( int i=0; i<10; i++ ){
            StaticPolygonInputs.POLYGON_TYPE3.add( new Vector2D( 
                    150 + Math.cos( ( Math.PI / 5 ) * i ) * (50 + 100*(i % 2)),
                    150 + Math.sin( ( Math.PI / 5 ) * i ) * (50 + 100*(i % 2))
                    ) );
        }

	// U - Shape
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 20 , 20  ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 280, 20  ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 280, 280 ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 20 , 280 ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 20 , 100 ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 200 , 100 ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 200 , 60 ) );
        StaticPolygonInputs.POLYGON_TYPE4.add( new Vector2D( 20 , 60 ) );

    }
    
    private Image image;
    private ArrayList<Vector2D> polygon;
    private int scaleFactor = 1;
    
    
    public StaticPolygonInputs() {
    }

    public StaticPolygonInputs( ArrayList<Vector2D> polygon ) {
        this.polygon = polygon;
        
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

    private void renderPolygon() {
        BufferedImage img = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.clearRect(0, 0, 600, 600);
        int x[] = new int[this.polygon.size()];
        int y[] = new int[this.polygon.size()];
        
        for (int i = 0; i < this.polygon.size(); i++) {
            x[i] = (int) this.polygon.get(i).x * this.scaleFactor;
            y[i] = (int) this.polygon.get(i).y * this.scaleFactor;
        }
        
        // test if bigger then 1
        g.fillPolygon(x,y,this.polygon.size());
        this.image = Toolkit.getDefaultToolkit().createImage(img.getSource());
    }

	@Override
	public void update(int i) {
		// do nothing
	}

	@Override
	public boolean pointInPolygon(Vector2D vec) {
		// do nothing
		return true;
	}

	@Override
	public boolean linesCrossed(double x1, double y1, double x2, double y2) {
		// do notihng
		return false;
	}
    
}
