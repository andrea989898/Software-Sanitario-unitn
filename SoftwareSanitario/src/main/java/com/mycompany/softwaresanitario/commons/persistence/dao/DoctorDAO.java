/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Doctor;
import java.util.ArrayList;

/**
 *
 * @author PC Andrea
 */
public interface DoctorDAO extends DAO<Doctor, String>{
    public ArrayList<Doctor> getAllDoctors() throws DAOException;
    public ArrayList<Doctor> getAllSpecialist() throws DAOException;
}
