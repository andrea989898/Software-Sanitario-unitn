/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
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
public class JDBCUserDAO extends JDBCDAO<User, String> implements UserDAO{
    
    
    public JDBCUserDAO(Connection con) {
        super(con);
    }

    @Override
    public User getByEmail(String email) throws DAOException {
        User user = new User();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Users WHERE email = ?")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    user.setCode(rs.getString("code"));
                    return user;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }

    
    
    
    public User getByEmailAndPassword(String email, String password) throws DAOException {
        User user = new User();
        if ((email == null) || (password == null)) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?")) {
            stm.setString(1, email);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setTipo(rs.getString("tipo"));
                    return user;
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
            stm.setString(1, password);
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
            throw new DAOException("Impossible to insert the user", ex);
        }
        
    }

    @Override
    public User insertUser(String email, String password, String code) throws DAOException, UnsupportedEncodingException {
        User user = new User();
   
        try (PreparedStatement stm = CON.prepareStatement("UPDATE public.users\n" +
                                                          "   SET email=?, password=?\n" +
                                                          " WHERE code = ?;")) {
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setString(3, code);
            //System.out.println(code);
            
            
            if(stm.executeUpdate()==1){
                user.setCode(code);
                user.setEmail(email);
                user.setPassword(password);
                return user;
            }else{
                return null;
            }
            
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert the user", ex);
        }
        
    }

    @Override
    public User getByCodeProfileNotSet(String code) throws DAOException {
        User user = new User();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Users WHERE code = ?")) {
            stm.setString(1, code);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    user.setCode(rs.getString("code"));
                    if(rs.getString("email").equals("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"))    return user;
                    else return null;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    

}
