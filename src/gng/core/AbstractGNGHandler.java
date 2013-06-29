/*
 * GNG Handler controlls the process of the GNG Programm
 * 
 */
package gng.core;

import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;




/**
 * Handels the gng process
 * @author mhhf
 */
public abstract class AbstractGNGHandler {
    
    // GNG Parameter
    // TODO: write getter and setter for evolution?
    private int max_nodes = 100;
    private int a_max = 100;
    private int l = 300;
    private double alpha = 0.5;
    private double betta = 0.0005;
    
    // iterator counter
    private int iterator = 0;
    
    /**
     * the connection collection, containing all connections between nodes
     */
    protected ArrayList<Connection> connections = new ArrayList();
    
    /**
     *  the nodes collection, containing all nodes of the graph
     */
    protected ArrayList<Node> nodes = new ArrayList();
    
    
    /**
     * metric for the performance of an iteration
     * summ of all node errors divided by the nodes
     */
    public double meanError = 0;

    
    /**
     * Main Constructor
     */
    public AbstractGNGHandler() {
        
    }
    
    /**
     * Initialize the gng cyclus with 2 nodes and a connection from with the
     * gng can evolve
     * 
     * TODO: make private and put it in the constructor?
     *  -> difficult do debug, because random nodes are placed on the field
     *    -> write an clear graph function, wich is started for the Debug process 
     */
    public void initCycle() {
        
        // Init
        
        Node n1 = new Node( getRandomInput() );
        Node n2 = new Node( getRandomInput() );

        this.addNode( n1 );
        this.addNode( n2 );

        this.connect(n1, n2);
        
    }
    
    /**
     * Cycle wrapper
     * @param i: the number of itarations to call
     * @return { selected input point, nearest point to the input, second nearest}
     */
    public ArrayList cycle(int i){
        for (int j = 0; j < i; j++) {
            iterate(this.iterator++);   
        }
        return iterate(this.iterator++);
    }
    
    /**
     * Iteration Wrapper
     * @return {selected input point, nearest point to the input, second nearest}
     */
    public ArrayList cycle(){
         return iterate(this.iterator++);
    }
    
    
    /**
     * Iteration cyclus for removing the blocked area from the graph
     * @param i: current iteration counter
     */
    private void negIterate(int i) {
        // select a random negative point as a negative input source
        DenseVector point = this.getRandomNegativeInput();
        
        // get the two nearest nodes to the point
        Node nearest[] = getNearestNodes( point );
        
    }
    
    /**
     * Main Iteration cyclus for adjust the gng to the input space
     * 
     * @param i
     * @return { selected input point, nearest point to the input, second nearest}
     */
    private ArrayList iterate(int i) {
        
        // select a random point as a new input source
        DenseVector point = getRandomInput();
        

        // get the two nearest nodes to the poinst
        Node nearest[] = getNearestNodes( point );
        
        if(nearest[0] != null && nearest[1] != null){
            // Update the local error of the nearest node
            nearest[0].updateLocalError(point);

            // move the nearest node and its neighbors to the point
            nearest[0].moveTo(point);

            // incremet the age of all connections to the node
            nearest[0].incConnectionAges();
            
            for ( Connection conn:nearest[0].connections ) {
                if(this.validateNodeForUpdate(conn.getOther(nearest[0]))) {
                    conn.age ++;
                }

            }

            // Update the connection between the nearest nodes
            if ( nearest[0].isConnected( nearest[1] ) ) {
                nearest[0].searchConnection( nearest[1] ).age = 0;
            } else {
                this.connect(nearest[0], nearest[1]);
            }       

            // remove all old connections which a bigger age then a_max
            this.removeOldConnections();

            // remove all nodes without connections
            this.removeOldNodes();


            // insert new node if the time is right
            if ( i % l == 0 && nodes.size() < max_nodes) {
                this.insertNewNode();
            }

            // decrease the error of all nodes
            this.decreaseAllError();

            // Return main results for debugging
            ArrayList arr = new ArrayList();
            arr.add(point);
            arr.add(nearest[0]);
            arr.add(nearest[1]);
            return arr;
        } else {
            return null;
        }

        
    }
    
    
    /**
     * Return two nearest nodes to a given point w
     * @param w: reference point
     * @return: two nearest points to the refference point, sorted by distance
     */
    protected Node[] getNearestNodes( DenseVector w ){
        Node tmpNode,retVal[] = {null, null};
        double first = 0,second = 0; // distance
        
        // look for two most nearest Nodes
        for (Node node:nodes ) {
            
            // test if the node is seen, for the update            
            if(retVal[0] == null || first > node.distanceTo( w )){
                if(retVal[0] != null)
                {
                    retVal[1] = retVal[0];
                    second = first;
                }
                retVal[0] = node;
                first = node.distanceTo( w );
            } else  if( retVal[1] == null || second >node.distanceTo( w ) ) {
                retVal[1] = node;
                second = node.distanceTo( w );
            }
            
        }
        
        // Sort this nodes
        if(first > second) {
            tmpNode = retVal[0];
            retVal[0] = retVal[1];
            retVal[1] = tmpNode;
        }
        
        return retVal;
    }

    /**
     * Returns an random input point inside the input space
     * @return: point
     */
    public abstract DenseVector getRandomInput();
    
    /**
     * Returns an point, outside the input space
     * @return: point
     */
    public abstract DenseVector getRandomNegativeInput();
    
    /**
     * tests if the given point is inside the input space
     * @param node
     * @return: boolean if the node is inside the input space
     */
    public abstract boolean validateNodeForUpdate(Node node);
    
    // public DenseVector getRandomInput() {
        
     //   DenseVector w;
     //   do{
     //       int rand = (int) Math.floor( Math.random()*inputs.size() );
      //      w = inputs.get(rand);
      //  } while( Math.random() > w.get(2));
        
     //   return w;
    //}

    
    /**
     * adds a node on the field
     * @param node: node to add
     */
    public void addNode( Node node ) {
        this.nodes.add( node );
    }
    
    // connects two nodes
    protected void connect( Node n1,Node n2 ) {
        this.connections.add( n1.connect(n2) );
    }
    
    // removes the connection of two nodes
    private void removeConnection( Connection conn ) {
        conn.remove();
        this.connections.remove( conn );
    }
    
    // Remover
    private void removeOldNodes() {
        ArrayList <Node> toRemove = new ArrayList();
        
        for (Node node:nodes) {
            if ( node.connections.isEmpty() ) {
                toRemove.add(node);
            }
        }
        
        for (Node node:toRemove) {
            nodes.remove(node);
        }
    }

    private void removeOldConnections() {
        ArrayList <Connection> toRemove = new ArrayList();
        
        for (Connection conn:connections) {
            if( conn.age >= a_max ) {
                toRemove.add(conn);
            }
        }
        
        for (Connection conn:toRemove) {
            removeConnection(conn);
        }
    }

    /**
     * 
     * @return
     */
    protected Node findNodeWithLargestError() {
        Node retVar = null;
        
        for (Node node:nodes) {
            
            if(retVar == null || node.error > retVar.error ){
                retVar = node;
            }
           
        }
        
        return retVar;
    }

    /**
     * 
     */
    protected void decreaseAllError() {
        for (Node node:nodes) {
            node.error -= node.error * this.betta;
        }
    }

    /**
     * insert node process
     * @return
     */
    protected Node insertNewNode() {
        Node node1 = this.findNodeWithLargestError();
        Node node2 = node1.getMaxErrorNaighbor();
        
        DenseVector w = (DenseVector) node1.w.copy().add(node2.w).scale(0.5);
        Node newNode = new Node( w ); 
        nodes.add(newNode);

        connect(newNode, node1 );
        connect(newNode, node2 );

        removeConnection( node1.searchConnection(node2) ); 

        node1.error *= alpha;
        node2.error *= alpha;
        newNode.error = node1.error;
        
        return newNode;
    }
    
    /**
     * nodes getter
     * @return
     */
    public ArrayList<Node> getNodes(){
        return this.nodes;
    }
    
    /**
     * connections getter
     * @return
     */
    public ArrayList<Connection> getConnections(){
        return this.connections;
    }
    
    /**
     * Calculates the performance of an iteration 
     * based on the sum of the node errors
     */
    public void calculateMeanError() {
        double sum = 0;
        for(Node node: nodes) {
            sum += node.error;
        }
        this.meanError = sum/(double) nodes.size();
    }
    
    
    
    
}
