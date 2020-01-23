/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author franc
 */
public interface GeneralDoctorDAO extends DAO<GeneralDoctor, String>{
    
    public GeneralDoctor getByCode(String SSD) throws DAOException;
    
    public List<User> getAllGeneralDoctors(String ssd, String ssd2, int city_id) throws DAOException;
    
    public boolean isAGeneralDoctor(String ssd) throws DAOException;
    
}
