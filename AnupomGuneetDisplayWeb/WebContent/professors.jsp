<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="ISO-8859-1">
<title>Professor Data</title>
</head>
<body>
<h1>Contents of the COLLEGE.PROFESSOR Table</h1>

<ol>
<c:forEach items="${requestScope.professors}" var ="prof">
<li>${prof.pid}: ${prof.firstName} ${prof.middleName} ${prof.lastName}
<ul>
	<c:forEach items="${prof.courses}" var="course">
	<li>teaches - ${ course.courseCode }: ${ course.courseTitle }</li>
	</c:forEach>
</ul>
</li>
</c:forEach>
</ol>
<p><a href="${pageContext.request.contextPath}/index.jsp"><b>Back</b></a></p>
</body>
</html>