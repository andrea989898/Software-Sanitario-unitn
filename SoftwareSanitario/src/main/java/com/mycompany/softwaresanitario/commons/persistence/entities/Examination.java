/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.entities;


import java.sql.Date;

/**
 *
 * @author Francesco
 */
public class Examination{
    public int SSD;
    public String doctor;
    public String time;
    public Date examinationDate;
    public boolean isDone;
    public boolean isSpecial;
    public String argument;
    
    public int getSSD() {
        return SSD;
    }
    
    public void setSSD(int SSD) {
        this.SSD = SSD;
    }
    
    public String getDoctor() {
        return doctor;
    }
    
    public void setDoctor(String d) {
        this.doctor = d;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String t) {
        this.time = t;
    }
    
    public Date getExaminationDate() {
        return examinationDate;
    }
    
    public void setExaminationDate(Date d) {
        this.examinationDate = d;
    }
    
    public boolean getIsDone() {
        return isDone;
    }
    
    public void setIsDone(boolean done) {
        this.isDone = done;
    }
    
    public boolean getIsSpecial() {
        return isSpecial;
    }
    
    public void setIsSpecial(boolean s) {
        this.isSpecial = s;
    }
    
    public String getArgument() {
        return argument;
    }
    
    public void setArgument(String a) {
        this.argument = a;
    }
}
