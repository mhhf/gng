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
    
    @Test
    public void testCalculateCOMPoint() {
        System.out.println("calculateCOMPoint");
        
        assertTrue(this.handler1.calculateCOMPoint().x - 50 < 0.01 && this.handler1.calculateCOMPoint().y - 150 < 0.01 );
        assertTrue(this.handler2.calculateCOMPoint().x - 200 < 0.01 && this.handler1.calculateCOMPoint().y - 200 < 0.01 );
        
    }
    
    @Test
    public void testCalculateCOMRadius(){
        System.out.println("calculateCOMRadius");
        
        this.handler1.calculateCOMPoint();
        this.handler2.calculateCOMPoint();
        
        
        assertEquals(133.4166, this.handler1.calculateCOMRadius() ,0.01);
        assertEquals(212.1320, this.handler2.calculateCOMRadius() ,0.01);
    }
    
    
    /**
     * Test of getRandomInput method, of class PolygonBasedGNGHandler.
     */
    @Test
    public void testPointInPolygon() {
        System.out.println("pointInPolygon");
        
        this.handler1.init();

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
