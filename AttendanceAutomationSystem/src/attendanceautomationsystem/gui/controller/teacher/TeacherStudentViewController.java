/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.teacher;

import attendanceautomationsystem.gui.model.TeacherStudentModel;
import attendanceautomationsystem.gui.util.AnimationUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kiddo
 */
public class TeacherStudentViewController implements Initializable {

    private TeacherStudentModel model;
    
    @FXML
    private AnchorPane ancMain;
    @FXML
    private Label lblStudentName;
    @FXML
    private ListView<String> lstAbsence;
    @FXML
    private ListView<String> lstPresence;
    @FXML
    private ToggleGroup tab;

    /**
     * Initializes the controller class.
     */
    @Override       
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> showStageWithAnimation());
        setToggleButtons();
    }
    
    private void setToggleButtons()
    {        
        tab.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
            {
                oldVal.setSelected(true);
            }
        });
    }
    
    public void injectModel(TeacherStudentModel model)
    {
        this.model = model;
        initializeView();
    }
    
    private void initializeView()
    {
        lblStudentName.setText(model.getStudentName());
        lstAbsence.setItems(model.getAbsenceDays());
        lstPresence.setItems(model.getPresenceDays());
    }
    
    @FXML
    private void clickClose(ActionEvent event) 
    {                 
        Stage stage = (Stage) ancMain.getScene().getWindow();
        Timeline timeline = AnimationUtil.createVerticalSlideWithFadingOut(stage);
        timeline.setOnFinished(e -> {
            stage.close();
	});
        timeline.play();
    }


    
    public void showStageWithAnimation()
    {
        Stage stage = (Stage) ancMain.getScene().getWindow();
        Timeline timeline = AnimationUtil.createVerticalSlideWithFadingIn(stage);
        timeline.play();
    }      

    @FXML
    private void clickAbsence(ActionEvent event) {
        lstPresence.setVisible(false);
        lstAbsence.setVisible(true);
        
    }

    @FXML
    private void clickPresence(ActionEvent event) {
        lstAbsence.setVisible(false);
        lstPresence.setVisible(true);
    }
    
}

