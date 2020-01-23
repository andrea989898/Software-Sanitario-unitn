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
        <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/js/all.min.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <script type="text/javascript" src="<%=request.getContextPath()%>/linkers/connector.js"></script>
        
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand" href="index.html">Healthcare software</a><button class="btn btn-link btn-sm order-1 order-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
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
                            <c:choose>
                                <c:when test="${!empty specialist}">
                                        <a class="nav-link" href="homeSpecialist.html"
                                            ><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                            Dashboard specialist</a>
                                </c:when>   
                            </c:choose> 
                            <div class="sb-sidenav-menu-heading">Action</div>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapseExams" aria-expanded="false" aria-controls="collapseLayouts"
                               ><div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Exams
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapseExams" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDash('screamExams')">Future Exams</a><a class="nav-link" onclick="openDash('exams')">All Exams</a></nav>
                            </div>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapseExaminations" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Examinations
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapseExaminations" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDash('screamExaminations')">Future Examinations</a><a class="nav-link" onclick="openDash('examinations')">All Examinations</a></nav>
                            </div>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapseTickets" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Tickets
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapseTickets" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDash('screamTickets')">To pay Tickets</a><a class="nav-link" onclick="openDash('tickets')">All Tickets</a></nav>
                            </div>
                            <a class="nav-link collapsed" data-toggle="collapse" onclick="openDash('recipes')" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Recipes
                            </a>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapseGeneral" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-edit"></i></div>
                                General Doctor
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapseGeneral" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDash('generalDoctor')">View general doctor</a><a class="nav-link" onclick="openDash('newGeneralDoctor')">Change general doctor</a></nav>
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
                    <div class="container-fluid" id="exams">
                        <h1 class="mt-4">Exams</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all your exams, those you have to do and those you have already done.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Exams</div>
                            <c:choose>
                                        <c:when test="${empty exams}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have exams.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('examTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableExams" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                             <th>Exam Code</th>
                                                             <th>Exam Date</th>
                                                             <th>Done</th>
                                                             <th>Get the pdf</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="examTable">
                                                    <c:forEach var="exam" items="${exams}">
                                                        <tr>
                                                            <td>${exam.getCode()}</td>
                                                            <td>${exam.getExaminationDate()}</td>
                                                            <td>${exam.getIsDone()}</td>
                                                            <td>
                                                                <a href="exportToPDF.handler?id=${exam.getCode()}&type=exam">
                                                                    <button class="btn btn-secondary">go</button>
                                                                </a>
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
                    
                    <div class="container-fluid" id="screamExams" style="display: none">
                        <h1 class="mt-4">Future exams</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all the exams you have to do.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Future exams</div>
                            <c:choose>
                                        <c:when test="${empty screamExams}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have exams to do.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('screamExamsTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableScreamExams" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                             <th>Exam Code</th>
                                                             <th>Exam Date</th>
                                                             <th>Done</th>
                                                             <th>Get the pdf</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="screamExamsTable">
                                                    <c:forEach var="exam" items="${exams}">
                                                        <tr>
                                                            <td>${exam.getCode()}</td>
                                                            <td>${exam.getExaminationDate()}</td>
                                                            <td>${exam.getIsDone()}</td>
                                                            <td>
                                                                <a href="exportToPDF.handler?id=${exam.getCode()}&type=exam">
                                                                    <button class="btn btn-secondary">go</button>
                                                                </a>
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
                    
                    <div class="container-fluid" id="screamExaminations" style="display: none">
                        <h1 class="mt-4">Future examinations</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all the examinations you have to do.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Future examinations</div>
                            <c:choose>
                                        <c:when test="${empty screamExaminations}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have examinations to do.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('screamExaminationTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableScreamExaminations" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Examination Code</th>
                                                            <th>Examination Date</th>
                                                            <th>Done</th>
                                                       </tr>
                                                    </thead>
                                                    <tbody id="screamExaminationTable">
                                                    <c:forEach var="examination" items="${screamExaminations}">
                                                        <tr>
                                                            <td>${examination.getSSD()}</td>
                                                            <td>${examination.getExaminationDate()}</td>
                                                            <td>${examination.getIsDone()}</td>
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
                    
                    <div class="container-fluid" id="examinations" style="display: none">
                        <h1 class="mt-4">Examinations</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all your examinations, those you have to do and those you have already done.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Examinations</div>
                            <c:choose>
                                        <c:when test="${empty examinations}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have examinations.
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
                                                            <th>Examination Code</th>
                                                            <th>Examination Date</th>
                                                            <th>Done</th>
                                                       </tr>
                                                    </thead>
                                                    <tbody id="examinationTable">
                                                    <c:forEach var="examination" items="${examinations}">
                                                        <tr>
                                                            <td>${examination.getSSD()}</td>
                                                            <td>${examination.getExaminationDate()}</td>
                                                            <td>${examination.getIsDone()}</td>
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
                    
                    <div class="container-fluid" id="screamTickets" style="display: none">
                        <h1 class="mt-4">Tickets you have to paid</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all the tickets you have to pay.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Tickets to pay</div>
                            <c:choose>
                                        <c:when test="${empty screamTickets}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have tickets to pay.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('screamTicketsTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableScreamTickets" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Ticket Code</th>
                                                            <th>Ticket Date</th>
                                                            <th>Expiration Date</th>
                                                            <th>Cost</th>
                                                            <th>Paid</th>
                                                            <th>Get the pdf</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="screamTicketsTable">
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
                                                                        <button class="btn btn-secondary">Go</button>
                                                                    </a>
                                                                <%--</button>--%>
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
                    
                    <div class="container-fluid" id="tickets" style="display: none">
                        <h1 class="mt-4">Tickets you have to paid</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all the tickets, those you have to pay and those you have already paid.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Tickets</div>
                            <c:choose>
                                        <c:when test="${empty tickets}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have tickets.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('ticketsTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableTickets" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Ticket Code</th>
                                                            <th>Ticket Date</th>
                                                            <th>Expiration Date</th>
                                                            <th>Cost</th>
                                                            <th>Paid</th>
                                                            <th>Get the pdf</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="ticketsTable">
                                                    <c:forEach var="ticket" items="${tickets}">
                                                        <tr>
                                                            <td>${ticket.getCode()}</td>
                                                            <td>${ticket.getDate()}</td>
                                                            <td>${ticket.getExpirationDate()}</td>
                                                            <td>${ticket.getCost()}</td>
                                                            <td>${ticket.getIsPaid()}</td>
                                                            <td>
                                                                <%--<button class="w3-button w3-round-large w3-blue">--%>
                                                                    <a href="exportToPDF.handler?id=${ticket.getCode()}&type=ticket">
                                                                        <button class="btn btn-secondary">Go</button>
                                                                    </a>
                                                                <%--</button>--%>
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
                    
                    <div class="container-fluid" id="recipes" style="display: none">
                        <h1 class="mt-4">Recipes</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all your recipes.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Recipes prescribed.</div>
                            <c:choose>
                                        <c:when test="${empty prescriptions}">
                                            <div class="card">
                                                <div class="card-body">
                                                    You don't have recipes.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <button class="btn btn-secondary" onclick="sortTable('recipesTable')">Sort by date</button><br><br>
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableRecipes" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Prescription Code</th>
                                                            <th>Date</th>
                                                            <th>Analisys</th>
                                                            <th>Doctor ssd</th>
                                                            <th>Patient ssd</th>
                                                            <th>Get the pdf</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="recipesTable">
                                                    <c:forEach var="prescription" items="${prescriptions}">
                                                        <tr>
                                                            <td>${prescription.getCode()}</td>
                                                            <td>${prescription.getDate()}</td>
                                                            <td>${prescription.getAnalisys()}</td>
                                                            <td>${prescription.getIddoctor()}</td>
                                                            <td>${prescription.getIdpatient()}</td>
                                                            <td>
                                                                <a href="exportToPDF.handler?id=${prescription.getCode()}&type=prescription">
                                                                    <button class="btn btn-secondary">Go</button>
                                                                </a>
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
                    
                    <div class="container-fluid" id="generalDoctor" style="display: none">
                        <h1 class="mt-4">General Doctor</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see your general doctor.</div>
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
                                </tbody>
                            </table>  
                            </div>
                        </div>
                    </div>
                                    
                    <div class="container-fluid" id="newGeneralDoctor" style="display: none">
                        <h1 class="mt-4">Change your general doctor</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can change your general doctor.</div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>General doctors available.</div>
                            <c:choose>
                                        <c:when test="${empty AllGeneralDoctor}">
                                            <div class="card">
                                                <div class="card-body">
                                                    In your province there aren't any available general doctors.
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-body">
                                                <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTableDoctors" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Name</th>
                                                            <th>Surname</th>
                                                            <th>Age</th>
                                                            <th>Studio address</th>
                                                            <th>Make change</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="doctorTable">
                                                    <c:forEach var="doctor" items="${AllGeneralDoctor}">
                                                        <tr>
                                                            <td>${doctor.getName()}</td>
                                                            <td>${doctor.getSurname()}</td>
                                                            <td>${doctor.getAge()}</td>
                                                            <td>${doctor.getAddress()}</td>
                                                            <td>
                                                                <a href="changeGeneralDoctor.handler?id=${doctor.getCf()}&ssd=${user.getCf()}">
                                                                    <button class="btn btn-secondary">Change</button>
                                                                </a>
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
