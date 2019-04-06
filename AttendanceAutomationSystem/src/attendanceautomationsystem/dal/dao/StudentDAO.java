/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.dal.dao;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.be.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class StudentDAO {
    
    public Student getStudent(Connection connection, User user) throws SQLException
    {
        String sqlStatement = "SELECT * FROM Student WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, user.getId());
        ResultSet rs = statement.executeQuery();
        if(!rs.next())
        {
            return null;
        }
        else
        {
            int courseId = rs.getInt("CourseID");
            Student student = new Student(user.getId(), user.getName(), courseId);
            return student;
        }      
    }       
    
    public List<Student> getCourseStudents(Connection connection, Course course) throws SQLException
    {
        String sql = "SELECT c.ID CourseID, s.ID StudentID, u.Name StudentName FROM Course c " +
            "INNER JOIN Student s ON c.ID = s.CourseID " +
            "INNER JOIN Users u ON s.ID = u.ID " +
            "WHERE c.ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, course.getId());
        ResultSet rs = statement.executeQuery();
        List<Student> courseStudents = new ArrayList();
        while(rs.next())
        {
            int studentId = rs.getInt("StudentID");
            String studentName = rs.getString("StudentName");
            Student student = new Student(studentId, studentName, course.getId());
            courseStudents.add(student);
        }
        return courseStudents;
    }
    
    public List<LocalDate> getStudentPresence(Connection connection, Student student) throws SQLException
    {
        String sqlStatement = "SELECT Date FROM StudentPresence WHERE StudentID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, student.getId());
        ResultSet rs = statement.executeQuery();
        List<LocalDate> presenceDays = new ArrayList();
        while(rs.next())
        {
            LocalDate presence = rs.getDate("Date").toLocalDate();
            presenceDays.add(presence);
        }
        return presenceDays;
    }
    
    public boolean isAttendanceMarked(Connection connection, Student student, LocalDate date) throws SQLException
    {
        String sql = "SELECT * FROM StudentPresence WHERE StudentID=? AND Date = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, student.getId());
        statement.setDate(2, Date.valueOf(date));
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }
    
    public void markAttendance(Connection connection, Student student, LocalDate date) throws SQLException
    {
        String sql = "INSERT INTO StudentPresence (StudentID,Date) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, student.getId());
        statement.setDate(2, Date.valueOf(date)); 
        statement.execute();
    }
    
}
