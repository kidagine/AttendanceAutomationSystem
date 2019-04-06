/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.bll.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class PasswordHashGenerator {
    
    private final static Logger LOGGER = Logger.getLogger(PasswordHashGenerator.class.getName());
    
    public static String getHashedPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //Initialize Hashing object
            byte [] encoded =  digest.digest(password.getBytes(StandardCharsets.UTF_8)); //Get hashed bytes from password
            return bytesToHex(encoded); //Returns bytes in HEXA format
            
        } catch (NoSuchAlgorithmException ex) //This should not happen - Has to be here because of MessageDigest.getInstace
        {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    
    private static String bytesToHex(byte[] hash)
    {
        return javax.xml.bind.DatatypeConverter.printHexBinary(hash);
    }
    
}
