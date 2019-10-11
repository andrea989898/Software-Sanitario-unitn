/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.CityDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.City;
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
public class JDBCCityDAO extends JDBCDAO<City, Integer> implements CityDAO{

    public JDBCCityDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public City getByPrimaryKey(Integer arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<City> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public City getByCode(int code) throws DAOException {
        City city = new City();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Cities WHERE code = ?")) {
            stm.setInt(1, code);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    city.setCode(rs.getInt("code"));
                    city.setName(rs.getString("name"));
                    city.setIdprovince(rs.getInt("idprovince"));
                    
                    return city;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }

    
}
