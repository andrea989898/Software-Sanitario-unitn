/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.mycompany.softwaresanitario.commons.persistence.entities.Specialist;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Francesco
 */

public class SelectQuerys {
    
/*    private final String url = "jdbc:postgresql://localhost/SoftwareSanitario";
    private final String user = "postgres";
    private final String password = "0000";
    
    public static ArrayList <GeneralDoctor> getGeneralDoctors(Connection conn) throws SQLException{
                String myGet = "SELECT al.name, al.surname, al.ssd, al.age\n" +
                       "FROM allDoctors al\n" +
                       "INNER JOIN generalDoctors d\n" +
                       "ON d.ssd = al.ssd";
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<GeneralDoctor> generalDoctors = new ArrayList<GeneralDoctor>();
        while (rst.next()) {
            GeneralDoctor generalDoctor = new GeneralDoctor(
                    rst.getString("ssd"),
                    rst.getString("Name"), 
                    rst.getString("Surname"), 
                    rst.getInt("age"));
            generalDoctors.add(generalDoctor); 
            System.out.println(generalDoctor.SSD);
        }
    stm.close();
    return generalDoctors;
    }
    
    public static ArrayList <Specialist> getSpecialists(Connection conn) throws SQLException{
                String myGet = "SELECT al.name, al.surname, al.ssd, al.age, s.specialization\n" +
                       "FROM allDoctors al\n" +
                       "INNER JOIN Specialists s\n" +
                       "ON s.ssd = al.ssd";
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Specialist> specialists = new ArrayList<Specialist>();
        while (rst.next()) {
            Specialist specialist = new Specialist(
                    rst.getString("ssd"),
                    rst.getString("Name"), 
                    rst.getString("Surname"), 
                    rst.getInt("age"),
                    rst.getString("specialization"));
            specialists.add(specialist); 
            System.out.println(specialist.SSD);
        }
    stm.close();
    return specialists;
    }
    
    public static ArrayList <Patient> getPatients(Connection conn, String doctor) throws SQLException{
                String myGet = "select pa.ssd, pa.name, pa.surname, pa.birthdate, pa.birthplace, pa.age, pa.email\n" +
                                "from patients pa\n" +
                                "inner join alldoctors al\n" +
                                "on pa.generaldoctor = al.ssd\n" +
                                "where al.ssd = "+doctor;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Patient> patients = new ArrayList<Patient>();
        while (rst.next()) {
           Patient patient = new Patient(
                    rst.getString("ssd"),
                    rst.getString("name"), 
                    rst.getString("surname"), 
                    rst.getDate("birthdate"),
                    rst.getString("birthPlace"),
                    rst.getInt("age"),
                    rst.getString("email"));
            patients.add(patient); 
            System.out.println(patient.name + patient.surname + patient.SSD);
        }
    stm.close();
    return patients;
    }
    
    public static ArrayList <Examination> getExaminations(Connection conn, String patient) throws SQLException{
                String myGet = "select e.idexamination, al.surname, e.time, e.examinationdate, e.isdone, e.isSpecial, e.argument\n" +
                                "from examinations e\n" +
                                "inner join alldoctors al  \n" +
                                "on al.ssd=e.iddoctor \n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = e.idpatient\n" +
                                "where pat.ssd=" + patient ;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Examination> examinations = new ArrayList<Examination>();
        while (rst.next()) {
            Examination examination = new Examination(
                    rst.getString("idexamination"),
                    rst.getString("surname"), 
                    rst.getString("time"), 
                    rst.getDate("examinationdate"), 
                    rst.getBoolean("isdone"),
                    rst.getBoolean("isspecial"),
                    rst.getString("argument")
            );
            examinations.add(examination); 
            System.out.println(examination.SSD + examination.doctor + examination.time + examination.examinationDate);
        }
    stm.close();
    return examinations;
    }
    
    public static ArrayList <Recipe> getRecipes(Connection conn, String patient) throws SQLException{
                String myGet = "select r.code, d.name, r.iddrug, pat.ssd\n" +
                                "from recipes r\n" +
                                "inner join drugs d\n" +
                                "on d.code = r.iddrug\n" +
                                "inner join patients pat\n" +
                                "on r.idpatient=pat.ssd\n" +
                                "where pat.ssd =" + patient ;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        while (rst.next()) {
            Recipe recipe = new Recipe(
                    rst.getInt("code"),
                    rst.getString("name"),
                    rst.getInt("iddrug"),
                    rst.getString("ssd")
            );
            recipes.add(recipe); 
            System.out.println(recipe.code + recipe.drugName + recipe.idDrug +" "+ recipe.idPatient);
        }
    stm.close();
    return recipes;
    }
   
    public static ArrayList <Prescription> getPrescriptions(Connection conn, String patient) throws SQLException{
                String myGet = "select pr.code, pr.exam_type, pat.ssd\n" +
                                "from prescriptions pr\n" +
                                "inner join patients pat\n" +
                                "on pr.idpatient=pat.ssd\n" +
                                "where pat.ssd =" + patient ;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
        while (rst.next()) {
            Prescription prescription = new Prescription(
                    rst.getInt("code"),
                    rst.getString("exam_type"),
                    rst.getString("ssd")
            );
            prescriptions.add(prescription); 
            System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
        }
    stm.close();
    return prescriptions;
    }
    
    public static ArrayList <Exam> getExams(Connection conn, String patient) throws SQLException{
                String myGet = "select e.code, e.idprescription, e.idrecipe, e.result, e.isdone, pat.ssd\n" +
                                "from exams e\n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = e.idpatient\n" +
                                "where pat.ssd =" + patient ;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Exam> exams = new ArrayList<Exam>();
        while (rst.next()) {
            Exam exam = new Exam(
                    rst.getInt("code"),
                    rst.getInt("idprescription"),
                    rst.getInt("idrecipe"),
                    rst.getString("result"),
                    rst.getBoolean("isdone"),
                    rst.getString("ssd")
            );
            if(exam.isDone==false){
                exam.result = "not done yet";
            }
            exams.add(exam); 
            System.out.println(exam.code + " " + exam.isDone +" s"+ exam.result);
        }
    stm.close();
    return exams;
    }
    
    public static ArrayList <Ticket> getTickets(Connection conn, String patient) throws SQLException{
                String myGet = "select tt.code, tt.cost, tt.date, tt.expirationdate, e.argument, tt.idexamination, pat.ssd, tt.ispaid\n" +
                                "from tickets tt inner join examinations e\n" +
                                "on tt.idexamination = e.idexamination\n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = tt.idpatient\n" +
                                "where pat.ssd = " + patient ;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        while (rst.next()) {
            Ticket ticket = new Ticket(
                    rst.getInt("code"),
                    rst.getInt("cost"),
                    rst.getDate("date"),
                    rst.getDate("expirationdate"),
                    rst.getInt("idexamination"),
                    rst.getString("ssd"),
                    rst.getBoolean("ispaid")
            );
            tickets.add(ticket); 
            System.out.println(ticket.code + " " + ticket.isPaid +" "+ ticket.expirationDate);
        }
    stm.close();
    return tickets;
    }
    
    public static void getImages(Connection conn) throws SQLException, FileNotFoundException, IOException{
        String query = "SELECT data, LENGTH(data) FROM images WHERE idpatient = '  1'";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery(); 
        rs.next();            
        File myFile = new File("C:\\Users\\Public\\Documents\\dbIPW19\\a.jpg");
        try (FileOutputStream fos = new FileOutputStream(myFile)) {
            int len = rs.getInt(2);
            byte[] buf = rs.getBytes("data");
            fos.write(buf, 0, len);
        }
    }
    
    public static void main(String[] args) throws SQLException, IOException  {
        DatabaseConnection app = new DatabaseConnection();
        Connection conn = app.connect();
        ArrayList<GeneralDoctor> general = getGeneralDoctors(conn);
        ArrayList<Examination> examinations = getExaminations(conn, "'1'");
        ArrayList<Specialist> specialists = getSpecialists(conn);
        ArrayList<Patient> patients = getPatients(conn, "'12345'");
        ArrayList<Recipe> recipes = getRecipes(conn, "'87'");
        ArrayList<Prescription> prescriptions = getPrescriptions(conn, "'30'");
        ArrayList<Exam> exams = getExams(conn, "'25'");
        ArrayList<Ticket> tickets = getTickets(conn, "'10'");
        getImages(conn);
    }
    */
}
