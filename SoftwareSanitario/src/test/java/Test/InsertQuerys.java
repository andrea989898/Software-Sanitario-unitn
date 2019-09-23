/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Francesco
 */
public class InsertQuerys{

    private final String url = "jdbc:postgresql://localhost/SoftwareSanitario";
    private final String user = "postgres";
    private final String password = "0000";
    
    public static void insertPrescription(Connection conn, String idP,
                                    int code, String exam_type) throws SQLException{
        code = checkCode(conn, code, "prescriptions");
        String myInsert = "insert into prescriptions (code, exam_type, idpatient)\n"+
                         "values ("+code+","+"'"+exam_type+"'"+","+"'"+idP+"')";
        PreparedStatement stm = conn.prepareStatement(myInsert);
        stm.executeUpdate();
    }
    
   public static void insertExam(Connection conn, String idP,
                                    int code, int idp, int idr, String r) throws SQLException{
        code = checkCode(conn, code, "exams");
        String myInsert = "insert into exams \n"+
                         "values ("+code+","+idp+","+idr +","+"'"+r+"',"+"false," + "'"+idP+"');";
        PreparedStatement stm = conn.prepareStatement(myInsert);
        stm.executeUpdate();
    }
    
    public static void insertRecipe(Connection conn, String idP,
                                    int code, int iddrug) throws SQLException{
        code = checkCode(conn, code, "recipes");
        String myInsert = "insert into recipes (code, iddrug, idpatient)\n"+
                         "values ("+code+","+iddrug+","+"'"+idP+"')";
        PreparedStatement stm = conn.prepareStatement(myInsert);

        stm.executeUpdate();
    }
    
    public static void insertTicket(Connection conn, String idP,
                                    int code, int c, int idex) throws SQLException{
        code = checkCode(conn, code, "tickets");
        Data d= new Data(2019, 10, 10); d.currentData(d); String data = d.toString(d);
        Data exd = new Data(2019, 10,10); exd.expirationData(exd); String expiration_data = exd.toString(exd);
        String myInsert = "insert into tickets (code, cost, date, expirationdate, idexamination, idpatient, ispaid)\n"+
                         "values ("+code+","+c+",'"+data+"','"+ expiration_data+"'," +idex+","+"'"+idP+"',false)";
        PreparedStatement stm = conn.prepareStatement(myInsert);

        stm.executeUpdate();
    }
    
    public static int checkCode(Connection conn, int code, String table) throws SQLException{
        String checkCode = "select r.code from " + table +" r";
        PreparedStatement pst= conn.prepareStatement(checkCode);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            if( code <= rst.getInt("code")){
            code = rst.getInt("code")+1;}
        }
        return code;
    }
    
    public static void insertImage(Connection conn, String folder, String idP, String photo_date, int num) throws SQLException{
        String query = "INSERT INTO images(data, idPatient, photo_date, photo_num) VALUES(?,'"+ idP + "','"+photo_date+"',"+num+")";
        PreparedStatement pst = conn.prepareStatement(query);

        File img = new File(folder);
        try (FileInputStream fin = new FileInputStream(img)) {
            pst.setBinaryStream(1, fin, (int) img.length());
            pst.executeUpdate();
        }catch (IOException ex){
            System.out.println("error");
        }
    }
    
    
    
    public static void main(String[] args) throws SQLException, ParseException {        
        DatabaseConnection app = new DatabaseConnection();
        Connection conn = app.connect();
        insertPrescription(conn, "50", 1, "dsadasd");
        insertRecipe(conn, "50", 1, 1);
        insertExam(conn, "50", 1, 1, 1, "dsadada");
        insertTicket(conn, "50", 1, 50, 1);
        //insertImage(conn, "D:\\Francesco\\OneDrive - Università degli Studi di Trento\\UniTN\\2018-2019\\corsi\\Introduzione prog. Web\\progetto\\Software-Sanitario-unitn\\SoftwareSanitario\\database\\patient_image.jpg", "2");
    }
}
