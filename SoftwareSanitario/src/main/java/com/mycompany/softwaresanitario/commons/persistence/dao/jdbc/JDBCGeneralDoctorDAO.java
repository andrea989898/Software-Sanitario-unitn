/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.GeneralDoctorDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
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
    public List<GeneralDoctor> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeneralDoctor getByCode(String SSD) throws DAOException { 
        GeneralDoctor generalDoctor = new GeneralDoctor();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM users u, alldoctors al WHERE u.code = al.ssd AND al.ssd IN (SELECT ssd FROM generaldoctors WHERE ssd = ?)")) {
            stm.setString(1, SSD);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    generalDoctor.setStudio_address(rs.getString("studio_address"));
                    generalDoctor.setCf(rs.getString("ssd"));
                    
                    
                    return generalDoctor;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    
}
