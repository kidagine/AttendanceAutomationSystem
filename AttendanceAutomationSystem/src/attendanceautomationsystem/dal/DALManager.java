/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.dal;

import attendanceautomationsystem.be.Course;
import attendanceautomationsystem.be.Student;
import attendanceautomationsystem.be.Teacher;
import attendanceautomationsystem.be.User;
import attendanceautomationsystem.dal.dao.CourseDAO;
import attendanceautomationsystem.dal.dao.StudentDAO;
import attendanceautomationsystem.dal.dao.TeacherDAO;
import attendanceautomationsystem.dal.dao.UserDAO;
import attendanceautomationsystem.util.exception.AttendanceException;
import attendanceautomationsystem.util.exception.AttendanceException.ErrorType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class DALManager implements DALFacade{

    private static final String DB_PROP_FILE_PATH = "data/connectionInfo.settings";
    private final static Logger LOGGER = Logger.getLogger(DALManager.class.getName());
    
    private DbConnectionProvider connector;
    private UserDAO userDao;
    private TeacherDAO teacherDao;
    private StudentDAO studentDao;
    private CourseDAO courseDao;
    
    public DALManager()
    {
        try 
        {
            connector = new DbConnectionProvider(DB_PROP_FILE_PATH);
            userDao = new UserDAO();
            teacherDao = new TeacherDAO();
            studentDao = new StudentDAO();
            courseDao = new CourseDAO();
        } 
        catch (IOException ex) 
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }    
    
    @Override
    public User getUser(String username, String password) throws AttendanceException
    {
        try(Connection connection = connector.getConnection())
        {
            User user =  userDao.getUser(connection, username, password);
            if(user == null)
            {
                throw new AttendanceException(ErrorType.USER_NOT_FOUND, "Cannot find user with given credentials.");
            }
            return user;
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
    }
    
    @Override
    public Student getStudent(User user) throws AttendanceException {
        try(Connection connection = connector.getConnection())
        {
            Student student =  studentDao.getStudent(connection, user);
            if(student == null)
            {
                throw new AttendanceException(ErrorType.USER_NOT_FOUND, "Cannot find student connected to this account");
            }
            else
            {
                List<LocalDate> studentPresence = studentDao.getStudentPresence(connection, student);
                student.setPresenceDays(studentPresence);
                return student;
            }
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
    }

    @Override
    public Teacher getTeacher(User user) throws AttendanceException {
        try(Connection connection = connector.getConnection())
        {
            Teacher teacher =  teacherDao.getTeacher(connection, user);
            if(teacher == null)
            {
                throw new AttendanceException(ErrorType.USER_NOT_FOUND, "Cannot find teacher connected to this account");
            }
            else
            {
                List<Course> courses = courseDao.getTeacherCourses(connection, teacher);
                for(Course c : courses)
                {
                    List<LocalDate> schoolDays = courseDao.getCourseSchoolDays(connection, c);
                    c.setSchoolDays(schoolDays);
                }
                teacher.setCourses(courses);
                return teacher;
            }
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
    }

    @Override
    public boolean isAttendanceMarked(Student student, LocalDate date) throws AttendanceException {
        try(Connection connection = connector.getConnection())
        {
            return studentDao.isAttendanceMarked(connection, student, date);
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
    }

    @Override
    public boolean isSchoolDay(int courseId, LocalDate date) throws AttendanceException {
        try(Connection connection = connector.getConnection())
        {
            return courseDao.isSchoolDay(connection, courseId, date);
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
        
    }

    @Override
    public void markAttendance(Student student, LocalDate date) throws AttendanceException {
        try(Connection connection = connector.getConnection())
        {
            studentDao.markAttendance(connection, student, date);
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
    }

    @Override
    public List<Student> getCourseStudents(Course course) throws AttendanceException {
        try(Connection connection = connector.getConnection())
        {
            List<Student> courseStudents = studentDao.getCourseStudents(connection, course);
            for(Student s : courseStudents)
            {
                List<LocalDate> studentPresence = studentDao.getStudentPresence(connection, s);
                s.setPresenceDays(studentPresence);
            }
            return courseStudents;
        }
        catch(SQLException ex)
        {
            throw new AttendanceException(ErrorType.NO_DATA_ACCESS, "Cannot access the server.", ex);
        }
    }
    
}
