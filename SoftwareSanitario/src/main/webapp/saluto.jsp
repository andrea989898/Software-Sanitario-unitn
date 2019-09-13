<%-- 
    Document   : saluto
    Created on : 11 set 2019, 15:59:11
    Author     : franc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Software sanitario: Private Area</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> 
    </head>
    <body>
        <jsp:scriptlet>
             response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        </jsp:scriptlet>
        <div class="w3-container w3-blue">
            <h2>Welcome authenticated user!</h2>
        </div>
        <br>
        <div class="w3-container">
            <h4>Iscriviti se non hai ancora un account.</h4>
            <a href="cambioPassword.html"><button class="w3-button w3-round-large w3-blue">Cambia password</button></a>
        </div>
        <br>
        <div class="w3-container">
            <a href="logout.handler" class="w3-container"><button class="w3-button w3-round-large w3-blue">Logout</button></a>
        </div>
    </body>
</html>
