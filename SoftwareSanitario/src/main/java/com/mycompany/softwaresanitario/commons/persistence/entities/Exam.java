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
public class Exam {
    private int code;
    private String IDDoctor;
    private Date ExaminationDate;
    private String Result;
    private String time;
    private boolean IsDone;
    private String IDPatient;
    private boolean IsRecall;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public void setIsRecall(boolean IsRecall) {
        this.IsRecall = IsRecall;
    }

    public boolean getIsRecall() {
        return IsRecall;
    }

    public void setIDDoctor(String IDDoctor) {
        this.IDDoctor = IDDoctor;
    }

    public void setExaminationDate(Date ExaminationDate) {
        this.ExaminationDate = ExaminationDate;
    }

    public String getIDDoctor() {
        return IDDoctor;
    }

    public Date getExaminationDate() {
        return ExaminationDate;
    }

    public int getCode() {
        return code;
    }


    public String getResult() {
        return Result;
    }

    public boolean getIsDone() {
        return IsDone;
    }

    public String getIDPatient() {
        return IDPatient;
    }

    public void setCode(int code) {
        this.code = code;
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
