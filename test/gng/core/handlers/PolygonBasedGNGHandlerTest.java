/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.handlers;

import gng.core.Node;
import gng.core.handlers.inputs.StaticPolygonInputs;
import java.util.ArrayList;
import math.Vector2D;
import no.uib.cipr.matrix.DenseVector;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mhhf
 */
public class PolygonBasedGNGHandlerTest {
    
    PolygonBasedGNGHandler handler1, handler2;
    
    public PolygonBasedGNGHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {        
        // FIXME: fucking radius calculation is shit, when the polygon is like w:10 h:400 
        
        //this.handler1 = new PolygonBasedGNGHandler( StaticPolygonInputs.POLYGON_TYPE1 );
        //this.handler2 = new PolygonBasedGNGHandler( StaticPolygonInputs.POLYGON_TYPE2 );
    }
    
    @After
    public void tearDown() {
    }
    
    
    
    /**
     * Test of getRandomInput method, of class PolygonBasedGNGHandler.
     */
    @Test
    public void testPointInPolygon() {
        System.out.println("pointInPolygon");

        assertTrue( handler1.pointInPolygon(new Vector2D( 20, 20 )) );
        assertTrue( handler1.pointInPolygon(new Vector2D( 20.1, 79.9 )) );
        assertTrue( handler1.pointInPolygon(new Vector2D( 20.1, 279.9 )) );
        assertTrue( handler1.pointInPolygon(new Vector2D( 79.9, 20.1 )) );
        assertTrue( handler1.pointInPolygon(new Vector2D( 79.9, 279.9 )) );
        assertFalse( handler1.pointInPolygon(new Vector2D( 20, 19.9 )) );
        assertFalse( handler1.pointInPolygon(new Vector2D( 19, 150 )) );
        assertFalse( handler1.pointInPolygon(new Vector2D( 80.1, 100 )) );
        
    }
    

    /**
     * Test of getRandomInput method, of class PolygonBasedGNGHandler.
     */
    @Test
    public void testGetRandomInput() {
        System.out.println("getRandomInput");
        
        this.handler1.init();
                
        for(int i=0; i<20; i++){
            DenseVector result = this.handler1.getRandomInput();

            // expect to be in the radius
            assertTrue( result.get(0) > 20 && result.get(0) < 80 && result.get(1) > 20 && result.get(1) < 280 );
        }   

    }
    
    

    /**
     * Test of getRandomNegativeInput method, of class PolygonBasedGNGHandler.
     */
    @Test
    public void testGetRandomNegativeInput() {
        // TODO
    }

    /**
     * Test of validateNodeForUpdate method, of class PolygonBasedGNGHandler.
     */
    @Test
    public void testValidateNodeForUpdate() {
        // TODO
    }
}
