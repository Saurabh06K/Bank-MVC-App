<%@page import="com.Aurionpro.Model.CustomerAccount"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Customers</title>
<style>
body {
margin: 0;
}
.container {
	width: 100%;
	background-color: #E7E8D1;
	overflow-y: auto;
	border-radius: 5px;
}

.customer-table {
	 width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
    table-layout: auto;
}

.customer-table th, .customer-table td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: center;
	word-break: break-word;
}

.customer-table th {
	background-color: #1995AD;
	color: white;
}


h1 {
	text-align: center;
}

.header {
	display: flex;
	justify-content: space-between;
	margin-left: 10%;
	margin-right: 8%;
	font-size: 25px;
}

button {
font-size: 25px;
color: white;
background-color: #1995AD;
border-radius: 5px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>View Customers</h1>

		<div class="header">
			<form method="get"
				action="<%=request.getContextPath()%>/ViewCustomersController">
				<div>
					<label for="searchName">Search by Name:</label> <input type="text"
						id="searchName" name="searchName"
						placeholder="Enter first or last name"> <input
						type="submit" value="Search">
				</div>
			</form>
			<button onclick="window.location.href='Admin/admin.jsp'">Back</button>
		</div>

		<table class="customer-table">
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Account Number</th>
					<th>Balance</th>
				</tr>
			</thead>
			<tbody>
				<%
					List<CustomerAccount> customers = (List<CustomerAccount>) request.getAttribute("customers");
					System.out.println(customers);
					if (customers != null && !customers.isEmpty()) {
						for (CustomerAccount customer : customers) {
				%>
				<tr>
					<td><%=customer.getFirstName()%></td>
					<td><%=customer.getLastName()%></td>
					<td><%=customer.getAccountNumber()%></td>
					<td><%=customer.getBalance()%></td>
				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="4">No customers found.</td>
				</tr>
				<%
					}
				%>

			</tbody>
		</table>
	</div>
</body>
</html>
