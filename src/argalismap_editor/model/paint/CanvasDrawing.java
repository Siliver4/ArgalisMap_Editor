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

import argalismap_editor.model.saveAndLoadFile.SaveToFile;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * represents the Canvas from the fxml file and its functionalities, such as
 * being able to draw image on it.
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class CanvasDrawing {

  private final Canvas canvas;
  private final PaintManager pM;

  private int lineNb;
  private int columnNb;
  private int tileWidth;
  private int tileHeight;

  /**
   * represents the entire canvas tile value in a 1 dimension array
   */
  private int[] tileValues;

  /**
   * Constructor.
   *
   * @param canvas the canvas from the fxml file
   * @param pM the reference to the PaintManager to interact with it
   */
  public CanvasDrawing(Canvas canvas, PaintManager pM) {
    this.canvas = canvas;
    this.pM = pM;
    this.tileValues = new int[0];
    initializeHandler();
  }

  /**
   * Set the dimension of CanvasDrawing.
   *
   * @param lineNb the line number of canvas
   * @param columnNb the column number of canvas
   * @param tileWidth the tile width in pixel
   * @param tileHeight the tile height in pixel
   */
  public void setDimension(int lineNb, int columnNb, int tileWidth, int tileHeight) {
    this.lineNb = lineNb;
    this.columnNb = columnNb;
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    canvas.setHeight(lineNb * tileHeight);
    canvas.setWidth(columnNb * tileWidth);
    tileValues = new int[lineNb * columnNb];
  }

  public void saveToFileFromIntegerTable() {
    StringBuilder data = new StringBuilder();
    for (int i = 0; i < lineNb; i++) {
      for (int j = 0; j < columnNb; j++) {
        data.append(tileValues[j + i * columnNb] + " ");
      }
      data.append("\n");
    }
    SaveToFile.saveFromString(data.toString());
  }

  public void saveToPNG() {
    SaveToFile.saveFromCanvasToPNG(canvas);
  }

  public void saveToGIF() {
    SaveToFile.saveFromCanvasToGIF(canvas);
  }

  public void saveToJPG() {
    SaveToFile.saveFromCanvasToJPG(canvas);
  }

  public void saveToJPEG() {
    SaveToFile.saveFromCanvasToJPEG(canvas);
  }

  /**
   * Draws an image at x,y location.
   *
   * @param img the image we want to draw
   * @param x the abscissa position of the draw
   * @param y the ordinate position of the draw
   */
  private void drawTile(Image img, int x, int y) {
    canvas.getGraphicsContext2D().drawImage(img, x, y);
  }

  /**
   * Does 2 things, first set the drawn tile value to the table tileValues,
   * second draw the tile to the canvas.
   *
   * @param posX the click abscissa position inside the canvas
   * @param posY the click ordinate position inside the canvas
   */
  private void setAndDrawTile(int posX, int posY) {
    if ((posX < canvas.getWidth()) || (posY < canvas.getHeight()) || (posX >= 0) || (posY >= 0)) {

      // set the tile value
      int xTabIndex = posX / tileWidth;
      int yTabIndex = posY / tileHeight;
      int index = xTabIndex + yTabIndex * columnNb;
      tileValues[index] = pM.getSelectedTileValue();

      // draw the tile
      int xCanvas = xTabIndex * tileWidth;
      int yCanvas = yTabIndex * tileHeight;
      drawTile(pM.getSelectedTile(), xCanvas, yCanvas);
    }
  }

  /**
   * initialize the onMouse Handler for this Object
   */
  private void initializeHandler() {
    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {

      }
    });

    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        setAndDrawTile((int) event.getX(), (int) event.getY());
      }
    });

    canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        setAndDrawTile((int) event.getX(), (int) event.getY());
      }
    });
  }

}
