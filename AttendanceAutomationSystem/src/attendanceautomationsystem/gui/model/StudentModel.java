/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.model;

import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.bll.BLLFacade;
import attendanceautomationsystem.bll.BLLFacade.AttendanceStatus;
import attendanceautomationsystem.util.exception.AttendanceException;

/**
 *
 * @author Acer
 */
public class StudentModel {
    
    private Student student;
    private BLLFacade facade;
    
    public StudentModel(BLLFacade facade, Student student)
    {
        this.facade = facade;
        this.student = student;
    }
    
    public AttendanceStatus updateStudentsAttendance() throws AttendanceException
    {
        return facade.updateStudentsAttendance(student);
    }
    
    public String getCurrentDateInString()
    {
        return facade.getCurrentDateInString();
    }
    
}
