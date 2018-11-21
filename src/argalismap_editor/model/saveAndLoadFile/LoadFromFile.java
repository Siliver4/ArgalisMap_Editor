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
package argalismap_editor.model.saveAndLoadFile;

import argalismap_editor.model.paint.CanvasDrawing;
import argalismap_editor.model.paint.ImageViewPicking;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class LoadFromFile {
  
  /**
   * load the content of an argalis file, which is an integer table, to the 
   * canvasDrawing, using the currently selected imageViewPicking to get the 
   * cropped images and values to set to the canvasDrawing.
   * 
   * @param imageViewPicking the imageViewPicking where the images and values 
   * are taken from
   * @param canvasDrawing  the canvasDrawing where the images and values are 
   * setted to
   */
  public static void loadFromArgalisFileToCanvasDrawing(ImageViewPicking imageViewPicking, CanvasDrawing canvasDrawing) {
    
    File file = fileChooser("argalis");
    
    if (file != null) {
      try {
        // declaration of the resources
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
          new FileInputStream(file)));

        try {
          // use of the ressources
          String contentRead = bufferedReader.readLine();
          int x = 0; 
          int y = 0;

          // the entire file is readed and then used to set the canvasDrawing
          while (contentRead != null) {
            String[] contentReadAsTable = contentRead.split(" ");

            // one line of the file per iteration
            for ( String value : contentReadAsTable ) {
              int intValue = Integer.parseInt(value); 
              imageViewPicking.setAndPickTile(intValue);
              canvasDrawing.setAndDrawTileByIndex(x, y);
              x = x + 1; 
            } 

            x = 0;
            y = y + 1;
            contentRead = bufferedReader.readLine();
          }

        } finally {
          // closure of the resources
          if(bufferedReader != null) {
              bufferedReader.close();
          }

        }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
  }
  
  /**
   * load a png file image and put its content to the imageView
   * 
   * @param imageView the imageView we are aiming to
   */
  public static void loadImageFromPNG(ImageView imageView) {
    loadImageFromFile(imageView, "png");
  }
  
  /**
   * load a gif file image and put its content to the imageView
   * 
   * @param imageView the imageView we are aiming to
   */
  public static void loadImageFromGIF(ImageView imageView) {
    loadImageFromFile(imageView, "gif");
  }
  
  /**
   * load a jpg file image and put its content to the imageView
   * 
   * @param imageView the imageView we are aiming to
   */
  public static void loadImageFromJPG(ImageView imageView) {
    loadImageFromFile(imageView, "jpg");
  }
  
  /**
   * load a jpeg file image and put its content to the imageView
   * 
   * @param imageView the imageView we are aiming to
   */
  public static void loadImageFromJPEG(ImageView imageView) {
    loadImageFromFile(imageView, "jpeg");
  }
  
  /**
   * Generic function to ask the user to load a file image content 
   * to the imageView. All image file type can be used with it, but I 
   * restricted it with the 4 main usage, aka png, gif, jpg, and jpeg.
   * 
   * @param imageView the imageView we are aiming to
   * @param fileExtension the file extension of the image file
   */
  private static void loadImageFromFile(ImageView imageView, String fileExtension) {
    try {
      File file = fileChooser(fileExtension);
      
      if (file != null) {
        BufferedImage bufferedImage = ImageIO.read(file);
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(writableImage);
      }
    } catch (IOException e) {
        e.printStackTrace();
    }    
  }
  
  /**
   * Invoke an open dialog window for selecting the file you want to load,
   * and returns this new file.
   *
   * @param fileExtension the file extension
   * @return the new file
   */
  private static File fileChooser(String fileExtension) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save image as " + fileExtension + "...");
    FileChooser.ExtensionFilter extFilter
      = new FileChooser.ExtensionFilter(fileExtension + " (*." + fileExtension + ")", "*." + fileExtension);
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    return file;
  }
}
