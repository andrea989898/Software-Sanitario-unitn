<%-- 
    Document   : index
    Created on : 10 ott 2019, 23:39:52
    Author     : franc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="javax.servlet.http.Cookie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <title>Software sanitario: Authentication Area</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> 
    </head>
    <body>
        <%
	    Cookie[] cookies=request.getCookies();
	    String email = "", password = "",rememberVal="";
	    if (cookies != null) {
	         for (Cookie cookie : cookies) {
	           if(cookie.getName().equals("cookemail")) {
	             email = cookie.getValue();
	           }
	           if(cookie.getName().equals("cookpass")){
	             password = cookie.getValue();
	           }
	           if(cookie.getName().equals("cookrem")){
	             rememberVal = cookie.getValue();
	           }
	        }
	    }
	%>
        <div align="left" style="width: 50%; height: 100%; position:absolute; float:left;" class="w3-blue">
            
        </div>
        <div style=" width: 50%; height: 100%; background-color: white; float:right; ">
        <div style="width: 30%;
height: 30%;
position: relative;
top: 40%;
left: 40%;
margin-top: 40%;"align="center" vertical-align="middle">
        <form class="w3-container" method="POST" action="login.handler">
            <label class="w3-text-teal"><b>Email:</b></label>
            <input class="w3-input w3-border w3-light-grey " type="email" id="username" name="username" placeholder="mariorossi@email.it" value="<%=email%>">
    

            <label class="w3-text-teal"><b>Password:</b></label>
            <input class="w3-input w3-border w3-light-grey" type="password" name="password" id="password" placeholder="password" value="<%=password%>">
            <br>
            <input class="w3-check" type="checkbox" name="remember" value="1">
            <label>Remember me</label>
            <br><br>
            <button class="w3-button w3-round-large w3-blue" type="submit">Submit</button>
            <button class="w3-button w3-round-large w3-blue" type="reset">Reset</button>
        </form> 
        <div class="w3-container w3-margin-top">
            <a href="recuperaPassword.html"><button class="w3-button w3-round-large w3-blue">Forgot password?</button></a>  
        </div>
        </div>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>