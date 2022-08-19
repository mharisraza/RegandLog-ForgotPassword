<%

    Users user = (Users) session.getAttribute("currentUser");

    if (user == null) {

        response.sendRedirect("login.jsp");

    }


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.student.practice.entities.Users"%>




<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CSS only -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <title>JSP Page</title>
    </head>
    <body>
        
    <center><h1>Welcome: <%= user != null ? user.getName() : ""%></h1>
        <center><h2>Your Email is: <%= user != null ? user.getEmail() : ""%></h2>
            <center><h1> Please click on logout button to be logged out </h1><!-- comment -->
                <center></h1><a href="LogOutServlet"> Logout</a></h1>

                    </body>
                    </html>


