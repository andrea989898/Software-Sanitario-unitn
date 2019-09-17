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
                    examination.setDoctor(rs.getString("iddoctor"));
                    examination.setExaminationDate(rs.getDate("examinationdate"));
                    examination.setIsDone(rs.getBoolean("isdone"));
                    examination.setIsSpecial(rs.getBoolean("isspecial"));
                    examination.setTime(rs.getString("time"));
                   
                    return examination;
                    
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the exam", ex);
        }
    }
    public ArrayList <Examination> getExaminations(Connection conn, String patient) throws SQLException{
                String myGet = "select e.idexamination, al.surname, e.time, e.examinationdate, e.isdone, e.isSpecial, e.argument\n" +
                                "from examinations e\n" +
                                "inner join alldoctors al  \n" +
                                "on al.ssd=e.iddoctor \n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = e.idpatient\n" +
                                "where pat.ssd=" + patient ;
        PreparedStatement stm = conn.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Examination> examinations = new ArrayList<Examination>();
        while (rst.next()) {
            Examination examination = new Examination();
            examination.setSSD(rst.getInt("idexamination"));
            examination.setDoctor(rst.getString("surname"));
            examination.setTime(rst.getString("time"));
            examination.setExaminationDate(rst.getDate("examinationdate"));
            examination.setIsDone(rst.getBoolean("isdone"));
            examination.setIsSpecial(rst.getBoolean("isspecial"));
            examination.setArgument(rst.getString("argument"));
            examinations.add(examination); 
            System.out.println(examination.SSD + examination.doctor + examination.time + examination.examinationDate);
        }
    stm.close();
    return examinations;
    }
}