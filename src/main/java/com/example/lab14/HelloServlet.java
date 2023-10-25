package com.example.lab14;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private Connection connection;
    private PreparedStatement insertOrderPreparedStatement;
    private PreparedStatement insertToppingPreparedStatement;
    Statement statement;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencydb", "root", "");
            statement = connection.createStatement();
            insertOrderPreparedStatement = connection.prepareStatement("INSERT INTO Orders (customer_name, phone_number, email, delivery_address, pizza_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertToppingPreparedStatement = connection.prepareStatement("INSERT INTO OrderToppings (order_id, topping_id) VALUES (?, ?)");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pizza> pizzas = new ArrayList<>();

        String sql = "SELECT * FROM Pizzas";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                pizzas.add(new Pizza(id, name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Topping> toppings  = new ArrayList<>();
        String sql2 = "SELECT * FROM Toppings";
        try {
            ResultSet resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                toppings.add(new Topping(id, name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Ingredients> ingredients = new ArrayList<>();
        String IngredientsSql = "SELECT * FROM Ingredients";
        try {
            ResultSet ingredientsSqlResultSet = statement.executeQuery(IngredientsSql);
            while (ingredientsSqlResultSet.next()) {
                int id = ingredientsSqlResultSet.getInt("id");
                String name = ingredientsSqlResultSet.getString("name");
                ingredients.add(new Ingredients(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        request.setAttribute("ingredients", ingredients);
        request.setAttribute("pizzas", pizzas);
        request.setAttribute("toppings", toppings);

        RequestDispatcher dispatcher = request.getRequestDispatcher("hello-servlet.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String customerName = request.getParameter("customerName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String[] selectedToppings = request.getParameterValues("toppings");
        String[] selectedIng = request.getParameterValues("customPizzaName");

        try {
            insertOrderPreparedStatement.setString(1, customerName);
            insertOrderPreparedStatement.setString(2, phoneNumber);
            insertOrderPreparedStatement.setString(3, email);
            insertOrderPreparedStatement.setString(4, deliveryAddress);
            if(request.getParameter("pizzaId") != null) {
                int pizzaId = Integer.parseInt(request.getParameter("pizzaId"));
                insertOrderPreparedStatement.setInt(5, pizzaId);
            }
            else{
                insertOrderPreparedStatement.setNull(5, Types.INTEGER);
            }
            int rowsInserted = insertOrderPreparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = insertOrderPreparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    if (selectedToppings != null) {
                        for (String toppingId : selectedToppings) {
                            insertToppingPreparedStatement.setInt(1, orderId);
                            insertToppingPreparedStatement.setInt(2, Integer.parseInt(toppingId));
                            insertToppingPreparedStatement.executeUpdate();
                        }
                    }
                }
            }


            if(rowsInserted > 0) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("order-success.jsp");
                dispatcher.forward(request, response);
            }
            else{
                RequestDispatcher dispatcher = request.getRequestDispatcher("order-error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}