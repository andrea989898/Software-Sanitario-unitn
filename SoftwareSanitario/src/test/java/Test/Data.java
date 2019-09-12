/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.util.Calendar;

/**
 *
 * @author Francesco
 */
public class Data {
    int year;
    int month;
    int day;
    
    public Data(int y, int m, int d){
        year = y; 
        month = m;
        day = d;       
    }
    
    public void currentData(Data date){
        Calendar cal = Calendar.getInstance();
        date.day = cal.get(Calendar.DATE);
        date.month = cal.get(Calendar.MONTH)+1;
        date.year = cal.get(Calendar.YEAR);
    }
    
    public void expirationData(Data date){
        Calendar cal = Calendar.getInstance();
        date.day = cal.get(Calendar.DATE);
        date.month = cal.get(Calendar.MONTH)+2;
        date.year = cal.get(Calendar.YEAR);
    }  
    
    public String toString(Data d){
        String date = d.year+"-"+d.month+"-"+d.day;
        return date;
    }
}
