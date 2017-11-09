<%@ page contentType="text/html;charset=UTF-8" %>
<link href="style.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="meals">Meals</a></h3>
<h2>Edit meal</h2>
<form method="post" action="meals">
    <input name="id" type="hidden" value=${meal.id}>
    Date: <input type="datetime-local" name="dateTime" value=${meal.dateTime}><br>
    Description: <input type="text" name="description" value=${meal.description}></br>
    Calories: <input type="text" name="calories" value=${meal.calories}><br>
    <input type="reset" value="Reset">
    <input type="submit" value="Save">
</form>
</body>
</html>