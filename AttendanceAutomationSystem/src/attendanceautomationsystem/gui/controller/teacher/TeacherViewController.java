/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.teacher;

import attendanceautomationsystem.gui.controller.LoginViewController;
import attendanceautomationsystem.gui.model.MainModel;
import attendanceautomationsystem.gui.model.TeacherModel;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class TeacherViewController implements Initializable {

    
    private MainModel mainModel;
    private TeacherModel teacherModel;
    
    private final static Logger LOGGER = Logger.getLogger(TeacherViewController.class.getName());
    
    @FXML
    private Label lblTeacherName;
    @FXML
    private BorderPane brdMain;
    @FXML
    private AnchorPane ancMain;
    @FXML
    private Button btnLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showAnimation();
    }    
    
    public void injectModels(MainModel model, TeacherModel tModel)
    {
        this.mainModel = model;
        this.teacherModel = tModel;
        lblTeacherName.setText(model.getUserName());
    }
    
    private void showAnimation()
    {
        FadeTransition animation = AnimationUtil.createDelayedDisplayAnimation(brdMain);
        animation.play();

    }
    
    public void loadCoursesView()
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
            controller.injectModel(teacherModel);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    private void clickLogout(ActionEvent event) {
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
