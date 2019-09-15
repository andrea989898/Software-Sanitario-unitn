/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author Francesco
 */
public class Specialist {
    public String SSD;
    public String name;
    public String surname;
    public int age;
    public String specialization;
    
    public Specialist(String id, String n, String s, int a, String sp){
        setSSD(id);
        setName(n);
        setSurname(s);
        setAge(a);
        setSpecialization(sp);
        
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

    public String getSpecialization(){
        return specialization;
    }
    public void setSpecialization(String sp){
        this.specialization=sp;
    }
        
}
