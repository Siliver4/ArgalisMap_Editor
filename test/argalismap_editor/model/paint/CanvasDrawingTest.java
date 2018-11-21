/*
 * The MIT License
 *
 * Copyright 2018 Alexandre 'ROKH' MAILLIU.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package argalismap_editor.model.paint;

import javafx.scene.canvas.Canvas;
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
    classUnderTest.setDimension(10, 10, 100, 100);
    assertEquals(0, classUnderTest.getTileIndex(0, 0));
    assertEquals(0, classUnderTest.getTileIndex(55, 47));
    assertEquals(0, classUnderTest.getTileIndex(99, 47));
    assertEquals(1, classUnderTest.getTileIndex(100, 47));
    assertEquals(2, classUnderTest.getTileIndex(200, 47));
    assertEquals(9, classUnderTest.getTileIndex(999, 47));
    assertEquals(10, classUnderTest.getTileIndex(55, 147));
  }

  /**
   * Test of initializeHandler method, of class CanvasDrawing.
   */
  @Test
  public void testInitializeHandler() {
  }

}
