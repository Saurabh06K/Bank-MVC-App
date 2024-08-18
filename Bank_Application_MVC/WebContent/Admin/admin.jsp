<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="admin.css">
<title>Admin Dashboard</title>
<style type="text/css">
body {
	overflow: hidden;
}

.container {
	padding-bottom: 30%;
}

.nav {
	padding: 3%;
}

.header {
	display: flex;
	justify-content: flex-end;
	padding: 30px;
}

h3 {
	font-size: 30px;
	font-style: italic;
}

.content {
	font-size: 20px;
}

.header h1 {
	margin-right: 34%;
}

.logout {
	padding: 5px 10px;
	font-size: 1.5em;
	background-color: #C4DFE6;
	color: #1995AD;
	text-decoration: none;
	border: 1px solid #ccc;
	border-radius: 10px;
}

.logout-button:hover {
	background-color: #ddd;
}
</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<h1>Admin Home</h1>
			<form action="<%=request.getContextPath()%>/LogoutController">
				<button class="logout">Log out</button>
			</form>
		</div>

		<!-- Navigation Menu -->
		<div class="nav">
			<button onclick="window.location.href='addCustomer.jsp'">Add
				New Customer</button>
			<button onclick="window.location.href='addBankAccount.jsp'">Add
				Bank Account</button>
			<form action="<%=request.getContextPath()%>/ViewCustomersController"
				method="GET">
				<button type="submit">View Customers</button>
			</form>
			<form
				action="<%=request.getContextPath()%>/ViewTransactionController"
				method="GET">
				<button type="submit">View Transactions</button>
			</form>
		</div>

		<div>
			<h3>Welcome to Admin Dashboard!</h3>
		</div>
		<div class="content">
			<p>Customer Addition / View Customers / View Transactions</p>
		</div>
	</div>
</body>
</html>