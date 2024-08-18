<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.Aurionpro.Model.Customer"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
@charset "ISO-8859-1";

body {
	background-color: #E7E8D1;
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: Arial, Helvetica, sans-serif;
}

.container {
	background-color: #C4DFE6;
	border-radius: 10px;
	box-shadow: 0px 0px 12px 0px #000;
	width: 100%;
	max-height: 600px;
	margin: 0;
	text-align: center;;
}

.header {
	background-color: #1995AD;
	padding: 15px;
	font-size: 20px;
	text-align: center;
	color: #ffffff;
	display: flex;
	justify-content: space-between;
}

.customer-details {
	background-color: #ffffff;
	border: 1px solid #ddd;
	padding: 8px;
	align-items: center;
	width: 50%;
	margin: auto;
	border-radius: 20px;
	display: flex;
	flex-direction: column;
}

.recent-customers {
	background-color: #ffffff;
	border: 1px solid #ddd;
	padding: 8px;
	align-items: center;
	width: 50%;
	margin: auto;
	border-radius: 20px;
	display: flex;
	flex-direction: column;
}

h3 {
	padding: 15px;
	font-size: 20px;
	text-align: center;
}

.button-container {
	position: absolute;
	top: 10px;
	right: 20px;
}

.header h1 {
	font-size: 2em;
	margin-left: 35%;
}

.back {
	margin: auto;
	padding: 6px;
	font-size: 1.5em;
	margin-right: 20px;
	border-radius: 10px;
	color: #1995AD;
}

.logout-button {
	position: absolute;
	top: 10px;
	right: 20px;
	padding: 5px 10px;
	background-color: white;
	color: #333;
	text-decoration: none;
	border: 1px solid #ccc;
	border-radius: 5px;
}

.logout-button:hover {
	background-color: #ddd;
}

.content {
	background-color: #C4DFE6;
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	width: 100%;
	height: 100%;
	font-family: Arial, Helvetica, sans-serif;
	display: flex;
	justify-content: center;
	overflow: hidden;
	font-family: Arial, Helvetica, sans-serif;
}

.button-container {
	display: flex;
	justify-content: flex-end;
	margin-top: 20px;
	margin-right: 25%;
}

label, select, input {
	display: block;
	width: 75%;
	margin-bottom: 0;
	margin-top: 15px;
	margin-left: 10%;
	padding: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
}

input[type="submit"], input[type="reset"] {
	width: 25%;
	text-align: center;
	padding: 15px;
	border: none;
	border-radius: 5px;
	background-color: #1995AD;
	color: white;
	cursor: pointer;
	margin-top: 30px;
	margin-left: 35%;
	margin-bottom: 10px;
	font-size: 16px;
}

input[type="submit"]:hover, input[type="reset"]:hover {
	opacity: 0.9;
}
</style>
<title>Add Bank Account</title>
</head>
<body>
	<div class="header">
		<h1>Add Bank Account</h1>
		<button class="back"
			onclick="window.location.href='<%=request.getContextPath()%>/Admin/admin.jsp'">Back</button>
	</div>

	<div class="form-container">
		<form method="get"
			action="<%=request.getContextPath()%>/AddBankAccountController">
			<div>
				<input type="text" id="customerId" name="customerId"
					placeholder="Search by -> Customer ID:" required>
			</div>
			<div>
				<input type="submit" value="Submit">
			</div>
		</form>

		<%-- Display error or success messages if any --%>
		<%
			String errorMessage = (String) request.getAttribute("errorMessage");
			String successMessage = (String) request.getAttribute("successMessage");
			Customer customer = (Customer) request.getAttribute("customer");
		%>

		<%
			if (errorMessage != null && !errorMessage.trim().isEmpty()) {
		%>
		<div class="error-message" align="center">
			<p>
				<strong><%=errorMessage%></strong>
			</p>
		</div>
		<%
			}
		%>

		<%
			if (successMessage != null && !successMessage.trim().isEmpty()) {
		%>
		<div class="success-message" align="center">
			<p>
				<strong><%=successMessage%></strong>
			</p>
		</div>
		<%
			}
		%>

		<%
			if (customer != null) {
		%>
		<div class="customer-details">
			<h3>Customer Details</h3>
			<p>
				<strong>Customer ID:</strong>
				<%=customer.getCustomerid()%></p>
			<p>
				<strong>First Name:</strong>
				<%=customer.getFirstname()%></p>
			<p>
				<strong>Last Name:</strong>
				<%=customer.getLasttname()%></p>
			<p>
				<strong>Email:</strong>
				<%=customer.getEmail()%></p>
		</div>
		<%
			} else if (request.getParameter("customerId") != null
					&& !request.getParameter("customerId").trim().isEmpty()) {
		%>
		<div class="customer-details" align="center">
			<p>
				<strong>CustomerID:</strong>
				<%=request.getParameter("customerId")%></p>
		</div>
		<br>
		<%
			}
		%>

		<%-- Recently Added Customers Section --%>
		<div class="recent-customers">
			<h3>Recently Added Customers</h3>
			<ul>
				<%
					List<Customer> recentCustomers = (List<Customer>) request.getAttribute("recentCustomers");
					if (recentCustomers != null) {
						for (Customer recentCustomer : recentCustomers) {
				%>
				<li>
					<p>
						<strong>Customer ID:</strong>
						<%=recentCustomer.getCustomerid()%></p>
					<p>
						<strong>Email:</strong>
						<%=recentCustomer.getEmail()%></p>
				</li>
				<%
					}
					}
				%>
			</ul>
		</div>

		<%-- Generate Account Number Section --%>
		<form method="post"
			action="<%=request.getContextPath()%>/AddBankAccountController">
			<div>
				<input type="hidden" name="customerId"
					value="<%=request.getParameter("customerId")%>"> <input
					type="submit" value="Generate Account Number">
			</div>
		</form>
	</div>
</body>
</html>
