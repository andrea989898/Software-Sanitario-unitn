/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.SspDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ssp;
import com.mycompany.softwaresanitario.crypt.CryptPassword;
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
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author franc
 */
public class JDBCSspDAO extends JDBCDAO<Ssp, String> implements SspDAO{
    
    public JDBCSspDAO(Connection con) {
        super(con);
    }
    
    
    @Override
    public Ssp getByEmailAndPassword(String email, String password) throws DAOException {
        Ssp ssp = new Ssp();
        if ((email == null) || (password == null)) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Ssp s, provinces p WHERE s.email = ? AND s.idprovince = p.code")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    
                    ssp.setId(rs.getString("code"));
                    ssp.setEmail(rs.getString("email"));
                   
                    ssp.setProvince_id(rs.getInt("idprovince"));
                    ssp.setProvinceName(rs.getString("name"));
                    
                    //System.out.println(user.getAvatarPath());
                    //System.out.print(rs.getString("password"));
                    //System.out.println(CryptPassword.validate(password, "$2a$12$CI8PpQb0f0j6wwc2gB6KCuF15mEqXnaG8UXvV5VS782cq557SuO/i"));
                    String psw = rs.getString("password");
                    
                    if(CryptPassword.validate(password, psw.substring(0,60))) {
                        ssp.setPassword(rs.getString("password"));
                        return ssp;
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
    public Ssp getByCity(Integer cityid) throws DAOException {
        Ssp ssp = new Ssp();
        if (cityid == null) {
            throw new DAOException("City id is a mandatory field", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Ssp s, provinces p, cities c WHERE c.code = ? AND s.idprovince = p.code AND c.idprovince = s.idprovince")) {
            stm.setInt(1, cityid);
            try (ResultSet rs = stm.executeQuery()) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    
                    ssp.setId(rs.getString("code"));
                    ssp.setEmail(rs.getString("email"));
                   
                    ssp.setProvince_id(rs.getInt("idprovince"));
                    ssp.setProvinceName(rs.getString("name"));
                    
                    return ssp;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    
    @Override
    public Ssp updatePassword(String email, String password) throws DAOException{
        Ssp ssp = new Ssp();
   
        try (PreparedStatement stm = CON.prepareStatement("UPDATE public.ssp\n" +
                                                          "   SET password=?\n" +
                                                          " WHERE email = ?;")) {
            String psw = CryptPassword.hashPassword(password);
            //System.out.println(psw);
            stm.setString(1, psw);
            stm.setString(2, email);
            //System.out.println(code);
            
            
            if(stm.executeUpdate()==1){
                ssp.setEmail(email);
                ssp.setPassword(password);
                return ssp;
            }else{
                return null;
            }
            
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the password of the ssp", ex);
        }
        
    }
    

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ssp getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ssp> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
