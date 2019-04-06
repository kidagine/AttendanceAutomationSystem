/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.controller;

import attendanceautomationsystem.gui.model.MainModel;
import attendanceautomationsystem.gui.util.AnimationUtil;
import attendanceautomationsystem.gui.util.ErrorDisplayer;
import attendanceautomationsystem.util.exception.AttendanceException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class LoginViewController implements Initializable {

    private final static Logger LOGGER = Logger.getLogger(LoginViewController.class.getName());
    
    private MainModel model;
    private ErrorDisplayer error;
    
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXToggleButton tglRememberMe;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private StackPane stcCredentials;
    @FXML
    private StackPane stcErrorMessage;
    @FXML
    private Label lblErrorMessage;
    @FXML
    private StackPane stcMain;

    public LoginViewController()
    {
        error = new ErrorDisplayer();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        setFocus();
        checkRememberMe();
        createTextFieldsListeners();
    }    

    public void injectModel(MainModel model)
    {
        this.model = model;
    }
    
    private void setFocus()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                    setFocusPriority();
                }
            });
    }
    
    private void setFocusPriority()
    {
        if(txtUsername.getText() == null || txtUsername.getText().isEmpty())
        {
            txtUsername.requestFocus();
        }
        else if(txtPassword.getText().isEmpty())
        {
            txtPassword.requestFocus();
        }
        else
        {
            btnLogin.requestFocus();
        }
    }
    
    private void createTextFieldsListeners() 
    {
        txtUsername.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent key) 
                    {
                        if(key.getCode() == KeyCode.ENTER)
                        {
                            txtPassword.requestFocus();
                        }
                    }
                    
                }
            );
        txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent key) 
                    {
                        if(key.getCode() == KeyCode.ENTER)
                        {
                            btnLogin.fire();
                        }
                    }
                    
                }
            );
        btnLogin.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent key) 
                    {
                        if(key.getCode() == KeyCode.ENTER)
                        {
                            btnLogin.fire();
                        }
                    }
                    
                }
            );
    }
    
    @FXML
    private void clickLogin(ActionEvent event) throws IOException 
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                login();
            }          
        });
        t.start();
        
    }
    
    private void login()
    {
        try
        {
            stcMain.setDisable(true);
            model.loginUser(txtUsername.getText(), txtPassword.getText());
            setRememberMe();
            Platform.runLater(() -> showMainView());
        }
        catch(AttendanceException ex)
        {
            switch(ex.getErrorType())
            {
                case USER_NOT_FOUND: Platform.runLater(() -> showUserNotFoundAnimation());
                                     break;

                case NO_DATA_ACCESS: Platform.runLater(() -> showErrorMessage(ex.getMessage()));
                                     break;

                default:             Platform.runLater(() -> error.showErrorWindow(ex.getMessage()));
                                     break;
            }
            stcMain.setDisable(false);
            setFocus();
        }
    }
    
    private void showMainView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/attendanceautomationsystem/gui/view/MainView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene currentScene = btnLogin.getScene(); 
            currentScene.setRoot(root);

            MainViewController controller = fxmlLoader.getController();
            controller.injectModel(model);
        }
        catch(IOException ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
    }
    
        
    private void checkRememberMe()
    {
        Preferences prefs = Preferences.userNodeForPackage(LoginViewController.class);
        String username = prefs.get("username", null);
        String password = prefs.get("password", null);
        if(username != null)
        {
            txtUsername.setText(username);
        }        
        if(password!=null)
        {
            tglRememberMe.setSelected(true);
            txtPassword.setText(password);
        }
        
    }
    
        private void setRememberMe()
    {
        Preferences prefs = Preferences.userNodeForPackage(LoginViewController.class);
        prefs.put("username", txtUsername.getText());
        if(tglRememberMe.isSelected())
        {
            prefs.put("password", txtPassword.getText());
        }
        else
        {
            prefs.remove("password");
        }
    }
        
            
    private void showUserNotFoundAnimation()
    {
        stcErrorMessage.setVisible(true);
        lblErrorMessage.setText("The username or password is incorrect.");

        txtUsername.getStyleClass().add("credentialFieldIncorrect");
        txtPassword.getStyleClass().add("credentialFieldIncorrect");
        SequentialTransition transition = AnimationUtil.createShakingAnimation(stcCredentials);
        transition.play();
    }
    
    private void showErrorMessage(String message)
    {
        stcErrorMessage.setVisible(true);
        lblErrorMessage.setText(message);
    }
    
}
