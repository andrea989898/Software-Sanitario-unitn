/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao;

import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author franc
 */
public interface ExamDAO extends DAO<Exam, String>{
    
    public Exam getByCode(String SSD) throws DAOException;
    public ArrayList <Exam> getExams(String patient) throws DAOException;
}