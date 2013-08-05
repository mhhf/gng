/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core;

import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Vector;


/**
 *
 * @author mhhf
 */
public class Node {
    
    public static double e_w = 0.05; // fration for moving the vector to the input node
    public static double e_n = 0.0006; // fraction for moving the neighbers to the input node

    protected DenseVector w; // Refference Vector
    protected ArrayList< Connection > connections = new ArrayList();
    public double error = 0;
    
    
    public Node( DenseVector w){
        this.w = w;
    }
    
    public Node ( double[] d ) {
        this(new DenseVector(d));
    }
    
    // todo Check if self or connected
    public Connection connect( Node node ){
        return new Connection( this, node );
    }
    
    /*
     * Looks, if a connection to the given Node is established,
     * if so, returns the connection, otherwise returns null
     */
    public Connection searchConnection( Node node ){
        Connection retValue = null;
        
        for(Connection conn: this.connections){
            if (conn.contains( node )) {
                retValue = conn;
                break;
            }
        }
        
        return retValue;
    }
    
    public Node getMaxErrorNaighbor() {
        Node retVar = null;
        
        for (Connection conn:connections) {
            if ( retVar == null || conn.getOther(this).error > retVar.error ) {
                retVar = conn.getOther(this);
            }
            
        }
        
        return retVar;
    }
    
    public boolean isConnected( Node node ) {
        return this.searchConnection(node) != null;
    }

    // returns the Euklidian norm of the distance of this node and the given vector
    // return ||w - this.w|| as double
    public double distanceTo(DenseVector x) {
        DenseVector _x = x.copy();
        DenseVector _w = w.copy();
        _x.add(_w.scale(-1));
        return _x.norm(Vector.Norm.Two);
    }
    
    public double distanceTo( Node n ) {
        return this.distanceTo( n.w );
    }
    
    
    // Update Local Error: error = error + || x - w ||
    public double updateLocalError( DenseVector x ) {
        DenseVector _w = this.w.copy();
        DenseVector _x = x.copy();
        this.error += _w.add(_x.scale(-1)).norm(Vector.Norm.Two);
        return error;
    }

    
    // Moves the node to a vector x with e_w scale 
    // and all its connected neighbers to with e_n
    // w += e_w(x-w)
    public void moveTo(DenseVector x) {
        // move main Vector near to
        this.moveTowards(e_w, x);
        Node n;
        
        for (Connection conn:connections) {
            n = conn.getOther(this);
            n.moveTowards(e_n, x);
        }
        
    }
    
    
    // Move this vector towards a vector x with a certin scale
    protected void moveTowards( double scale, DenseVector x ) {
        DenseVector _x = x.copy();
        DenseVector _n = this.w.copy();
        this.w.add(_x.add(_n.scale(-1)).scale(scale));
    }
    
    // incremet the age of all connected nodes
    void incConnectionAges() {
        for ( Connection conn:connections ) {
            conn.age ++;
        }
    }
    
    public DenseVector getVector(){
        return this.w;
    }
    
    public double getX(){
        return this.w.get(0);
    }
    
    public double getY(){
        return this.w.get(1);
    }
    
    @Override
    public String toString(){
        return this.w.get(0)+" "+this.w.get(1);
    }
    
    public void checkError(){
        if(this.w.get(0)<0 || this.w.get(1)<0){
            System.out.print("FUCK");
        }
    }
}
