/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.SpecialistDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.Specialist;
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
public class JDBCSpecialistDAO extends JDBCDAO<Specialist, String> implements SpecialistDAO{
    
    public JDBCSpecialistDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Specialist getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Specialist> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Specialist getByCode(String SSD) throws DAOException {
        Specialist specialDoctor = new Specialist();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM alldoctors a, users u WHERE a.ssd=u.code AND a.ssd IN (SELECT ssd FROM specialists WHERE ssd = ?)")) {
            stm.setString(1, SSD);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    specialDoctor.setSSD(rs.getString("ssd"));
                    specialDoctor.setName(rs.getString("name"));
                    specialDoctor.setSurname(rs.getString("surname"));
                    specialDoctor.setAge(rs.getInt("age"));
                    specialDoctor.setSpecialization(rs.getString("specialization"));
                    specialDoctor.setStudioAddress(rs.getString("studio_Address"));
                    
                    return specialDoctor;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    
}
