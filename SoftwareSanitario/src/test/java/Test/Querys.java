/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

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

public class Querys {
    
    private final String url = "jdbc:postgresql://localhost/SoftwareSanitario";
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
    
   
    
    public static void main(String[] args) throws SQLException  {
        DatabaseConnection app = new DatabaseConnection();
        Connection conn = app.connect();
        ArrayList<GeneralDoctor> general = getGeneralDoctors(conn);
        ArrayList<Examination> examinations = getExaminations(conn, "'1'");
        ArrayList<Specialist> specialists = getSpecialists(conn);
        ArrayList<Patient> patients = getPatients(conn, "'12345'");
    }
    
}
