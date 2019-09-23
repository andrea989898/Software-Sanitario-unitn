/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.DrugDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Drug;
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
public class JDBCDrugDAO extends JDBCDAO<Drug, String> implements DrugDAO{

    public JDBCDrugDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Drug getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Drug> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ArrayList<Drug> getAllDrugs() throws DAOException{
        String myGet = "select *\n" +
                                "from drugs pr\n" ;
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Drug> drugs = new ArrayList<Drug>();
                while (rst.next()) {
                    Drug drug = new Drug();
                    drug.setCode(rst.getInt("code"));
                    drug.setIsforprescription(rst.getBoolean("isforprescription"));
                    drug.setName(rst.getString("name"));
                    drugs.add(drug); 
                }
                stm.close();
                return drugs;
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }  
}

