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
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Francesco
 */

public class Querys {
    
    private final String url = "jdbc:postgresql://localhost/SoftwareSanitario";
    private final String user = "postgres";
    private final String password = "0000";
    
    public static ArrayList <GeneralDoctor> getGeneralDoctors(Connection conn) throws SQLException{
                String myGet = "SELECT al.name, al.surname, al.ssd\n" +
                       "FROM allDoctors al\n" +
                       "INNER JOIN generalDoctors d\n" +
                       "ON d.ssd = al.ssd";
        PreparedStatement stm = conn.prepareStatement(myGet);
        stm.addBatch();
        ResultSet rst = stm.executeQuery();
        ArrayList<GeneralDoctor> generalDoctors = new ArrayList<GeneralDoctor>();
        while (rst.next()) {
            GeneralDoctor generalDoctor = new GeneralDoctor(
                    rst.getString("ssd"),
                    rst.getString("Name"), 
                    rst.getString("Surname"), 
                    rst.getInt("Age"));
            /*GeneralDoctor generalDoctor = new GeneralDoctor();
            generalDoctor.setSSD(rst.getString("ssd"));
            generalDoctor.setName(rst.getString("Name"));
            generalDoctor.setSurname(rst.getString("Surname"));
            generalDoctor.setAge(rst.getString("Age"));
            generalDoctors.add(generalDoctor);*/
            System.out.println(generalDoctor.SSD);
        }
    stm.close();
    return generalDoctors;
    }
    
    public static void main(String[] args) throws SQLException  {
        DatabaseConnection app = new DatabaseConnection();
        Connection conn = app.connect();
        ArrayList<GeneralDoctor> general = new ArrayList<>();
        general = getGeneralDoctors(conn);
        
    }
    
}
