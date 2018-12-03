<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="ISO-8859-1">
<title>Course Data</title>
</head>
<body>
<h1>Contents of the COLLEGE.COURSE Table</h1>

<ol>
<c:forEach items="${requestScope.courses}" var ="course">
<li>${course.courseCode}: ${course.courseTitle} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[enrollment ${course.enrolled}/${course.capacity}] 
<ul>
	<c:forEach items="${course.professors}" var="prof">
	<li>instructor - ${prof}</li>
	</c:forEach>
</ul>
</li>
</c:forEach>
</ol>
<p><a href="${pageContext.request.contextPath}/index.jsp"><b>Back</b></a></p>
</body>
</html>