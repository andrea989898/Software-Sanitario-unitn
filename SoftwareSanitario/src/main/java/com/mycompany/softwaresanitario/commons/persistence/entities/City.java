/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.entities;

/**
 *
 * @author franc
 */
public class City {
    private int code;
    private String name;
    private int idprovince;

    public int getIdprovince() {
        return idprovince;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setIdprovince(int idprovince) {
        this.idprovince = idprovince;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
