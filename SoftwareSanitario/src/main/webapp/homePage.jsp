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
        </style>
    </head>
    <body>
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
                        <a href="#about" onclick="w3_close()" class="w3-bar-item w3-button w3-padding "><i class="fa fa-user fa-fw w3-margin-right"></i>Dashboard specialist</a> 
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
        <header id="portfolio">
                <a href="#"><img src="${avatarPath}" style="width:65px;" class="w3-circle w3-right w3-margin w3-hide-large w3-hover-opacity"></a>
                <span class="w3-button w3-hide-large w3-xxlarge w3-hover-text-grey" onclick="w3_open()"><i class="fa fa-bars"></i></span>
        </header>
        <div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>
        
        <div class="w3-main" style="margin-left:300px">
        
            <div class="w3-bar w3-white">
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamExams')">Exams</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamExaminations')">Examinations</button></h2>
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamTickets')">Tickets</button></h2>
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
                <script>
                    function openExams(){
                        document.getElementById("Exams").style.display = "block";  
                    }
                </script>
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
                                            <td>${exam.getCode()}</td>
                                            <td>${exam.getExaminationDate()}</td>
                                            <td>${exam.getIsDone()}</td>
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
                <%--
                <div id="newExamination" class="w3-container dash" style="display:none">
                <h4>New examinations:</h4>
                    <form class="w3-container " method="POST" action="newExamination.handler?idPatient=${user.getCf()}&idDoctor=${generaldoctorpatient.getCf()}">
                        <label class="w3-text-teal"><b>Giorno:</b></label>
                        <input class="w3-input w3-border w3-light-grey" type="date" id="date" name="date">

                        <label class="w3-text-teal"><b>Ora:</b></label>
                        <input class="w3-input w3-border w3-light-grey" type="time" name="time" id="time">
                        <br>
                        <button class="w3-button w3-round-large w3-blue" type="submit">Submit</button>
                        <button class="w3-button w3-round-large w3-blue" type="reset">Reset</button>
                    </form>
                </div>
                --%>
                <script>
                    function openExaminations(){ 
                        document.getElementById("Examinations").style.display = "block";  
                    }
                </script>
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
                <script>
                    function openTickets(){
                        document.getElementById("Tickets").style.display = "block";  
                    }
                </script>
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
                                    Codice fiscale :
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
                        <script>
                            function openDashGeneralDoctor(dashName) {
                                document.getElementById(dashName).style.display = "block";  
                            }
                        </script>    
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
            </div>
            
        </div>
        <br>
    <script>
        
        function openDash(dashName) {
            var i;
            var x = document.getElementsByClassName("dash");
            for (i = 0; i < x.length; i++) {
                x[i].style.display = "none";  
            }
            document.getElementById(dashName).style.display = "block";  
        }

        // Script to open and close sidebar
        function w3_open() {
            document.getElementById("mySidebar").style.display = "block";
            document.getElementById("myOverlay").style.display = "block";
        }
 
        function w3_close() {
            document.getElementById("mySidebar").style.display = "none";
            document.getElementById("myOverlay").style.display = "none";
        }
        function search() {
            // Declare variables 
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("myInput");
            filter = input.value.toUpperCase();
            table = document.getElementById("myTable");
            tr = table.getElementsByTagName("tr");

            // Loop through all table rows, and hide those who don't match the search query
            for (i = 0; i < tr.length; i++) {
              td = tr[i].getElementsByTagName("td")[0];
              if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                  tr[i].style.display = "";
                } else {
                  tr[i].style.display = "none";
                }
              } 
            }
        }
        function sortTable(myTable) {
          var table, rows, switching, i, x, y, shouldSwitch, count;
          count = 0;
          table = document.getElementById(myTable);
          switching = true;
          /*Make a loop that will continue until
          no switching has been done:*/
          while (switching) {
            //start by saying: no switching is done:
            switching = false;
            rows = table.rows;
            /*Loop through all table rows (except the
            first, which contains table headers):*/
            for (i = 1; i < (rows.length - 1); i++) {
              //start by saying there should be no switching:
              shouldSwitch = false;
              /*Get the two elements you want to compare,
              one from current row and one from the next:*/
              x = rows[i].getElementsByTagName("TD")[1];
              y = rows[i + 1].getElementsByTagName("TD")[1];
              //check if the two rows should switch place:
              if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                //if so, mark as a switch and break the loop:
                count++;
                shouldSwitch = true;
                break;
              }
            }
            if (shouldSwitch) {
              /*If a switch has been marked, make the switch
              and mark that a switch has been done:*/
              rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
              switching = true;
            }
          }
          if(count == 0){
            switching = true;
            /*Make a loop that will continue until
            no switching has been done:*/
            while (switching) {
              //start by saying: no switching is done:
              switching = false;
              rows = table.rows;
              /*Loop through all table rows (except the
              first, which contains table headers):*/
              for (i = 1; i < (rows.length - 1); i++) {
                //start by saying there should be no switching:
                shouldSwitch = false;
                /*Get the two elements you want to compare,
                one from current row and one from the next:*/
                x = rows[i].getElementsByTagName("TD")[1];
                y = rows[i + 1].getElementsByTagName("TD")[1];
                //check if the two rows should switch place:
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                  //if so, mark as a switch and break the loop:
                  //count++;
                  shouldSwitch = true;
                  break;
                }
              }
              if (shouldSwitch) {
                /*If a switch has been marked, make the switch
                and mark that a switch has been done:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
              }
            }
          }
        }
    </script>    
    
    </body>
</html>
