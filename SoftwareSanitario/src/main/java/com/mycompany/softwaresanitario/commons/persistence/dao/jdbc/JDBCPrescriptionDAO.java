/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.PrescriptionDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC Andrea
 */
public class JDBCPrescriptionDAO extends JDBCDAO<Prescription, Integer> implements PrescriptionDAO{

    public JDBCPrescriptionDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Prescription> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList <Prescription> getPrescriptions(String patient) throws DAOException{
        String myGet = "select *\n" +
"                                from prescriptions pr\n" +
"                                where pr.idpatient = ? AND pr.idrecipe IS NOT NULL ";

        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, patient);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
                while (rst.next()) {
                    Prescription prescription = new Prescription();
                    prescription.setCode(rst.getInt("code"));
                    prescription.setAnalisys(rst.getString("analysis"));
                    prescription.setDate(rst.getDate("date"));
                    prescription.setIddoctor(rst.getString("iddoctor"));
                    prescription.setIdpatient(rst.getString("idpatient"));
                    prescription.setIdrecipe(rst.getInt("idrecipe"));
                    prescriptions.add(prescription); 
                    //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
                }
                stm.close();
                return prescriptions;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    @Override
    public Prescription getPrescriptionByExamCode(int idExam) throws DAOException{
         String myGet = "select *\n" +
                        "from prescriptions pr\n" +
                        "where pr.idexam = ?";
         try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setInt(1, idExam);
            try(ResultSet rst = stm.executeQuery()){
                Prescription prescription = new Prescription();
                while (rst.next()) {
                    prescription.setCode(rst.getInt("code"));
                    //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
                }
                stm.close();
                return prescription;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    @Override
    public List<String> getAllDateOfRecipe(int idprovince) throws DAOException{
        String myGet = "select distinct date\n" +
                       "from prescriptions pr, users u\n" +
                       "where pr.idrecipe IS NOT NULL AND pr.iddoctor = u.code AND u.city_id IN (SELECT code FROM cities WHERE idprovince = ?);";

        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setInt(1, idprovince);
            try(ResultSet rst = stm.executeQuery()){
                List<String> dates = new ArrayList<String>();
                while (rst.next()) {
                    dates.add(rst.getDate("date").toString()); 
                    //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
                }
                stm.close();
                return dates;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    @Override
    public List<Prescription> getPrescriptionsByDateAndProvince(String date, String idprovince) throws DAOException{
        date = date.replaceAll("\\s+$", "");
        idprovince = idprovince.replaceAll("\\s+$", "");
        //System.out.println(Integer.parseInt(idprovince));
        
        String myGet = "select distinct pr.code, pr.analysis, pr.idrecipe, pr.iddoctor, pr.idpatient, pr.date\n" +
                       "  from prescriptions pr, users u\n" +
                       "  where pr.idrecipe IS NOT NULL AND pr.date = TO_DATE(?, 'YYYY/MM/DD') AND pr.iddoctor = u.code AND u.city_id IN (SELECT code FROM cities WHERE idprovince = ?);";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, date);
            stm.setInt(2, Integer.parseInt(idprovince));
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
                while (rst.next()) {
                    Prescription prescription = new Prescription();
                    prescription.setCode(rst.getInt("code"));
                    prescription.setAnalisys(rst.getString("analysis"));
                    prescription.setDate(rst.getDate("date"));
                    prescription.setIddoctor(rst.getString("iddoctor"));
                    prescription.setIdpatient(rst.getString("idpatient"));
                    prescription.setIdrecipe(rst.getInt("idrecipe"));
                    prescriptions.add(prescription); 
                    //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
                }
                stm.close();
                return prescriptions;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public Prescription getByPrimaryKey(Integer primaryKey) throws DAOException {
        Prescription prescription = new Prescription();
        String myGet = "select *\n" +
"                                from prescriptions pr\n" +
"                                where pr.code = ?";

        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setInt(1, primaryKey);
            try(ResultSet rst = stm.executeQuery()){
                while (rst.next()) {
                    prescription.setCode(rst.getInt("code"));
                    prescription.setAnalisys(rst.getString("analysis"));
                    prescription.setDate(rst.getDate("date"));
                    prescription.setIddoctor(rst.getString("iddoctor"));
                    prescription.setIdpatient(rst.getString("idpatient"));
                    prescription.setIdrecipe(rst.getInt("idrecipe"));
                    //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
                }
                stm.close();
                return prescription;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
