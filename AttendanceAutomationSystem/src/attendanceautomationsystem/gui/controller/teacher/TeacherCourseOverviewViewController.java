/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.teacher;

import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.gui.model.StudentModel;
import attendanceautomationsystem.gui.model.TeacherModel;
import attendanceautomationsystem.gui.model.TeacherStudentModel;
import attendanceautomationsystem.gui.util.AnimationUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class TeacherCourseOverviewViewController implements Initializable {

    private final static Logger LOGGER = Logger.getLogger(TeacherCourseOverviewViewController.class.getName());
    private TeacherModel model;
    
    @FXML
    private AnchorPane ancMain;
    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<Student, String> colStudentName;
    @FXML
    private TableColumn<Student, String> colStudentPresence;
    @FXML
    private AnchorPane ancCourseOverview;
    @FXML
    private StackPane stcDarken;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showSlideAnimation();
    }    
    
    public void injectModel(TeacherModel model)
    {
        this.model = model;
        setTableView();
    }
    
    private void setTableView()
    {
        colStudentName.setCellValueFactory(new PropertyValueFactory("name"));
        colStudentPresence.setCellValueFactory(cell -> new SimpleStringProperty(model.getPercentageOfPresence(cell.getValue()) + "%"));
        colStudentPresence.setSortType(TableColumn.SortType.DESCENDING);
        tblStudents.setItems(model.getCourseStudents());
        tblStudents.getSortOrder().add(colStudentPresence);
    }
    
    private void showSlideAnimation()
    {
        ParallelTransition transition = AnimationUtil.createDisplayWithHorizontalSlideAnimation(1000, 0, ancCourseOverview);
        transition.play();
    }

    @FXML
    private void clickMyCourses(ActionEvent event) throws IOException 
    {    
        ParallelTransition transition = AnimationUtil.createFadeWithHorizontalSlideAnimation(0, 1000, ancCourseOverview);
        transition.setOnFinished((e) -> 
            {
                ancMain.getChildren().remove(ancCourseOverview);
                showCoursesView();
            });
        transition.play();
    }
    
    private void showCoursesView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/teacher/TeacherCoursesView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            ancMain.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            TeacherCoursesViewController controller = fxmlLoader.getController();
            controller.injectModel(model);
            controller.showSlideAnimation();
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    @FXML
    private void clickOnStudents(MouseEvent event) throws IOException
    {
        Student selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
        if(selectedStudent != null && event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
        {
            dimCurrentStage();
            
            TeacherStudentModel sModel = model.instantiateTeacherStudentModel(selectedStudent);
            showTeacherStudentView(sModel);
            
            enlightCurrentStage();
        }
    }
    
 
    private void showTeacherStudentView(TeacherStudentModel model) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/teacher/TeacherStudentView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        TeacherStudentViewController controller = fxmlLoader.getController();
        
        Stage currentStage = (Stage) ancMain.getScene().getWindow();      
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        controller.injectModel(model);
        setTeacherStudentStage(currentStage, stage);
        stage.showAndWait();             
    }
    
    private void dimCurrentStage()
    {
        stcDarken.setVisible(true);
        FadeTransition transition = new FadeTransition(Duration.millis(200), stcDarken);
        transition.setToValue(0.5);
        transition.play();
        
    }
    
    private void enlightCurrentStage()
    {
        stcDarken.setVisible(false);
        FadeTransition transition = new FadeTransition(Duration.millis(200), stcDarken);
        transition.setToValue(0);
        transition.play();
    }
    
    private void setTeacherStudentStage(Stage currentStage, Stage studentStage)
    {
        setTeacherStudentStageCentering(currentStage, studentStage);
        setTeacherStudentStageMode(studentStage);
    }
    
    private void setTeacherStudentStageCentering(Stage currentStage, Stage studentStage)
    {
        double centerXPosition = currentStage.getX() + currentStage.getWidth()/2d;
        double centerYPosition = currentStage.getY() + currentStage.getHeight()/2d;
        studentStage.setOnShowing(e -> studentStage.hide());
        studentStage.setOnShown(e -> {
                studentStage.setX(centerXPosition - studentStage.getWidth()/2d);
                studentStage.setY(centerYPosition - studentStage.getHeight()/2d );
                studentStage.show();});
    }
    
    private void setTeacherStudentStageMode(Stage studentStage)
    {
        studentStage.initStyle(StageStyle.UNDECORATED);
        studentStage.initModality(Modality.APPLICATION_MODAL);
    }
    
}
