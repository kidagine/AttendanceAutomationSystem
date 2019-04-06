/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller;

import attendanceautomationsystem.gui.controller.student.StudentViewController;
import attendanceautomationsystem.be.User.UserType;
import attendanceautomationsystem.gui.controller.teacher.TeacherViewController;
import attendanceautomationsystem.gui.model.MainModel;
import attendanceautomationsystem.gui.model.StudentModel;
import attendanceautomationsystem.gui.model.TeacherModel;
import attendanceautomationsystem.gui.util.ErrorDisplayer;
import attendanceautomationsystem.util.exception.AttendanceException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class MainViewController implements Initializable {

    private final static Logger LOGGER = Logger.getLogger(MainViewController.class.getName());
    private MainModel model;
    private ErrorDisplayer error;
    
    @FXML
    private AnchorPane ancMain;
    
    public MainViewController()
    {
        error = new ErrorDisplayer();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void injectModel(MainModel model)
    {
        this.model = model;
        displayCorrectView();
    }
    
    private void displayCorrectView()
    {
        try
        {
            UserType userType = model.getLoggedInUserType();
            if(userType == UserType.STUDENT)
            {
                StudentModel sModel = model.instantiateStudentModel();
                showStudentView(sModel);
            }
            else
            {
                TeacherModel tModel = model.instantiateTeacherModel();
                showTeacherView(tModel);
            }
        }
        catch(AttendanceException ex)
        {
            error.showErrorWindow(ex.getMessage());
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    private void showTeacherView(TeacherModel tModel) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/teacher/TeacherView.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Scene currentScene = ancMain.getScene(); 
        currentScene.setRoot(root);     
        
        TeacherViewController controller = fxmlLoader.getController();
        controller.injectModels(model, tModel);
        controller.loadCoursesView();
    }
    
    private void showStudentView(StudentModel sModel) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student/StudentView.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Scene currentScene = ancMain.getScene();
        currentScene.setRoot(root);
        
        StudentViewController controller = fxmlLoader.getController();
        controller.injectModels(model, sModel);
        controller.loadAttendanceView();
    }
    
}
