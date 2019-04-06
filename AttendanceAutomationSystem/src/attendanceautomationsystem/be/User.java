/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.be;

/**
 *
 * @author Acer
 */
public class User {
    
    public enum UserType {
        STUDENT, TEACHER
    }
    
    private int id;
    private String name;
    private UserType type;
    
    public User(int id, String name, UserType type)
    {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId()
    {
        return id;
    }
    
    public String getName() 
    {
        return name;
    }

    public UserType getType() 
    {
        return type;
    }
    
    
}
