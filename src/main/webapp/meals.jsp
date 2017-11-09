<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="style.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<h1><a href="mealEdit.jsp">Add meal</a></h1>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>isExceed</th>
        <th>Action</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td class=${meal.exceed ? "exceeded" : "notExceeded" }>${meal.exceed}</td>
        <td>
            <a href=?action=edit&id=${meal.id}>edit</a>
            <a href=?action=delete&id=${meal.id}>delete</a>
        </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>