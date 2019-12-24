/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.manipulate;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import java.util.Calendar;
import java.util.*;
/**
 *
 * @author franc
 */
public class ManipulateExam {
    
    public static ArrayList<Exam> ScreamExamByDate(List<Exam> exams){
        ArrayList<Exam> examsScream = new ArrayList<Exam>();
        Calendar cal = Calendar.getInstance();
        
        int actualyear = cal.get(1);
        int actualmonth = cal.get(2);
        int actualday = cal.get(2);
        
        for(Exam exam:exams){
            if(exam.getExaminationDate().compareTo(cal.getTime())>0){
                examsScream.add(exam);
            }
        }
        
        
        
        return examsScream;
        
    }
 
    public static ArrayList<Exam> ScreamExamsByDone(List<Exam> exams){
        ArrayList<Exam> examsScream = new ArrayList<Exam>();
        
        for(Exam exam:exams){
            if(!exam.getIsDone()){
                examsScream.add(exam);
            }
        }
        return examsScream;
    }
    
    
}
