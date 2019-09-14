<%-- 
    Document   : cambioPassword
    Created on : 14 set 2019, 22:48:43
    Author     : franc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Software sanitario: Cambio password</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> 
    </head>
    <body>
         <div class="w3-container w3-blue">
            <h2>Cambio password</h2>
        </div>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
