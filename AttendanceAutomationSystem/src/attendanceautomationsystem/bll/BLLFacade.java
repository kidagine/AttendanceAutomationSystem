/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.bll;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.be.Teacher;
import attendanceautomationsystem.be.User;
import attendanceautomationsystem.util.exception.AttendanceException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface BLLFacade {
    
    enum AttendanceStatus {
        SUBMITTED, NOT_SUBMITTED, NOT_RECORDED
    }
    
    User getUser(String username, String password) throws AttendanceException;
    
    Student getStudent(User user) throws AttendanceException;
    
    Teacher getTeacher(User user) throws AttendanceException;
    
    AttendanceStatus updateStudentsAttendance(Student student) throws AttendanceException;
    
    List<Student> getCourseStudents(Course course) throws AttendanceException;
    
    int getPercentageOfPresence(Student student, Course course);
    
    List<String> getAbsenceAsStrings(Student student, Course course);
    
    List<String> getPresenceAsStrings(Student student, Course course);
    
    String getCurrentDateInString();
    
}
