/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.entities.Examination;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExaminationDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author PC Andrea
 */
public class JDBCExaminationDAO extends JDBCDAO<Examination, String> implements ExaminationDAO{

    public JDBCExaminationDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Examination getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Examination> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Examination getByCode(int code) throws DAOException {
        Examination examination = new Examination();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Examination WHERE IDExamination = ?")) {
            stm.setInt(1, code);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    examination.setSSD(rs.getInt("IDExamination"));
                    examination.setArgument(rs.getString("Argument"));
                    examination.setIDdoctor(rs.getString("iddoctor"));
                    examination.setExaminationDate(rs.getDate("examinationdate"));
                    examination.setIsDone(rs.getBoolean("isdone"));
                    examination.setTime(rs.getString("time"));
                    examination.setIDPatient(rs.getString("IDPatient"));
                    examination.setIDPrescription(rs.getInt("IDPrescription"));
                    examination.setIDRecipe(rs.getInt("IDRecipe"));
                   
                    return examination;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the exam", ex);
        }
    }
    @Override
    public ArrayList <Examination> getExaminations(String patient) throws DAOException{
        String myGet = "select *\n" +
                                "from examinations e\n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = e.idpatient\n" +
                                "where pat.ssd=?\n" +
                                "order by e.examinationdate DESC";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, patient);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Examination> examinations = new ArrayList<Examination>();
                while (rst.next()) {
                    Examination examination = new Examination();
                    examination.setSSD(rst.getInt("IDExamination"));
                    examination.setArgument(rst.getString("Argument"));
                    examination.setIDdoctor(rst.getString("iddoctor"));
                    examination.setExaminationDate(rst.getDate("examinationdate"));
                    examination.setIsDone(rst.getBoolean("isdone"));
                    examination.setTime(rst.getString("time"));
                    examination.setIDPatient(rst.getString("IDPatient"));
                    //examination.setIDPrescription(rst.getInt("IDPrescription"));
                    //examination.setIDRecipe(rst.getInt("IDRecipe"));
                    examinations.add(examination); 
                    //System.out.println(examination.SSD + examination.doctor + examination.time + examination.examinationDate);
                }
                
                
                return examinations;
            }
        }catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }

    @Override
    public boolean newExamination(String date, String time, String idpatient, String iddoctor) throws DAOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO public.examinations(\n" +
                                    "            idexamination, idpatient, iddoctor, \n" +
                                    "            \"time\", isdone, examinationdate)\n" +
                                    "    VALUES ((SELECT MAX(idexamination) FROM examinations)+1), ?, ?, \n" +
                                    "            ?, false, ?);")) {
            
            /*
            
            Query di prova per test
            
            INSERT INTO public.examinations(
            idexamination, idpatient, iddoctor, isdone)
             VALUES ((SELECT MAX(idexamination) FROM examinations)+1),'0ADDOVJ1SD1QR9R9', 'AEO8LWP5Q1I2KNS2', 
             false);
            */
            
            
            
            
            //non va la query
            stm.setString(1, idpatient);
            stm.setString(2, iddoctor);
            stm.setString(3, time);
            stm.setString(4, date);
            //System.out.println(code);
            
            if(stm.execute())  return true;
             
            return false;
            } catch (SQLException ex) {
            Logger.getLogger(JDBCPatientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
}
