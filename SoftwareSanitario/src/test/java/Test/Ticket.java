/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.sql.Date;

/**
 *
 * @author Francesco
 */
public class Ticket {
    public int code;
    public int cost;
    public Date date;
    public Date expirationDate;
    public int idExamination;
    public String idPatient;
    public boolean isPaid;
    
    public Ticket(int c, int co, Date d, Date eD, int e, String p, boolean t){
        setCode(c);
        setCost(co);
        setDate(d);
        setExpirationDate(eD);
        setIdExamination(e);
        setIdPatient(p);
        setIsPaid(t);
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
