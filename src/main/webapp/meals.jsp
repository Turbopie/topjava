<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="meals" scope="request" type="java.util.List"/>

<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        @import url("style.css");
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <tbody>
    <tr>
        <td style="">Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Update</td>
        <td>Delete</td>
    </tr>
    <c:forEach var="mealList" items="${meals}">
        <tr <c:if test="${mealList.excess}">style="color:#ff0000"</c:if> >
        <td>
        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
        ${fn:replace(mealList.dateTime, 'T', ' ')}
        </td>
        <td>${mealList.description}</td>
        <td>${mealList.calories}</td>
        <td><a href="?action=edit&mealId=${mealList.id}">Update</a></td>
        <td><a href="?action=delete&mealId=${mealList.id}">Delete</a></td>
        </tr>
    </c:forEach>
    <br>
    <p><a href="?action=edit">Add new meal</a></p>
    </tbody>
</table>
</body>
</html>
