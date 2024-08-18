<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Transaction</title>
<style type="text/css">
body {
	background-color: #C4DFE6;
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: Arial, Helvetica, sans-serif;
	display: flex;
	justify-content: center;
	font-family: Arial, Helvetica, sans-serif;
	overflow: hidden;
}

.container {
	background-color: #C4DFE6;
	border-radius: 10px;
	box-shadow: 0px 0px 12px 0px #000;
	width: 100%;
	margin: 0;
	text-align: center;
}

.mainForm {
	padding: 3%;
}

.header {
	position: relative;
	background-color: #1995AD;
	padding: 25px;
	font-size: 20px;
	text-align: center;
	color: #ffffff;
}

.header h1 {
	margin: 0;
	font-size: 2em;
}

.button-container {
	display: flex;
	justify-content: space-between;
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

button {
	width: 25%;
	text-align: center;
	padding: 15px;
	border: none;
	border-radius: 5px;
	background-color: #1995AD;
	color: white;
	cursor: pointer;
	margin-top: 10px;
	margin-left: 35%;
	margin-bottom: 4%;
	font-size: 16px;
}

input[type="submit"], input[type="button"] {
	width: 25%;
	text-align: center;
	padding: 15px;
	border: none;
	border-radius: 5px;
	background-color: #1995AD;
	color: white;
	cursor: pointer;
	margin-top: 10px;
	margin-left: 35%;
	margin-bottom: 4%;
	font-size: 16px;
}

input[type="submit"]:hover, input[type="reset"]:hover {
	opacity: 0.9;
}
</style>
<script type="text/javascript">
function toggleAccountFields() {
	var transactionType = document.getElementById("transaction").value;
	var fromAccountField = document.getElementById("fromAccountNo");
	var toAccountField = document.getElementById("toAccountNo");

	if (transactionType === "transfer") {
		fromAccountField.style.display = "block";
		toAccountField.style.display = "block";
		fromAccountField.required = true;
		toAccountField.required = true;
	} else {
		fromAccountField.style.display = "block";
		toAccountField.style.display = "none";
		fromAccountField.required = true;
		toAccountField.required = false;
	}
}

function showAlert() {
	var errorMessage = '<%=request.getAttribute("errorMessage")%>';
	var successMessage = '<%=request.getAttribute("successMessage")%>';
		if (successMessage != null && successMessage.trim() !== '') {
			alert(successMessage);
		} else {
			alert(errorMessage);
		}
	}
</script>
</head>
<body onload="showAlert()">
	<div class="container">
		<div class="header">
			<h1>New Transaction</h1>
		</div>

		<div>
			<form class="mainForm"
				action="<%=request.getContextPath()%>/TransactionController"
				method="post">
				<select id="transaction" name="transaction"
					onchange="toggleAccountFields()">
					<option value="">Select Type Of Transaction:</option>
					<option value="credit">Credit</option>
					<option value="debit">Debit</option>
					<option value="transfer">Transfer</option>
				</select><br>
				<br> <input type="text" id="fromAccountNo" name="fromAccountNo"
					placeholder="Account Number" required style="display: none;"><br>
				<br> <input type="text" id="toAccountNo" name="toAccountNo"
					placeholder="To Account Number" style="display: none;"><br>
				<br> <input type="number" id="amount" name="amount"
					placeholder="Enter Amount" required><br>
				<br>

				<div class="button-container">
					<input type="submit" value="Submit">
					<button type="button"
						onclick="window.location.href='/Bank_Application_MVC/Customer/customer.jsp'">Back</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>