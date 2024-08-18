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

@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDb userDb = new UserDb();

	public TransactionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		userDb.connectToDb();

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		String transactionType = request.getParameter("transaction");
		String fromAccountNo = request.getParameter("fromAccountNo");
		String toAccountNo = request.getParameter("toAccountNo");
		double amount = Double.parseDouble(request.getParameter("amount"));

		try {
			// Handle transaction
			if (transactionType.equalsIgnoreCase("credit") || transactionType.equalsIgnoreCase("debit")) {
				userDb.handleSameAccountTransaction(fromAccountNo, transactionType, amount);
			} else if (transactionType.equalsIgnoreCase("transfer")) {
				userDb.handleDifferentAccountTransaction(fromAccountNo, toAccountNo, amount);
			}
			request.setAttribute("successMessage", "Transaction completed successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("Customer/transaction.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
