/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argalismap_editor.model.paint;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
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
     * Constructor.
     * 
     * @param canvas the canvas from the fxml file
     * @param pM the reference to the PaintManager to interact with it
     */
    public CanvasDrawing(Canvas canvas, PaintManager pM) {
        this.canvas = canvas;
        this.pM = pM;
        initializeHandler();
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
        if( (posX >= canvas.getWidth()) || (posY >= canvas.getHeight()) || (posX < 0) || (posY < 0) ) 
            throw new IndexOutOfBoundsException();
        int x = posX / tileWidth;
        int y = posY / tileHeight;
        return x + y * columnNb;
    } 
    
    /**
     * Set the dimension of CanvasDrawing.
     * 
     * @param lineNb the line number of canvas
     * @param columnNb the column number of canvas
     * @param tileWidth the tile width in pixel
     * @param tileHeight  the tile height in pixel
     */
    public void setDimension(int lineNb, int columnNb, int tileWidth, int tileHeight) {
        this.lineNb = lineNb;
        this.columnNb = columnNb;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        canvas.setHeight(lineNb * tileHeight);
        canvas.setWidth(columnNb * tileWidth);
    }
    
    /**
     * Draws an image at x,y location.
     * 
     * @param img the image we want to draw
     * @param x the abscissa position of the draw
     * @param y the ordinate position of the draw 
     */
    public void drawTile(Image img, int x, int y) {
        canvas.getGraphicsContext2D().drawImage(img, x, y);
    }
    
    /**
     * initialize the onMouse Handler for this Object
     */
    private void initializeHandler() {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                
            }
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                
            }
        });
 
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                int x = ((int) event.getX() / tileWidth) * tileWidth;
                int y = ((int) event.getY() / tileHeight) * tileHeight;
                drawTile(pM.getSelectedTile(), x, y);
            }
        });
    }
    
    
    
}
