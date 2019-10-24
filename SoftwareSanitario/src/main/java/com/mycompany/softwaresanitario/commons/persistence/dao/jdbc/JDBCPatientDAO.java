/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.PatientDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
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

    @Override
    public List<User> getAllByDoctor(String cf) {
        String myGet = "select u.name, u.surname, u.age, u.birthdate, u.gender, u.address, u.code, u.email, i.data, c.name as city, ci.name as birth_city\n" +
                        "from users u\n" +
                        "inner join patients pat\n" +
                        "on u.code = pat.ssd\n" +
                        "inner join Images i\n" +
                        "on i.idpatient=u.code\n" +
                        "inner join cities c\n" +
                        "on u.city_id = c.code\n" +
                        "inner join cities ci\n" +
                        "on u.birth_city_id = ci.code\n" +
                        "where pat.generaldoctor = ?";
        try(PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, cf);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<User> patients = new ArrayList<User>();
                //System.out.println("Sono qui");
                while (rst.next()) {
                    User user = new User();
                    //user.setCode(rst.getInt("code"));
                    user.setCf(rst.getString("code"));
                    user.setEmail(rst.getString("email"));
                    //user.setPassword(rst.getString("password"));
                    user.setName(rst.getString("name"));
                    user.setSurname(rst.getString("surname"));
                    user.setAge(rst.getInt("age"));
                    user.setBirthdate(rst.getDate("birthdate"));
                   // user.setBirthplace(rs.getString("birthplace"));
                    user.setBirth_city(rst.getString("birth_city"));
                    user.setCity(rst.getString("city"));
                    user.setGender(rst.getString("gender"));
                    user.setAddress(rst.getString("address"));
                    user.setAvatarPath(rst.getString("data"));
                    
                    
                    patients.add(user); 
                    //System.out.println(ticket.code + " " + ticket.isPaid +" "+ ticket.expirationDate);
                }
                
                
                return patients;
            }
        }catch (SQLException ex) {
            try {
                throw new DAOException("Impossible to find the user", ex);
            } catch (DAOException ex1) {
                Logger.getLogger(JDBCPatientDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }
    
}
