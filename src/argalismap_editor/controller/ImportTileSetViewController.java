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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alexandre 'ROKH' MAILLIU
 */
public class ImportTileSetViewController implements Initializable {

  private final static int VALID_FIELDS = 0;
  private final static int EMPTY_FIELDS = 1;
  private final static int NOT_POSITIVE_NUMBER_FIELDS = 2;
  
  /**
   * the list of values for the ChoiceBox_importTileSetFileType
   */
  ObservableList<String> fileTypeList = FXCollections.observableArrayList("png", "gif", "jpg", "jpeg");
  
  @FXML
  private TextField TextField_tileWidthInPixel, TextField_tileHeightInPixel;
  
  @FXML
  private ChoiceBox ChoiceBox_importTileSetFileType;
  
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ChoiceBox_importTileSetFileType.setValue("png");
    ChoiceBox_importTileSetFileType.setItems(fileTypeList);
  }
  
  @FXML
  private void handleImportTileSetButtonAction(ActionEvent event) {
    
    int fieldsValidity = checkFieldValidity();
    
    if(fieldsValidity != VALID_FIELDS) {
      
      Alert alert;
      if(fieldsValidity == EMPTY_FIELDS) {
          alert = new Alert(Alert.AlertType.WARNING, "All the fields aren't filled !", ButtonType.OK, ButtonType.CANCEL);
      } else { // NOT_POSITIVE_NUMBER_FIELDS
          alert = new Alert(Alert.AlertType.WARNING, "All the fields aren't positive number !", ButtonType.OK, ButtonType.CANCEL);
      }
      
      alert.showAndWait();
      if (alert.getResult() == ButtonType.OK) {
        return;
      }
      if (alert.getResult() == ButtonType.CANCEL) {
        closeStage();
        return;
      }
    }
    
    // from here all the parameters are ok
    String tileWidthInPixel = TextField_tileWidthInPixel.getText();
    String tileHeightInPixel = TextField_tileHeightInPixel.getText();
    String fileType = (String) ChoiceBox_importTileSetFileType.getValue();
    int tileWidth = Integer.parseInt(tileWidthInPixel);
    int tileHeight = Integer.parseInt(tileHeightInPixel);
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/argalismap_editor/view/mainView.fxml"));
    try {
      loader.load();
    } catch (IOException e) {
      Logger.getLogger(NewMapViewController.class.getName()).log(Level.SEVERE, null, e);
    }
    MainViewController mainViewController = loader.getController();
    
    // if tileSet file loading is correctly done, then close the stage
    if(mainViewController.importTileSet(tileWidth, tileHeight, fileType)) {
      closeStage();
    }
  }

  /**
   * check if the fields are valid or not
   * 
   * @return true if the fields are not valid, else true
   */
  private int checkFieldValidity() {
    if(TextField_tileWidthInPixel.getText().isEmpty()) return EMPTY_FIELDS;
    if(TextField_tileHeightInPixel.getText().isEmpty()) return EMPTY_FIELDS;
    // check if its not a positive number
    if(!TextField_tileWidthInPixel.getText().matches("\\d*")) return NOT_POSITIVE_NUMBER_FIELDS;
    if(!TextField_tileHeightInPixel.getText().matches("\\d*")) return NOT_POSITIVE_NUMBER_FIELDS;
    return VALID_FIELDS;
  }
  
  /**
   * close this stage
   */
  private void closeStage() {
    // get a handle to the stage
    Stage stage = (Stage) ChoiceBox_importTileSetFileType.getScene().getWindow();
    stage.close();
  }
}
