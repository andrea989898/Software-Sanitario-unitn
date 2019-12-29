/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.entities;

import java.sql.Date;

/**
 *
 * @author PC Andrea
 */
public class Prescription {
    private int code;
    private String iddoctor;
    private String idpatient;
    private String analisys;
    private int idExamination;
    private int idExam;
    private int idrecipe;
    private Date date;
    
    public int getCode(){
        return code;
    }
    public void setCode(int c){
        this.code=c;
    }

    public String getAnalisys() {
        return analisys;
    }

    public void setAnalisys(String analisys) {
        this.analisys = analisys;
    }

    public int getIdrecipe() {
        return idrecipe;
    }

    public void setIdrecipe(int idrecipe) {
        this.idrecipe = idrecipe;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public int getIdExamination() {
        return idExamination;
    }

    public void setIdExamination(int idExamination) {
        this.idExamination = idExamination;
    }

    public String getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(String iddoctor) {
        this.iddoctor = iddoctor;
    }

    public String getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(String idpatient) {
        this.idpatient = idpatient;
    }
    
}

