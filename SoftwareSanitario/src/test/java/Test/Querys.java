/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Francesco
 */
public class Querys {

    public ArrayList <String> GetGeneralDoctors(Connection conn) throws SQLException{
        ArrayList generalDoctors = new ArrayList();
        PreparedStatement statement;
        String myGet = "SELECT SSD FROM GeneralDoctors";
        statement = conn.prepareStatement(myGet);
        statement.addBatch();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String columnValue = rs.getString(1);
            generalDoctors.add(columnValue);
        }
        statement.close();
        return generalDoctors;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
