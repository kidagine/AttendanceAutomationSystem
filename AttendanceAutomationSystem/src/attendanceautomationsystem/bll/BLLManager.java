    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.bll;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.be.Teacher;
import attendanceautomationsystem.be.User;
import attendanceautomationsystem.bll.util.AttendanceUtil;
import attendanceautomationsystem.bll.util.ConnectionValidator;
import attendanceautomationsystem.bll.util.DateUtil;
import attendanceautomationsystem.bll.util.PasswordHashGenerator;
import attendanceautomationsystem.dal.DALFacade;
import attendanceautomationsystem.util.exception.AttendanceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements BLLFacade{
    
    private DALFacade facade;

    public BLLManager(DALFacade facade)
    {
        this.facade = facade;
    }
    
    @Override
    public User getUser(String username, String password) throws AttendanceException
    {
        String hashedPassword = PasswordHashGenerator.getHashedPassword(password);
        return facade.getUser(username, hashedPassword);
    }
    
    @Override
    public Student getStudent(User user) throws AttendanceException 
    {
        return facade.getStudent(user);
    }

    @Override
    public Teacher getTeacher(User user) throws AttendanceException 
    {
        return facade.getTeacher(user);
    }

    @Override
    public AttendanceStatus updateStudentsAttendance(Student student) throws AttendanceException {
        LocalDate currentDate = DateUtil.getCurrentDate();
        if(facade.isSchoolDay(student.getCourseId(), currentDate))
        {
            if(facade.isAttendanceMarked(student, currentDate))
            {
                return AttendanceStatus.SUBMITTED;
            }
            else
            {
                if(ConnectionValidator.isConnectedToSchoolNetwork())
                {
                    facade.markAttendance(student, currentDate);
                    student.addPresence(currentDate);
                    return AttendanceStatus.SUBMITTED;
                }
                else
                {
                    return AttendanceStatus.NOT_SUBMITTED;
                }
            }
        }
        else
        {
            return AttendanceStatus.NOT_RECORDED;
        }
    }

    @Override
    public String getCurrentDateInString() 
    {
        return DateUtil.getCurrentDateInString();
    }

    @Override
    public List<Student> getCourseStudents(Course course) throws AttendanceException {
        return facade.getCourseStudents(course);
    }

    @Override
    public int getPercentageOfPresence(Student student, Course course) {
        LocalDate currentDate = DateUtil.getCurrentDate();
        return AttendanceUtil.getPercentageOfPresence(student, course, currentDate);
    }

    @Override
    public List<String> getAbsenceAsStrings(Student student, Course course) {
        LocalDate currentDate = DateUtil.getCurrentDate();
        List<LocalDate> absence = AttendanceUtil.getAbsenceDays(student, course, currentDate);
        List<String> absenceAsStrings = new ArrayList();
        for(LocalDate date : absence)
        {
            absenceAsStrings.add(DateUtil.getDateInString(date));
        }
        return absenceAsStrings;
    }

    @Override
    public List<String> getPresenceAsStrings(Student student, Course course) {
        LocalDate currentDate = DateUtil.getCurrentDate();
        List<LocalDate> presence = student.getPresenceDays();
        List<String> presenceAsStrings = new ArrayList();
        for(LocalDate date : presence)
        {
            presenceAsStrings.add(DateUtil.getDateInString(date));
        }
        return presenceAsStrings;
        
    }
    
}
