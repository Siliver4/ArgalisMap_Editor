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
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * represents the ImageView from the fxml file and its functionalities, such as
 * being able to pick up crop image from it.
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class ImageViewPicking {

  private final ImageView imageView;
  private int lineNb;
  private int columnNb;
  private int tileWidth;
  private int tileHeight;
  private int lineSelected;
  private int columnSelected;

  /**
   * the current selected tile, as a cropped image from this canvas content
   */
  private WritableImage tileSelected;

  /**
   * the current selected tile value
   */
  private int tileSelectedValue;

  /**
   * Constructor.
   *
   * @param imageView the ImageView from the fxml file
   */
  public ImageViewPicking(ImageView imageView) {
    this.imageView = imageView;
    this.lineSelected = 0;
    this.columnSelected = 0;
    initializeHandler();
  }

  /**
   * Set the dimension of this ImageViewPicking.
   *
   * @param tileWidth the tile width in pixel
   * @param tileHeight the tile height in pixel
   */
  public void setDimension(int tileWidth, int tileHeight) {
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    this.lineNb = (int) imageView.getImage().getHeight() / tileHeight;
    this.columnNb = (int) imageView.getImage().getWidth() / tileWidth;
  }

  /**
   * load a tileSet from a png file image and put its content to the imageView
   */
  public void loadImageFromPNG() {
    LoadFromFile.loadImageFromPNG(imageView);
  }
  
  /**
   * load a tileSet from a gif file image and put its content to the imageView
   */
  public void loadImageFromGIF() {
    LoadFromFile.loadImageFromGIF(imageView);
  }
  
  /**
   * load a tileSet from a jpg file image and put its content to the imageView
   */
  public void loadImageFromJPG() {
    LoadFromFile.loadImageFromJPG(imageView);
  }
  
  /**
   * load a tileSet from a jpeg file image and put its content to the imageView
   */
  public void loadImageFromJPEG() {
    LoadFromFile.loadImageFromJPEG(imageView);
  }
  
  /**
   * Get the current selected tile image.
   *
   * @return the current selected tile image
   */
  public Image getSelectedTile() {
    return tileSelected;
  }

  /**
   * Get the current selected tile value.
   *
   * @return the current selected tile value
   */
  public int getSelectedTileValue() {
    return tileSelectedValue;
  }

  /**
   * pick up the tile at (xTabIndex, yTabIndex) location in the imageView as a 
   * cropped image and keep it in tileSelect attribute.
   * 
   * @param xTabIndex the column index of the cropped image
   * @param yTabIndex the line index of the cropped image
   */
  private void pickTile(int xTabIndex, int yTabIndex) {
      SnapshotParameters parameters = new SnapshotParameters();
      parameters.setFill(Color.WHITE);
      int xImageView = xTabIndex * tileWidth;
      int yImageView = yTabIndex * tileHeight;
      parameters.setViewport(new Rectangle2D(xImageView, yImageView, tileWidth, tileHeight));
      tileSelected = new WritableImage(tileWidth, tileHeight);
      imageView.snapshot(parameters, tileSelected);
  }
  
  /**
   * Does 2 things, first set the selected tile value from the ImageView, second
   * pick up the tile as a cropped image and keep it in tileSelect attribute.
   *
   * @param tileIndex the tile index in our imageView, index goes from 0 to 
   * (columnNb * lineNb - 1), and start from top left corner to right bottom.
   */
  public void setAndPickTile(int tileIndex) {
    if (( tileIndex >= 0 ) && (tileIndex < columnNb * lineNb )) {

      // set the selected tile value
      tileSelectedValue = tileIndex;

      // pick up the tile as a cropped image from the ImageView
      int xTabIndex = tileIndex % columnNb;
      int yTabIndex = tileIndex / columnNb;
      pickTile(xTabIndex, yTabIndex);
    }
  }
  
  /**
   * Does 2 things, first set the selected tile value from the ImageView, second
   * pick up the tile as a cropped image and keep it in tileSelect attribute.
   *
   * @param posX the click abscissa position inside the ImageView
   * @param posY the click ordinate position inside the ImageView
   */
  public void setAndPickTile(int posX, int posY) {
    if ((posX < imageView.getImage().getWidth()) && (posY < imageView.getImage().getHeight()) && (posX >= 0) && (posY >= 0)) {

      // set the selected tile value
      int xTabIndex = posX / tileWidth;
      int yTabIndex = posY / tileHeight;
      tileSelectedValue = xTabIndex + yTabIndex * columnNb;

      // pick up the tile as a cropped image from the ImageView
      pickTile(xTabIndex, yTabIndex);
    }
  }

  /**
   * initialize the onMouse Handler for this Object
   */
  private void initializeHandler() {

    imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {

      }
    });
    
    imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        setAndPickTile((int) event.getX(), (int) event.getY());
      }
    });

    imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        setAndPickTile((int) event.getX(), (int) event.getY());
      }
    });
  }

  public int getLineNb() {
    return lineNb;
  }

  public int getColumnNb() {
    return columnNb;
  }

  public int getTileWidth() {
    return tileWidth;
  }

  public int getTileHeight() {
    return tileHeight;
  }
  
}
