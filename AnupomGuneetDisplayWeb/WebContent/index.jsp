<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test/Debug Utility Web</title>
</head>
<body>
<h1>Get data from RESTful Services</h1>
<p>You may view data in the COLLEGE database.</p> 
<p>${ requestScope.message }</p>
<form method="post" action="${pageContext.request.contextPath}/showData">
<input type = "submit" name = "submit" value = "Courses" /> 
or
<input type = "submit" name = "submit" value = "Professors" /> 
</form>
</body>
</html>