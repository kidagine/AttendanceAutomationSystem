/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.bll.util;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class AttendanceUtil {
    
    public static int getPercentageOfPresence(Student student, Course course, LocalDate currentDate)
    {
        double numberOfPresenceDays = 0;
        double numberOfSchoolDays = 0;
        for(LocalDate date : course.getSchoolDays())
        {
            if(!date.isAfter(currentDate))
            {
                numberOfSchoolDays++;
            }
            if(student.getPresenceDays().contains(date))
            {
                numberOfPresenceDays++;
            }
        }
        return (int) ((numberOfPresenceDays / numberOfSchoolDays)*100);
    }
    
    public static List<LocalDate> getAbsenceDays(Student student, Course course, LocalDate currentDate)
    {
        List<LocalDate> absence = new ArrayList();
        for(LocalDate date : course.getSchoolDays())
        {
            if(!student.getPresenceDays().contains(date) && !date.isAfter(currentDate))
            {
                absence.add(date);
            }
        }
        return absence;
    }
    
}
