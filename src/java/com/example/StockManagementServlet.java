package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/stockAction")
public class StockManagementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String productName = request.getParameter("product_name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DatabaseConnection.getConnection()) {

            switch (action) {
                case "Add Product":
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO stock (product_name, quantity) VALUES (?, ?)")) {
                        stmt.setString(1, productName);
                        stmt.setInt(2, quantity);
                        stmt.executeUpdate();
                        out.println("<h2 style='color:green;'>✅ Product added successfully!</h2>");
                    }
                    break;

                case "Update Product":
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE stock SET quantity = ? WHERE product_name = ?")) {
                        stmt.setInt(1, quantity);
                        stmt.setString(2, productName);
                        int rows = stmt.executeUpdate();
                        if (rows > 0)
                            out.println("<h2 style='color:blue;'>🔄 Product updated successfully!</h2>");
                        else
                            out.println("<h2 style='color:orange;'>⚠️ Product not found!</h2>");
                    }
                    break;

                case "Delete Product":
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "DELETE FROM stock WHERE product_name = ?")) {
                        stmt.setString(1, productName);
                        int rows = stmt.executeUpdate();
                        if (rows > 0)
                            out.println("<h2 style='color:red;'>❌ Product deleted successfully!</h2>");
                        else
                            out.println("<h2 style='color:orange;'>⚠️ Product not found!</h2>");
                    }
                    break;

                default:
                    out.println("<h2 style='color:red;'>❗ Invalid action</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h2 style='color:red;'>❌ Database Error: " + e.getMessage() + "</h2>");
        }
    }
}
