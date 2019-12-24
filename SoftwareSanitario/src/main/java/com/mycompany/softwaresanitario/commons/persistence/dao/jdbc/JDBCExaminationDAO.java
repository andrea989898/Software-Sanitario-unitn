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
import java.sql.Time;
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
            throw new DAOException("Impossible to find examinations", ex);
        }
    }

    @Override
    public boolean newExamination(String date, String time, String idpatient, String iddoctor, String type, String analisys) throws DAOException {
        
        String temp1 = "1";
        String temp2 = "2";
        String myGet="";
        if(type.equals(temp1)){
            //System.out.println("Sono nel type 1");
            myGet ="INSERT INTO public.examinations(\n" +
                    "              idexamination, idpatient, iddoctor, \"time\", isdone, examinationdate, \n" +
                    "              argument)\n" +
                    "                  VALUES (((select max(idexamination)\n" +
                    "            from examinations) +1), ?, ?, TO_TIMESTAMP(?, 'HH24:MI:SS'), false, TO_DATE(?, 'YYYY/MM/DD'), ?);"+
                        
                    "            INSERT INTO public.tickets(\n" +
                    "                        code, cost, date, expirationdate, idexamination, idexam, idpatient, \n" +
                    "                        ispaid)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from tickets)\n" +
                    "        +1), 0, NOW(), TO_DATE(?, 'YYYY/MM/DD'), (select max(idexamination)\n" +
                    "            from examinations), null, ?, \n" +
                    "                        false);\n" +
                    "\n" +
                    "\n" +
                    "            INSERT INTO public.prescriptions(\n" +
                    "                        code, analysis, idexam, idexamination, idrecipe)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from prescriptions)+1), ?, null, (select max(idexamination)\n" +
                    "            from examinations), null);";
            
        }else if (type.equals(temp2)){
                //System.out.println("Sono nel type 2");
                myGet ="INSERT INTO public.examinations(\n" +
                    "              idexamination, idpatient, iddoctor, \"time\", isdone, examinationdate, \n" +
                    "              argument)\n" +
                    "                  VALUES (((select max(idexamination)\n" +
                    "            from examinations) +1), ?, ?, TO_TIMESTAMP(?, 'HH24:MI:SS'), false, TO_DATE(?, 'YYYY/MM/DD'), ?);"+
                        
                    "            INSERT INTO public.tickets(\n" +
                    "                        code, cost, date, expirationdate, idexamination, idexam, idpatient, \n" +
                    "                        ispaid)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from tickets)\n" +
                    "        +1), 50, NOW(), TO_DATE(?, 'YYYY/MM/DD'), (select max(idexamination)\n" +
                    "            from examinations), null, ?, \n" +
                    "                        false);\n" +
                    "\n" +
                    "\n" +
                    "            INSERT INTO public.prescriptions(\n" +
                    "                        code, analysis, idexam, idexamination, idrecipe)\n" +
                    "                VALUES (((select max(code)\n" +
                    "            from prescriptions)+1), ?, null, (select max(idexamination)\n" +
                    "            from examinations), null);";
            }
        
        
         try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, idpatient);
            stm.setString(2, iddoctor);
            stm.setString(3, time);
            stm.setString(4, date);
            stm.setString(5, analisys);
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
        
        
        /*(parte finale di controllo)
            if(stm.execute())  return true;
             
            return false;
            } catch (SQLException ex) {
            Logger.getLogger(JDBCPatientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return false;
    }

    @Override
    public ArrayList<Examination> getExaminationsSpecialist(String specialist) throws DAOException {
        String myGet = "select *\n" +
                                "from examinations\n" +
                                "where iddoctor=?\n" +
                                "order by examinationdate DESC";
        try (PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, specialist);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Examination> examinations = new ArrayList<Examination>();
                while (rst.next()) {
                    Examination examination = new Examination();
                    examination.setSSD(rst.getInt("idexamination"));
                    examination.setArgument(rst.getString("argument"));
                    examination.setIDdoctor(rst.getString("iddoctor"));
                    examination.setExaminationDate(rst.getDate("examinationdate"));
                    examination.setIsDone(rst.getBoolean("isdone"));
                    examination.setTime(rst.getString("time"));
                    examination.setIDPatient(rst.getString("idpatient"));
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
    public boolean updateExamination(int ssd, String report) throws DAOException {
        String myGet = "update examinations\n" +
                                "set report = ?, isdone = true\n" +
                                "where idexamination = ?;" +
                        "update tickets\n" +
                                "set ispaid = true\n" +
                                "where idexamination = ?;";
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
