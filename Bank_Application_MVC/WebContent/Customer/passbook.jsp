<%@page import="com.Aurionpro.Model.Transaction"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Passbook</title>
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
<script type="text/javascript">
function showAlert() {
	var errorMessage = '<%=request.getAttribute("errorMessage")%>';
	var successMessage = '<%=request.getAttribute("successMessage")%>';
	if (successMessage != null && successMessage.trim() !== '') {
		alert(successMessage);
	} else if (errorMessage != null && errorMessage.trim() !== '') {
		alert(errorMessage);
	}
}
</script>
</head>
<body onload="showAlert()">
	<div class="container">
		<h1>Passbook</h1>

		<div class="header">
			<form method="get"
				action="<%=request.getContextPath()%>/PassbookController">
				<div>
					<label for="accountNo">Enter Account Number:</label> <input
						type="text" id="accountNo" name="accountNo"> <input
						type="submit" value="Search">
				</div>
			</form>
			<button onclick="window.location.href='Customer/customer.jsp'">Back</button>
		</div>

		<table class="customer-table">
			<thead>
				<tr>
					<th>Sender Account No</th>
					<th>Receiver Account No</th>
					<th>Type of Transaction</th>
					<th>Amount</th>
					<th>Date</th>
				</tr>
			</thead>
			<tbody>
				<%
					// Assuming transactionList is a List of Transaction objects passed from the servlet
					List<Transaction> transactionList = (List<Transaction>) request.getAttribute("transactions");
					System.out.println(transactionList);
					if (transactionList != null && !transactionList.isEmpty()) {
						for (Transaction transaction : transactionList) {
				%>
				<tr>
					<td><%=transaction.getSenderAccNo()%></td>
					<td><%=transaction.getReceiverAccNo()%></td>
					<td><%=transaction.getTypeOfTransaction()%></td>
					<td><%=transaction.getAmount()%></td>
					<td><%=transaction.getDate()%></td>
				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="5">No transactions available</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>
