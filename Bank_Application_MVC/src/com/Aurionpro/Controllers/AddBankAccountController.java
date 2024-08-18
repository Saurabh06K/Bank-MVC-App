package com.Aurionpro.Controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Aurionpro.Data.UserDb;
import com.Aurionpro.Model.Customer;

@WebServlet("/AddBankAccountController")
public class AddBankAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDb userdb;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		userdb = new UserDb();
		userdb.connectToDb();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		RequestDispatcher requestDispatcher = null;

		// Fetch recently added customers
		List<Customer> recentCustomers = userdb.getRecentCustomers();
		System.out.println(recentCustomers);
		request.setAttribute("recentCustomers", recentCustomers);

		String customerIdParam = request.getParameter("customerId");

		if (customerIdParam != null && !customerIdParam.trim().isEmpty()) {
			try {
				int customerId = Integer.parseInt(customerIdParam);
				Customer customer = userdb.getCustomerById(customerId);

				if (customer != null) {
					request.setAttribute("customer", customer);
				} else {
					request.setAttribute("errorMessage", "Customer not found.");
				}
			} catch (NumberFormatException e) {
				request.setAttribute("errorMessage", "Invalid Customer ID format.");
			}
		} else {
			request.setAttribute("errorMessage", "Customer ID is required.");
		}

		request.getRequestDispatcher("/Admin/addBankAccount.jsp").forward(request, response);

		// Forward to JSP to display customer details or error message

		requestDispatcher = request.getRequestDispatcher("Admin/addBankAccount.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		userdb = new UserDb();
		userdb.connectToDb();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
			return;
		}


		String customerIdParam = request.getParameter("customerId");
		int customerId = Integer.parseInt(customerIdParam);

		String accountNumber = generateRandomAccountNumber();
		double balance = 5000.0;

		boolean accountAdded = userdb.addBankAccount(customerId, accountNumber, balance);

		if (accountAdded) {
			request.setAttribute("successMessage", "Account created successfully for Customer ID: " + customerId);
		} else {
			request.setAttribute("errorMessage", "Failed to create account for Customer ID: " + customerId);
		}
		request.getRequestDispatcher("Admin/addBankAccount.jsp").forward(request, response);
	}

	private String generateRandomAccountNumber() {
		// Logic to generate a random account number
		return "AC" + (int) (Math.random() * 1000000);
	}

}
