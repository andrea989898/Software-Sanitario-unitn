package Test;

import static Test.InsertQuerys.insertImage;
import com.mycompany.softwaresanitario.crypt.CryptPassword;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public static void main(String[] args) throws SQLException {
      
        DatabaseConnection app = new DatabaseConnection();
        Connection conn = app.connect();
            try{
                drop(conn); //cancello tabella person
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
            try{
                create(conn); //creo tabella person
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
            try{
                copy(conn);                
                //fillImages(conn);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            try{
                cryptUsers(conn);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            try{
                cryptSsp(conn);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
    }
    
    static public void create(Connection conn) throws SQLException{
       Statement statement;
            
            String myRegions = "CREATE TABLE Regions(\n"+
            "Code INT PRIMARY KEY NOT NULL, \n" +
            "Name CHAR(30) NOT NULL);";
            
            String myProvinces = "CREATE TABLE Provinces(\n"+
            "Code INT PRIMARY KEY NOT NULL, \n" + 
            "Name CHAR(30) NOT NULL, \n" +
            "IDRegion INT NOT NULL,\n" +
            "FOREIGN KEY(IDRegion) references Regions(Code));";
            
            String myCities = "CREATE TABLE Cities(\n"+
            "Code INT PRIMARY KEY NOT NULL, \n" + 
            "Name CHAR(100) NOT NULL, \n" +
            "IDProvince INT NOT NULL,\n" +
            "FOREIGN KEY(IDProvince) references Provinces(Code));";
            
            String mySSP = "CREATE TABLE SSP(\n" +
            "Code CHAR(16) NOT NULL PRIMARY KEY,\n"+
            "email CHAR(40) NOT NULL," +
            "password CHAR(100) NOT NULL," +
            "IDProvince INT NOT NULL," +
            "FOREIGN KEY(IDProvince) references Provinces(Code));";
                    
            String myAllUsers = "CREATE TABLE Users ( \n" +
            "Name CHAR(40) NOT NULL,\n"+
            "Surname CHAR(40) NOT NULL,\n" +
            "Age INT NOT NULL, \n" +        
            "BirthDate DATE NOT NULL,\n"+
            "Birth_City_ID INT NOT NULL, \n"+
            "Gender CHAR(2) NOT NULL,\n" +
            "Address CHAR(80) NOT NULL, \n" +
            "City_ID INT NOT NULL, \n" +
            "Code CHAR(16) NOT NULL PRIMARY KEY, \n" +
            "Email CHAR(40) NOT NULL, \n" +
            "Password CHAR(100) NOT NULL, \n" +
            "FOREIGN KEY(Birth_City_ID) references Cities(Code), \n" +
            "FOREIGN KEY(City_ID) references Cities(Code));";

       
            String myAllDoctors = "CREATE TABLE AllDoctors( \n" + 
            "studio_Address CHAR(80) NOT NULL, \n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "FOREIGN KEY(SSD) REFERENCES Users(Code));";
            
            String myDoctor = "CREATE TABLE GeneralDoctors( \n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n" +
            "FOREIGN KEY(SSD) REFERENCES AllDoctors(SSD));";
            
            String myPatient = "CREATE TABLE Patients(\n" +
            "SSD CHAR(16) PRIMARY KEY NOT NULL, \n"+
            "GeneralDoctor CHAR(16) NOT NULL,\n" +
            "FOREIGN KEY(GeneralDoctor) REFERENCES GeneralDoctors(SSD),\n"+
            "FOREIGN KEY(SSD) REFERENCES Users(Code));";
             
            String myImage = "CREATE TABLE Images("
                    + "data CHAR(255) NOT NULL,\n"
                    + "IDPatient CHAR(16)NOT NULL,\n"
                    + "Data_photo CHAR(16),\n"
                    + "photo_num INT NOT NULL,\n"
                    + "FOREIGN KEY(IDPatient) REFERENCES Patients(SSD));";
                        
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
            "ExaminationDate DATE,\n"+
            "Argument CHAR(100),\n" +
            "Report CHAR(255),\n" + 
            "FOREIGN KEY(IDDoctor) REFERENCES AllDoctors(SSD),\n" +
            "FOREIGN KEY(IDPatient) REFERENCES Patients(SSD));";

            String myTicket = "CREATE TABLE Tickets( \n"+
            "Code INT PRIMARY KEY NOT NULL,\n" +
            "Cost INT NOT NULL, \n" +
            "Date DATE NOT NULL, \n" +
            "ExpirationDate DATE NOT NULL, \n" +
            "IDExamination INT,\n" +
            "IDExam INT, \n" +
            "IDPatient CHAR(16) NOT NULL, \n" +
            "IsPaid BOOLEAN NOT NULL, \n" +
            "FOREIGN KEY (IDPatient) REFERENCES Patients(ssd), \n" +
            "FOREIGN KEY (IDExam) REFERENCES Exams(Code), \n" +
            "FOREIGN KEY (IDExamination) REFERENCES Examinations(IDExamination));";
            
            String myDrug = "CREATE TABLE Drugs( \n"+
            "Code INT PRIMARY KEY NOT NULL,\n"+
            "Name char(100))";
            
            String myPrescription = "CREATE TABLE Prescriptions( \n" +
            "Code INT PRIMARY KEY NOT NULL,\n"+
            "Analysis CHAR(100) NOT NULL,\n"+
            "IDExam INT,\n"+
            "IDExamination INT,\n" +
            "IDRecipe INT,\n" +
            "IDDoctor CHAR(16) NOT NULL,\n"+
            "IDPatient CHAR(16) NOT NULL,\n"+
            "Date DATE NOT NULL,\n"+
            "FOREIGN KEY(IDDoctor) REFERENCES AllDoctors(SSD),\n" +
            "FOREIGN KEY(IDPatient) REFERENCES Patients(SSD),\n" +
            "FOREIGN KEY(IDRecipe) REFERENCES Recipes(Code),\n" +
            "FOREIGN KEY(IDExamination) REFERENCES Examinations(IDExamination),\n" +
            "FOREIGN KEY(IDExam) REFERENCES Exams(Code));";     

            String myRecipe = "CREATE TABLE Recipes( \n"+
            "Code INT PRIMARY KEY NOT NULL);";          
            
            String myRecipeDrug =  "CREATE TABLE DrugsRecipes( \n"+
            "IDRecipe INT NOT NULL,\n"+
            "IDDrug INT NOT NULL,\n"+
            "FOREIGN KEY(IDRecipe) REFERENCES Recipes(Code),\n" +
            "FOREIGN KEY(IDDrug) REFERENCES Drugs(Code));";
            
            String myExam = "CREATE TABLE Exams( \n"+
            "Code INT PRIMARY KEY NOT NULL, \n" +
            "IDDoctor CHAR(16),\n" +
            "SspCode CHAR(16),\n" +
            "Result char(100),\n" +
            "Time TIME,\n" +
            "IsDone BOOLEAN NOT NULL,\n"+
            "ExaminationDate DATE,\n"+
            "IDPatient CHAR(16) NOT NULL, \n" +
            "IsRecall BOOLEAN, \n" +
            "FOREIGN KEY(IDDoctor) REFERENCES AllDoctors(SSD), \n" +
            "FOREIGN KEY(SspCode) REFERENCES SSP(Code), \n" +
            "FOREIGN KEY (IDPatient) REFERENCES Patients(ssd));";
            
            
            statement = conn.createStatement();
            
            
            statement.executeUpdate(myRegions);
            statement.executeUpdate(myProvinces);
            statement.executeUpdate(myCities);
            
            statement.executeUpdate(mySSP);


            statement.executeUpdate(myAllUsers);

            statement.executeUpdate(myAllDoctors);
            statement.executeUpdate(myDoctor);
            statement.executeUpdate(myPatient); 
            statement.executeUpdate(myImage);
            statement.executeUpdate(mySpecialist); 
            statement.executeUpdate(myExamination);
            statement.executeUpdate(myExam);
            statement.executeUpdate(myDrug); 
            statement.executeUpdate(myRecipe);
            statement.executeUpdate(myRecipeDrug);
            statement.executeUpdate(myTicket); 
            statement.executeUpdate(myPrescription);
            
            
            statement.close();
       
    }
    
    static public void drop(Connection conn) throws SQLException{
       Statement statement = conn.createStatement();
       String myRegions = "DROP TABLE Regions CASCADE";   
       String myProvinces = "DROP TABLE Provinces CASCADE";   
       String myCities = "DROP TABLE Cities CASCADE"; 
       String myExam = "DROP TABLE Exams CASCADE";      
       String myRecipe = "DROP TABLE Recipes CASCADE"; 
       String myPrescription = "DROP TABLE Prescriptions CASCADE"; 
       String myDrug = "DROP TABLE Drugs CASCADE"; 
       String myDrugRecipe = "DROP TABLE DrugsRecipes CASCADE"; 
       String myTicket = "DROP TABLE Tickets CASCADE"; 
       String myExamination = "DROP TABLE Examinations CASCADE"; 
       String mySpecialist = "DROP TABLE Specialists CASCADE"; 
       String myImage = "DROP TABLE Images CASCADE";
       String myPatient = "DROP TABLE Patients CASCADE"; 
       String myDoctor = "DROP TABLE GeneralDoctors CASCADE"; 
       String myAllDoctors = "DROP TABLE AllDoctors CASCADE"; 
       String myAllUsers = "DROP TABLE Users CASCADE"; 
       String mySsp = "DROP TABLE Ssp CASCADE"; 

       statement.executeUpdate(myRegions);
       statement.executeUpdate(myProvinces);
       statement.executeUpdate(myCities);
       statement.executeUpdate(myExam);
       statement.executeUpdate(myRecipe);
       statement.executeUpdate(myPrescription);
       statement.executeUpdate(myDrug);
       statement.executeUpdate(myDrugRecipe);
       statement.executeUpdate(myTicket);
       statement.executeUpdate(myExamination);
       statement.executeUpdate(mySpecialist);
       statement.executeUpdate(myImage);
       statement.executeUpdate(myPatient);
       statement.executeUpdate(myDoctor);
       statement.executeUpdate(myAllDoctors); 
       statement.executeUpdate(myAllUsers); 
       statement.executeUpdate(mySsp);
             
       statement.close();
    }
    
    public static void insertImage(Connection conn, String folder, String idP, String photo_date, int num) throws SQLException{
        Statement statement = conn.createStatement();
        String query = "INSERT INTO Images(data, idPatient, data_photo, photo_num) VALUES('"+folder+"','"+ idP + "','"+photo_date+"',"+num+")";
        statement.executeUpdate(query);
    }

    
    public static void fillImages(Connection conn) throws SQLException{
    
    Integer x = 1; int y=5;
    Data d = new Data(2010, 10, 10);
    d.currentData(d); 
    String data_photo = d.toString(d);
        while(x<=100 ){
            while(y>=1){
                if(y!=5) { data_photo = "201"+y+"-10-10";}
                insertImage(conn, 
                            "patient_image.jpg", 
                            x.toString(),
                            data_photo,
                            y
                            );
                y--;
                
            }
            data_photo = d.toString(d);
            y=5;
        x++;
        }
    }
    
    
    static public void copy(Connection conn) throws SQLException{
        Statement statement= conn.createStatement();
        
        String copy_regions = "COPY PUBLIC.REGIONS FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\Regions_data.csv' DELIMITER ';' CSV;";
        String copy_provinces = "COPY PUBLIC.PROVINCES FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\Provinces_data.csv' DELIMITER ';' CSV;";
        String copy_cities = "COPY PUBLIC.CITIES FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\Cities_data.csv' DELIMITER ';' CSV ENCODING 'windows-1251';";
        String copy_users = "COPY PUBLIC.USERS FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\users.csv' DELIMITER ';' CSV;";
        String copy_allDoctors = " COPY PUBLIC.alldoctors FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\allDoctors_data.csv' DELIMITER ';' CSV;";
        String copy_generalDoctors = "COPY PUBLIC.generaldoctors FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\generalDoctors_data.csv' DELIMITER ';' CSV;";
        String copy_specialists = "COPY PUBLIC.specialists FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\specialists_data.csv' DELIMITER ';' CSV;";
        String copy_patients = "COPY PUBLIC.patients FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\patient_data.csv' DELIMITER ';' CSV;";
        String copy_examinations = "COPY PUBLIC.examinations FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\examinations_data.csv' DELIMITER ';' CSV;";
        String copy_tickets = "COPY PUBLIC.tickets FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\tickets_data.csv' DELIMITER ';' CSV;";
        String copy_drugs = "COPY PUBLIC.drugs FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\drugs_data.csv' DELIMITER ';' CSV;";
        String copy_prescriptions = "COPY PUBLIC.prescriptions FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\prescriptions_data.csv' DELIMITER ';' CSV;";
        String copy_recipes = "COPY PUBLIC.recipes FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\recipes_data.csv' DELIMITER ';' CSV;";
        String copy_drugrecipes = "COPY PUBLIC.DrugsRecipes FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\recipeDrugs_data.csv' DELIMITER ';' CSV;";
        String copy_exams = "COPY PUBLIC.exams FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\exams_data.csv' DELIMITER ';' CSV;";
        String copy_images = "COPY PUBLIC.images FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\images_data.csv' DELIMITER ';' CSV;";
        String copy_ssp = "COPY PUBLIC.ssp FROM 'C:\\Users\\Public\\Documents\\dbIPW19\\ssp_data.csv' DELIMITER ';' CSV;";
        
        statement.executeUpdate(copy_regions);
        statement.executeUpdate(copy_provinces);
        statement.executeUpdate(copy_cities);
        statement.executeUpdate(copy_users);
        statement.executeUpdate(copy_allDoctors);
        statement.executeUpdate(copy_generalDoctors);
        statement.executeUpdate(copy_specialists);
        statement.executeUpdate(copy_patients);
        statement.executeUpdate(copy_drugs);
        statement.executeUpdate(copy_examinations);
        statement.executeUpdate(copy_exams);
        statement.executeUpdate(copy_recipes);
        statement.executeUpdate(copy_prescriptions);
        statement.executeUpdate(copy_drugrecipes);
        statement.executeUpdate(copy_tickets);
        statement.executeUpdate(copy_images);
        statement.executeUpdate(copy_ssp);
        
       
        statement.close();
    }
    
    static public void cryptUsers(Connection con) throws SQLException{
        ArrayList<String> emails = new ArrayList<String>();
        ArrayList<String> cryptatedPasswords = new ArrayList<String>();
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Users ");
        ResultSet rs = stm.executeQuery();
               
        while (rs.next()) {

            emails.add(rs.getString("email"));
            //System.out.println(rs.getString("password").substring(0, 8));
            cryptatedPasswords.add(CryptPassword.hashPassword(rs.getString("password").substring(0, 8)));
        }

                
           
        stm = con.prepareStatement("UPDATE Users SET password = ? WHERE email = ?");
        for(int i=0;i<emails.size();i++){
            stm.setString(1, cryptatedPasswords.get(i));
            stm.setString(2, emails.get(i));

            stm.executeUpdate();
        }
        
    }
    
    
    static public void cryptSsp(Connection con) throws SQLException{
        ArrayList<String> emails = new ArrayList<String>();
        ArrayList<String> cryptatedPasswords = new ArrayList<String>();
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM ssp ");
        ResultSet rs = stm.executeQuery();
               
        while (rs.next()) {

            emails.add(rs.getString("email"));
            //System.out.println(rs.getString("password").substring(0, 8));
            cryptatedPasswords.add(CryptPassword.hashPassword(rs.getString("password").substring(0, 8)));
        }

                
           
        stm = con.prepareStatement("UPDATE ssp SET password = ? WHERE email = ?");
        for(int i=0;i<emails.size();i++){
            stm.setString(1, cryptatedPasswords.get(i));
            stm.setString(2, emails.get(i));

            stm.executeUpdate();
        }
        
    }
}