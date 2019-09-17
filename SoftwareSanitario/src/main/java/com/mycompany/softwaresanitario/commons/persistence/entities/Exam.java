/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.entities;

/**
 *
 * @author PC Andrea
 */
public class Exam {
    public int code;
    public int IDPrescription;
    public int IDRecipe;
    public int IDExamination;
    public String Result;
    public boolean IsDone;
    public String IDPatient;

    public int getCode() {
        return code;
    }

    public int getIDPrescription() {
        return IDPrescription;
    }

    public int getIDRecipe() {
        return IDRecipe;
    }

    public int getIDExamination() {
        return IDExamination;
    }

    public String getResult() {
        return Result;
    }

    public boolean isIsDone() {
        return IsDone;
    }

    public String getIDPatient() {
        return IDPatient;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setIDPrescription(int IDPrescription) {
        this.IDPrescription = IDPrescription;
    }

    public void setIDRecipe(int IDRecipe) {
        this.IDRecipe = IDRecipe;
    }

    public void setIDExamination(int IDExamination) {
        this.IDExamination = IDExamination;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public void setIsDone(boolean IsDone) {
        this.IsDone = IsDone;
    }

    public void setIDPatient(String IDPatient) {
        this.IDPatient = IDPatient;
    }
    
}
