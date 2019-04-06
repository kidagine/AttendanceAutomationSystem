/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.be;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public class Student extends User {
    
    private int courseId;
    private List<LocalDate> presenceDays;
    
    public Student(int id, String name, int courseId)
    {
        super(id, name, UserType.STUDENT);
        this.courseId = courseId;
    }
    
    public void setPresenceDays(List<LocalDate> presenceDays)
    {
        this.presenceDays = presenceDays;
    }
    
    public List<LocalDate> getPresenceDays()
    {
        return presenceDays;
    }
    
    public void addPresence(LocalDate date)
    {
        presenceDays.add(date);
    }
    
    public int getCourseId()
    {
        return courseId;
    }
    
}
