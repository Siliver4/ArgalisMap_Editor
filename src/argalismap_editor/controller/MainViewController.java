/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argalismap_editor.controller;

import argalismap_editor.model.paint.PaintManager;
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
    
    PaintManager pM;
    
    @FXML
    private Label label;
    
    @FXML
    private MenuItem menuHelpAbout;
    
    @FXML
    private ImageView imageView1;
    
    @FXML
    private Canvas canvas1;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pM = new PaintManager(canvas1, imageView1);
        plugFunction();
    }    
    
    public void plugFunction() {
        pM.setCanvasDimensions(4, 4, 107, 107);
        pM.setImageViewDimension(107, 107);
    }
    
    @FXML
    private void openQuitView(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to quit ?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }
    
    @FXML
    private void openNewMapView(ActionEvent event) {
        openView("newMapView","New Map...");
    }
    
    @FXML
    private void openAboutView(ActionEvent event) {
        openView("aboutView","About");
    }
    
    /**
     * open an fxml file as a new window, from a fxmlFileName, and gives this new window 
     * the name viewTitle
     * @param fxmlFileName the name of the fxml file
     * @param viewTitle  the title of the new window
     */
    private void openView(String fxmlFileName, String viewTitle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/argalismap_editor/view/"+fxmlFileName+".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.setTitle(viewTitle);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * save the canvasDrawing content as an '.argalis' file, Meaning it contains
     * all the tile values as integers.
     */
    @FXML
    public void saveToARGALIS() {
        pM.saveToARGALIS();
    }
    
    /**
     * save the canvasDrawing content as an '.png' file.
     */
    @FXML
    public void saveToPNG() {
        pM.saveToPNG();
    }
    
    /**
     * save the canvasDrawing content as an '.gif' file.
     */
    @FXML
    public void saveToGIF() {
        pM.saveToGIF();
    }
    
    /**
     * save the canvasDrawing content as an '.jpg' file.
     */
    @FXML
    public void saveToJPG() {
        pM.saveToJPG();
    }
    
    /**
     * save the canvasDrawing content as an '.jpeg' file.
     */
    @FXML
    public void saveToJPEG() {
        pM.saveToJPEG();
    }
}
