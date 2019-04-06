/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.be;

import java.util.List;

/**
 *
 * @author Acer
 */
public class Teacher extends User {
    
    private List<Course> courses;
    
    public Teacher(int id, String name)
    {
        super(id,name, UserType.TEACHER);
    }
    
    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }
    
    public List<Course> getCourses()
    {
        return courses;
    }
    
}
