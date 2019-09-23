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
public class Patient {
    private String cf;
    private String generalDoctorCf;
    
    public String getCf() {
        return cf;
    }

    public String getGeneralDoctorCf() {
        return generalDoctorCf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setGeneralDoctorCf(String generalDoctorCf) {
        this.generalDoctorCf = generalDoctorCf;
    }
    
    
}
