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
public class Recipe {
    public int code;
    public int idDrug;
    public String idPatient;
    public String drugName;
    
    public Recipe(int c, String n, int d, String p){
        setCode(c);
        setDrugName(n);
        setIdDrug(d);
        setIdPatient(p);
    }
    
    public int getCode(){
        return code;
    }
    public void setCode(int c){
        this.code=c;
    }
    
    public String getDrugName(){
        return drugName;
    }
    public void setDrugName(String d){
        this.drugName=d;
    }
    
    public int getIdDrug(){
        return idDrug;
    }
    public void setIdDrug(int d){
        this.idDrug=d;
    }
    
    public String getIdPatient(){
        return idPatient;
    }
    public void setIdPatient(String p){
        this.idPatient=p;
    }
}
