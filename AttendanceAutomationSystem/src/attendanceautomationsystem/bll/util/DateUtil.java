/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.bll.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Acer
 */
public class DateUtil {
    
    private static String[] monthNames = {"January", "February", "March", "April", "May", 
        "June", "July", "August", "September", "October", "November", "December"};
    
    private static final String DATE_FORMAT = "dd MMMM yyyy";
    
    public static String getDateInString(LocalDate date)
    {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
    
    public static String getCurrentDateInString()
    {
        LocalDate date = LocalDate.now();
        return getDateInString(date);
    }
    
    public static LocalDate getCurrentDate()
    {
        return LocalDate.now();    
    }
    
}
