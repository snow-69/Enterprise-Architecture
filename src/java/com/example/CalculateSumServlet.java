package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculateSum")
public class CalculateSumServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Get numbers from the form
        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        int sum = num1 + num2;

        // Set content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output styled HTML result
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head><meta charset='UTF-8'><title>Sum Result</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background: #f2f2f2; display: flex; height: 100vh; justify-content: center; align-items: center; }");
        out.println(".result { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center; }");
        out.println("h1 { color: #007BFF; }");
        out.println("</style></head><body>");
        out.println("<div class='result'>");
        out.println("<h1>The sum of " + num1 + " and " + num2 + " is: " + sum + "</h1>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
