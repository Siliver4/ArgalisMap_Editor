/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argalismap_editor.model.paint;

import argalismap_editor.model.saveAndLoadFile.SaveToFile;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Manager class to handle interaction between canvasDrawing and imageViewPicking classes.
 * 
 * @author Alexandre 'ROKH' MAILLIU
 */
public class PaintManager {
    
    CanvasDrawing canvasDrawing;
    ImageViewPicking imageViewPicking;
    
    /**
     * Constructor
     * 
     * @param canvas the canvas from the fxml file where we want to draw things
     * @param imageView the imageView from the fxml where we want to pick up things
     */
    public PaintManager(Canvas canvas, ImageView imageView) {
        this.canvasDrawing = new CanvasDrawing(canvas, this);
        this.imageViewPicking = new ImageViewPicking(imageView);
    }
    
    /**
     * Set the dimension of canvasDrawing.
     * 
     * @param lineNb the line number of canvasDrawing
     * @param columnNb the column number of canvasDrawing
     * @param tileWidth the tile width in pixel
     * @param tileHeight  the tile height in pixel
     */
    public void setCanvasDimensions(int lineNb, int columnNb, int tileWidth, int tileHeight) {
        canvasDrawing.setDimension(lineNb, columnNb, tileWidth, tileHeight);
    }
    
    /**
     * Set the dimension of this ImageViewPicking.
     * 
     * @param tileWidth the tile width in pixel
     * @param tileHeight the tile height in pixel
     */
    public void setImageViewDimension(int tileWidth, int tileHeight) {
        imageViewPicking.setDimension(tileWidth, tileHeight);
    }
    
    /**
     * Get the selected tile from the imageViewPicking and returns it as an Image.
     * 
     * @return the selected tile
     */
    public Image getSelectedTile() {
        return imageViewPicking.getSelectedTile();
    }
    
    /**
     * Get the selected tile value from the imageViewPicking and returns it as an int.
     * 
     * @return the selected tile value
     */
    public int getSelectedTileValue() {
        return imageViewPicking.getSelectedTileValue();
    }
    
    /**
     * save the canvasDrawing content as an '.argalis' file, Meaning it contains
     * all the tile values as integers.
     */
    public void saveToARGALIS() {
        canvasDrawing.saveToFileFromIntegerTable();
    }
    
    /**
     * save the canvasDrawing content as an '.png' file.
     */
    public void saveToPNG() {
        canvasDrawing.saveToPNG();
    }
    
    /**
     * save the canvasDrawing content as an '.gif' file.
     */
    public void saveToGIF() {
        canvasDrawing.saveToGIF();
    }
    
    /**
     * save the canvasDrawing content as an '.jpg' file.
     */
    public void saveToJPG() {
        canvasDrawing.saveToJPG();
    }
    
    /**
     * save the canvasDrawing content as an '.jpeg' file.
     */
    public void saveToJPEG() {
        canvasDrawing.saveToJPEG();
    }
}
