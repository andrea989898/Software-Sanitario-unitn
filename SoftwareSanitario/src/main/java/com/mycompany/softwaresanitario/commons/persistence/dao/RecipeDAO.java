/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Recipe;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author franc
 */
public interface RecipeDAO extends DAO<Recipe, String>{
    
    public List<Recipe> getAllBySSDPatient(String SSD) throws DAOException;
    
    public Recipe getByCode(String SSD) throws DAOException;

    public boolean newrecipe(String idpatient, String analysis, String prescriptor);
    
    
}
