/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.student;

import attendanceautomationsystem.bll.BLLFacade.AttendanceStatus;
import attendanceautomationsystem.gui.model.StudentModel;
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
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class StudentAttendanceViewController implements Initializable {

    private StudentModel model;
    private ErrorDisplayer error;
    
    private final static Logger LOGGER = Logger.getLogger(StudentAttendanceViewController.class.getName());
    
    @FXML
    private StackPane stcMain;
    @FXML
    private Label lblCurrentDate;
    
    public StudentAttendanceViewController()
    {
        error = new ErrorDisplayer();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void injectModel(StudentModel model) throws IOException
    {
        this.model = model;
        loadAttendanceInformation();
    }
    
    private void loadAttendanceInformation() throws IOException
    {
        lblCurrentDate.setText(model.getCurrentDateInString());
        FXMLLoader fxmlLoader = null;
        
        try
        {
            AttendanceStatus status = model.updateStudentsAttendance();

            switch(status)
            {
                case SUBMITTED:     fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student"
                                        + "/attendance/StudentAttendanceSubmitted.fxml"));
                                    break;

                case NOT_SUBMITTED: fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student"
                                        + "/attendance/StudentAttendanceNotSubmitted.fxml"));
                                    break;

                case NOT_RECORDED:  fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student"
                                        + "/attendance/StudentAttendanceNotRecorded.fxml"));
                                    break;
            }

            Parent root = (Parent) fxmlLoader.load();
            stcMain.getChildren().add(root);
        }
        catch(AttendanceException ex)
        {
            switch(ex.getErrorType())
            {
                case NO_INTERNET_CONNECTION: showAttendanceCannotBeCheckedView();
                                             break;
                                             
                case NO_DATA_ACCESS:         showAttendanceCannotBeCheckedView();
                                             break;
                
                default:                      error.showErrorWindow(ex.getMessage());
                                              break;
            }
        }
    }
    
    private void showAttendanceCannotBeCheckedView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/student"
                + "/attendance/StudentAttendanceCannotBeChecked.fxml"));
             Parent root = (Parent) fxmlLoader.load();
             stcMain.getChildren().add(root);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
}
