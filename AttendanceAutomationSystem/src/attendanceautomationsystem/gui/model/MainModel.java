/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.model;

import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.be.Teacher;
import attendanceautomationsystem.be.User;
import attendanceautomationsystem.be.User.UserType;
import attendanceautomationsystem.bll.BLLFacade;
import attendanceautomationsystem.util.exception.AttendanceException;

/**
 *
 * @author Acer
 */
public class MainModel 
{
    
    private BLLFacade facade;
    private User loggedInUser;
    
    
    public MainModel(BLLFacade facade)
    {
        this.facade = facade;
    }
    
    public void loginUser(String username, String password) throws AttendanceException
    {
        loggedInUser =  facade.getUser(username, password);
    }
    
    public StudentModel instantiateStudentModel() throws AttendanceException
    {
        Student student = facade.getStudent(loggedInUser);
        return new StudentModel(facade, student);
    }
    
    public TeacherModel instantiateTeacherModel() throws AttendanceException
    {
        Teacher teacher = facade.getTeacher(loggedInUser);
        return new TeacherModel(facade, teacher);
    }
    
    public void logoutUser()
    {
        loggedInUser = null;
    }
    
    public String getUserName()
    {
        return loggedInUser.getName();
    }
    
    public UserType getLoggedInUserType()
    {
        return loggedInUser.getType();
    }
    
}
