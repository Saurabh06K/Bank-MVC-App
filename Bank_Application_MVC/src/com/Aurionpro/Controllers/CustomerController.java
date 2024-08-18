package com.Aurionpro.Controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Aurionpro.Data.UserDb;

@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CustomerController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDb userDb = new UserDb();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		RequestDispatcher requestDispatcher = null;
		try {
			userDb.connectToDb();
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Database Connection Failed");
			request.getRequestDispatcher("Admin/admin.jsp").forward(request, response);
			return;
		}

		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Check if email exists in the database
		boolean emailExists = userDb.checkEmailInDatabase(email);
		if (emailExists) {
			request.setAttribute("errorMessage", "Email is already registered.");
			requestDispatcher = request.getRequestDispatcher("Admin/addCustomer.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		System.out.println("Attempting to register user");

		boolean isRegistered = userDb.addCustomer(firstname, lastname, email, password);

		if (isRegistered) {
			System.out.println("Signup successful for user!");
			request.getSession().setAttribute("successMessage", "Signup successful for user!");
			response.sendRedirect("/Bank_Application_MVC/Admin/addCustomer.jsp");
		} else {
			request.setAttribute("errorMessage", "Signup Failed");
			requestDispatcher = request.getRequestDispatcher("/Bank_Application_MVC/Admin/addCustomer.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
