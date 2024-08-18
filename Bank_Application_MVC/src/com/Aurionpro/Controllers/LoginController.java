package com.Aurionpro.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Aurionpro.Data.UserDb;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDb userDb = new UserDb();
		userDb.connectToDb();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String selectedRole = request.getParameter("role");

		String loginRole = null;

		if (selectedRole.equalsIgnoreCase("admin")) {
			loginRole = userDb.validateAdmin(email, password);
		} else if (selectedRole.equalsIgnoreCase("customer")) {
			loginRole = userDb.validateCustomers(email, password);
		}

		if (loginRole != null && selectedRole.equalsIgnoreCase(loginRole)) {
			System.out.println("Login successful for user: " + email + " as " + loginRole);

			// Start Session
			session.setAttribute("email", email);
			session.setAttribute("role", loginRole);

			// Set session timeout (optional, in minutes)
			session.setMaxInactiveInterval(60 * 60); // 30 minutes

			// Checking Roles
			if (loginRole.equalsIgnoreCase("admin")) {
				response.sendRedirect("Admin/admin.jsp");
				return;
			} else if (loginRole.equalsIgnoreCase("customer")) {
				response.sendRedirect("Customer/customer.jsp");
				return;
			}
		}

		// If login fails
		request.setAttribute("errorMessage", "Invalid username, password, or role.");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
