/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.bll.util;

import attendanceautomationsystem.util.exception.AttendanceException;
import attendanceautomationsystem.util.exception.AttendanceException.ErrorType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Acer
 */
public class ConnectionValidator {
    
    public static final String EASV_GATEWAY = "10.176.160.1";
    public static final String HOME_GATEWAY = "100.80.128.1";
    
    public static boolean isConnectedToSchoolNetwork() throws AttendanceException
    {
        String defaultGateway = getDefaultGateway();
        if(defaultGateway == null)
        {
            throw new AttendanceException(ErrorType.NO_INTERNET_CONNECTION, "No Wi-Fi connection detected");
        }
        else if(defaultGateway.equals(EASV_GATEWAY)) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }
    
    private static String getDefaultGateway() 
    {
        try 
        {
            Process process = Runtime.getRuntime().exec("ipconfig");

            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) 
            {
                String line;
                while ((line = bufferedReader.readLine()) != null) 
                {
                    if(line.contains("Wi-Fi") || line.contains("Wi-Fi"))
                    {
                        while ((line = bufferedReader.readLine()) != null)
                        {
                            if(line.trim().startsWith("Default Gateway")) 
                            {
                                String ipAddress = line.substring(line.indexOf(":") + 1).trim();
                                return ipAddress;
                            }
                        }
                    }
                    
                }
                return null;
            }
        } 
        catch (IOException e) 
        {
            return null;
        }
    }    
}
