
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public List<Recipe> getAllBySSDPatient(String SSD) throws DAOException {
        String myGet = "select r.code, d.name, rd.iddrug, pat.ssd\n" +
                                "from recipes r\n" + 
                                "inner join DrugsRecipes rd\n" +
                                "on r.code = rd.idrecipe\n" +
                                "inner join drugs d\n" +
                                "on rd.IDDrug = d.code\n" +
                                "inner join prescriptions pre\n" +
                                "on pre.idrecipe = rd.idrecipe\n " +
                                "inner join examinations ex\n"+
                                "on ex.idexamination = pre.idexamination\n" +
                                "inner join patients pat\n" +
                                "on ex.idpatient=pat.ssd\n" +
                                "where pat.ssd = ?";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, SSD);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Recipe> recipes = new ArrayList<Recipe>();
                while (rst.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setCode(rst.getInt("code"));
                    recipe.setIdPatient(rst.getString("ssd"));
                    recipes.add(recipe); 
                    //System.out.println(recipe.code + recipe.drugName + recipe.idDrug +" "+ recipe.idPatient);
                }
                stm.close();
                return recipes;
             }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public List<Recipe> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Recipe getByCode(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean newrecipe(String idpatient, String analysis, String prescriptor) {
        //INSERT INTO public.prescriptions(
       //     code, analysis, idexam, idexamination, idrecipe)
    //VALUES (?, ?, ?, ?, ?);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date d = new Date();
        String dayofprescription = dateFormat.format(d);
	dayofprescription = dayofprescription.substring(0, 10);
        prescriptor = prescriptor.replaceAll("\\s+$", "");
        
        String myGet="INSERT INTO public.recipes(\n" +
"            code)\n" +
"    VALUES ((select max(code) from recipes)+1);"
                + ""
                + "INSERT INTO public.prescriptions(\n" +
"            code, analysis, idexam, idexamination, idrecipe, iddoctor, idpatient, date)\n" +
"    VALUES (((select max(code)\n" +
                    "            from prescriptions)+1), ?, null, null, (select max(code) from recipes), ?, ?, TO_DATE(?, 'YYYY/MM/DD'));"
                + "";
        
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, analysis);
            stm.setString(2, prescriptor);
            stm.setString(3, idpatient);
            stm.setString(4, dayofprescription);
            int c = stm.executeUpdate();
            if(c == 1){
                //System.out.println(c);
                return true;
            }
        } catch (SQLException ex) {
             Logger.getLogger(JDBCRecipeDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        
         return false;
    }

    
}
