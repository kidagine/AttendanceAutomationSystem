/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.student;

import attendanceautomationsystem.gui.controller.LoginViewController;
import attendanceautomationsystem.gui.model.MainModel;
import attendanceautomationsystem.gui.model.StudentModel;
import attendanceautomationsystem.gui.util.AnimationUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class StudentViewController implements Initializable {

    private MainModel mainModel;
    private StudentModel studentModel;
    
    private final static Logger LOGGER = Logger.getLogger(StudentViewController.class.getName());
    
    @FXML
    private Label lblStudentName;
    @FXML
    private AnchorPane ancMain;
    @FXML
    private BorderPane brdMain;
    @FXML
    private ToggleGroup tab;
    @FXML
    private Button btnLogout;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setToggleButtons();
        showAnimation();     
    }   
    
    public void injectModels(MainModel model, StudentModel sModel)
    {
        this.mainModel = model;
        this.studentModel = sModel;
        lblStudentName.setText(model.getUserName());
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
    
    private void showAnimation()
    {
        FadeTransition animation = AnimationUtil.createDelayedDisplayAnimation(brdMain);
        animation.play();

    }
    
    @FXML
    private void clickAttendance(ActionEvent event) 
    {
        ancMain.getChildren().clear();
        loadAttendanceView();
    }

    @FXML
    private void clickCalendar(ActionEvent event) 
    {
        ancMain.getChildren().clear();
        loadCalendarView();
    }

    @FXML
    private void clickOverview(ActionEvent event) 
    {
        ancMain.getChildren().clear();
        loadOverviewView();
    }
    
    public void loadAttendanceView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student/StudentAttendanceView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            ancMain.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            StudentAttendanceViewController controller = fxmlLoader.getController();
            controller.injectModel(studentModel);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public void loadCalendarView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student/StudentCalendarView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            ancMain.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public void loadOverviewView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student/StudentOverviewView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            ancMain.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    private void clickLogout(ActionEvent event) throws IOException {
        btnLogout.setDisable(true);
        mainModel.logoutUser();
        FadeTransition animation = AnimationUtil.createFadeAnimation(brdMain);
        animation.setOnFinished((e) -> {
            showLoginView();
        });
        animation.play();
    }
    
    private void showLoginView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/LoginView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            LoginViewController controller = fxmlLoader.getController();
            controller.injectModel(mainModel);


            Scene currentScene = brdMain.getScene();
            currentScene.setRoot(root);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
}
