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
    public Exam getByCode(int SSD) throws DAOException {
        Exam exam = new Exam();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Exams WHERE Code = ?")) {
            stm.setInt(1, SSD);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    exam.setCode(rs.getInt("code"));
                    exam.setIDDoctor(rs.getString("IDDoctor"));
                    exam.setExaminationDate(rs.getDate("ExaminationDate"));
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
                    //exam.setIDPrescription(rst.getInt("IDPrescription"));
                    exam.setIDDoctor(rst.getString("IDDoctor"));
                    exam.setExaminationDate(rst.getDate("ExaminationDate"));
                    exam.setIsDone(rst.getBoolean("IsDone"));
                    exam.setIDPatient(rst.getString("IDPatient"));
                    exam.setIsRecall(rst.getBoolean("IsRecall"));
                    if(exam.getIsDone()==false){
                        exam.setResult("not done yet"); 
                    }else{
                        exam.setResult(rst.getString("Result"));
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
    public boolean newExam(String date, String time, String idpatient, String iddoctor, String analisys, String recall) throws DAOException {
        /*
        code, iddoctor, result, "time", isdone, examinationdate, idpatient, 
            isrecall)
    VALUES (?, ?, ?, ?, ?, ?, ?, 
            ?)
        */
        
        
        
        String myGet ="INSERT INTO public.exams(\n" +
                    "              code, iddoctor, result, \"time\", isdone, examinationdate, idpatient, \n" +
"            isrecall)\n" +
                    "                  VALUES (((select max(code)\n" +
                    "            from exams) +1), ?, '', TO_TIMESTAMP(?, 'HH24:MI:SS'), false, TO_DATE(?, 'YYYY/MM/DD'), ?, CAST(? AS BOOLEAN));"+
                        
                    "            INSERT INTO public.tickets(\n" +
                    "                        code, cost, date, expirationdate, idexamination, idexam, idpatient, \n" +
                    "                        ispaid)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from tickets)\n" +
                    "        +1), 11, NOW(), TO_DATE(?, 'YYYY/MM/DD'), null,(select max(code)\n" +
                    "            from exams),  ?, \n" +
                    "                        false);\n" +
                    "\n" +
                    "\n" +
                    "            INSERT INTO public.prescriptions(\n" +
                    "                        code, analysis, idexam, idexamination, idrecipe)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from prescriptions)+1), ?, (select max(code)\n" +
                    "            from exams), null, null);";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, iddoctor);
            stm.setString(2, time);
            stm.setString(3, date);
            stm.setString(4, idpatient);
            stm.setString(5, recall);
            stm.setString(6, date);
            stm.setString(7, idpatient);
            stm.setString(8, analisys);
            
            int c = stm.executeUpdate();
            if(c == 1){
                //System.out.println(c);
                return true;
            }
        }catch (SQLException ex) {
            System.out.print(String.valueOf(ex));
            throw new DAOException("Impossible to find the user", ex);
        }
        return false;
    }
}
