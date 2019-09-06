
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francesco
 */

class ISS{
    Doctor[] doctors = new Doctor[100];
    Specialist[] specialists = new Specialist[100];
}
class Person  {
    char[] name;
    char[] surname;
    Date birth_date;
    char[] birth_place;
    char[] gender;
    int[] photo = new int[4];
    char[] email;
}

class Examination{
    char[] examination_name;
    Date examination_date;
    char[] examination_doctor;
}

class Ticket{
    char[] ticket_name;
    int ticket_import;
    Date expiration_date;
}

class Recipe{
    char[] drug;
    
}

class Prescription{
    Exam exam;
    char[] specialist_name;
}

class Exam{
    char[] exam_name;
    Date exam_date;
    Doctor doctor_name;
    char[] exame_result;
}

class Patient extends Person{
    char[] ssd= new char[16];
    Examination[] examinations = new Examination[100];
    Ticket[] tickets = new Ticket[10];
    Recipe[] recipes = new Recipe[10];
    Prescription[] prescriptions = new Prescription[100];
    Doctor doctor;
    
    public Doctor change_doctor(){
        return doctor;
    }
    
    public void book_exam(){}
    
}

class Doctor{
    Patient[] patients = new Patient[100];
    
    public void release_examination(Patient p){}
    public void release_prescription(){}
    public void release_recipe(){}
    public void show_patientList(){}
    public void show_patient(Patient p){}
    public void show_exams(){}   
}

class Specialist{
    Patient[] patiens = new Patient[100];
    
    public void release_recipe(){};
    public void release_examination(){};
    public void record_exam(){};
    public void show_patientList(){};
    public void show_patient(Patient p){};
    public void insert_ticket(Patient p){};
}

