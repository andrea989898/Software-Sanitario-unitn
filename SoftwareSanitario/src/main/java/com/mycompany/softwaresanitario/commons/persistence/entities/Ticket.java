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
public class Ticket {
    private int code;
    private int cost;
    private Date date;
    private Date expirationDate;
    private int idExamination;
    private String idPatient;
    private boolean isPaid;
    private int IDExam;

    public void setIDExam(int IDExam) {
        this.IDExam = IDExam;
    }

    public int getIDExam() {
        return IDExam;
    }
    
    
    public int getCode(){
        return code;
    }
    public void setCode(int c){
        this.code=c;
    }
    
    public int getCost(){
        return cost;
    }
    public void setCost(int c){
        this.cost=c;
    }
    
    public Date getDate(){
        return date;
    }
    public void setDate(Date c){
        this.date=c;
    }
    
    public Date getExpirationDate(){
        return expirationDate;
    }
    public void setExpirationDate(Date c){
        this.expirationDate=c;
    }
    
    
    public int getIdExamination(){
        return idExamination;
    }
    public void setIdExamination(int c){
        this.idExamination=c;
    }
    
    public String getIdPatient(){
        return idPatient;
    }
    public void setIdPatient(String p){
        this.idPatient=p;
    }
    
    public boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(boolean done) {
        this.isPaid = done;
    }
}
