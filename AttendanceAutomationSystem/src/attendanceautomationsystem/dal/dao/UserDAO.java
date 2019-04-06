/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.dal.dao;

import attendanceautomationsystem.be.User;
import attendanceautomationsystem.be.User.UserType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class UserDAO {
    
    public User getUser(Connection connection, String username, String password) throws SQLException
    {
        String sqlStatement = "SELECT * FROM Users WHERE Username=? AND Password=?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        if(!rs.next())
        {
            return null;
        }
        int id = rs.getInt("ID");
        String name = rs.getString("Name");
        boolean isTeacher = rs.getBoolean("IsTeacher");
        if(isTeacher)
        {
            return new User(id,name,UserType.TEACHER);
        }
        else
        {
            return new User(id,name,UserType.STUDENT);
        }
    }
    
}
