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
    public String SSD;
    public String name;
    public String surname;
    public Date birthDate;
    public String birthPlace;
    public int age;
    public String email;
    private String avatarPath;
    
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
    
    public String getSSD() {
        return SSD;
    }
    
    public void setSSD(String SSD) {
        this.SSD = SSD;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date bd) {
        this.birthDate = bd;
    }
    public String getBirthPlace() {
        return birthPlace;
    }
    
    public void setBirthPlace(String bp) {
        this.birthPlace = bp;
    }
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String e){
        this.email = e;
    }
}
