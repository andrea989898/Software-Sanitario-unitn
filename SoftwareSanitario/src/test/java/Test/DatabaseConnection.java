package Test;

import java.sql.*;
import java.util.Properties;
import java.util.*;
/**
 *
 * @author postgresqltutorial.com
 */
public class DatabaseConnection{
 
    private final String url = "jdbc:postgresql://localhost/SoftwareSanitario";
    private final String user = "postgres";
    private final String password = "61223180";
 
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        DatabaseConnection app = new DatabaseConnection();
        Connection conn = app.connect();
            try{
                drop(conn); //cancello tabella person
            }
            catch(SQLException e){
                System.out.println("Errore drop tabelle");
            }
            try{
                create(conn); //creo tabella person
            }
            catch(SQLException e){
                System.out.println("Errore create tabelle");
            }
            try{
                copy(conn);
            }catch(SQLException e){
                System.out.println("errore copia");
            }
            
            
    }
    
    static public void create(Connection conn) throws SQLException{
       Statement statement;
             
            String myUser = "CREATE TABLE Users(\n"+
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "User_type CHAR(20) NOT NULL);";
                    
            String myAllDoctors = "CREATE TABLE AllDoctors( \n" +
            "Name CHAR(40) NOT NULL,\n"+
            "Surname CHAR(40) NOT NULL,\n" +
            "Age INT NOT NULL, \n" +        
            "BirthDate DATE NOT NULL,\n"+
            "BirthPlace CHAR(40) NOT NULL, \n"+
            "Address CHAR(80) NOT NULL, \n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "FOREIGN KEY(SSD) REFERENCES Users(SSD), \n" +
            "Email CHAR(80) NOT NULL);";
            
            String myDoctor = "CREATE TABLE GeneralDoctors( \n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n" +
            "FOREIGN KEY(SSD) REFERENCES AllDoctors(SSD));";
            
            String myPatient = "CREATE TABLE Patients(\n" +
            "Name CHAR(40) NOT NULL,\n"+
            "Surname CHAR(40) NOT NULL,\n"+
            "Age INT NOT NULL, \n" +
            "BirthDate DATE NOT NULL, \n"+
            "BirthPlace CHAR(40) NOT NULL, \n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "Gender CHAR(2) NOT NULL,\n" +
            "Photos INT NOT NULL,\n" +
            "GeneralDoctor CHAR(16),\n" +
            "Email CHAR(80) NOT NULL,\n "+
            "FOREIGN KEY(SSD) REFERENCES Users(SSD));";
            //"FOREIGN KEY(GeneralDoctor) REFERENCES GeneralDoctors(SSD))
                    

            String mySpecialist = "CREATE TABLE Specialists( \n" +
            "Specialization CHAR(70), \n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL,\n"+  
            "FOREIGN KEY(SSD) REFERENCES AllDoctors(SSD));";

            String myExamination = "CREATE TABLE Examinations( \n" +
            "IDExamination INT PRIMARY KEY NOT NULL,\n" +
            "IDPatient CHAR(16) NOT NULL,\n" +
            "IDDoctor CHAR(16)NOT NULL,\n" +
            "Time TIME,\n" +
            "IsDone BOOLEAN NOT NULL,\n"+
            "IsSpecial BOOLEAN NOT NULL,\n" +
            "ExaminationDate DATE,\n"+
            "Argument CHAR(100),\n" +
            "FOREIGN KEY(IDDoctor) REFERENCES AllDoctors(SSD)," +
            "FOREIGN KEY(IDPatient) REFERENCES Patients(SSD));";


            String myTicket = "CREATE TABLE Tickets( \n"+
            "Code INT PRIMARY KEY NOT NULL,\n" +
            "IDExamination INT,\n" +
            "FOREIGN KEY (IDExamination) REFERENCES Examinations(IDExamination));";
            
            String myDrug = "CREATE TABLE Drugs( \n"+
            "Code INT PRIMARY KEY NOT NULL,\n"+
            "IsForPrescription BOOLEAN NOT NULL,\n"+
            "Name char(100))";
            
            String myPrescription = "CREATE TABLE Prescriptions( \n" +
            "Code INT PRIMARY KEY NOT NULL,\n"+
            "Exam_type CHAR(20) NOT NULL,\n"+
            "IDPatient CHAR(16) NOT NULL,\n"+
            "FOREIGN KEY(IDPatient) REFERENCES Patients(SSD));";

            String myRecipe = "CREATE TABLE Recipes( \n"+
            "Code INT PRIMARY KEY NOT NULL,\n"+
            "IDDrug INT,\n"+
            "FOREIGN KEY (IDDrug) REFERENCES Drugs(Code));";
            
            String myExam = "CREATE TABLE Exams( \n"+
            "Code INT PRIMARY KEY NOT NULL, \n" +
            "IDPrescription INT,\n" +
            "IDRecipe INT, \n" +
            "IDExamination INT,\n" +
            "Result char(100),\n" +
            "IsDone BOOLEAN NOT NULL,\n" +
            "FOREIGN KEY (IDExamination) REFERENCES Examinations(IDExamination),\n"+
            "FOREIGN KEY (IDPrescription) REFERENCES Prescriptions(Code),\n" +
            "FOREIGN KEY (IDRecipe) REFERENCES Recipes(Code));" ;
            
            statement = conn.createStatement();
            statement.executeUpdate(myUser);
            statement.executeUpdate(myAllDoctors);
            statement.executeUpdate(myDoctor);
            statement.executeUpdate(myPatient);               
            statement.executeUpdate(mySpecialist); 
            statement.executeUpdate(myExamination); 
            statement.executeUpdate(myTicket); 
            statement.executeUpdate(myDrug); 
            statement.executeUpdate(myPrescription); 
            statement.executeUpdate(myRecipe);
            statement.executeUpdate(myExam);
            statement.close();
       
    }
    
    static public void drop(Connection conn) throws SQLException{
       Statement statement = conn.createStatement();
       String myExam = "DROP TABLE Exams";      
       String myRecipe = "DROP TABLE Recipes"; 
       String myPrescription = "DROP TABLE Prescriptions"; 
       String myDrug = "DROP TABLE Drugs"; 
       String myTicket = "DROP TABLE Tickets"; 
       String myExamination = "DROP TABLE Examinations"; 
       String mySpecialist = "DROP TABLE Specialists"; 
       String myPatient = "DROP TABLE Patients"; 
       String myDoctor = "DROP TABLE GeneralDoctors"; 
       String myAllDoctors = "DROP TABLE AllDoctors"; 
       String myUser = "DROP TABLE Users";
       
       statement.executeUpdate(myExam);
       statement.executeUpdate(myRecipe);
       statement.executeUpdate(myPrescription);
       statement.executeUpdate(myDrug);
       statement.executeUpdate(myTicket);
       statement.executeUpdate(myExamination);
       statement.executeUpdate(mySpecialist);
       statement.executeUpdate(myPatient);
       statement.executeUpdate(myDoctor);
       statement.executeUpdate(myAllDoctors);
       statement.executeUpdate(myUser);
             
       statement.close();
    }
    
    static public void copy(Connection conn) throws SQLException{
        Statement statement= conn.createStatement();
        
        String copy_users = "COPY PUBLIC.USERS FROM 'C:\\Users\\Public\\Documents\\users.csv' DELIMITER ';' CSV;";
        String copy_allDoctors = " COPY PUBLIC.alldoctors FROM 'C:\\Users\\Public\\Documents\\allDoctors_data.csv' DELIMITER ';' CSV;";
        String copy_generalDoctors = "COPY PUBLIC.generaldoctors FROM 'C:\\Users\\Public\\Documents\\generalDoctors_data.csv' DELIMITER ';' CSV;";
        String copy_specialists = "COPY PUBLIC.specialists FROM 'C:\\Users\\Public\\Documents\\specialists_data.csv' DELIMITER ';' CSV;";
        String copy_patients = "COPY PUBLIC.patients FROM 'C:\\Users\\Public\\Documents\\patient_data.csv' DELIMITER ';' CSV;";
        String copy_examinations = "COPY PUBLIC.examinations FROM 'C:\\Users\\Public\\Documents\\examinations_data.csv' DELIMITER ';' CSV;";
        String copy_tickets = "COPY PUBLIC.tickets FROM 'C:\\Users\\Public\\Documents\\tickets_data.csv' DELIMITER ';' CSV;";
        String copy_drugs = "COPY PUBLIC.drugs FROM 'C:\\Users\\Public\\Documents\\drugs_data.csv' DELIMITER ';' CSV;";
        String copy_prescriptions = "COPY PUBLIC.prescriptions FROM 'C:\\Users\\Public\\Documents\\prescriptions_data.csv' DELIMITER ';' CSV;";
        String copy_recipes = "COPY PUBLIC.recipes FROM 'C:\\Users\\Public\\Documents\\recipes_data.csv' DELIMITER ';' CSV;";
        String copy_exams = "COPY PUBLIC.exams FROM 'C:\\Users\\Public\\Documents\\exams_data.csv' DELIMITER ';' CSV;";
        
        
        statement.executeUpdate(copy_users);
        statement.executeUpdate(copy_allDoctors);
        statement.executeUpdate(copy_generalDoctors);
        statement.executeUpdate(copy_specialists);
        statement.executeUpdate(copy_patients);
        statement.executeUpdate(copy_examinations);
        statement.executeUpdate(copy_tickets);
        statement.executeUpdate(copy_drugs);
        statement.executeUpdate(copy_prescriptions);
        statement.executeUpdate(copy_recipes);
        statement.executeUpdate(copy_exams);
        statement.close();
    }
}