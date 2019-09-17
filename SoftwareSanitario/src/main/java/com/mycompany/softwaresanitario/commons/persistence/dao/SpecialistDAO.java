/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Specialist;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 *
 * @author franc
 */
public interface SpecialistDAO extends DAO<Specialist, String>{
    
    public Specialist getByCode(String SSD) throws DAOException;
    
    
}
