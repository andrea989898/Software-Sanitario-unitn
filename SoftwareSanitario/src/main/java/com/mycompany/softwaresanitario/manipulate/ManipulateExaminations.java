/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.manipulate;
import com.mycompany.softwaresanitario.commons.persistence.entities.Examination;
import java.util.Calendar;
import java.util.*;
/**
 *
 * @author franc
 */
public class ManipulateExaminations {
        
    
    
    public static ArrayList<Examination> ScreamExaminationsByDate(List<Examination> examinations){
        ArrayList<Examination> examinationsScream = new ArrayList<Examination>();
        Calendar cal = Calendar.getInstance();
        
        int actualyear = cal.get(1);
        int actualmonth = cal.get(2);
        int actualday = cal.get(2);
        
        for(Examination examination:examinations){
            if(examination.getExaminationDate().compareTo(cal.getTime())>0){
                examinationsScream.add(examination);
            }
        }
        return examinationsScream;
    }
    
    public static ArrayList<Examination> ScreamExaminationsByDone(List<Examination> examinations){
        ArrayList<Examination> examinationsScream = new ArrayList<Examination>();
        
        for(Examination examination:examinations){
            if(!examination.getIsDone()){
                examinationsScream.add(examination);
            }
        }
        return examinationsScream;
    }
    
    
}
