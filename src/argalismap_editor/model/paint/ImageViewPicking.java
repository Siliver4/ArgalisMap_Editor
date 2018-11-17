/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argalismap_editor.model.paint;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
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
     * the current selected crop image from this canvas content, it represent a tile.
     */
    private WritableImage tileSelected;
    
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
     * @param tileHeight  the tile height in pixel
     */
    public void setDimension(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.lineNb = (int) imageView.getImage().getHeight() / tileHeight;
        this.columnNb = (int) imageView.getImage().getWidth() / tileWidth;
    }
    
    /**
     * Get the index position of the tile we clicked on, considering the tileSet 
     * as a 1 dimension array, starting from top left corner at index 0.
     * 
     * @param posX the abscissa position in the canvas
     * @param posY the ordinate position in the canvas
     * @return the index of the corresponding tile
     * @throws IndexOutOfBoundsException if outside the canvas dimension
     */
    public int getTileIndex(int posX, int posY) throws IndexOutOfBoundsException {
        if( (posX >= imageView.getImage().getWidth()) || (posY >= imageView.getImage().getHeight()) || (posX < 0) || (posY < 0) ) 
            throw new IndexOutOfBoundsException();
        int x = posX / tileWidth;
        int y = posY / tileHeight;
        return x + y * columnNb;
    } 
    
    /**
     * Get the current selected tile.
     * 
     * @return the current selected tile
     */
    public Image getSelectedTile() {
        return tileSelected;
    }
    
    /**
     * initialize the onMouse Handler for this Object
     */
    private void initializeHandler() {
        
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                
            }
        });
        
        imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                
            }
        });
 
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.WHITE);
                int x = ((int) event.getX() / tileWidth) * tileWidth;
                int y = ((int) event.getY() / tileHeight) * tileHeight;
                parameters.setViewport(new Rectangle2D(x, y, tileWidth, tileHeight));
                tileSelected = new WritableImage(tileWidth, tileHeight);
                imageView.snapshot(parameters, tileSelected);
            }
        });
    }
    
}
