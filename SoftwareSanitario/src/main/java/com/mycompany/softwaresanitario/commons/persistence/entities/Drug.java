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
public class Drug {
    public int code;
    public boolean isforprescription;
    public String name;

    public int getCode() {
        return code;
    }

    public boolean isIsforprescription() {
        return isforprescription;
    }

    public String getName() {
        return name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setIsforprescription(boolean isforprescription) {
        this.isforprescription = isforprescription;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
