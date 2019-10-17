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
                <h2><button class="w3-bar-item w3-button" onclick="openDash('screamPatients')">Patients</button></h2>
            </div>
            <div id="screamPatients" class="w3-container dash">
                
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
