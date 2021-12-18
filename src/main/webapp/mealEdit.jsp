<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal" />

<html>
<head>
    <title>Новая еда/редактирование</title>
</head>
<body>

<form method="post" accept-charset="UTF-8">
    <fieldset>
        <legend>Новая еда/редактирование</legend>
        <input type="hidden" id="id" name="mealId" value="${meal.id}">
        <p><label for="date">Дата</label><input type="datetime-local" id="date" name= "dateTime" value="${meal.dateTime}"></p>
        <p><label for="description">Описание</label><input type="text" id="description" name="description" value="${meal.description}"></p>
        <p><label for="calories">Калории</label><input type="text" id="calories" name="calories" value="${meal.calories}"></p>
    </fieldset>
    <p><input type="submit" value="Отправить"></p>
</form>

</body>
</html>
