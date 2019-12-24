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
    
    public Exam getByCode(int SSD) throws DAOException;
    public ArrayList <Exam> getExams(String patient) throws DAOException;
    public boolean newExam(String date, String time, String idpatient, String iddoctor, String analisys , String recall) throws DAOException;
    public ArrayList <Exam> getExamsOfSpecialist(String specialist) throws DAOException;
    public ArrayList <Exam> getExamsOfSsp(String ssp) throws DAOException;
    public boolean updateExam(int ssd, String report) throws DAOException;
}