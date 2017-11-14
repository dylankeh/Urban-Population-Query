<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //String message=request.getParameter("message");
    String message = (request.getAttribute("message")==null)
            ? "": (String)request.getAttribute("message");
    
    if (message == null || message.trim().length()<1 ) {
        message="";
    }
%> 
<!DOCTYPE html>
<html>
    <head>
        <title>Lab1</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/login.css">
    </head>
    <body>
        <div class="main-page">
            <div class="form">
                <form class="login-form" action="DisplayInfo" method="POST">
                    <input type="text" name="ID" placeholder="City ID" />
                    <button>DISPLAY INFO</button>
                    <p class="error"><%=message %></p>
                </form>
            </div>
        </div>
    </body>
</html>