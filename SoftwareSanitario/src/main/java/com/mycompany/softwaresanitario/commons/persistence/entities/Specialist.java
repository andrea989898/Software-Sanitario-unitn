/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.entities;

/**
 *
 * @author Francesco
 */
public class Specialist {
    private String cf;
    private String studio_address;
    private String specialization;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCf() {
        return cf;
    }

    public String getStudio_address() {
        return studio_address;
    }

    public void setStudio_address(String studio_address) {
        this.studio_address = studio_address;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }
    
    
}
