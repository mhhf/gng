/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.ArrayList;

/**
 *
 * @author mhhf
 */
public class BoundingBox {
    
    public double x,y,w,h;

    public BoundingBox() {
    
    }
    
    public BoundingBox(ArrayList<Vector2D> vecs){
        this.calculateBoundingBox(vecs);
    }
    
    
    public BoundingBox(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    /**
     * Looking for the minimal and maximal position of points and calculate
     * the width and the height based on this points
     * @param vecs: points of the polygon
     */
    public void calculateBoundingBox(ArrayList<Vector2D> vecs) {
        double mx=-1, my=-1;
        
        for(Vector2D vec: vecs) {
            if(vec.x < x){ this.x = vec.x; }
            if(vec.y < y){ this.y = vec.y; }
            
            if(mx == -1 || vec.x > mx ) { mx = vec.x; }
            if(my == -1 || vec.y > my ) { my = vec.y; }
        }
        
        this.w = mx - this.x;
        this.h = my - this.y;
    }
    
    
}
