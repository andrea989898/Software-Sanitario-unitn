/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author Francesco
 */
public class Prescription {
    public int code;
    public String examType;
    public String idPatient;
    
    
    public Prescription(int c, String t,String p){
        setCode(c);
        setType(t);
        setIdPatient(p);
    }
    
    public int getCode(){
        return code;
    }
    public void setCode(int c){
        this.code=c;
    }
    
    public String getType(){
        return examType;
    }
    public void setType(String t){
        this.examType=t;
    }
    
    public String getIdPatient(){
        return idPatient;
    }
    public void setIdPatient(String p){
        this.idPatient=p;
    }
}
