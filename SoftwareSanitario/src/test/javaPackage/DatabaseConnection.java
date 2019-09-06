package javaPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DatabaseConnection {
    Connection connection;
    String url = "jdbc:postgresql://localhost:5432/SoftwareSanitario";
    String user = "postgres";
    String password = "0000";

    public Connection dbConnection() {

        try {

        Class.forName("org.postgresql.Driver");

        } catch(ClassNotFoundException e ){
                  e.getMessage();
        }

        try {
           connection = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }


        public static void main(String[] args) {
            DatabaseConnection db = new DatabaseConnection();
            db.dbConnection();
        }
   
}