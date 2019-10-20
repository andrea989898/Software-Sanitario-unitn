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
public class Doctor {
    private String name;
    private String surname;
    private int age;
    private Date birthdate;
    private int birth_city_id;
    private String birth_city;
    private String city;
    private String address;
    private int city_id;
    private String gender;
    private String cf;
    private String email;
    private String password;
    private String avatarPath;
    private String studio_address;
    private String specialization;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    

    public String getStudio_address() {
        return studio_address;
    }

    public void setStudio_address(String studio_address) {
        this.studio_address = studio_address;
    }

    public void setBirth_city(String birth_city) {
        this.birth_city = birth_city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirth_city() {
        return birth_city;
    }

    public String getCity() {
        return city;
    }

    public void setBirth_city_id(int birth_city_id) {
        this.birth_city_id = birth_city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getBirth_city_id() {
        return birth_city_id;
    }

    public int getCity_id() {
        return city_id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    
    public int getAge() {
        return age;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getCf() {
        return cf;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
