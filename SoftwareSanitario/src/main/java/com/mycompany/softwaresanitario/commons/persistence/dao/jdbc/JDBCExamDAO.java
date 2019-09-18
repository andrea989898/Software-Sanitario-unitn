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
                    exam.setIDRecipe(rs.getInt("IDRecipe"));
                    exam.setIDExamination(rs.getInt("IDExamination"));
                    exam.setIsDone(rs.getBoolean("IsDone"));
                    exam.setResult(rs.getString("Result"));
                    exam.setIDPatient(rs.getString("IDPatient"));
     
                   
                    return exam;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the exam", ex);
        }
    
    }
    
    @Override
    public ArrayList <Exam> getExams(Connection conn, String patient) throws SQLException{
                String myGet = "select e.code, e.idprescription, e.idrecipe, e.result, e.isdone, pat.ssd\n" +
                                "from exams e\n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = e.idpatient\n" +
                                "where pat.ssd =" + patient ;
        PreparedStatement stm = CON.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Exam> exams = new ArrayList<Exam>();
        while (rst.next()) {
            Exam exam = new Exam();
            exam.setCode(rst.getInt("code"));
            exam.setIDPrescription(rst.getInt("idprescription"));
            exam.setIDRecipe(rst.getInt("idrecipe"));
            exam.setResult(rst.getString("result"));
            exam.setIsDone(rst.getBoolean("isdone"));
            exam.setIDPatient(rst.getString("ssd"));
            if(exam.IsDone==false){
                exam.Result = "not done yet";
            }
            exams.add(exam); 
            //System.out.println(exam.code + " " + exam.IsDone +" s"+ exam.Result);
        }
        stm.close();
        return exams;
    }
}
