/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Examination;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC Andrea
 */
public interface ExaminationDAO extends DAO<Examination, String>{
    public Examination getByCode(int code) throws DAOException;
    public ArrayList <Examination> getExaminations(String patient) throws DAOException;
    public ArrayList <Examination> getExaminationsSpecialist(String specialist) throws DAOException;
    public boolean newExamination(String date, String time, String idpatient, String iddoctor, String type, String analysis, String prescriptor) throws DAOException;
    public boolean updateExamination(int ssd, String report) throws DAOException;
}
