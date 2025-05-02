package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/displayProducts")
public class DisplayProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Stock List</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background: #f9f9f9; padding: 20px; }");
        out.println("h1 { color: #333; }");
        out.println("table { border-collapse: collapse; width: 50%; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }");
        out.println("th { background: #007BFF; color: white; }");
        out.println("</style></head><body>");

        out.println("<h1>Stock List</h1>");
        out.println("<table>");
        out.println("<tr><th>Product Name</th><th>Quantity</th></tr>");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT product_name, quantity FROM stock")) {

            while (rs.next()) {
                String name = rs.getString("product_name");
                int qty    = rs.getInt("quantity");
                out.printf("<tr><td>%s</td><td>%d</td></tr>%n", name, qty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<tr><td colspan='2' style='color:red;'>Database Error: "
                        + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}
