/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem;

import attendanceautomationsystem.bll.BLLManager;
import attendanceautomationsystem.dal.DALManager;
import attendanceautomationsystem.gui.controller.LoginViewController;
import attendanceautomationsystem.gui.model.MainModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Acer
 */
public class AttendanceAutomationSystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/LoginView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        
        LoginViewController controller = fxmlLoader.getController();
        controller.injectModel(new MainModel(new BLLManager(new DALManager())));
        
        Scene scene = new Scene(root, 1000, 600, Color.web("#f4f4f4"));
        stage.setTitle("EASV Attendance System");
        Image icon = new Image(getClass().getResourceAsStream("/attendanceautomationsystem/gui/images/EASYDVEST.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
