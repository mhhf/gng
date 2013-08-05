/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core;

import gng.core.handlers.SetBasedGNGHandler;
import gng.core.Node;
import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mhhf
 */
public class GNGHandlerTest {
    
    Node node1,node2,node3,node4,node5,node6;
    AbstractGNGHandler handler;
    
    /**
     * 
     */
    public GNGHandlerTest() {
    }

    /**
     * 
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * 
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    /**
     * 
     */
    @Before
    public void setUp() {
        double[] d1 = {2.0, 2.0};
        double[] d2 = {-2.0, -2.0};
        double[] d3 = {-2.0, 2.0};
        double[] d4 = {5.0, 5.0};
        double[] d5 = {2.0, 0.0};
        double[] d6 = {0.0, 2.0};
        
        this.node1 = new Node( d1 );
        this.node2 = new Node( d2 );
        this.node3 = new Node( d3 );
        this.node4 = new Node( d4 );
        this.node5 = new Node( d5 );
        this.node6 = new Node( d6 );
        
        node1.connect(node2);
        node1.connect(node3);
        node1.connect(node4);
        
        node2.connect(node4);
        node2.connect(node5);
        
        node1.error = 1;
        node2.error = 2;
        node3.error = 3;
        node4.error = 20;
        node5.error = 10;
        node6.error = 16;
        
        
        this.handler = new SetBasedGNGHandler();
        
        handler.addNode(node1);
        handler.addNode(node2);
        handler.addNode(node3);
        handler.addNode(node4);
        handler.addNode(node5);
        handler.addNode(node6);
        
    }
    
    /**
     * 
     */
    @After
    public void tearDown() {
    }


    /**
     * Test of getNearestNodes method, of class GNGHandler.
     */
    @Test
    public void testGetNearestNodes() {
        System.out.println("getNearestNodes");
        double[] d1 = {0.0,0.0001};
        double[] d2 = {-2.0,0.0001};
        double[] d3 = {0.0,-2};
        double[] d4 = {500.0,0.0};
        
        Node[] expResult1 = {this.node6, this.node5};
        Node[] expResult2 = {this.node3, this.node2};
        Node[] expResult3 = {this.node2, this.node5};
        Node[] expResult4 = {this.node4, this.node5};
        
        Node[] result1 = handler.getNearestNodes( new DenseVector( d1 ));
        Node[] result2 = handler.getNearestNodes( new DenseVector( d2 ));
        Node[] result3 = handler.getNearestNodes( new DenseVector( d3 ));
        Node[] result4 = handler.getNearestNodes( new DenseVector( d4 ));
        
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
    }
    
    /**
     * 
     */
    @Test
    public void testLargestError(){
        System.out.println("getNearestNodes");   
        
        Node result = handler.findNodeWithLargestError();
        
        assertEquals(result, node4);
    }



    /**
     * Test of addNode method, of class GNGHandler.
     */
    @Test
    public void testAddNode() {
        System.out.println("addNode");
        
        int prev = handler.getNodes().size();
        double[] d = {0,0};
        Node node = new Node(d);
        handler.addNode(node);
        int pos = handler.getNodes().size();
        
        assertTrue(pos == prev+1);
    }

    /**
     * Test of connect method, of class GNGHandler.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        
        handler.connect(node3, node2);
        
        assertTrue( handler.connections.contains(node3.searchConnection(node2)) );
    }



    /**
     * Test of insertNewNode method, of class GNGHandler.
     */
    @Test
    public void testInsertNewNode() {
        System.out.println("insertNewNode");
        
        
        Node node = handler.insertNewNode();
        assertTrue(node.getX() == 1.5 && node.getY() == 1.5);
        
        //TODO: This test Fails, find out why this happen
        //assertEquals( 30, node.error, 0.01);
        
        // Pos don't differ
        assertTrue(node4.w.get(0) == 5);
        
    }
    
    
    
}
