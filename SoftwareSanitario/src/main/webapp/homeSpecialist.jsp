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
<%@page import="com.mycompany.softwaresanitario.commons.persistence.entities.Examination"%>
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
            #myInputPassed {
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }
            #myInputConfirmed {
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }
            #myInputExams {
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }
            #myInputConfirmedExams {
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }
            textarea {
                resize: none;
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
            </div>
            <div class="w3-bar-block">
                <a href="homePage.html" class="w3-bar-item w3-button w3-padding "><i class="fa fa-user fa-fw w3-margin-right"></i>Dashboard patient</a> 
                <c:choose>
                    <c:when test="${!empty generalDoctor}">
                        <a href="homeGeneralDoctor.html" onclick="w3_close()" class="w3-bar-item w3-button w3-padding "><i class="fa fa-user fa-fw w3-margin-right"></i>Dashboard doctor</a> 
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
                <h2><button class="w3-bar-item w3-button" onclick="openDash('allPatients')">All patients</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('allexaminations')">All examinations</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('confirmExaminations')">Confirm an examinations</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('allExams')">All exams</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('confirmExams')">Confirm exams</button></h2>
            </div>
            <div id="allPatients" class="w3-container dash">

                <h3>All patients:</h3>

                <input class="w3-bordered" type="text" id="myInput" onkeyup="search('myInput', 'myTable')" placeholder="Search for surnames..">
                <table class="w3-table w3-bordered" id="myTable">
                    <tr>
                        <th>Cf</th>
                         <th>Name</th>
                         <th>Surname</th>
                         <th>Age</th>
                    </tr>

                    <c:forEach var="patient" items="${patients}">
                        <tr>
                            <td>${patient.getCf()}</td>
                            <td>${patient.getName()}</td>
                            <td>${patient.getSurname()}</td> 
                            <td>${patient.getAge()}</td>
                            <td>
                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#patientDetail${patient.getCf()}">-></button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:forEach var="patient" items="${patients}">        
                    <div class="modal fade" id="patientDetail${patient.getCf()}" style="display:none" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Information about ${patient.getName()} ${patient.getSurname()}:</h4>
                                </div>
                                <div class="modal-body">
                                    <nav class="navbar navbar-default">
                                        <div class="container-fluid">
                                            <ul class="nav navbar-nav">
                                              <li class="active"><a href="#informationAbout${patient.getCf()}" data-toggle="tab">Information about</a></li>
                                            </ul>
                                        </div>
                                    </nav>
                                </div>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="informationAbout${patient.getCf()}" style="margin:20px">
                                        <p>Fiscal code: ${patient.getCf()}</p>
                                        <p>Name: ${patient.getName()}</p>
                                        <p>Surname: ${patient.getSurname()}</p>
                                        <p>Age: ${patient.getAge()}</p>
                                        <p>Place of birth: ${patient.getBirth_city()}</p>
                                        <p>Place: ${patient.getCity()}</p>
                                        <p>Date of birth: ${patient.getBirthdate()}</p>
                                        <p>Gender: ${patient.getBirthdate()}</p>
                                        <p>Email: ${patient.getEmail()}</p>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>    
            </div>
            <div id="allexaminations" class="w3-container dash" style="display:none">
                <c:choose>
                            <c:when test="${empty examinations}">
                                <div class="card">
                                    <div class="card-body">
                                        You haven't already done any examination.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h3>Examinations:</h3>
                                <input class="w3-bordered" type="text" id="myInputPassed" onkeyup="search('myInputPassed', 'myTablePassed')" placeholder="Search for dates..">
                                
                                <table class="w3-table w3-bordered" id="myTablePassed">
                                        <tr>
                                            <th>Code</th>
                                             <th>Patient</th>
                                             <th>Date</th>
                                             <th>Time</th>
                                             <th>Done</th>
                                             <th>Argument</th>
                                        </tr>   
                                    <c:forEach var="examination" items="${examinations}">
                                        <tr>
                                            <td>${examination.getSSD()}</td>
                                            <td>${examination.getIDPatient()}</td>
                                            <td>${examination.getExaminationDate()}</td> 
                                            <td>${examination.getTime()}</td>
                                            <td>${examination.getIsDone()}</td>
                                            <td>${examination.getArgument()}</td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
            </div>              
            <div id="confirmExaminations" class="w3-container dash" style="display:none">
                
                <c:choose>
                            <c:when test="${empty screamExaminationsByDone}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have examinations to confirm.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h3>Examinations that have to be confirmed:</h3>

                                <input class="w3-bordered" type="text" id="myInputConfirmed" onkeyup="search('myInputConfirmed', 'myTableConfirmed')" placeholder="Search for dates..">
                                <table class="w3-table w3-bordered" id="myTableConfirmed">
                                        <tr>
                                            <th>Code</th>
                                             <th>Patient</th>
                                             <th>Date</th>
                                             <th>Time</th>
                                             <th>Done</th>
                                             <th>Argument</th>
                                             <th>Confirm examination</th>
                                        </tr>  
                                        <c:forEach var="examination" items="${screamExaminationsByDone}">
                                            <tr>
                                                <td>${examination.getSSD()}</td>
                                                <td>${examination.getIDPatient()}</td>
                                                <td>${examination.getExaminationDate()}</td> 
                                                <td>${examination.getTime()}</td>
                                                <td>${examination.getIsDone()}</td>
                                                <td>${examination.getArgument()}</td>
                                                <td>
                                                    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#examinationToConfirm${examination.getSSD()}">-></button>
                                                </td>
                                            </tr>    
                                            <div class="modal fade" id="examinationToConfirm${examination.getSSD()}" style="display:none" role="dialog">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Examination ${examination.getSSD()}. <br> Confirm examination and write the report. When you confirm the examination, this means that the ticket has been payed.</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form method="POST" action="confirmExamination.handler">
                                                                <div class="form-group">
                                                                  <label for="comment">Report:</label>
                                                                  <input name="id" id="id" type="text" class="form-control" value="${examination.getSSD()}" style="display:none">
                                                                  <textarea class="form-control noresize" rows="5" id="report" name="report"></textarea>
                                                                </div>
                                                                <button type="submit" class="btn btn-default">Conferma</button>
                                                            </form>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
            </div>
            <div id="allExams" class="w3-container dash" style="display:none">
                <c:choose>
                            <c:when test="${empty exams}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have exams to do or already done.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h3>Exams:</h3>
                                <input class="w3-bordered" type="text" id="myInputExams" onkeyup="search('myInputExams', 'myTablePassed2')" placeholder="Search for dates..">
                                
                                <table class="w3-table w3-bordered" id="myTablePassed2">
                                        <tr>
                                            <th>Code</th>
                                            <th>Patient</th>
                                            <th>Date</th>
                                            <th>Result</th>
                                            <th>Done</th>
                                            <th>Recall</th>
                                        </tr>   
                                    <c:forEach var="exam" items="${exams}">
                                        <tr>
                                            <td>${exam.getCode()}</td>
                                            <td>${exam.getIDPatient()}</td>
                                            <td>${exam.getExaminationDate()}</td> 
                                            <td>${exam.getResult()}</td>
                                            <td>${exam.getIsDone()}</td>
                                            <td>${exam.getIsRecall()}</td>
                                        </tr>          
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
            </div>       
            
            
            <div id="confirmExams" class="w3-container dash" style="display:none">
                
                <c:choose>
                            <c:when test="${empty screamExamsByDone}">
                                <div class="card">
                                    <div class="card-body">
                                        You don't have exams to confirm.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h3>Exams that have to be confirmed:</h3>

                                <input class="w3-bordered" type="text" id="myInputConfirmedExams" onkeyup="search('myInputConfirmedExams', 'myTableConfirmed2')" placeholder="Search for dates..">
                                <table class="w3-table w3-bordered" id="myTableConfirmed2">
                                        <tr>
                                            <th>Code</th>
                                             <th>Patient</th>
                                             <th>Date</th>
                                             <th>Result</th>
                                             <th>Done</th>
                                             <th>Recall</th>
                                             <th>Confirm exam</th>
                                        </tr>  
                                        <c:forEach var="exam" items="${screamExamsByDone}">
                                            <tr>
                                                <td>${exam.getCode()}</td>
                                                <td>${exam.getIDPatient()}</td>
                                                <td>${exam.getExaminationDate()}</td> 
                                                <td>${exam.getResult()}</td>
                                                <td>${exam.getIsDone()}</td>
                                                <td>${exam.getIsRecall()}</td>
                                                <td>
                                                    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#examinationToConfirm${exam.getCode()}">-></button>
                                                </td>
                                            </tr>    
                                            <div class="modal fade" id="examinationToConfirm${exam.getCode()}" style="display:none" role="dialog">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Exam: ${exam.getCode()}.<br>Confirm exam and write the result. When you confirm the exam, this means that the ticket has been payed.</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form method="POST" action="confirmExam.handler">
                                                                <div class="form-group">
                                                                  <label for="comment">Result:</label>
                                                                  <input name="id" id="id" type="text" class="form-control" value="${exam.getCode()}" style="display:none">
                                                                  <input name="type" value="specialist" style="display:none">
                                                                  <textarea class="form-control noresize" rows="5" id="result" name="result"></textarea>
                                                                </div>
                                                                <button type="submit" class="btn btn-default">Confirm</button>
                                                            </form>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                </table>
                            </c:otherwise>
                </c:choose>
            </div>
        </div>
        <br>
        
    <script type="text/javascript" src="<%=request.getContextPath()%>/linkers/connector.js"></script>
    </body>
</html>


                
