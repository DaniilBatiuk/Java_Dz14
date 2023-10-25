<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Виберіть піцу:</h1>
<form method="post" action="hello-servlet">
    <select name="pizzaId">
        <c:forEach items="${pizzas}" var="pizza">
            <option value="${pizza.id}">${pizza.name} - ${pizza.price}</option>
        </c:forEach>
    </select>

    <h2>Виберіть топінги:</h2>
    <c:forEach items="${toppings}" var="topping">
        <input type="checkbox" name="toppings" value="${topping.id}">${topping.name} - ${topping.price}<br>
    </c:forEach>

    <h2>Ваші дані:</h2>
    Ім'я: <input type="text" name="customerName"><br>
    Телефон: <input type="text" name="phoneNumber"><br>
    Email: <input type="text" name="email"><br>
    Адреса доставки: <input type="text" name="deliveryAddress"><br>

    <input type="submit" value="Замовити">
</form>

<h1>Custom pizza:</h1>
<form method="post" action="hello-servlet">
    <h2>Виберіть інгредієнти:</h2>
    <c:forEach items="${ingredients}" var="ingredient">
        <input type="checkbox" name="customPizzaName" value="${ingredient.id}">${ingredient.name}<br>
    </c:forEach>

    <h2>Ваші дані:</h2>
    Ім'я: <input type="text" name="customerName"><br>
    Телефон: <input type="text" name="phoneNumber"><br>
    Email: <input type="text" name="email"><br>
    Адреса доставки: <input type="text" name="deliveryAddress"><br>

    <input type="submit" value="Замовити">
</form>
</body>
</html>
