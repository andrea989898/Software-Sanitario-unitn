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
        <link rel="icon" href="<%=request.getContextPath()%>/images/favicon/ssp.png">
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
                            <a class="nav-link" href="homeGeneralDoctor.html"
                                            ><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                            Dashboard general doctor</a>
                            <c:choose>
                                <c:when test="${!empty specialist}">
                                        <a class="nav-link" href="homeSpecialist.html"
                                            ><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                            Dashboard specialist</a>
                                </c:when>   
                            </c:choose> 
                            <div class="sb-sidenav-menu-heading">Action</div>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapsePatients" onclick="openDash('patients')" aria-expanded="false" aria-controls="collapseLayouts"
                               ><div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Patients
                            </a>
                            <a class="nav-link collapsed" data-toggle="collapse" data-target="#collapsePrescriptions" aria-expanded="false" aria-controls="collapsePages"
                                ><div class="sb-nav-link-icon"><i class="fas fa-edit"></i></div>
                                Prescriptions
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div
                            ></a>
                            <div class="collapse" id="collapsePrescriptions" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav"><a class="nav-link" onclick="openDashandClean('prescribeExaminations', 'patientExaminations')">Prescribe examinations</a><a class="nav-link" onclick="openDashandClean('prescribeExams', 'patientExams')">Prescribe exams</a><a class="nav-link" onclick="openDashandClean('prescribeDrugs', 'patientDrugs')">Prescribe some drugs</a></nav>
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
                    <c:choose>
                        <c:when test="${!empty creationExamSuccess}">
                            <div class="alert alert-success">
                                <a  class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>Success!</strong> New exam prescribed to ${creationExamSuccess}
                            </div>
                            <c:set var = "creationExamSuccess" scope = "session" value = "${null}"/>
                        </c:when>
                    </c:choose>
                    
                    <c:choose>
                        <c:when test="${!empty creationExaminationSuccess}">
                            <div class="alert alert-success">
                                <a  class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>Success!</strong> New examination prescribed to ${creationExaminationSuccess}
                            </div>
                            <c:set var = "creationExaminationSuccess" scope = "session" value = "${null}"/>
                        </c:when>
                    </c:choose>
                    
                    <c:choose>
                        <c:when test="${!empty creationDrugsSuccess}">
                            <div class="alert alert-success">
                                <a  class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>Success!</strong> New drugs prescribed to ${creationDrugsSuccess}
                            </div>
                            <c:set var = "creationDrugsSuccess" scope = "session" value = "${null}"/>
                        </c:when>
                    </c:choose>
                    
                    <div class="container-fluid" id="patients">
                        <h1 class="mt-4">Patients</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can see all your patients.</div>
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
                                                <table class="table table-bordered" id="dataTablePatients" width="100%" cellspacing="0">
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
                    <jsp:scriptlet>
                        int y=0;
                    </jsp:scriptlet> 
                    <c:forEach var="patient" items="${patients}">        
                        <div class="modal fade" id="patientDetail${patient.getCf()}" style="display:none" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Information about ${patient.getName()} ${patient.getSurname()}:</h4>
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container mt-3">
                                            <!-- Nav tabs -->
                                            <ul class="nav nav-tabs">
                                              <li class="nav-item">
                                                <a class="nav-link active" data-toggle="tab" href="#informationAbout${patient.getCf()}">Informations</a>
                                              </li>
                                              <li class="nav-item">
                                                <a class="nav-link" data-toggle="tab" href="#examsModal${patient.getCf()}">Exams</a>
                                              </li>
                                              <li class="nav-item">
                                                <a class="nav-link" data-toggle="tab" href="#examinationsModal${patient.getCf()}">Examinations</a>
                                              </li>
                                            </ul>

                                            <!-- Tab panes -->
                                            <div class="tab-content">
                                              <div id="informationAbout${patient.getCf()}" class="container tab-pane active"><br>
                                                <p>Fiscal code: ${patient.getCf()}</p>
                                                <p>Name: ${patient.getName()}</p>
                                                <p>Surname: ${patient.getSurname()}</p>
                                                <p>Age: ${patient.getAge()}</p>
                                                <p>Place of birth: ${patient.getBirth_city()}</p>
                                                <p>Place: ${patient.getCity()}</p>
                                                <p>Date of birth: ${patient.getBirthdate()}</p>
                                                <p>Gender: ${patient.getGender()}</p>
                                                <p>Email: ${patient.getEmail()}</p>
                                              </div>
                                              <div id="examsModal${patient.getCf()}" class="container tab-pane fade"><br>
                                                  <div class="table-responsive">
                                                    <table class="table table-bordered" name="dataTablePatientsExams" id="dataTableEx${patient.getCf()}">
                                                        <thead>
                                                            <tr>
                                                                <th>Code</th>
                                                                <th>Date</th>
                                                                <th>Result</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="exam" items="${patient.getExams()}">
                                                                <tr>
                                                                    <td>${exam.getCode()}</td>
                                                                    <td>${exam.getExaminationDate()}</td>
                                                                    <td>${exam.getResult()}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                  </div>
                                              </div>
                                              <div id="examinationsModal${patient.getCf()}" class="container tab-pane fade"><br>
                                                <div class="table-responsive">
                                                    <table class="table table-bordered" name="dataTablePatientsExaminations" id="dataTableExm${patient.getCf()}">
                                                        <thead>
                                                            <tr>
                                                                <th>Code</th>
                                                                <th>Date</th>
                                                                <th>Argument</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="examination" items="${patient.getExaminations()}">
                                                                <tr>
                                                                    <td>${examination.getSSD()}</td>
                                                                    <td>${examination.getExaminationDate()}</td>
                                                                    <td>${examination.getArgument()}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                              </div>
                                            </div>
                                        </div>
                                    </div>                                                      
                                    <div class="modal-footer">
                                        <jsp:scriptlet>
                                            y++;
                                        </jsp:scriptlet> 
                                        <button type="button" id="<%=(y)%>" name="${patient.getCf()}" class="btn btn-secondary" data-dismiss="modal" onclick="openDashandSaveName('prescribeExaminations', 'patientExaminations',<%=(y)%>)">Prescibe an examination</button>
                                        <button type="button" id="<%=(y)%>" name="${patient.getCf()}" class="btn btn-secondary" data-dismiss="modal" onclick="openDashandSaveName('prescribeExams', 'patientExams',<%=(y)%>)">Prescibe an exam</button>
                                        <button type="button" id="<%=(y)%>" name="${patient.getCf()}" class="btn btn-secondary" data-dismiss="modal" onclick="openDashandSaveName('prescribeDrugs','patientDrugs',<%=(y)%>)">Prescribe a recipe</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <div class="container-fluid" id="prescribeExaminations" style="display: none">
                        <h1 class="mt-4">Prescribe examinations</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can prescribe an examinations. All fields are mandatory.</div>
                        </div>
                        <form method="POST" action="newExamination.handler" onsubmit="return testpassExaminations(this)">
                            <input type="text" name="prescriptor" id="prescriptor" value="${user.getCf()}" style="display:none">
                            <div class="form-group">
                                <select id="patientExaminations" name="patientExaminations" class="form-control selectpicker">
                                    <option value="0" disabled selected hidden>Select patient:</option>
                                    <c:forEach var="patient" items="${patients}">
                                        <option data-tokens="${patient.getName()}${patient.getSurname()}" value="${patient.getCf()}">${patient.getName()} ${patient.getSurname()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <select id="type" name="type" class="form-control selectpicker">
                                    <option value="0" disabled selected hidden>Select type of examinations:</option>
                                    <option value="1">Normal examination</option>
                                    <option value="2">Special examination</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <select id="doctor" name="doctor" class="form-control selectpicker">
                                    <option value="0" disabled selected hidden>Select the doctor:</option>
                                    <c:forEach var="doctor" items="${doctors}">
                                        <option data-tokens="${doctor.getName()}${doctor.getSurname()}" value="${doctor.getCf()}">${doctor.getName()} ${doctor.getSurname()} specialization: ${doctor.getSpecialization()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <br><br>
                            <div class="form-group">
                                <textarea class="form-control" name="analysis" id="analysis" placeholder="Write what needs to be done in the visit"></textarea>
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input class="form-control" type="date" name="date" id="date">
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input class="form-control" type="time" name="time" id="time">
                            </div>
                            <button class="btn btn-secondary" type="submit">Submit</button>
                            <button class="btn btn-secondary" type="reset">Reset</button>
                        </form>
                    </div>
                            
                    <div class="container-fluid" id="prescribeExams" style="display: none">
                        <h1 class="mt-4">Prescribe exams</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can prescribe an exam. All fields are mandatory.</div>
                        </div>
                        <form method="POST" action="newExam.handler" onsubmit="return testpassExams(this)">
                            <input type="text" name="prescriptor" id="prescriptor" value="${user.getCf()}" style="display:none">
                            <div class="form-group">
                                <select class="form-control selectpicker" id="patientExams" name="patientExams">
                                    <option value="0" disabled selected hidden>Select patient:</option>
                                    <c:forEach var="patient" items="${patients}">
                                        <option data-tokens="${patient.getName()}${patient.getSurname()}" value="${patient.getCf()}">${patient.getName()} ${patient.getSurname()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control selectpicker" id="doctor" name="doctor" data-live-search="true">
                                    <option value="0" disabled selected hidden>Select the doctor or the province service:</option>
                                    <c:forEach var="doctor" items="${doctors}">
                                        <option data-tokens="${doctor.getName()}${doctor.getSurname()}" value="${doctor.getCf()}">${doctor.getName()} ${doctor.getSurname()} specialization: ${doctor.getSpecialization()}</option>
                                    </c:forEach>
                                    <option data-tokens="ssp" value="${ssp.getId()}">Servizio sanitario della provincia di ${ssp.getProvinceName()}</option>    
                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control selectpicker" id="recall" name="recall">
                                    <option value="0" disabled selected hidden>Select if is a recall:</option>
                                    <option value="true">This exam is a recall</option>
                                    <option value="false">This exam isn't a recall</option>
                                </select>
                            </div>
                            <br><br>
                            <div class="form-group">
                                <select class="form-control selectpicker" name="analysis" id="analysis">
                                    <option value="0" disabled selected hidden>Select exam:</option>
                                    <optgroup label="Estrazione e ricostruzione di denti">
                                        <option value="Estrazione di dente deciduo (gratuita fino a 14 anni)">Estrazione di dente deciduo (gratuita fino a 14 anni)</option>
                                        <option value="Estrazione di dente permanente (gratuita fino a 14 anni)">Estrazione di dente permanente (gratuita fino a 14 anni)</option>
                                        <option value="Altra estrazione chirurgica dente (gratuita fino a 14 anni)">Altra estrazione chirurgica dente (gratuita fino a 14 anni)</option>
                                        <option value="Ricostruzione dente con otturazione (gratuita fino a 14 anni solo in caso di evento traumatico)">Ricostruzione dente con otturazione (gratuita fino a 14 anni solo in caso di evento traumatico)</option>
                                        <option value="Ricostruzione dente mediante otturazione a tre o più superfici">Ricostruzione dente mediante otturazione a tre o più superfici</option>
                                        <option value="Ricostruzione dente mediante intarsio">Ricostruzione dente mediante intarsio</option>
                                        <option value="Applicazione di corona">Applicazione di corona</option>
                                        <option value="Applicazione di corona in lega aurea">Applicazione di corona in lega aurea</option>
                                        <option value="Altra applicazione corona">Altra applicazione corona</option>
                                        <option value="Applicazione corona e perno">Applicazione corona e perno</option>
                                    </optgroup>
                                    <optgroup label="Interventi su denti, gengive e alveoli">
                                        <option value="Gengivoplastica">Gengivoplastica</option>
                                        <option value="Asportazione di tessuto della gengiva (gratuita fino a 14 anni)">Asportazione di tessuto della gengiva (gratuita fino a 14 anni)</option>
                                        <option value="Levigatura delle radici">Levigatura delle radici</option>
                                        <option value="Intervento chirurgico preprotesico">Intervento chirurgico preprotesico</option>
                                        <option value="Asportazione di lesione dentaria della mandibola (gratuita fino a 14 anni)">Asportazione di lesione dentaria della mandibola (gratuita fino a 14 anni)</option>
                                        <option value="Trattamento ortodontico con apparecchi mobili">Trattamento ortodontico con apparecchi mobili</option>
                                        <option value="Trattamento con apparecchi fissi">Trattamento con apparecchi fissi</option>
                                        <option value="Trattamento con apparecchi funzionali">Trattamento con apparecchi funzionali</option>
                                        <option value="Riparazione di apparecchio ortodontico">Riparazione di apparecchio ortodontico</option>
                                        <option value="Radiologia diagnostica">Radiologia diagnostica</option>
                                    </optgroup>
                                    <optgroup label="Prestazioni di laboratorio">
                                        <option value="Deossicortisolo">Deossicortisolo</option>
                                        <option value="Acido 5 idrossi 3 indolacetico">Acido 5 idrossi 3 indolacetico</option>
                                        <option value="Acido delta">Acido delta</option>
                                        <option value="Ala deidrasi">Ala deidrasi</option>
                                        <option value="Alanina">Alanina</option>
                                        <option value="Albumina">Albumina</option>
                                        <option value="Aldolasi">Aldolasi</option>
                                        <option value="Alfa amilasi">Alfa amilasi</option>
                                        <option value="Alfa amilasi isoenzimi">Alfa amilasi isoenzimi</option>
                                        <option value="Androstenediolo">Androstenediolo</option>
                                    </optgroup>
                                    <optgroup label="Dermatologia allergologica">
                                        <option value="Orticarie fisiche">Orticarie fisiche</option>
                                        <option value="Inalanti">Inalanti</option>
                                        <option value="Test epicutanei a lettura ritardata">Test epicutanei a lettura ritardata</option>
                                        <option value="Test a lettura immediata">Test a lettura immediata</option>
                                        <option value="Tomoscintigrafia miocardica (PET)">Tomoscintigrafia miocardica (PET)</option>
                                        <option value="Tomoscintigrafia Cerebrale (PET)">Tomoscintigrafia Cerebrale (PET)</option>
                                        <option value="Radioterapia stereotassica">Radioterapia stereotassica</option>
                                        <option value="Irradiazione cutanea">Irradiazione cutanea</option>
                                        <option value="Terapie e riabilitazioni">Terapie e riabilitazioni</option>
                                    </optgroup>
                                    <optgroup label="Altre procedure ed esami specialistici">
                                        <option value="Ablazione tartaro">Ablazione tartaro</option>
                                        <option value="Sigillatura solchi e fossette">Sigillatura solchi e fossette</option>
                                        <option value="Rimozione protesi dentarie">Rimozione protesi dentarie</option>
                                        <option value="Immunizzazione allergia">Immunizzazione allergia</option>
                                        <option value="Immunizzazione malattia autoimmune">Immunizzazione malattia autoimmune</option>
                                        <option value="Terapia luce ultravioletta">Terapia luce ultravioletta</option>
                                        <option value="Splintaggio per gruppi di 4 denti">Splintaggio per gruppi di 4 denti</option>
                                        <option value="Trattamento applicazioni protesi semovibili">Trattamento applicazioni protesi semovibili</option>
                                    </optgroup>
                                </select>
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input class="form-control" type="date" name="date" id="date">
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input class="form-control" type="time" name="time" id="time">
                            </div>                 
                            <button class="btn btn-secondary" type="submit">Submit</button>
                            <button class="btn btn-secondary" type="reset">Reset</button>
                        </form>
                    </div>
                    
                    <div class="container-fluid" id="prescribeDrugs" style="display: none">
                        <h1 class="mt-4">Prescribe drugs</h1>
                        <div class="card mb-4">
                            <div class="card-body">Here you can prescribe some drugs. You have to prescribe at least one drug.</div>
                        </div>
                        <form method="POST" action="newRecipe.handler" onsubmit="return testpassDrugs(this)">
                            <input class="w3-input w3-border w3-light-grey" type="text" name="prescriptor" id="prescripto" value="${user.getCf()}" style="display:none">
                            <div class="form-group">
                                <select id="patientDrugs" name="patientDrugs" class="form-control selectpicker">
                                    <option value="0" disabled selected hidden>Select patient:</option>
                                    <c:forEach var="patient" items="${patients}">
                                        <option data-tokens="${patient.getName()}${patient.getSurname()}" value="${patient.getCf()}">${patient.getName()} ${patient.getSurname()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <br><br>
                            <jsp:scriptlet>
                                int j=0; 
                            </jsp:scriptlet>
                            <div class="form-group">
                                <select id="drug-1" name="drug-1" class="form-control selectpicker">
                                    <option value="false" disabled selected hidden>Select drug 1:</option>
                                    <c:forEach var="drug" items="${drugs}">
                                        <option data-tokens="${drug.getName()}" value="<%=(++j)%>">${drug.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <jsp:scriptlet>
                                j=0; 
                            </jsp:scriptlet> 
                            <div class="form-group">
                                <select id="drug-2" name="drug-2" class="form-control selectpicker">
                                    <option value="false" disabled selected hidden>Select drug 2:</option>
                                    <c:forEach var="drug" items="${drugs}">
                                        <option data-tokens="${drug.getName()}" value="<%=(++j)%>">${drug.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <jsp:scriptlet>
                                j=0; 
                            </jsp:scriptlet> 
                            <div class="form-group">
                                <select id="drug-3" name="drug-3" class="form-control selectpicker">
                                    <option value="false" disabled selected hidden>Select drug 3:</option>
                                    <c:forEach var="drug" items="${drugs}">
                                        <option data-tokens="${drug.getName()}" value="<%=(++j)%>">${drug.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <jsp:scriptlet>
                                j=0; 
                            </jsp:scriptlet> 
                            <div class="form-group">
                                <select id="drug-4" name="drug-4" class="form-control selectpicker">
                                    <option value="false" disabled selected hidden>Select drug 4:</option>
                                    <c:forEach var="drug" items="${drugs}">
                                        <option data-tokens="${drug.getName()}" value="<%=(++j)%>">${drug.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <jsp:scriptlet>
                                j=0; 
                            </jsp:scriptlet> 
                            <div class="form-group">
                                <select id="drug-5" name="drug-5" class="form-control selectpicker">
                                    <option value="false" disabled selected hidden>Select drug 5:</option>
                                    <c:forEach var="drug" items="${drugs}">
                                        <option data-tokens="${drug.getName()}" value="<%=(++j)%>">${drug.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <jsp:scriptlet>
                                j=0; 
                            </jsp:scriptlet> 
                            <br><br>
                            <div class="form-group">
                                <input class="form-control" name="analysis" id="analysis" placeholder="Write the information about the recipe"></textarea>
                            </div>               
                            <button class="btn btn-secondary" type="submit">Submit</button>
                            <button class="btn btn-secondary" type="reset">Reset</button>
                        </form>
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
