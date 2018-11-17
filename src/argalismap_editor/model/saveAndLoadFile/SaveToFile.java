/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argalismap_editor.model.saveAndLoadFile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * Helper class, handle the file saving methods, using canvas' drawing, Strings.
 * 
 * @author @author Alexandre 'ROKH' MAILLIU
 */
public class SaveToFile {
    
    /**
     * save the string content as an '.argalis' file.
     * 
     * @param data the string to save to the new file
     */
    public static boolean saveFromString(String data) {
        
        try {
            // declaration of the resources
            FileOutputStream fileOutputStream = null;
            File file = fileChooser("argalis");
            
            if(file != null) {
                try {
                    // use of the resources
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(data.getBytes());
                    fileOutputStream.flush();
                    return true;

                } finally {
                    // closure of the resources
                    if(fileOutputStream != null)
                        fileOutputStream.close();
                }
            }
        } catch(FileNotFoundException e1) {
            e1.printStackTrace();
        } catch(IOException e2) {
            e2.printStackTrace();
        }
        return false;
    }
    
    /**
     * save the canvas content as a png file.
     * 
     * @param canvas the canvas from which the content is save to a new file
     */
    public static void saveFromCanvasToPNG(Canvas canvas) {
        saveFromCanvasToFile(canvas, "png");
    }
    
    /**
     * save the canvas content as a gif file.
     * 
     * @param canvas the canvas from which the content is save to a new file
     */
    public static void saveFromCanvasToGIF(Canvas canvas) {
        saveFromCanvasToFile(canvas, "gif");
    }
    
    /**
     * save the canvas content as a jpg file.
     * 
     * @param canvas the canvas from which the content is save to a new file
     */
    public static void saveFromCanvasToJPG(Canvas canvas) {
        saveFromCanvasToFile(canvas, "jpg");
    }
    
    /**
     * save the canvas content as a jpeg file.
     * 
     * @param canvas the canvas from which the content is save to a new file
     */
    public static void saveFromCanvasToJPEG(Canvas canvas) {
        saveFromCanvasToFile(canvas, "jpeg");
    }
    
    /**
     * Generic function to ask the user to save the canvas content as a fileExtension file.
     * All file type can be used with it, but I restricted it with the 4 main usage, aka
     * png, gif, jpg, and jpeg.
     * @see https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html
     * 
     * @param canvas the canvas from which the content is save to the a file
     * @param fileExtension the file extension
     */
    private static void saveFromCanvasToFile(Canvas canvas, String fileExtension) {
        
        File file = fileChooser(fileExtension);
        
        if(file != null) {
            try {
                // to handle java 8.0 pinky jpg and jpeg files see this link
                // https://stackoverflow.com/questions/19548363/image-saved-in-javafx-as-jpg-is-pink-toned#answer-19605733
                
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);

                // get buffered image
                BufferedImage image = SwingFXUtils.fromFXImage(writableImage, null); 

                // remove alpha-channel from buffered image
                BufferedImage imageRGB = new BufferedImage(
                    (int)writableImage.getWidth(), (int)writableImage.getHeight(), 
                    BufferedImage.OPAQUE); 

                Graphics2D graphics = imageRGB.createGraphics();
                graphics.drawImage(image, 0, 0, null);

                ImageIO.write(imageRGB, fileExtension, file);
            } catch (IOException e) {
                e.printStackTrace();
                //Logger.getLogger(JavaFX_DrawOnCanvas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Invoke an open dialog window for selecting the file where you want to save,
     * and returns this new file.
     * 
     * @param fileExtension the file extension
     * @return the new file
     */
    private static File fileChooser(String fileExtension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save image as " + fileExtension + "...");
        FileChooser.ExtensionFilter extFilter = 
            new FileChooser.ExtensionFilter(fileExtension + " (*." + fileExtension + ")", "*." + fileExtension);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        return file;
    }
   
}
