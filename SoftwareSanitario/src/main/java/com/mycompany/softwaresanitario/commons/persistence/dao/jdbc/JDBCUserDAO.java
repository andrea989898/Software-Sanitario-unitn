/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import com.mycompany.softwaresanitario.crypt.CryptPassword;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author franc
 */
public class JDBCUserDAO extends JDBCDAO<User, String> implements UserDAO{
    
    
    public JDBCUserDAO(Connection con) {
        super(con);
    }

    @Override
    public User getByEmail(String email) throws DAOException {
        User user = new User();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Users u, Images i WHERE u.email = ? AND i.idpatient=u.code")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    user.setCf(rs.getString("code"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setSurname(rs.getString("surname"));
                    user.setAge(rs.getInt("age"));
                    user.setBirthdate(rs.getDate("birthdate"));
                   // user.setBirthplace(rs.getString("birthplace"));
                    user.setBirth_city_id(rs.getInt("birth_city_id"));
                    user.setCity_id(rs.getInt("city_id"));
                    user.setGender(rs.getString("gender"));
                    user.setAddress(rs.getString("address"));
                    user.setAvatarPath(rs.getString("data"));
                    
                    System.out.println(user.getAvatarPath());
                    
                    return user;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }

    
    
    
    @Override
    public User getByEmailAndPassword(String email, String password) throws DAOException {
        User user = new User();
        if ((email == null) || (password == null)) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Users u, Images i WHERE u.email = ? AND i.idpatient=u.code")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    
                    user.setCf(rs.getString("code"));
                    user.setEmail(rs.getString("email"));
                    
                    user.setName(rs.getString("name"));
                    user.setSurname(rs.getString("surname"));
                    user.setAge(rs.getInt("age"));
                    user.setBirthdate(rs.getDate("birthdate"));
                    user.setBirth_city_id(rs.getInt("birth_city_id"));
                    user.setCity_id(rs.getInt("city_id"));
                    user.setGender(rs.getString("gender"));
                    user.setAddress(rs.getString("address"));
                    user.setAvatarPath(rs.getString("data"));
                    
                    //System.out.println(user.getAvatarPath());
                    //System.out.print(rs.getString("password"));
                    //System.out.println(CryptPassword.validate(password, "$2a$12$CI8PpQb0f0j6wwc2gB6KCuF15mEqXnaG8UXvV5VS782cq557SuO/i"));
                    String psw = rs.getString("password");
                    
                    if(CryptPassword.validate(password, psw.substring(0,60))) {
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                    else return null;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    
    

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public User updatePassword(String email, String password) throws DAOException{
        User user = new User();
   
        try (PreparedStatement stm = CON.prepareStatement("UPDATE public.users\n" +
                                                          "   SET password=?\n" +
                                                          " WHERE email = ?;")) {
            String psw = CryptPassword.hashPassword(password);
            //System.out.println(psw);
            stm.setString(1, psw);
            stm.setString(2, email);
            //System.out.println(code);
            
            
            if(stm.executeUpdate()==1){
                user.setEmail(email);
                user.setPassword(password);
                return user;
            }else{
                return null;
            }
            
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the password of the user", ex);
        }
        
    }
    
    @Override
    public User getByCode(String ssd) throws DAOException{
        User user = new User();
   
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM users u WHERE code = ?")) {
            stm.setString(1, ssd);
            //System.out.println(code);
            
            try (ResultSet rs = stm.executeQuery()) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    
                    user.setCf(rs.getString("code"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setSurname(rs.getString("surname"));
                    user.setAge(rs.getInt("age"));
                    user.setBirthdate(rs.getDate("birthdate"));
                    user.setBirth_city_id(rs.getInt("birth_city_id"));
                    user.setCity_id(rs.getInt("city_id"));
                    user.setGender(rs.getString("gender"));
                    user.setAddress(rs.getString("address"));
                    
                    System.out.println(user.getAvatarPath());
                    
                    return user;
                }

                return null;
            }
            
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert the user", ex);
        }
        
    }

    @Override
    public boolean updateImage(String code, String data) throws DAOException{
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date d = new Date();
        String dayofchange = dateFormat.format(d);
	dayofchange = dayofchange.substring(0, 10);
   
        try (PreparedStatement stm = CON.prepareStatement("UPDATE images\n" +
                                                          "   SET data=?, data_photo=TO_DATE(?, 'YYYY/MM/DD')\n" +
                                                          " WHERE idpatient = ?;")) {
            //System.out.println(psw);
            stm.setString(1, data);
            stm.setString(2, dayofchange);
            stm.setString(3, code);
            //System.out.println(code);
            
            
            if(stm.executeUpdate()==1){
                return true;
            }else{
                return false;
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new DAOException("Impossible to change the image", ex);
        }
        
    }


}
