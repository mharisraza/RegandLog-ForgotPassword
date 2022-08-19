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
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Best-Studio
 */
@MultipartConfig
public class RegisterServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            UserDao dao = new UserDao(ConnectionProvider.getConnection());

            HttpSession session = request.getSession();

            RequestDispatcher dispatcher = null;

            String userName = request.getParameter("user_name");

            String userEmail = request.getParameter("user_email");

            String userPassword = request.getParameter("user_password");

            String userConfirmPassword = request.getParameter("re_pass");

            if (userName == null || userName.equals("")) {

                Message msg = new Message("Username field cannot be empty.", "error", "alert-danger");
                try {
                    ConnectionProvider.getConnection().close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);

            }
            if (userPassword == null || userPassword.equals("")) {
                try {
                    ConnectionProvider.getConnection().close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
                Message msg = new Message("Password field cannot be empty.", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);

            }
            if (userEmail == null || userEmail.equals("")) {
                try {
                    ConnectionProvider.getConnection().close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
                Message msg = new Message("Email field cannot be empty.", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);

            }

            if (userConfirmPassword == null || userConfirmPassword.equals("")) {
                try {
                    ConnectionProvider.getConnection().close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
                Message msg = new Message("Confirm Password field cannot be empty.", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);

            } else if (!userConfirmPassword.equals(userPassword)) {
                try {
                    ConnectionProvider.getConnection().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = new Message("Confirm Password do not Match Password field", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);
            }
 if (ConnectionProvider.getConnection() == null) {
                Message msg = new Message("Please try later, our services are busy.", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            }

            Users users = new Users(userName, userEmail, userPassword);
            try {
                ConnectionProvider.getConnection().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (dao.saveUser(users)) { // if data saved to user 
                Message msg = new Message("Registered Successfully, You can login now!", "success", "alert-success");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            } else { // if any error caused 
                Message msg = new Message("An Error has occurred,", "error", "alert-danger");
                session.setAttribute("status", msg);
                dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);
                     
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
