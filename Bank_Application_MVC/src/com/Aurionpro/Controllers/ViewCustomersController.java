package com.Aurionpro.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Aurionpro.Data.UserDb;
import com.Aurionpro.Model.CustomerAccount;;

@WebServlet("/ViewCustomersController")
public class ViewCustomersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewCustomersController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDb userDb = new UserDb();
		userDb.connectToDb();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// By filter
		String searchName = request.getParameter("searchName");
		// Get all customers
		List<CustomerAccount> customers = userDb.getAllCustomers();
		try {
			if (searchName != null && !searchName.trim().isEmpty()) {
				customers = userDb.getCustomersByName(searchName);
			} else {
				customers = userDb.getAllCustomers();
			}
			request.setAttribute("customers", customers);
			request.getRequestDispatcher("Admin/viewCustomers.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error retrieving customer data.");
			request.getRequestDispatcher("Admin/viewCustomers.jsp").forward(request, response);
		}

		request.getRequestDispatcher("Admin/viewCustomers.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
