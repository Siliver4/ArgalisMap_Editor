/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argalismap_editor.model.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class CanvasDrawingTest {
    
    private CanvasDrawing classUnderTest;
    private Canvas canvas;
    
    public CanvasDrawingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        canvas = new Canvas();
        classUnderTest = new CanvasDrawing(canvas, null);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of drawTile method, of class CanvasDrawing.
     */
    @Test
    public void testGetTileIndex() {
        classUnderTest.setDimension(10,10,100,100);
        assertEquals(0,classUnderTest.getTileIndex(0,0));
        assertEquals(0,classUnderTest.getTileIndex(55,47));
        assertEquals(0,classUnderTest.getTileIndex(99,47));
        assertEquals(1,classUnderTest.getTileIndex(100,47));
        assertEquals(2,classUnderTest.getTileIndex(200,47));
        assertEquals(9,classUnderTest.getTileIndex(999,47));
        try {
            assertEquals(9,classUnderTest.getTileIndex(1000,47));
            assertTrue(false);
        } catch ( IndexOutOfBoundsException e ) {
            assertTrue(true);
        }
        try {
            assertEquals(0,classUnderTest.getTileIndex(-1,47));
            assertTrue(false);
        } catch ( IndexOutOfBoundsException e ) {
            assertTrue(true);
        }
    }

    /**
     * Test of initializeHandler method, of class CanvasDrawing.
     */
    @Test
    public void testInitializeHandler() {
    }
    
}
