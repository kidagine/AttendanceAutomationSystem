/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.model;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.be.Teacher;
import attendanceautomationsystem.bll.BLLFacade;
import attendanceautomationsystem.util.exception.AttendanceException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public class TeacherModel {
    
    private BLLFacade facade;
    private Teacher teacher;
    private Course selectedCourse;
    private ObservableList<Student> selectedCourseStudents;
    
    public TeacherModel(BLLFacade facade, Teacher teacher)
    {
        this.facade = facade;
        this.teacher = teacher;
    }
    
    public TeacherStudentModel instantiateTeacherStudentModel(Student student)
    {
        return new TeacherStudentModel(facade, student, selectedCourse);
    }
    
    public List<Course> getTeacherCourses()
    {
        return teacher.getCourses();
    }
    
    public void setSelectedCourse(Course course) throws AttendanceException
    {
        this.selectedCourse = course;
        selectedCourseStudents = FXCollections.observableArrayList(facade.getCourseStudents(selectedCourse));
    }
    
    public ObservableList<Student> getCourseStudents()
    {
        return selectedCourseStudents;
    }
    
    public int getPercentageOfPresence(Student student)
    {
        return facade.getPercentageOfPresence(student, selectedCourse);
    }
    
}
