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
public class Course {
    
    private int id;
    private String name;
    private List<LocalDate> schoolDays;
    
    public Course(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public void setSchoolDays(List<LocalDate> days)
    {
        this.schoolDays = days;
    }
    
    public List<LocalDate> getSchoolDays()
    {
        return schoolDays;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
}
