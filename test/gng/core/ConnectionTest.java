/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core;

import gng.core.Connection;
import gng.core.Node;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mhhf
 */
public class ConnectionTest {
    
    Node node1,node2,node3,node4,node5,node6;
    
    public ConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        double[] d1 = {1.0, 0.0};
        double[] d2 = {1.0, 1.0};
        double[] d3 = {1.0, 2.0};
        double[] d4 = {1.0, 3.0};
        double[] d5 = {1.0, 4.0};
        double[] d6 = {1.0, 5.0};
        
        this.node1 = new Node( d1 );
        this.node2 = new Node( d2 );
        this.node3 = new Node( d3 );
        this.node4 = new Node( d4 );
        this.node5 = new Node( d5 );
        this.node6 = new Node( d6 );
        
        node1.connect(node2);
        node1.connect(node3);
        node1.connect(node4);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of contains method, of class Connection.
     */
    @Test
    public void testContains() {
        System.out.println("contains");

        Connection instance = node1.connect(node2);
        assertTrue(instance.contains(node1) && instance.contains(node2));        
    }
    
    @Test
    public void testGetOther() {
        System.out.println("getOther");
        
        Connection instance = node1.connect(node2);
        assertEquals(instance.getOther(node1),node2);
    }
    
    @Test
    public void testRemove() {
        
        Connection instance = node1.connect(node5);
        instance.remove();
        assertFalse(node1.connections.contains(instance));
        assertTrue(node1.searchConnection(node5) == null);
        assertTrue(node5.searchConnection(node1) == null);
    }
}
