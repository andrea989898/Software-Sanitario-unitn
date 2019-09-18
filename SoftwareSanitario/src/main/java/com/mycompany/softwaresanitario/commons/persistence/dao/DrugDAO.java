/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Drug;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC Andrea
 */
public interface DrugDAO extends DAO<Drug, String>{
    public ArrayList<Drug> getAllDrugs() throws DAOException, SQLException;
}
