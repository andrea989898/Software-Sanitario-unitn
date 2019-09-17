/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francesco
 */
public class Image {
    public String data;
    public String idPatient;
    public Date photo_date;
    public int num;
    
    public void setData(String name) {
        this.data = name;
    }
    
    public void setIdPatient(String idP) {
        this.idPatient = idP;
    }
    
    public void setPhoto_date(Date p) {
        this.photo_date = p;
    }
    
    public void setNum(int n) {
        this.num = n;
    }
    
}
