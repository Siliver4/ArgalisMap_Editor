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

import argalismap_editor.model.saveAndLoadFile.LoadFromFile;
import argalismap_editor.model.saveAndLoadFile.SaveToFile;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Manager class to handle interaction between canvasDrawing and
 * imageViewPicking classes.
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class PaintManager {

  private CanvasDrawing canvasDrawing;
  private ImageViewPicking imageViewPicking;
  private int tileWidth;
  private int tileHeight;

  /**
   * Constructor.
   *
   * @param canvas the canvas from the fxml file where we want to draw things
   * @param imageView the imageView from the fxml where we want to pick up
   * things
   */
  public PaintManager(Canvas canvas, ImageView imageView) {
    this.canvasDrawing = new CanvasDrawing(canvas, this);
    this.imageViewPicking = new ImageViewPicking(imageView, this);
  }

  /**
   * if the tile dimensions are different from the previous ones, ask the user 
   * if he really wants to load a new map ( because it will modify its scale )
   * 
   * @return true if the user click on Ok or if the tile dimensions are the 
   * same as before the call of this function. Else false.
   */
  public boolean isNewTileDimensionOkForUser(int tileWidth, int tileHeight) {
    if ( (this.tileWidth == tileWidth) && (this.tileHeight == tileHeight) ) {
      // if no tile dimensions change
      return true;
    } else {
      // if there is a change in tiles dimensions, ask the user for a change
      Alert alert = new Alert(Alert.AlertType.WARNING, "New Tiles Dimensions ! Load a new map ?", ButtonType.OK, ButtonType.CANCEL);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.OK) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * set the dimensions of canvasDrawing, imageViewPicking and tiles after 
   * a tileSet loading.
   * 
   * @param tileWidth the tiles width in pixels
   * @param tileHeight the tiles height in pixels
   */
  public void setDimensionAfterNewTileSetLoading(int tileWidth, int tileHeight) {
    if ( (this.tileWidth == tileWidth) && (this.tileHeight == tileHeight) ) {
      // if no tile dimensions change
      setTileDimension(tileWidth, tileHeight);
      setImageViewDimension();
    } else {
      // if there is a change in tiles dimensions, alert the user
      setTileDimension(tileWidth, tileHeight);
      setCanvasDimensionsAfterNewTileSetLoading();
      setImageViewDimension();
    }
  }
  
  /**
   * set the tiles dimension in pixels.
   * 
   * @param tileWidth the tiles width in pixels
   * @param tileHeight the tiles height in pixels
   */
  public void setTileDimension(int tileWidth, int tileHeight) {
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
  }
  
  /**
   * Set the dimension of canvasDrawing.
   *
   * @param lineNb the line number of canvasDrawing
   * @param columnNb the column number of canvasDrawing
   */
  public void setCanvasDimensions(int lineNb, int columnNb) {
    canvasDrawing.setDimension(lineNb, columnNb);
  }
  
  /**
   * Set the dimension of canvasDrawing after loading a new tileSet.
   */
  public void setCanvasDimensionsAfterNewTileSetLoading() {
    canvasDrawing.setCanvasDimensionsAfterNewTileSetLoading();
  }

  /**
   * Set the dimension of this ImageViewPicking.
   *
   */
  public void setImageViewDimension() {
    imageViewPicking.setDimension();
  }

  /**
   * Get the selected tile image from the imageViewPicking and returns it.
   *
   * @return the selected tile
   */
  public Image getSelectedTile() {
    return imageViewPicking.getSelectedTile();
  }
  
  /**
   * Get the selected tile value from the imageViewPicking and returns it.
   *
   * @return the selected tile value
   */
  public int getSelectedTileValue() {
    return imageViewPicking.getSelectedTileValue();
  }
  
  /**
   * load an argalis file content, which is a integer table, and draws it to 
   * the canvasDrawing's canvas.
   * this method is a bit tricky, cause it needs to have both imageViewPicking 
   * and canvasDrawing references to work between them.
   */
  public void loadMapFromARGALIS() {
    LoadFromFile.loadFromArgalisFileToCanvasDrawing(imageViewPicking, canvasDrawing);
  }
  
  /**
   * load a tileSet from a png file image and put its content to the imageView.
   * 
   * @return true if the tileSet file have been correctly loaded, else false
   */
  public boolean loadTileSetImageFromPNG() {
    return imageViewPicking.loadImageFromPNG();
  }
  
  /**
   * load a tileSet from a gif file image and put its content to the imageView.
   * 
   * @return true if the tileSet file have been correctly loaded, else false
   */
  public boolean loadTileSetImageFromGIF() {
    return imageViewPicking.loadImageFromGIF();
  }
  
  /**
   * load a tileSet from a jpg file image and put its content to the imageView.
   * 
   * @return true if the tileSet file have been correctly loaded, else false
   */
  public boolean loadTileSetImageFromJPG() {
    return imageViewPicking.loadImageFromJPG();
  }
  
  /**
   * load a tileSet from a jpeg file image and put its content to the imageView.
   * 
   * @return true if the tileSet file have been correctly loaded, else false
   */
  public boolean loadTileSetImageFromJPEG() {
    return imageViewPicking.loadImageFromJPEG();
  }
  
  /**
   * save the canvasDrawing content as an '.argalis' file, Meaning it contains
   * all the tile values as integers.
   */
  public void saveMapToARGALIS() {
    canvasDrawing.saveToFileFromIntegerTable();
  }

  /**
   * save the canvasDrawing content as an '.png' file.
   */
  public void saveMapToPNG() {
    canvasDrawing.saveToPNG();
  }

  /**
   * save the canvasDrawing content as an '.gif' file.
   */
  public void saveMapToGIF() {
    canvasDrawing.saveToGIF();
  }

  /**
   * save the canvasDrawing content as an '.jpg' file.
   */
  public void saveMapToJPG() {
    canvasDrawing.saveToJPG();
  }

  /**
   * save the canvasDrawing content as an '.jpeg' file.
   */
  public void saveMapToJPEG() {
    canvasDrawing.saveToJPEG();
  }

  public int getTileWidth() {
    return tileWidth;
  }

  public int getTileHeight() {
    return tileHeight;
  }
  
}
