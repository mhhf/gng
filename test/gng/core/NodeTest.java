/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core;

import gng.core.Connection;
import gng.core.Node;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.distributed.DistVector;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mhhf
 */
public class NodeTest {
    
    Node node1,node2,node3,node4,node5,node6,zero;
    
    public NodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        double[] d0 = {0, 0};
        double[] d1 = {2.0, 2.0};
        double[] d2 = {-2.0, -2.0};
        double[] d3 = {-2.0, 2.0};
        double[] d4 = {5.0, 5.0};
        double[] d5 = {2.0, 0.0};
        double[] d6 = {0.0, 2.0};
        
        this.zero = new Node( d0 );
        this.node1 = new Node( d1 );
        this.node2 = new Node( d2 );
        this.node3 = new Node( d3 );
        this.node4 = new Node( d4 );
        this.node5 = new Node( d5 );
        this.node6 = new Node( d6 );
        
        node1.error = 1;
        node2.error = 2;
        node3.error = 3;
        node4.error = 20;
        node5.error = 10;
        node6.error = 16;
        
        node1.connect(node2);
        node1.connect(node3);
        node1.connect(node4);
        
        node2.connect(node4);
        node2.connect(node5);
    }
    
    @After
    public void tearDown() {
    }
    
    

    /**
     * Test of searchConnection method, of class Node.
     */
    @Test
    public void testConnections() {
        System.out.println("searchConnection");
        
        Connection result1 = this.node1.searchConnection(this.node2);
        Connection result2 = this.node2.searchConnection(this.node1);
        assertEquals(result1, result2);
    }
    
    @Test
    public void testDistanceTo(){
        System.out.println("distance To "+zero.distanceTo(node2));
        assertTrue( Math.abs(node1.distanceTo(node3)-4.0)<0.0001 );
        assertTrue( Math.abs(zero.distanceTo(node2)-Math.sqrt(8))<0.0001 );
    }
    
    
   
    @Test
    public void testMoveToVector(){
        System.out.println("move to Vector");
        
        
        // Input
        double[] d = {0,0};
        DenseVector x =  new DenseVector( d );
        
        double previousDistance1 = node1.distanceTo( x ); // for the node itself
        double previousDistance2 = node2.distanceTo( x ); // for one of the naigbers
        double previousDistance5 = node5.distanceTo( x ); // for one unconnected node
        
        node1.moveTo( x );
        
        double newDistance1 = node1.distanceTo( x ); // for the node itself
        double newDistance2 = node2.distanceTo( x ); // for one of the naigbers
        double newDistance5 = node5.distanceTo( x ); // for one unconnected node
        
        assertTrue( newDistance1 < previousDistance1 ); // node move towards x
        assertTrue( newDistance2 < previousDistance2 ); // neighber move towards x
        
        // System.out.println( newDistance5 +" "+previousDistance5 );
        assertTrue( newDistance5 == previousDistance5 ); // unaffected node dont move
        
    }
    
    @Test
    public void testMoveTowards () {
        double[] d = {0,0};
        DenseVector x =  new DenseVector( d );
        
        double previousDistance = node6.distanceTo( x );
        
        node6.moveTowards(0.5, x);
        
        double newDistance = node6.distanceTo( x );
        
        assertTrue( newDistance < previousDistance );
        
    }
    
    @Test
    public void testGetOthers () {
        
        Node n;
        for (Connection conn:node1.connections) {
            n = conn.getOther(node1);
            assertTrue( n.equals(node2) || n.equals(node3) || n.equals(node4) );
        }
    }

    


    /**
     * Test of getMaxErrorNaighbor method, of class Node.
     */
    @Test
    public void testGetMaxErrorNaighbor() {
        System.out.println("getMaxErrorNaighbor");
        Node expResult = node2;
        Node result = node4.getMaxErrorNaighbor();
        assertEquals(expResult, result);
    }

   
    
}
