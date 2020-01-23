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
<jsp:scriptlet>
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
</jsp:scriptlet>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="SoftwareSanitarioUnitn" />
        <meta name="author" content="I magici ragazzi" />
        <title>Healthcare software</title>
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/css/ourStyle.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/js/all.min.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <script type="text/javascript" src="<%=request.getContextPath()%>/linkers/connector.js"></script>
        
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand" href="homePage.html">Healthcare software</a><button class="btn btn-link btn-sm order-1 order-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0" >
                <div class="input-group" style="display:none">
                    <input class="form-control" type="text" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" />
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="button"><i class="fas fa-search"></i></button>
                    </div>
                </div>
            </form>
            <!-- Navbar-->
            <ul class="navbar-nav ml-auto ml-md-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="userDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                        <a class="dropdown-item" data-toggle="modal" data-target="#changePassword">Change password</a>
                        <a class="dropdown-item" onclick="openDash('personalInformation')">Personal information</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="logout.handler">Logout</a>
                    </div>
                </li>
            </ul>
        </nav>
        
        <div class="modal fade" id="changePassword" style="display:none" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Change password</h4><br>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="changePassoword.handler" onsubmit="return testpass(this)">
                           <div class="form-group">
                                <label class="font-weight-normal"><b>New password:</b></label>
                                <input class="form-control" type="password" name="password_2" placeholder="password">
                                <label class="font-weight-normal"><b>Confirm password:</b></label>
                                <input class="form-control" type="password" name="password" placeholder="password">
                                <br>
                                <input class="btn btn-secondary" type="submit" value="Submit">
                                <input class="btn btn-secondary" type="reset" value="Reset">
                           </div>
                       </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
        
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">
                                <img src="${avatarPath}" style="width:85%;" class="rounded"/><br>
                            </div>
                            <div class="sb-sidenav-menu-heading">Your dashboard</div>
                            <a class="nav-link" href="homePage.html"
                                ><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard patient</a>
                            <c:choose>
                                <c:when test="${!empty generalDoctor}">
                                    <a class="nav-link" href="homeGeneralDoctor.html"
                                                    ><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                                    Dashboard general doctor</a>
                                </c:when>
                            </c:choose>
                            <a class="nav-link" href="homeSpecialist.html"
                                 ><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                 Dashboard specialist</a>
                            <div class="sb-sidenav-menu-heading">Action</div>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapsePatients" onclick="openDash('allPatients')" aria-expanded="false" aria-controls="collapseLayouts"
                               ><div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Patients
                            </a>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapseExaminations" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-edit"></i></div>
                                Examinations
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapseExaminations" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDash('allExaminations')">All examinations</a><a class="nav-link" onclick="openDash('confirmExaminations')">Confirm examinations</a></nav>
                            </div>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapseExams" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-edit"></i></div>
                                Exams
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapseExams" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDash('allExams')">All exams</a><a class="nav-link" onclick="openDash('confirmExams')">Confirm exams</a></nav>
                            </div>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        ${user.getName()} ${user.getSurname()}
                    </div>
                </nav>
            </div>
            
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid" id="allPatients">
                        <h1 class="mt-4">Patients</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all the patients.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Patients</div>
                            <c:choose>
                                        <c:when test="${empty patients}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have patients.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTablePatientsSpecialist" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Cf</th>
                                                             <th>Name</th>
                                                             <th>Surname</th>
                                                             <th>Age</th>
                                                             <th>About</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="patientsTable">
                                                    <c:forEach var="patient" items="${patients}">
                                                        <tr>
                                                            <td>${patient.getCf()}</td>
                                                            <td>${patient.getName()}</td>
                                                            <td>${patient.getSurname()}</td> 
                                                            <td>${patient.getAge()}</td>
                                                            <td>
                                                                <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#patientDetail${patient.getCf()}">-></button>
                                                            </td>
                                                        </tr> 
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                </div>
                                            </div>
                                        </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    
                    <c:forEach var="patient" items="${patients}">        
                        <div class="modal fade" id="patientDetail${patient.getCf()}" style="display:none" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Information about ${patient.getName()} ${patient.getSurname()}:</h4>
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="list-group list-group-horizontal" id="navModalPatient">
                                            <a href="#informationAbout${patient.getCf()}"  class="list-group-item active list-group-item-action" data-toggle="tab">Information about</a>
                                        </div>
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
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <div class="container-fluid" id="allExaminations" style="display: none">
                        <h1 class="mt-4">Examinations</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all your examinations, those you have to perform and those you have already performed.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Examinations</div>
                            <c:choose>
                                        <c:when test="${empty examinations}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You haven't already performed any examination.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('examinationTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableExaminations" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Code</th>
                                                            <th>Date</th>
                                                            <th>Patient</th>
                                                            <th>Time</th>
                                                            <th>Done</th>
                                                            <th>Argument</th>
                                                        </tr>   
                                                    </thead>
                                                    <tbody id="examinationTable">
                                                    <c:forEach var="examination" items="${examinations}">
                                                        <tr>
                                                            <td>${examination.getSSD()}</td>
                                                            <td>${examination.getExaminationDate()}</td>
                                                            <td>${examination.getIDPatient()}</td> 
                                                            <td>${examination.getTime()}</td>
                                                            <td>${examination.getIsDone()}</td>
                                                            <td>${examination.getArgument()}</td>
                                                        </tr>                 
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                </div>
                                            </div>
                                        </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    
                    
                            
                    <div class="container-fluid" id="confirmExaminations" style="display: none">
                        <h1 class="mt-4">Confirm examinations</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can confirm an examination you have performed.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Examinations</div>
                            <c:choose>
                                        <c:when test="${empty screamExaminationsByDone}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You haven't already performed any examination.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('confirmExaminationTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableConfirmExaminations" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Code</th>
                                                            <th>Date</th>
                                                             <th>Patient</th>
                                                             <th>Time</th>
                                                             <th>Done</th>
                                                             <th>Argument</th>
                                                             <th>Confirm examination</th>
                                                        </tr>   
                                                    </thead>
                                                    <tbody id="confirmExaminationTable">
                                                    <c:forEach var="examination" items="${screamExaminationsByDone}">
                                                        <tr>
                                                            <td>${examination.getSSD()}</td>
                                                            <td>${examination.getExaminationDate()}</td> 
                                                            <td>${examination.getIDPatient()}</td>
                                                            <td>${examination.getTime()}</td>
                                                            <td>${examination.getIsDone()}</td>
                                                            <td>${examination.getArgument()}</td>
                                                            <td>
                                                                <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#examinationToConfirm${examination.getSSD()}">-></button>
                                                            </td>
                                                        </tr>    
                                                        <div class="modal fade" id="examinationToConfirm${examination.getSSD()}" style="display:none" role="dialog">
                                                            <div class="modal-dialog modal-lg">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h4 class="modal-title">Examination ${examination.getSSD()}</h4>
                                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <p class="font-weight-normal"> Confirm examination and write the report. When you confirm the examination, this means that the ticket has been payed.</p>
                                                                        <form method="POST" action="confirmExamination.handler">
                                                                            <div class="form-group">
                                                                              <label for="comment">Report:</label>
                                                                              <input name="id" id="id" type="text" class="form-control" value="${examination.getSSD()}" style="display:none">
                                                                              <textarea class="form-control noresize" rows="5" id="report" name="report"></textarea>
                                                                            </div>
                                                                            <button type="submit" class="btn btn-secondary">Confirm</button>
                                                                        </form>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>         
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                </div>
                                            </div>
                                        </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    
                    <div class="container-fluid" id="allExams" style="display: none">
                        <h1 class="mt-4">Exams</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all the exams, those you have to perform and those you have already performed.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Exams</div>
                            <c:choose>
                                        <c:when test="${empty exams}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You haven't already performed any exam.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('examsTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableExams" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Code</th>
                                                            <th>Date</th>
                                                            <th>Patient</th>
                                                            <th>Result</th>
                                                            <th>Done</th>
                                                            <th>Recall</th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody id="examsTable">
                                                    <c:forEach var="exam" items="${exams}">
                                                        <tr>
                                                            <td>${exam.getCode()}</td>
                                                            <td>${exam.getExaminationDate()}</td> 
                                                            <td>${exam.getIDPatient()}</td>
                                                            <td>${exam.getResult()}</td>
                                                            <td>${exam.getIsDone()}</td>
                                                            <td>${exam.getIsRecall()}</td>
                                                        </tr>                 
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                </div>
                                            </div>
                                        </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    
                    <div class="container-fluid" id="confirmExams" style="display: none">
                        <h1 class="mt-4">Confirm exams</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can confirm an exam you have performed.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Exams</div>
                            <c:choose>
                                        <c:when test="${empty screamExamsByDone}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You haven't already performed any exam.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('confirmExamTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableConfirmExams" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Code</th>
                                                            <th>Date</th>
                                                             <th>Patient</th>
                                                             <th>Result</th>
                                                             <th>Done</th>
                                                             <th>Recall</th>
                                                             <th>Confirm exam</th>
                                                        </tr>  
                                                    </thead>
                                                    <tbody id="confirmExamTable">
                                                    <c:forEach var="exam" items="${screamExamsByDone}">
                                                        <tr>
                                                            <td>${exam.getCode()}</td>
                                                            <td>${exam.getExaminationDate()}</td> 
                                                            <td>${exam.getIDPatient()}</td>
                                                            <td>${exam.getResult()}</td>
                                                            <td>${exam.getIsDone()}</td>
                                                            <td>${exam.getIsRecall()}</td>
                                                            <td>
                                                                <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#examToConfirm${exam.getCode()}">-></button>
                                                            </td>
                                                        </tr>    
                                                        <div class="modal fade" id="examToConfirm${exam.getCode()}" style="display:none" role="dialog">
                                                            <div class="modal-dialog modal-lg">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h4 class="modal-title">Exam ${exam.getCode()}</h4>
                                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <p class="font-weight-normal"> Confirm exam and write the report. When you confirm the exam, this means that the ticket has been payed.</p>
                                                                        <form method="POST" action="confirmExam.handler">
                                                                            <div class="form-group">
                                                                              <label for="comment">Report:</label>
                                                                              <input name="id" id="id" type="text" class="form-control" value="${exam.getCode()}" style="display:none">
                                                                              <input name="type" value="specialist" style="display:none">
                                                                              <textarea class="form-control noresize" rows="5" id="result" name="result"></textarea>
                                                                            </div>
                                                                            <button type="submit" class="btn btn-secondary">Confirm</button>
                                                                        </form>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>         
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                </div>
                                            </div>
                                        </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    
                                    
                    <div class="container-fluid" id="personalInformation" style="display: none">
                        <h1 class="mt-4">Your personal information</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see your personal information.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="table-responsive">
                            <table class="table borderless">
                                <tbody>
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
                                <tr>
                                    <td>
                                        <h3><button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#uploadphoto">Change your photo</button></h3>
                                    </td>
                                    <td></td>
                                </tr>
                                </tbody>
                            </table> 
                            </div>
                        </div>
                    </div>
                                    
                     <div class="modal fade" id="uploadphoto" style="display:none" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Change image of ${user.getName()} ${user.getSurname()}:</h4>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    <p class="font-weight-normal">Please select the image in your device.</p>
                                    <form method="POST" action="uploadImage.handler" enctype = "multipart/form-data">
                                        <div class="upload-btn-wrapper">
                                            <%--<button class="btnfile">Choose a file</button>--%>
                                            <input type="file" name="myfile" accept="image/*" style="border-radius: 6px; width: 1000px"/>
                                        </div>                                        
                                        <br>
                                        <button type="submit" class="btn btn-success">Upload file</button>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid" id="foot">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Software sanitario 2020</div>
                            <div>
                                <a href="<%=request.getContextPath()%>/download/cookie.pdf" download>Cookies</a>
                                &middot;
                                <a href="<%=request.getContextPath()%>/download/privacy.pdf" download>Privacy Policy</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%=request.getContextPath()%>/js/scripts.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
        <script src="<%=request.getContextPath()%>/assets/demo/datatables-demo.js"></script>
    </body>
</html>
