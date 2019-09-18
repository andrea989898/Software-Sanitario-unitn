/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.PrescriptionDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
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
public class JDBCPrescriptionDAO extends JDBCDAO<Prescription, String> implements PrescriptionDAO{

    public JDBCPrescriptionDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Prescription getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Prescription> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public ArrayList <Prescription> getPrescriptions(Connection conn, String patient) throws SQLException{
                String myGet = "select pr.code, pr.exam_type, pat.ssd\n" +
                                "from prescriptions pr\n" +
                                "inner join patients pat\n" +
                                "on pr.idpatient=pat.ssd\n" +
                                "where pat.ssd =" + patient ;
        PreparedStatement stm = CON.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
        while (rst.next()) {
            Prescription prescription = new Prescription();
            prescription.setCode(rst.getInt("code"));
            prescription.setType(rst.getString("exam_type"));
            prescription.setIdPatient(rst.getString("ssd"));
            prescriptions.add(prescription); 
            //System.out.println(prescription.code + " " + prescription.examType + prescription.idPatient);
        }
        stm.close();
        return prescriptions;
    }
}
