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
    private int code;

    private String name;

    public int getCode() {
        return code;
    }

   

    public String getName() {
        return name;
    }

    public void setCode(int code) {
        this.code = code;
    }

 
    public void setName(String name) {
        this.name = name;
    }
    
}
