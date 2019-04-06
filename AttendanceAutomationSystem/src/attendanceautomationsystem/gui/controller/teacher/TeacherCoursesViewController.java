/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.teacher;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.gui.model.TeacherModel;
import attendanceautomationsystem.gui.util.AnimationUtil;
import attendanceautomationsystem.gui.util.ErrorDisplayer;
import attendanceautomationsystem.util.exception.AttendanceException;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class TeacherCoursesViewController implements Initializable {
    
    private final static Logger LOGGER = Logger.getLogger(TeacherCoursesViewController.class.getName());
    
    private HashMap<JFXButton, Course> courses;
    private TeacherModel model;
    private ErrorDisplayer error;

    @FXML
    private GridPane grdCourses;
    @FXML
    private ScrollPane scrCourses;
    @FXML
    private StackPane stcCourses;
    @FXML
    private AnchorPane ancMain;
    
    public TeacherCoursesViewController()
    {
        error = new ErrorDisplayer();
        courses = new HashMap();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void showSlideAnimation()
    {
        ParallelTransition transition = AnimationUtil.createDisplayWithHorizontalSlideAnimation(-1000, 0, ancMain);
        transition.play();
    }
    
    public void injectModel(TeacherModel model)
    {
        this.model = model;
        loadCourses();
    }
    
    private void loadCourses()
    {
        List<Course> teacherCourses = model.getTeacherCourses();
        for(int i = 0; i < teacherCourses.size(); i++)
        {
            JFXButton button = new JFXButton();
            button.getStyleClass().add("buttonCourse");
            button.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setText(teacherCourses.get(i).getName());
            button.setOnAction((e) -> clickOnCourse(e));
            courses.put(button, teacherCourses.get(i));
            grdCourses.add(button, i % 3, i / 3);
        }
        if (teacherCourses.size() <= 6)
        {
            scrCourses.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
    }
    
    private void clickOnCourse(ActionEvent event)
    {
        try
        {
            JFXButton clickedButton = (JFXButton) event.getSource();
            Course selectedCourse = courses.get(clickedButton);
            model.setSelectedCourse(selectedCourse);
            ParallelTransition transition = AnimationUtil.createFadeWithHorizontalSlideAnimation(0, -1000, stcCourses);
            transition.setOnFinished((e) -> 
                {
                    ancMain.getChildren().remove(stcCourses);
                    showCourseOverviewView();
                });
            transition.play();
        }
        catch(AttendanceException ex)
        {
            error.showErrorWindow(ex.getMessage());
        }
    }
    
    private void showCourseOverviewView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/teacher/TeacherCourseOverviewView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            ancMain.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            
            TeacherCourseOverviewViewController controller = fxmlLoader.getController();
            controller.injectModel(model);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
}
