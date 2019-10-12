/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.crypt;


import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author franc
 */
public class CryptPassword {
    private static final Logger log = Logger.getLogger(CryptPassword.class.getName());

    private static final int COST = 12;
    
    
     public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
        
    }
     
     
    public static boolean validate(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
    
    
    
}
