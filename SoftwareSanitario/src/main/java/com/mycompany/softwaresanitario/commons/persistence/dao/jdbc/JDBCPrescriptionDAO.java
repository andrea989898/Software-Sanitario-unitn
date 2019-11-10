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
public class JDBCPrescriptionDAO extends JDBCDAO<Prescription, String> implements PrescriptionDAO{

    public JDBCPrescriptionDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Prescription getByPrimaryKey(String primaryKey) throws DAOException {
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
"                                inner join examinations ex\n" +
"                                on ex.idexamination = pr.idexamination\n" +
"                                inner join patients pat\n" +
"                                on ex.idpatient=pat.ssd\n" +
"                                where pat.ssd = ?";

        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, patient);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
                while (rst.next()) {
                    Prescription prescription = new Prescription();
                    prescription.setCode(rst.getInt("code"));
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
"                                from prescriptions pr\n" +
"                                where pr.idexam = ?";
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
}
