/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.City;
import java.sql.SQLException;
/**
 *
 * @author franc
 */
public interface CityDAO extends DAO<City, Integer>{
    public City getByCode(int code) throws DAOException;
}

    
