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
                                "from drugs order by code\n" ;
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Drug> drugs = new ArrayList<Drug>();
                while (rst.next()) {
                    Drug drug = new Drug();
                    drug.setCode(rst.getInt("code"));
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

    @Override
    public boolean newdrugforrecipe(String drug) throws DAOException {
        String myGet ="INSERT INTO public.drugsrecipes(\n" +
                        "            idrecipe, iddrug)\n" +
                        "    VALUES (((select max(code)\n" +
                    "            from recipes)), ?);";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setInt(1, Integer.parseInt(drug));
            int c = stm.executeUpdate();
            if(c == 1){
                //System.out.println(c);
                return true;
            }
    }   catch (SQLException ex) {
            Logger.getLogger(JDBCDrugDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ArrayList<Drug> getAllDrugsByRecipe(int idrecipe) throws DAOException {
        String myGet = "select dr.code, dr.name\n" +
                       "from drugs dr, drugsrecipes drec\n" +
                       "where drec.idrecipe = ? AND dr.code = drec.iddrug\n" +
                       "order by code;" ;
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setInt(1, idrecipe);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Drug> drugs = new ArrayList<Drug>();
                while (rst.next()) {
                    Drug drug = new Drug();
                    drug.setCode(rst.getInt("code"));
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

