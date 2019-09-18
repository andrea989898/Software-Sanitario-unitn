/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.RecipeDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.Recipe;
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
public class JDBCRecipeDAO extends JDBCDAO<Recipe, String> implements RecipeDAO{
    
     public JDBCRecipeDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Recipe getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Recipe> getAllBySSDPatient(String SSD) throws DAOException, SQLException {
        String myGet = "select r.code, d.name, r.iddrug, pat.ssd\n" +
                                "from recipes r\n" +
                                "inner join drugs d\n" +
                                "on d.code = r.iddrug\n" +
                                "inner join patients pat\n" +
                                "on r.idpatient=pat.ssd\n" +
                                "where pat.ssd = ?";
        PreparedStatement stm = CON.prepareStatement(myGet);
        stm.setString(1, SSD);
        ResultSet rst = stm.executeQuery();
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        while (rst.next()) {
            Recipe recipe = new Recipe();
            recipe.setCode(rst.getInt("code"));
            recipe.setDrugName(rst.getString("name"));
            recipe.setIdDrug(rst.getInt("iddrug"));
            recipe.setIdPatient(rst.getString("ssd"));
            recipes.add(recipe); 
            //System.out.println(recipe.code + recipe.drugName + recipe.idDrug +" "+ recipe.idPatient);
        }
        stm.close();
        return recipes;
    }

    @Override
    public List<Recipe> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Recipe getByCode(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
