/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.dal.dao;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Teacher;
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
public class CourseDAO {
    
    public boolean isSchoolDay(Connection connection, int courseId, LocalDate date) throws SQLException
    {
        String sql = "SELECT * FROM CourseDate WHERE CourseID = ? AND Date = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, courseId);
        statement.setDate(2, Date.valueOf(date));
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }
    
    public List<Course> getTeacherCourses(Connection connection, Teacher teacher) throws SQLException
    {
        String sqlStatement = "SELECT c.ID, c.Name FROM TeacherCourse tc " +
            "INNER JOIN Course c ON tc.CourseID = c.ID " +
            "WHERE TeacherID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, teacher.getId());
        ResultSet rs = statement.executeQuery();
        List<Course> teacherCourses = new ArrayList();
        while(rs.next())
        {
            int courseId = rs.getInt("ID");
            String courseName = rs.getString("Name");
            teacherCourses.add(new Course(courseId, courseName));
        }
        return teacherCourses;
    }
    
    public List<LocalDate> getCourseSchoolDays(Connection connection, Course course) throws SQLException
    {
        String sql = "SELECT c.ID CourseID, cd.Date FROM Course c " +
        "INNER JOIN CourseDate cd ON c.ID = cd.CourseID " +
        "WHERE c.ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, course.getId());
        ResultSet rs = statement.executeQuery();
        List<LocalDate> schoolDays = new ArrayList();
        while(rs.next())
        {
            LocalDate schoolDate = rs.getDate("Date").toLocalDate();
            schoolDays.add(schoolDate);
        }
        return schoolDays;     
    }
}
