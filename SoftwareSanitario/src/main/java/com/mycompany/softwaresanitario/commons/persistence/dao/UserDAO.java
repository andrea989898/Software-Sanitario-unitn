/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 *
 * @author franc
 */
public interface UserDAO extends DAO<User, String>{
    
    public User insertUser(String email, String password, String code) throws DAOException, UnsupportedEncodingException;
    
    public User getByCode(String code) throws DAOException;
    
    public User getByEmailAndPassword(String email, String password) throws DAOException;
}
