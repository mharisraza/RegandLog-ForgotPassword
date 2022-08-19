/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.student.practice.Servlets;

import com.student.practice.Dao.UserDao;
import com.student.practice.Helper.ConnectionProvider;
import com.student.practice.entities.Message;
import com.student.practice.entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Best-Studio
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String userEmail = request.getParameter("user_email");

            String userPassword = request.getParameter("user_password");

            if (userEmail == null || userEmail.equals("")) {
                Message msg = new Message("Invalid Email", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            }
            if (userPassword == null || userPassword.equals("")) {
                Message msg = new Message("Invalid Password", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            }

            if (ConnectionProvider.getConnection() == null) {
                Message msg = new Message("Please try later, our services are busy.", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            }

            UserDao dao = new UserDao(ConnectionProvider.getConnection());

            Users u = dao.getUserByEmailAndPassword(userEmail, userPassword);

            if (u == null) {
                Message msg = new Message("Invalid Email or Password", "error", "alert-danger");
                HttpSession s = request.getSession();
                s.setAttribute("status", msg);
                response.sendRedirect("login.jsp");

            } else {
                HttpSession s = request.getSession();
                s.setAttribute("currentUser", u);
                response.sendRedirect("home.jsp");

            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
