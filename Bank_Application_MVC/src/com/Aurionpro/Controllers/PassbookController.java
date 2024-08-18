package com.Aurionpro.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Aurionpro.Data.UserDb;
import com.Aurionpro.Model.Transaction;

@WebServlet("/PassbookController")
public class PassbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDb userDb = new UserDb();

	public PassbookController() {
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

		String userEmail = (String) session.getAttribute("email");
		String accountNo = request.getParameter("accountNo");
		if (accountNo == null || accountNo.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Account number is required.");
		}

		boolean isAccountOwnedByUser = userDb.isAccountOwnedByEmail(accountNo, userEmail);
		if (!isAccountOwnedByUser) {
			request.setAttribute("errorMessage", "You are not authorized to view transactions for this account.");
		}

		List<Transaction> transactions = userDb.getTransactionsForAccount(accountNo);
		request.setAttribute("transactions", transactions);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Customer/passbook.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
