/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.dal;

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
public interface DALFacade {
    
    User getUser(String username, String password) throws AttendanceException;
    
    Student getStudent(User user) throws AttendanceException;
    
    Teacher getTeacher(User user) throws AttendanceException;
    
    boolean isAttendanceMarked(Student student, LocalDate date) throws AttendanceException;
    
    boolean isSchoolDay(int courseId, LocalDate date) throws AttendanceException;
    
    List<Student> getCourseStudents(Course course) throws AttendanceException;
    
    void markAttendance(Student student, LocalDate date) throws AttendanceException;
    
}
