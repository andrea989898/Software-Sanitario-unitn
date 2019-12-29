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
            #myInputPatient{
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }
        </style>
    </head>
    <body>
        <jsp:scriptlet>
             response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        </jsp:scriptlet>
        <header class="w3-container w3-teal">
            <h2>Software sanitario</h2>
        </header>
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
                    <c:when test="${!empty specialist}">
                        <a href="homeSpecialist.html" onclick="w3_close()" class="w3-bar-item w3-button w3-padding "><i class="fa fa-user fa-fw w3-margin-right"></i>Dashboard specialist</a> 
                    </c:when>   
                </c:choose>
            </div>
            <div class="w3-container">
                <div class="w3-section w3-padding-16">
                    <a href="cambioPassword.html" class="w3-container"><button class="w3-button w3-black"><i class="fa fa-refresh w3-margin-right"></i>Cambia password</button></a>
                    <a href="logout.handler" class="w3-container"><button class="w3-button w3-black"><i class="fa fa-close w3-margin-right"></i>Esci</button></a>
                    <!--<button class="w3-button w3-white w3-hide-small"><i class="fa fa-photo w3-margin-right"></i>Photos</button>
                    <button class="w3-button w3-white w3-hide-small"><i class="fa fa-map-pin w3-margin-right"></i>Art</button>-->
                </div> 
            </div>
        </nav>
        <div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>
        
        <div class="w3-main" style="margin-left:300px">
        
            <div class="w3-bar w3-white">
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamPatients')">Patients</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamExPrescriptions')">Prescribe an examination</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamExamPrescriptions')">Prescribe an exam</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamRePrescriptions')">Prescribe a recipe</button></h2>
            </div>
            <div id="screamPatients" class="w3-container dash">

                <h3>All your patients:</h3>

                <input class="w3-bordered" type="text" id="myInputPatient" onkeyup="search('myInputPatient','myTablePatient')" placeholder="Search for surnames..">
                <table class="w3-table w3-bordered" id="myTablePatient">
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
                                              <li><a href="#examsModal${patient.getCf()}" data-toggle="tab">Exams</a></li>
                                              <li><a href="#examinationsModal${patient.getCf()}" data-toggle="tab">Examinations</a></li>
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
                                    <div class="tab-pane" id="examsModal${patient.getCf()}" style="margin:20px">
                                        <table class="w3-table w3-bordered" id="myTable">
                                            <tr>
                                                <th>Code</th>
                                                <th>Date</th>
                                                <th>Result</th>
                                            </tr>
                                            <c:forEach var="exam" items="${patient.getExams()}">
                                                <tr>
                                                    <td>${exam.getCode()}</td>
                                                    <td>${exam.getExaminationDate()}</td>
                                                    <td>${exam.getResult()}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                    <div class="tab-pane" id="examinationsModal${patient.getCf()}" style="margin:20px">
                                        <table class="w3-table w3-bordered" id="myTableexaminations">
                                            <tr>
                                                <th>Code</th>
                                                <th>Date</th>
                                                <th>Argument</th>
                                            </tr>
                                            <c:forEach var="examination" items="${patient.getExaminations()}">
                                                <tr>
                                                    <td>${examination.getSSD()}</td>
                                                    <td>${examination.getExaminationDate()}</td>
                                                    <td>${examination.getArgument()}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
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
            <div id="screamExPrescriptions" class="w3-container dash" style="display:none">
                
                <h3>Prescribe a new examination:</h3>
                
                <form class="w3-container " method="POST" action="newExamination.handler">
                    <input class="w3-input w3-border w3-light-grey" type="text" name="prescriptor" id="time" value="${user.getCf()}" style="display:none">
                    <br><br>
                    <select id="patient" name="patient" class="form-control selectpicker">
                        <option value="0" disabled selected hidden>Select patient:</option>
                        <c:forEach var="patient" items="${patients}">
                            <option data-tokens="${patient.getName()}${patient.getSurname()}" value="${patient.getCf()}">${patient.getName()} ${patient.getSurname()}</option>
                        </c:forEach>
                    </select>
                    <br><br>
                    <select id="type" name="type" class="form-control selectpicker">
                        <option value="0" disabled selected hidden>Select type of examinations:</option>
                        <option value="1">Normal examination</option>
                        <option value="2">Special examination</option>
                    </select>
                    <br><br>
                    <select id="doctor" name="doctor" class="form-control selectpicker">
                        <option value="0" disabled selected hidden>Select the doctor:</option>
                        <c:forEach var="doctor" items="${doctors}">
                            <option data-tokens="${doctor.getName()}${doctor.getSurname()}" value="${doctor.getCf()}">${doctor.getName()} ${doctor.getSurname()} specialization: ${doctor.getSpecialization()}</option>
                        </c:forEach>
                    </select>
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" name="analysis" id="analysis" placeholder="Write what needs to be done in the visit"></textarea>
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" type="date" name="date" id="date">
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" type="time" name="time" id="time">
                    <br><br>
                    <button class="w3-button w3-round-large w3-blue" type="submit">Submit</button>
                    <button class="w3-button w3-round-large w3-blue" type="reset">Reset</button>
                </form>
            </div>
            <div id="screamExamPrescriptions" class="w3-container dash" style="display:none">
                
                <h3>Prescribe a new exam:</h3>
                
                <form class="w3-container " method="POST" action="newExam.handler">
                    <input class="w3-input w3-border w3-light-grey" type="text" name="prescriptor" id="time" value="${user.getCf()}" style="display:none">
                    <br><br>
                    <select class="form-control selectpicker" id="patient" name="patient">
                        <option value="0" disabled selected hidden>Select patient:</option>
                        <c:forEach var="patient" items="${patients}">
                            <option data-tokens="${patient.getName()}${patient.getSurname()}" value="${patient.getCf()}">${patient.getName()} ${patient.getSurname()}</option>
                        </c:forEach>
                    </select>
                    <br><br>
                    <select class="form-control selectpicker" id="doctor" name="doctor" data-live-search="true">
                        <option value="0" disabled selected hidden>Select the doctor or the province service:</option>
                        <c:forEach var="doctor" items="${doctors}">
                            <option data-tokens="${doctor.getName()}${doctor.getSurname()}" value="${doctor.getCf()}">${doctor.getName()} ${doctor.getSurname()} specialization: ${doctor.getSpecialization()}</option>
                        </c:forEach>
                        <option data-tokens="ssp" value="${ssp.getId()}">Servizio sanitario della provincia di ${ssp.getProvinceName()}</option>    
                    </select>
                    <br><br>
                    <select class="form-control selectpicker" id="recall" name="recall">
                        <option value="0" disabled selected hidden>Select if is a recall:</option>
                        <option value="true">This exam is a recall</option>
                        <option value="false">This exam isn't a recall</option>
                    </select>
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" name="analysis" id="analysis" placeholder="Write what needs to be done in the exam"></textarea>
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" type="date" name="date" id="date">
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" type="time" name="time" id="time">
                    <br><br>                  
                    <button class="w3-button w3-round-large w3-blue" type="submit">Submit</button>
                    <button class="w3-button w3-round-large w3-blue" type="reset">Reset</button>
                </form>
            </div>
            <div id="screamRePrescriptions" class="w3-container dash" style="display:none">
                <h3>Prescribe a new recipe:</h3>
                
                <form class="w3-container " method="POST" action="newRecipe.handler">
                    <input class="w3-input w3-border w3-light-grey" type="text" name="prescriptor" id="time" value="${user.getCf()}" style="display:none">
                    <br><br>
                    <select id="patient" name="patient" class="form-control selectpicker">
                        <option value="0" disabled selected hidden>Select patient:</option>
                        <c:forEach var="patient" items="${patients}">
                            <option data-tokens="${patient.getName()}${patient.getSurname()}" value="${patient.getCf()}">${patient.getName()} ${patient.getSurname()}</option>
                        </c:forEach>
                    </select>
                    <br><br>
                    <jsp:scriptlet>
                        int j=0; 
                    </jsp:scriptlet>
                    <select id="drug-1" name="drug-1" class="form-control selectpicker">
                        <option value="false" disabled selected hidden>Select drug 1:</option>
                        <c:forEach var="drug" items="${drugs}">
                            <option data-tokens="${drug.getName()}" value="<%=(j++)%>">${drug.getName()}</option>
                        </c:forEach>
                    </select>
                    <jsp:scriptlet>
                        j=0; 
                    </jsp:scriptlet> 
                    <br><br>
                    <select id="drug-2" name="drug-2" class="form-control selectpicker">
                        <option value="false" disabled selected hidden>Select drug 2:</option>
                        <c:forEach var="drug" items="${drugs}">
                            <option data-tokens="${drug.getName()}" value="<%=(j++)%>">${drug.getName()}</option>
                        </c:forEach>
                    </select>
                    <jsp:scriptlet>
                        j=0; 
                    </jsp:scriptlet> 
                    <br><br>
                    <select id="drug-3" name="drug-3" class="form-control selectpicker">
                        <option value="false" disabled selected hidden>Select drug 3:</option>
                        <c:forEach var="drug" items="${drugs}">
                            <option data-tokens="${drug.getName()}" value="<%=(j++)%>">${drug.getName()}</option>
                        </c:forEach>
                    </select>
                    <jsp:scriptlet>
                        j=0; 
                    </jsp:scriptlet> 
                    <br><br>
                    <select id="drug-4" name="drug-4" class="form-control selectpicker">
                        <option value="false" disabled selected hidden>Select drug 4:</option>
                        <c:forEach var="drug" items="${drugs}">
                            <option data-tokens="${drug.getName()}" value="<%=(j++)%>">${drug.getName()}</option>
                        </c:forEach>
                    </select>
                    <jsp:scriptlet>
                        j=0; 
                    </jsp:scriptlet> 
                    <br><br>
                    <select id="drug-5" name="drug-5" class="form-control selectpicker">
                        <option value="false" disabled selected hidden>Select drug 5:</option>
                        <c:forEach var="drug" items="${drugs}">
                            <option data-tokens="${drug.getName()}" value="<%=(j++)%>">${drug.getName()}</option>
                        </c:forEach>
                    </select>
                    <jsp:scriptlet>
                        j=0; 
                    </jsp:scriptlet> 
                    <br><br>
                    <input class="w3-input w3-border w3-light-grey" name="analysis" id="analysis" placeholder="Write the information about the recipe"></textarea>
                    <br><br>               
                    <button class="w3-button w3-round-large w3-blue" type="submit">Submit</button>
                    <button class="w3-button w3-round-large w3-blue" type="reset">Reset</button>
                </form>
            </div>
        </div>
        <br>
    <script type="text/javascript" src="<%=request.getContextPath()%>/linkers/connector.js"></script>
    
</body>    
</html>
