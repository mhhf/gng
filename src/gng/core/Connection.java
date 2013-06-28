/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core;

import java.util.ArrayList;

/**
 *
 * @author mhhf
 */
public class Connection {

    
    
    public Node n1,n2;
    protected int age = 0;
    /*
     * Representation of a Connection between 2 Nodes
     */
    public Connection( Node n1, Node n2){
        this.n1 = n1;
        this.n2 = n2;
        n1.connections.add(this);
        n2.connections.add(this);
    }

    boolean contains(Node node) {
        return (this.n1 == node || this.n2 == node);
    }
    
    public Node getOther( Node node ){
        if(n1 == node) {
            return n2;
        } else {
            return n1;
        }
    }

    protected void remove() {
        this.n1.connections.remove(this);
        this.n2.connections.remove(this); 
    }
    
}
