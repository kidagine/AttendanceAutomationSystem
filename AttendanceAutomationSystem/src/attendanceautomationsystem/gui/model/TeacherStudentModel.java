/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.model;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.bll.BLLFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public class TeacherStudentModel {
    
    private Student student;
    private Course studentsCourse;
    private BLLFacade facade;
    private ObservableList<String> absence;
    private ObservableList<String> presence;
    
    public TeacherStudentModel(BLLFacade facade, Student student, Course studentsCourse)
    {
        this.facade = facade;
        this.student = student;
        this.studentsCourse = studentsCourse;
        initializeStudentsAbsenceAndPresence();
    }
    
    private void initializeStudentsAbsenceAndPresence()
    {
        absence = FXCollections.observableArrayList(facade.getAbsenceAsStrings(student, studentsCourse));
        presence = FXCollections.observableArrayList(facade.getPresenceAsStrings(student, studentsCourse));
    }
    
    public ObservableList<String> getAbsenceDays()
    {
        return absence;
    }
    
    public ObservableList<String> getPresenceDays()
    {
        return presence;
    }
    
    public String getStudentName()
    {
        return student.getName();
    }
    
}
