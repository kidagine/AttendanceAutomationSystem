/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.dal.dao;

import attendanceautomationsystem.be.Teacher;
import attendanceautomationsystem.be.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class TeacherDAO {
    
    public Teacher getTeacher(Connection connection, User user) throws SQLException
    {
        String sqlStatement = "SELECT * FROM Teacher WHERE Teacher.ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, user.getId());
        ResultSet rs = statement.executeQuery();
        if(!rs.next())
        {
            return null;                      
        }
        return new Teacher(user.getId(), user.getName());
    }
    
}
