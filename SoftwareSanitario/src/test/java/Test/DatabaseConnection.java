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
    private final String password = "0000";
 
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
                create(conn); //creo tabella person                
            }
            catch(SQLException e){
                System.out.println("Errore create tabelle");
            }
            
            /*try{
                drop(conn); //cancello tabella person
            }
            catch(SQLException e){
                System.out.println("Errore drop tabelle");
            }*/
    }
    
    static public void create(Connection conn) throws SQLException{
       Statement statement;
                        
            String myAllDoctors = "CREATE TABLE allDoctors( \n" +
            "Nome CHAR(40) NOT NULL,\n"+
            "Cognome CHAR(40) NOT NULL,\n" +
            "Nascita DATE NOT NULL,\n"+
            "LuogoNascita CHAR(40) NOT NULL, \n"+
            "CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "Email CHAR(80) NOT NULL;";
            
            String myPatient = "CREATE TABLE Patient(\n" +
            "Nome CHAR(40) NOT NULL,\n"+
            "Cognome CHAR(40) NOT NULL,\n"+
            "Nascita DATE NOT NULL, \n"+
            "LuogoNascita CHAR(40) NOT NULL, \n" +
            "CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "Sesso CHAR(2) NOT NULL,\n" +
            "FotoPaziente INT NOT NULL,\n" +
            "MedicoBase CHAR(16),\n" +
            "Email CHAR(80) NOT NULL); "+
            "FOREIGN KEY(MedicoBase) REFERENCES MediciBase(CodiceFiscale));";



            String myDoctor = "CREATE TABLE Doctors( \n" +
            "CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL, \n" +
            "FOREIGN KEY(CodiceFiscale) REFERENCES Medici(CodiceFiscale));";

            String mySpecialist = "CREATE TABLE Specialist( \n" +
            "Specializzazione CHAR(70), \n" +
            "CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL,\n"+  
            "FOREIGN KEY(CodiceFiscale) REFERENCES Medici(CodiceFiscale));";

            String myExamination = "CREATE TABLE Examinations( \n" +
            "IDVisita INT PRIMARY KEY NOT NULL,\n" +
            "IDPaziente CHAR(16) NOT NULL,\n" +
            "IDMedico CHAR(16)NOT NULL,\n" +
            "Orario TIME,\n" +
            "Luogo CHAR(20),\n" +
            "IsDone BOOLEAN NOT NULL,\n"+
            "IsSpecial BOOLEAN NOT NULL,\n" +
            "DataVisita DATE,\n"+
            "Argomento CHAR(100),\n" +
            "FOREIGN KEY(IDMedico) REFERENCES Medici(CodiceFiscale)," +
            "FOREIGN KEY(IDPaziente) REFERENCES Pazienti(CodiceFiscale));";


            String myTicket = "CREATE TABLE Tickets( \n"+
            "Codice INT PRIMARY KEY NOT NULL,\n" +
            "IDVisita INT,\n" +
            "FOREIGN KEY (IDVisita) REFERENCES Visite(IDVisita));";
            
            String myDrugs = "CREATE TABLE Drugs( \n"+
            "Code INT PRIMARY KEY NOT NULL,\n"+
            "IsForPrescription BOOLEAN NOT NULL,\n"+
            "Name char(100))";
            
            String myPrescription = "CREATE TABLE Prescriptions( \n" +
            "Code INT PRIMARY KEY NOT NULL,\n" +
            "IDDrug INT, \n" +
            "FOREIGN KEY (IDMedicina) REFERENCES Medicine(Codice));";

            String myRecipes = "CREATE TABLE Recipes( \n"+
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
            "FOREIGN KEY (IDRecipe) REFERENCES Ricipes(Code));" ;
            
            statement = conn.createStatement();
            statement.executeUpdate(myPatient);   
            statement.close();
       
    }
    
    static public void drop(Connection conn) throws SQLException{
       Statement stmt = conn.createStatement();
       String query = "DROP TABLE" + " Pazienti";           
       stmt.executeUpdate(query);
       stmt.close();
    }
}