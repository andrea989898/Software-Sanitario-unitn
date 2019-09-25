/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.GeneralDoctorDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
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
public class JDBCGeneralDoctorDAO extends JDBCDAO<GeneralDoctor, String> implements GeneralDoctorDAO{

    public JDBCGeneralDoctorDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeneralDoctor getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllGeneralDoctors(String ssd, String ssd2) throws DAOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        ArrayList<User> users = new ArrayList<User>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM generaldoctors g, users u, alldoctors a WHERE u.code = g.ssd AND u.code = a.ssd and u.code <> ? and u.code <> ?" )){
            stm.setString(1, ssd);
            stm.setString(2, ssd2);
            try(ResultSet rst = stm.executeQuery()){
                while (rst.next()) {
                    User user = new User();
                    user.setCf(rst.getString("code"));
                    user.setEmail(rst.getString("email"));
                    user.setPassword(rst.getString("password"));
                    user.setName(rst.getString("name"));
                    user.setSurname(rst.getString("surname"));
                    user.setAge(rst.getInt("age"));
                    user.setAddress(rst.getString("studio_address"));
                    users.add(user);
                    //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
                }
                stm.close();
                return users;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    @Override
    public GeneralDoctor getByCode(String SSD) throws DAOException { 
        GeneralDoctor generalDoctor = new GeneralDoctor();
        try (PreparedStatement stm = CON.prepareStatement("SELECT a.ssd  FROM alldoctors a, generaldoctors g, patients p WHERE a.ssd = g.ssd and p.generaldoctor = g.ssd and p.ssd = ?")) {
            stm.setString(1, SSD);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    //generalDoctor.setStudio_address(rs.getString("studio_address"));
                    generalDoctor.setCf(rs.getString("ssd"));
                    
                    
                    return generalDoctor;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }

    @Override
    public List<GeneralDoctor> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAGeneralDoctor(String ssd) throws DAOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try (PreparedStatement stm = CON.prepareStatement("SELECT *  FROM generaldoctors  WHERE ssd = ?")) {
            stm.setString(1, ssd);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    return true;
                }

                return false;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    
}
