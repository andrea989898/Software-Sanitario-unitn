/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 *
 * @author franc
 */
public interface PatientDAO extends DAO<Patient, String>{
 
    public Patient getByCode(String SSD) throws DAOException;
    
    public boolean setNewDoctor(String ssdd, String ssd) throws DAOException;

    public List<User> getAllByDoctor(String cf);
}
