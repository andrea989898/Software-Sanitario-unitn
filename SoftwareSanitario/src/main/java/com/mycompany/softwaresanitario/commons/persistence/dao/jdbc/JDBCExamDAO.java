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
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    
    @Override
    public ArrayList <Exam> getExamsOfSpecialist(String specialist) throws DAOException{
        String myGet = "select *\n" +
                                "from exams e\n" +
                                "inner join specialists s\n" +
                                "on s.ssd = e.iddoctor\n" +
                                "where s.ssd = ?";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, specialist);
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
            throw new DAOException("Impossible to find exams", ex);
        }
    }
    
    @Override
    public ArrayList <Exam> getExamsOfSsp(String ssp) throws DAOException{
        String myGet = "select *\n" +
                                "from exams e\n" +
                                "inner join ssp s\n" +
                                "on s.code = e.sspcode\n" +
                                "where s.code = ?";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, ssp);
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
            throw new DAOException("Impossible to find exams", ex);
        }
    }
    
    public boolean newExam(String date, String time, String idpatient, String iddoctor, String analisys, String recall, String prescriptor) throws DAOException {
        
        System.out.println("tempo" + time);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date d = new Date();
        String dayofprescription = dateFormat.format(d);
	dayofprescription = dayofprescription.substring(0, 10);
        idpatient = idpatient.replaceAll("\\s+$", "");
        iddoctor = iddoctor.replaceAll("\\s+$", "");
        prescriptor = prescriptor.replaceAll("\\s+$", "");
        String myGet ="INSERT INTO public.exams(\n" +
                    "              code, iddoctor, sspcode, result, \"time\", isdone, examinationdate, idpatient, \n" +
"            isrecall)\n" +
                    "                  VALUES (((select max(code)\n" +
                    "            from exams) +1), ?, ?, '', TO_TIMESTAMP(?, 'HH24:MI:SS'), false, TO_DATE(?, 'YYYY/MM/DD'), ?, CAST(? AS BOOLEAN));"+
                        
                    "            INSERT INTO public.tickets(\n" +
                    "                        code, cost, date, expirationdate, idexamination, idexam, idpatient, \n" +
                    "                        ispaid)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from tickets)\n" +
                    "        +1), ?, NOW(), TO_DATE(?, 'YYYY/MM/DD'), null,(select max(code)\n" +
                    "            from exams),  ?, \n" +
                    "                        false);\n" +
                    "\n" +
                    "\n" +
                    "            INSERT INTO public.prescriptions(\n" +
                    "                        code, analysis, idexam, idexamination, idrecipe, iddoctor, idpatient, date)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from prescriptions)+1), ?, (select max(code)\n" +
                    "            from exams), null, null, ?, ?, TO_DATE(?, 'YYYY/MM/DD'));";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            if(iddoctor.length() >= 16)     stm.setString(1, iddoctor);
            else stm.setString(1, null);
            if(iddoctor.length() < 16)     stm.setString(2, iddoctor);
            else stm.setString(2, null);
            stm.setString(3, time);
            stm.setString(4, date);
            stm.setString(5, idpatient);
            stm.setString(6, recall);
            if(iddoctor.length() >= 16)     stm.setInt(7, 50);
            else stm.setInt(7, 11);
            stm.setString(8, date);
            stm.setString(9, idpatient);
            stm.setString(10, analisys);
            stm.setString(11, prescriptor);
            stm.setString(12, idpatient);
            stm.setString(13, dayofprescription);
            
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
    
    @Override
    public boolean updateExam(int ssd, String report) throws DAOException {
        String myGet = "update exams\n" +
                                "set result = ?, isdone = true\n" +
                                "where code = ?;" +
                        "update tickets\n" +
                                "set ispaid = true\n" +
                                "where idexam = ?;";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, report);
            stm.setInt(2, ssd);
            stm.setInt(3, ssd);
            int c = stm.executeUpdate();
            if(c == 1){
                //System.out.println(c);
                return true;
            }
            return false;
             
        }catch (SQLException ex) {
            throw new DAOException("Impossible to update exam", ex);
        }
        
    }
}
