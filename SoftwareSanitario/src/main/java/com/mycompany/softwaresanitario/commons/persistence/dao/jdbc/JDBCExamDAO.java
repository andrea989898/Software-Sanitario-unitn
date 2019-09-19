/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author PC Andrea
 */
public class JDBCExamDAO extends JDBCDAO<Exam, String> implements ExamDAO {

    public JDBCExamDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exam getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Exam> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exam getByCode(String SSD) throws DAOException {
        Exam exam = new Exam();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Exams WHERE Code = ?")) {
            stm.setString(1, SSD);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    exam.setCode(rs.getInt("code"));
                    exam.setIDPrescription(rs.getInt("IDPrescription"));
                    exam.setIDDoctor(rs.getString("IDDoctor"));
                    exam.setExaminationDate(rs.getDate("ExaminationDate"));
                    exam.setIDRecipe(rs.getInt("IDRecipe"));
                    exam.setIDExamination(rs.getInt("IDExamination"));
                    exam.setIsDone(rs.getBoolean("IsDone"));
                    exam.setResult(rs.getString("Result"));
                    exam.setIDPatient(rs.getString("IDPatient"));
                    exam.setIsRecall(rs.getBoolean("IsRecall"));
     
                   
                    return exam;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the exam", ex);
        }
    
    }
    
    @Override
    public ArrayList <Exam> getExams(String patient) throws DAOException{
        String myGet = "select *\n" +
                                "from exams e\n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = e.idpatient\n" +
                                "where pat.ssd = ?";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, patient);
            try (ResultSet rst = stm.executeQuery()){
                ArrayList<Exam> exams = new ArrayList<Exam>();
                while (rst.next()) {
                    Exam exam = new Exam();
                    exam.setCode(rst.getInt("code"));
                    exam.setIDPrescription(rst.getInt("IDPrescription"));
                    exam.setIDDoctor(rst.getString("IDDoctor"));
                    exam.setExaminationDate(rst.getDate("ExaminationDate"));
                    exam.setIDRecipe(rst.getInt("IDRecipe"));
                    exam.setIDExamination(rst.getInt("IDExamination"));
                    exam.setIsDone(rst.getBoolean("IsDone"));
                    exam.setResult(rst.getString("Result"));
                    exam.setIDPatient(rst.getString("IDPatient"));
                    exam.setIsRecall(rst.getBoolean("IsRecall"));
                    if(exam.IsDone==false){
                        exam.Result = "not done yet";
                    }
                    exams.add(exam); 
                    //System.out.println(exam.code + " " + exam.IsDone +" s"+ exam.Result);
                }
                
                return exams;
            }
        }catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
}
