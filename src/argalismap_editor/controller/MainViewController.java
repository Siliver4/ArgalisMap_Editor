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
package argalismap_editor.controller;

import argalismap_editor.model.paint.PaintManager;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class MainViewController implements Initializable {

  /**
   * the PaintManager who handles the interactions between views, to pick 
   * cropped images from one hand, and then draw them on the second hand.
   */
  PaintManager pM;

  @FXML
  private Label label;

  @FXML
  private MenuItem menuHelpAbout;

  @FXML
  private ImageView imageView1;

  @FXML
  private Canvas canvas1;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    pM = new PaintManager(canvas1, imageView1);
    plugFunction();
  }

  /**
   * temporary function to plug values before we got the user to do it manually 
   * from the GUI
   */
  public void plugFunction() {
    pM.setCanvasDimensions(4, 4, 107, 107);
    pM.setImageViewDimension(107, 107);
  }
  
  /**
   * open the quit view 
   * 
   * @param event the onClick event 
   */
  @FXML
  private void openQuitView(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to quit ?", ButtonType.YES, ButtonType.CANCEL);
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      Platform.exit();
    }
  }
  
  /**
   * open the about view 
   * 
   * @param event the onClick event 
   */
  @FXML
  private void openNewMapView(ActionEvent event) {
    openView("newMapView", "New Map...");
    
    /**
     * 
          // check if there is a column number error
          if(canvasDrawing.getColumnNb() != contentReadAsTable.length) {
            Alert alert = new Alert(AlertType.WARNING, "The file does not have a correct column number of element !", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
              return;
            }
          }
     */
  }

  /**
   * open the github/issues tab in the user favorite web browser.
   * 
   * @param event the onClick event
   */
  @FXML
  private void openReportIssue(ActionEvent event) {
    try {
      Desktop.getDesktop().browse(new URI("https://github.com/Siliver4/ArgalisMap_Editor/issues"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * open the about view 
   * 
   * @param event the onClick event 
   */
  @FXML
  private void openAboutView(ActionEvent event) {
    openView("aboutView", "About");
  }

  /**
   * open an fxml file as a new window, from a fxmlFileName, and gives this new
   * window the name viewTitle
   *
   * @param fxmlFileName the name of the fxml file
   * @param viewTitle the title of the new window
   */
  private void openView(String fxmlFileName, String viewTitle) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/argalismap_editor/view/" + fxmlFileName + ".fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.setTitle(viewTitle);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * load an argalis file content, which is a integer table, and draws it to 
   * the canvasDrawing's canvas
   */
  public void loadMapFromARGALIS() {
    pM.loadMapFromARGALIS();
  }

  /**
   * load a tileSet from a png file image
   */
  public void loadTileSetImageFromPNG() {
    pM.loadTileSetImageFromPNG();
  }
  
  /**
   * load a tileSet from a gif file image
   */
  public void loadTileSetImageFromGIF() {
    pM.loadTileSetImageFromGIF();
  }
  
  /**
   * load a tileSet from a jpg file image
   */
  public void loadTileSetImageFromJPG() {
    pM.loadTileSetImageFromJPG();
  }
  
  /**
   * load a tileSet from a jpeg file image
   */
  public void loadTileSetImageFromJPEG() {
    pM.loadTileSetImageFromJPEG();
  }
  
  /**
   * save the current map content as an '.argalis' file, Meaning it contains
   * all the tile values as an integer table.
   */
  @FXML
  public void saveMapToARGALIS() {
    pM.saveMapToARGALIS();
  }

  /**
   * save the current map content as an '.png' file.
   */
  @FXML
  public void saveMapToPNG() {
    pM.saveMapToPNG();
  }

  /**
   * save the current map content as an '.gif' file.
   */
  @FXML
  public void saveMapToGIF() {
    pM.saveMapToGIF();
  }

  /**
   * save the current map content as an '.jpg' file.
   */
  @FXML
  public void saveMapToJPG() {
    pM.saveMapToJPG();
  }

  /**
   * save the current map content as an '.jpeg' file.
   */
  @FXML
  public void saveMapToJPEG() {
    pM.saveMapToJPEG();
  }
}
