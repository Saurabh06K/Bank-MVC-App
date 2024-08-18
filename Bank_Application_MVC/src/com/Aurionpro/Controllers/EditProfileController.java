package com.Aurionpro.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Aurionpro.Data.UserDb;

@WebServlet("/EditProfileController")
public class EditProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection connection;
	UserDb userDb = new UserDb();
	private PreparedStatement preparedStatement;

	public EditProfileController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		userDb.connectToDb();
		connection = userDb.getConnection();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		String email = (String) session.getAttribute("email");
		System.out.println("Email from session: " + email);

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String password = request.getParameter("password");

		String updateQuery = "UPDATE customers SET firstname=?, lastname=?,  password=? WHERE email=?";

		try {
		    preparedStatement = connection.prepareStatement(updateQuery);
		    preparedStatement.setString(1, firstName);
		    preparedStatement.setString(2, lastName);
		    preparedStatement.setString(3, password);
		    preparedStatement.setString(4, email);

		    int result = preparedStatement.executeUpdate();

		    System.out.println(result);

		    if (result > 0) {
		        session.setAttribute("successMessage", "Profile updated successfully!");
		        response.sendRedirect("login.jsp"); // Redirect to customer home page
		    } else {
		        request.setAttribute("errorMessage", "Profile update failed!");
		        RequestDispatcher dispatcher = request.getRequestDispatcher("Customer/editProfile.jsp");
		        dispatcher.forward(request, response); // Forward to the edit profile page
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    request.setAttribute("errorMessage", "An error occurred while updating your profile.");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("Customer/editProfile.jsp");
		    dispatcher.forward(request, response); // Forward to the edit profile page
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
