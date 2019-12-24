/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.DoctorDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Doctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC Andrea
 */
public class JDBCDoctorDAO extends JDBCDAO<Doctor, String> implements DoctorDAO{

    public JDBCDoctorDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Doctor getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Doctor> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Doctor> getAllDoctors() throws DAOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String myGet = "select u.name, u.surname, u.age, u.birthdate, u.gender, u.address, u.code, u.email, c.name as city, ci.name as birth_city, d.studio_address, s.specialization\n" +
"                        from (users u                       \n" +
"                        inner join cities c\n" +
"			on u.city_id = c.code\n" +
"                        inner join cities ci\n" +
"                        on u.birth_city_id = ci.code\n" +
"                        inner join AllDoctors d\n" +
"                        on d.ssd = u.code) \n" +
"                        left join  specialists s\n" +
"			on s.ssd = u.code\n" +
"                        ";
        try(PreparedStatement stm = CON.prepareStatement(myGet)){
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Doctor> doctors = new ArrayList<Doctor>();
                //System.out.println("Sono qui");
                while (rst.next()) {
                    Doctor doctor = new Doctor();
                    //user.setCode(rst.getInt("code"));
                    doctor.setCf(rst.getString("code"));
                    doctor.setEmail(rst.getString("email"));
                    //user.setPassword(rst.getString("password"));
                    doctor.setName(rst.getString("name"));
                    doctor.setSurname(rst.getString("surname"));
                    doctor.setAge(rst.getInt("age"));
                    doctor.setBirthdate(rst.getDate("birthdate"));
                   // user.setBirthplace(rs.getString("birthplace"));
                    doctor.setBirth_city(rst.getString("birth_city"));
                    doctor.setCity(rst.getString("city"));
                    doctor.setGender(rst.getString("gender"));
                    doctor.setAddress(rst.getString("address"));
                    //doctor.setAvatarPath(rst.getString("data"));
                    doctor.setSpecialization(rst.getString("specialization"));
                    
                    doctors.add(doctor); 
                    //System.out.println(ticket.code + " " + ticket.isPaid +" "+ ticket.expirationDate);
                }
                
                
                return doctors;
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
    
    @Override
    public ArrayList<Doctor> getAllSpecialist() throws DAOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String myGet = "select u.name, u.surname, u.age, u.birthdate, u.gender, u.address, u.code, u.email, d.studio_address, s.specialization\n" +
                       "from users u\n" +
                       "inner join  specialists s\n" +
                       "on s.ssd = u.code\n" +
                       "inner join AllDoctors d\n" +
                       "on s.ssd = d.ssd";
        try(PreparedStatement stm = CON.prepareStatement(myGet)){
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Doctor> doctors = new ArrayList<Doctor>();
                //System.out.println("Sono qui");
                while (rst.next()) {
                    Doctor doctor = new Doctor();
                    //user.setCode(rst.getInt("code"));
                    doctor.setCf(rst.getString("code"));
                    doctor.setEmail(rst.getString("email"));
                    //user.setPassword(rst.getString("password"));
                    doctor.setName(rst.getString("name"));
                    doctor.setSurname(rst.getString("surname"));
                    doctor.setAge(rst.getInt("age"));
                    doctor.setBirthdate(rst.getDate("birthdate"));
                   // user.setBirthplace(rs.getString("birthplace"));
                    doctor.setGender(rst.getString("gender"));
                    doctor.setAddress(rst.getString("studio_address"));
                    //doctor.setAvatarPath(rst.getString("data"));
                    doctor.setSpecialization(rst.getString("specialization"));
                    
                    doctors.add(doctor); 
                    //System.out.println(ticket.code + " " + ticket.isPaid +" "+ ticket.expirationDate);
                }
                
                
                return doctors;
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
