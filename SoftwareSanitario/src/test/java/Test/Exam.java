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
public class Exam {
    public int code;
    public int idPrescription;
    public int idRecipe;
    public String result;
    public boolean isDone;
    public String idPatient;
    
    public Exam(int c, int iP, int iR, String r, boolean isD, String p){
        setCode(c);
        setIdPrescription(iP);
        setIdRecipe(iR);
        setResult(r);
        setIsDone(isD);
        setIdPatient(p);
    }
    
    public int getCode(){
        return code;
    }
    public void setCode(int c){
        this.code=c;
    }
    
    public int getIdPrescription(){
        return idPrescription;
    }
    public void setIdPrescription(int c){
        this.idPrescription=c;
    }
    
    public int getIdRecipe(){
        return idRecipe;
    }
    public void setIdRecipe(int c){
        this.idRecipe=c;
    }
    
    public String getResult(){
        return result;
    }
    public void setResult(String p){
        this.result=p;
    }
    
    public boolean getIsDone() {
        return isDone;
    }
    public void setIsDone(boolean done) {
        this.isDone = done;
    }
    
    public String getIdPatient(){
        return idPatient;
    }
    public void setIdPatient(String p){
        this.idPatient=p;
    }
            
}
