package com.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // ǰ����֤�Ѿ�ȷ������һ�£���������һ�κ����֤
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.html?error=password_mismatch");
            return;
        }
        
        try {
            // ����û����Ƿ��Ѵ���
            if (UserDAO.isUsernameExists(username)) {
                response.sendRedirect("register.html?error=username_exists");
                return;
            }
            
            // �����û�����ע��
            User user = new User(username, password);
            
            if (UserDAO.register(user)) {
                response.sendRedirect("login.html?register=success");
            } else {
                response.sendRedirect("register.html?error=registration_failed");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.sendRedirect("register.html?error=server_error");
        }
    }
}
