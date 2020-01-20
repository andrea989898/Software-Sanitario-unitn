<%--
    Document   : saluto
    Created on : 11 set 2019, 15:59:11
    Author     : franc
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.User"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.PatientDAO"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.Patient"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.GeneralDoctorDAO"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.SpecialistDAO"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.Specialist"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.Exam"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.dao.CityDAO"%>
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.City"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Software sanitario: Private Area</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <style>
            body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", sans-serif}
            #myInput {
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }
            .upload-btn-wrapper {
                position: relative;
                overflow: hidden;
                display: inline-block;
            }

            .btnfile {
                border: 2px solid gray;
                color: gray;
                background-color: white;
                padding: 8px 20px;
                border-radius: 8px;
                font-size: 15px;
            }

            .upload-btn-wrapper input[type=file] {
                font-size: 100px;
                position: absolute;
                left: 0;
                top: 0;
                opacity: 0;
              }
        </style>
    </head>
    <body>
        <header class="w3-container w3-blue">
            <h2>Software sanitario</h2>
        </header>
        <jsp:scriptlet>
             response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        </jsp:scriptlet>
        <!-- Sidebar/menu -->
        <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
            <div class="w3-container">
                <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
                  <i class="fa fa-remove"></i>
                </a>
                <img src="${avatarPath}" style="width:45%;" class="w3-round"><br><br>
                <h4><b>${user.getName()} ${user.getSurname()}</b></h4>
                <h5>
                    GeneralDoctor: ${generaldoctorpatient.getName()} ${generaldoctorpatient.getSurname()}<br>
                </h5>
            </div>
            <div class="w3-bar-block">
                <c:choose>
                    <c:when test="${!empty generalDoctor}">
                        <a href="homeGeneralDoctor.html" onclick="w3_close()" class="w3-bar-item w3-button w3-padding "><i class="fa fa-user fa-fw w3-margin-right"></i>Dashboard doctor</a> 
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${!empty specialist}">
                        <a href="homeSpecialist.html" onclick="w3_close()" class="w3-bar-item w3-button w3-padding "><i class="fa fa-user fa-fw w3-margin-right"></i>Dashboard specialist</a> 
                    </c:when>   
                </c:choose>
            </div>
            <div class="w3-container">
                <div class="w3-section w3-padding-16">
                    <button data-toggle="modal" data-target="#changePassword" class="w3-button w3-black"><i class="fa fa-refresh w3-margin-right"></i>Cambia password</button>
                    <br>
                    <a href="logout.handler" class="w3-container"><button class="w3-button w3-black"><i class="fa fa-close w3-margin-right"></i>Esci</button></a>
                </div> 
            </div>
        </nav>
                
        <div class="modal fade" id="changePassword" style="display:none" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Change password:</h4>
                    </div>
                    <div class="modal-body">
                        <jsp:scriptlet>
                            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
                       </jsp:scriptlet>
                       <form class="w3-container " method="POST" action="changePassoword.handler">
                           <label class="w3-text-teal"><b>New password:</b></label>
                           <input class="w3-input w3-border w3-light-grey" type="password" name="password" id="password" placeholder="password">
                           <br>
                           <button class="w3-button w3-round-large w3-blue" type="submit">Submit</button>
                           <button class="w3-button w3-round-large w3-blue" type="reset">Reset</button>
                       </form>
                    </div>
                    <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>
        
        <div class="w3-main" style="margin-left:300px">
        
            <div class="w3-bar w3-white">
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamExams')">Exams</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamExaminations')">Examinations</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamTickets')">Tickets</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('allRecipes')">Recipes</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamGeneralDoctor')">Your general doctor</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamUsers')">Personal information</button></h2>
            </div>
                    
            <div id="screamExams" class="w3-container dash">
                <h3>Future exams:</h3>
                <c:choose>
                            <c:when test="${empty screamExams}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have exams to do.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <table class="w3-table w3-bordered">
                                        <tr>
                                             <th>Exam Code</th>
                                             <th>Exam Date</th>
                                             <th>Done</th>
                                             <th>Get the pdf</th>
                                        </tr>
                                    <c:forEach var="exam" items="${screamExams}">
                                        <tr>
                                            <td>${exam.getCode()}</td>
                                            <td>${exam.getExaminationDate()}</td>
                                            <td>${exam.getIsDone()}</td>
                                            <td>
                                                <a href="exportToPDF.handler?id=exam.getCode()">
                                                    <button class="w3-button w3-round-large w3-blue">go</button>
                                                </a>
                                            </td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
                <br>
                <button class="w3-button w3-round-large w3-blue" onclick="openExams()">View all exams</button>
                
                <div id="Exams" class="w3-container dash" style="display:none">
                <h4>Exams:</h4>
                <c:choose>
                            <c:when test="${empty exams}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have exams.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <button class="w3-button w3-round-large w3-blue" onclick="sortTable('examTable')">Sort by date</button>
                                <table class="w3-table w3-bordered" id="examTable">
                                        <tr>
                                             <th>Exam Code</th>
                                             <th>Exam Date</th>
                                             <th>Done</th>
                                             <th>Get the pdf</th>
                                        </tr>
                                    <c:forEach var="exam" items="${exams}">
                                        <tr>
                                            <td>${exam.getCode()}</td>
                                            <td>${exam.getExaminationDate()}</td>
                                            <td>${exam.getIsDone()}</td>
                                            <td>
                                                <a href="exportToPDF.handler?id=${exam.getCode()}&type=exam">
                                                    <button class="w3-button w3-round-large w3-blue">go</button>
                                                </a>
                                            </td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
                </div>
                
            </div>     

            <div id="screamExaminations" class="w3-container dash" style="display:none">
                <h3>Future examinations:</h3>
                <c:choose>
                            <c:when test="${empty screamExaminations}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have examinations to do.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <table class="w3-table w3-bordered">
                                        <tr>
                                             <th>Examination Code</th>
                                             <th>Examination Date</th>
                                             <th>Done</th>
                                        </tr>
                                    <c:forEach var="examination" items="${screamExaminations}">
                                        <tr>
                                            <td>${examination.getSSD()}</td>
                                            <td>${examination.getExaminationDate()}</td>
                                            <td>${examination.getIsDone()}</td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
                <br>
                <button class="w3-button w3-round-large w3-blue" onclick="openExaminations()">View all examinations</button>
                
                <div id="Examinations" class="w3-container dash" style="display:none">
                <h4>Examinations:</h4>
                <c:choose>
                            <c:when test="${empty examinations}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have examinations.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <button class="w3-button w3-round-large w3-blue" onclick="sortTable('examinationTable')">Sort by date</button>
                                <table class="w3-table w3-bordered" id="examinationTable">
                                        <tr>
                                             <th>Examination Code</th>
                                             <th>Examination Date</th>
                                             <th>Done</th>
                                        </tr>
                                    <c:forEach var="examination" items="${examinations}">
                                        <tr>
                                            <td>${examination.getSSD()}</td>
                                            <td>${examination.getExaminationDate()}</td>
                                            <td>${examination.getIsDone()}</td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
                </div>
            </div>

            <div id="screamTickets" class="w3-container dash" style="display:none">
                <h3>Tickets to pay:</h3>
                <c:choose>
                            <c:when test="${empty screamTickets}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have tickets to pay.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <table class="w3-table w3-bordered">
                                        <tr>
                                             <th>Ticket Code</th>
                                             <th>Ticket Date</th>
                                             <th>Expiration Date</th>
                                             <th>Cost</th>
                                             <th>Paid</th>
                                             <th>Get the pdf</th>
                                        </tr>
                                    <c:forEach var="ticket" items="${screamTickets}">
                                        <tr>
                                            <td>${ticket.getCode()}</td>
                                            <td>${ticket.getDate()}</td>
                                            <td>${ticket.getExpirationDate()}</td>
                                            <td>${ticket.getCost()}</td>
                                            <td>${ticket.getIsPaid()}</td>
                                            <td>
                                                <%--<button class="w3-button w3-round-large w3-blue">--%>
                                                    <a href="exportToPDF.handler?id=${ticket.getCode()}&type=ticket">
                                                        <button class="w3-button w3-round-large w3-blue">Go</button>
                                                    </a>
                                                <%--</button>--%>
                                            </td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
                <br>
                <button class="w3-button w3-round-large w3-blue" onclick="openTickets()">View all tickets</button>
                
                <div id="Tickets" class="w3-container dash" style="display:none">
                <h4>Tickets:</h4>
                <c:choose>
                            <c:when test="${empty tickets}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have tickets.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <button class="w3-button w3-round-large w3-blue" onclick="sortTable('ticketTable')">Sort by ticket date</button>
                                <table class="w3-table w3-bordered" id="ticketTable">
                                        <tr>
                                             <th>Ticket Code</th>
                                             <th>Ticket Date</th>
                                             <th>Expiration Date</th>
                                             <th>Cost</th>
                                             <th>Paid</th>
                                             <th>Get the pdf</th>
                                        </tr>
                                    <c:forEach var="ticket" items="${tickets}">
                                        <tr>
                                            <td>${ticket.getCode()}</td>
                                            <td>${ticket.getDate()}</td>
                                            <td>${ticket.getExpirationDate()}</td>
                                            <td>${ticket.getCost()}</td>
                                            <td>${ticket.getIsPaid()}</td>
                                            <td>
                                                <%--<button class="w3-button w3-round-large w3-blue" data-target="exportToPDF.handler?id=${ticket.getCode()}?type=ticket">--%>
                                                    <a href="exportToPDF.handler?id=${ticket.getCode()}&type=ticket">
                                                        <button class="w3-button w3-round-large w3-blue">Go</button>
                                                    </a>
                                                <%--</button>--%>
                                            </td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
                </div>
                
            </div>
            
            <div id="allRecipes" class="w3-container dash" style="display:none">
                <h3>Recipe prescribed:</h3>
                <c:choose>
                            <c:when test="${empty prescriptions}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have prescriptions.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <table class="w3-table w3-bordered" id="ticketTable">
                                        <tr>
                                             <th>Prescription Code</th>
                                             <th>Analisys</th>
                                             <th>Date</th>
                                             <th>Doctor ssd</th>
                                             <th>Patient ssd</th>
                                             <th>Get the pdf</th>
                                        </tr>
                                    <c:forEach var="prescription" items="${prescriptions}">
                                        <tr>
                                            <td>${prescription.getCode()}</td>
                                            <td>${prescription.getAnalisys()}</td>
                                            <td>${prescription.getDate()}</td>
                                            <td>${prescription.getIddoctor()}</td>
                                            <td>${prescription.getIdpatient()}</td>
                                            <td>
                                                <%--<button class="w3-button w3-round-large w3-blue" data-target="exportToPDF.handler?id=${ticket.getCode()}?type=ticket">--%>
                                                    <a href="exportToPDF.handler?id=${prescription.getCode()}&type=prescription">
                                                        <button class="w3-button w3-round-large w3-blue">Go</button>
                                                    </a>
                                                <%--</button>--%>
                                            </td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
            </div>
            
            <div id="screamGeneralDoctor" class="w3-container dash" style="display:none">
                    <h3>Your general doctor</h3>
                    <div>
                        
                        <table class="w3-table w3-bordered">
                            <tr>
                                <td>
                                    Name : 
                                </td>
                                <td>
                                    ${generaldoctorpatient.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Surname :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getSurname()}
                                </td>
                            </tr>      
                            <tr>
                                <td>
                                    Age :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getAge()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Birthdate :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getBirthdate()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Address :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getAddress()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Gender :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getGender()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Cf :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getCf()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Email :
                                </td>
                                <td>
                                    ${generaldoctorpatient.getEmail()}
                                </td>
                            </tr>
                        </table>                     
                        <h3><button class="w3-bar-item w3-button w3-blue w3-round-large" onclick="openDashGeneralDoctor('screamNewGeneralDoctor')">Change general doctor</button></h3>
                        <div id="screamNewGeneralDoctor" class="w3-container dash" style="display:none">
                            
                            <table class="w3-table w3-bordered">
                                <c:choose>
                                    <c:when test="${empty AllGeneralDoctor}">
                                        <div class="card">
                                            <div class="card-body">
                                                You can't change general doctor
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="w3-bordered" type="text" id="myInput" onkeyup="search()" placeholder="Search for names..">
                                        <table class="w3-table w3-bordered" id="myTable">
                                                <tr>
                                                     <th>Name</th>
                                                     <th>Surname</th>
                                                     <th>Age</th>
                                                     <th>Studio address</th>
                                                </tr>
                                            <c:forEach var="doctor" items="${AllGeneralDoctor}">
                                                <tr>
                                                    <td>${doctor.getName()}</td>
                                                    <td>${doctor.getSurname()}</td>
                                                    <td>${doctor.getAge()}</td>
                                                    <td>${doctor.getAddress()}</td>
                                                    <td>
                                                        <a href="changeGeneralDoctor.handler?id=${doctor.getCf()}&ssd=${user.getCf()}">
                                                            <button class="w3-button w3-round-large w3-blue">Change</button>
                                                        </a>
                                                    </td>
                                                </tr>          
                                            </c:forEach>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                        </div>
                         
                    </div>
            </div>
            
            <div id="screamUsers" class="w3-container dash" style="display:none">
                <h3>Personal information</h3>
                    <div>
                        <table class="w3-table w3-bordered">
                            <tr>
                                <td>
                                    Name : 
                                </td>
                                <td>
                                    ${user.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Surname :
                                </td>
                                <td>
                                    ${user.getSurname()}
                                </td>
                            </tr>      
                            <tr>
                                <td>
                                    Age :
                                </td>
                                <td>
                                    ${user.getAge()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Birthdate :
                                </td>
                                <td>
                                    ${user.getBirthdate()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Birthplace :
                                </td>
                                <td>
                                    ${birth_city_Patient.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Address :
                                </td>
                                <td>
                                    ${user.getAddress()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    City :
                                </td>
                                <td>
                                    ${city_Patient.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Gender :
                                </td>
                                <td>
                                    ${user.getGender()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Codice fiscale :
                                </td>
                                <td>
                                    ${user.getCf()}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Email :
                                </td>
                                <td>
                                    ${user.getEmail()}
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br>
                    <h3><button type="button" class="btn btn-info" data-toggle="modal" data-target="#uploadphoto">Change your photo</button></h3>
                    <div class="modal fade" id="uploadphoto" style="display:none" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Change image of ${user.getName()} ${user.getSurname()}:</h4>
                                </div>
                                <div class="modal-body">
                                    <p class="font-weight-normal">Please select the image in your device.</p>
                                    <form method="POST" action="uploadImage.handler" enctype = "multipart/form-data">
                                        <div class="upload-btn-wrapper">
                                            <button class="btnfile">Choose a file</button>
                                            <input type="file" name="myfile" accept="image/*"/>
                                        </div>                                        
                                        <br><br>
                                        <button type="submit" class="btn btn-success">Upload file</button>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
            
        </div>
        <br>
    <script type="text/javascript" src="<%=request.getContextPath()%>/linkers/connector.js"></script>
    
    </body>
</html>
