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
 * represents the ImageView from the fxml file and its functionalities,
 * such as being able to pick up crop image from it.
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
     * @param tileHeight  the tile height in pixel
     */
    public void setDimension(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.lineNb = (int) imageView.getImage().getHeight() / tileHeight;
        this.columnNb = (int) imageView.getImage().getWidth() / tileWidth;
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
     * Get the current selected tile value.
     * 
     * @return the current selected tile value
     */
    public int getSelectedTileValue() {
        return tileSelectedValue;
    }
    
    /**
     * Does 2 things, first set the selected tile value from the ImageView,
     * second pick up the tile as a cropped image and keep it in tileSelect.
     * 
     * @param posX the click abscissa position inside the ImageView
     * @param posY the click ordinate position inside the ImageView
     */
    private void setAndPickTile(int posX, int posY) {
        if( (posX < imageView.getImage().getWidth()) || (posY < imageView.getImage().getHeight()) || (posX >= 0) || (posY >= 0) ) {
            
            // set the selected tile value
            int xTabIndex = posX / tileWidth;
            int yTabIndex = posY / tileHeight;
            tileSelectedValue = xTabIndex + yTabIndex * columnNb;
        
            // pick up the tile as a cropped image from the ImageView
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.WHITE);
            int xImageView = xTabIndex * tileWidth;
            int yImageView = yTabIndex * tileHeight;
            parameters.setViewport(new Rectangle2D(xImageView, yImageView, tileWidth, tileHeight));
            tileSelected = new WritableImage(tileWidth, tileHeight);
            imageView.snapshot(parameters, tileSelected);
        }
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
                setAndPickTile((int) event.getX(), (int) event.getY());
            }
        });
 
        imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                setAndPickTile((int) event.getX(), (int) event.getY());
            }
        });
    }
    
}
