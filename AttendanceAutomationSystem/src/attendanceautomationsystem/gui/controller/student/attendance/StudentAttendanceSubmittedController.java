/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller.student.attendance;

import attendanceautomationsystem.gui.util.AnimationUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class StudentAttendanceSubmittedController implements Initializable {

    @FXML
    private ImageView imgAttendance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showAnimation();
    }    
    
    
    public void showAnimation()
    {
        ScaleTransition animation = AnimationUtil.createEnlargmentAnimation(imgAttendance);
        animation.play();
    } 

    
}
