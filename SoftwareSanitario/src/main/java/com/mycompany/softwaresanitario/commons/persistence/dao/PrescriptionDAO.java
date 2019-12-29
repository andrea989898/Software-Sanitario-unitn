/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC Andrea
 */
public interface PrescriptionDAO extends DAO<Prescription, String>{
    public ArrayList <Prescription> getPrescriptions(String patient) throws DAOException;
    public Prescription getPrescriptionByExamCode(int idExam) throws DAOException;
    public List<String> getAllDateOfRecipe(int idprovince) throws DAOException;
    public List<Prescription> getPrescriptionsByDateAndProvince(String date, String idprovince) throws DAOException;
}
