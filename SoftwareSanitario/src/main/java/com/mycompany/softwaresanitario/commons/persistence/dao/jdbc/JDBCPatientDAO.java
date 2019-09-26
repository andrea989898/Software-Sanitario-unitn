/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.PatientDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author franc
 */
public class JDBCPatientDAO extends JDBCDAO<Patient, String> implements PatientDAO{

    public JDBCPatientDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Patient getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Patient> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Patient getByCode(String SSD) throws DAOException {
        System.out.println("Sono nel DAO");
        Patient patient = new Patient();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM users u, patients p WHERE p.ssd = ? AND p.ssd=u.code")) {
            stm.setString(1, SSD);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    patient.setCf(rs.getString("ssd"));
                    patient.setGeneralDoctorCf(rs.getString("generaldoctor"));
                    
                    
                    return patient;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    @Override
    public boolean setNewDoctor(String doctor, String patient) throws DAOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //User user = new User();
   
        try (PreparedStatement stm = CON.prepareStatement("UPDATE public.patients SET generaldoctor = ? WHERE ssd = ?;")) {
            stm.setString(1, doctor);
            stm.setString(2, patient);
            //System.out.println(code);
            
            if(stm.executeUpdate()==1)  return true;
             
            return false;
            } catch (SQLException ex) {
            Logger.getLogger(JDBCPatientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
