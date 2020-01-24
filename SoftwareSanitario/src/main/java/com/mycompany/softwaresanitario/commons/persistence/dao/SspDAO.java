/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ssp;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 *
 * @author franc
 */
public interface SspDAO extends DAO<Ssp, String>{
    
    public Ssp getByEmailAndPassword(String email, String password) throws DAOException;
    
    public Ssp getByCity(Integer cityid) throws DAOException;
    
    public Ssp updatePassword(String email, String password) throws DAOException;
}
