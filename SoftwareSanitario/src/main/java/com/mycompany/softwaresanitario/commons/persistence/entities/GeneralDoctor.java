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
public class GeneralDoctor {
    private String cf;
    private String studio_address;

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
