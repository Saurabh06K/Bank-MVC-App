<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Customer Home</title>
<style>
body {
	background-color: white;
	box-sizing: border-box;
	margin: 0;
	padding: 0;
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
	padding-bottom: 32%;
}

.header {
	display: flex;
	background-color: #1995AD;
	justify-content: flex-end;
	color: white;
	padding: 20px;
}

.header h1 {
	margin-right: 34%;
}

.logout {
	padding: 5px 10px;
	font-size: 1.5em;
	margin-top: 15px;
	height: 50px;
	background-color: #C4DFE6;
	color: #1995AD;
	text-decoration: none;
	border: 1px solid #ccc;
	border-radius: 10px;
}

.logout-button:hover {
	background-color: #ddd;
}

.nav {
	background-color: #467FCF;
	overflow: hidden;
}

.nav {
	display: flex;
	justify-content: space-around;
	background-color: #C4DFE6;
	padding: 20px 0;
}

.nav button {
	background-color: #1995AD;
	color: white;
	padding: 10px 20px;
	border-radius: 5%;
	box-shadow: 0px 0px 12px 0px white;
	cursor: pointer;
	font-size: 20px;
	transition: background-color 0.3s;
	box-shadow: 0px 0px 12px 0px white;
}

.content {
	background-color: #C4DFE6;
	box-sizing: border-box;
	font-size: 2em;
	font-style: italic;
	margin: 0;
	padding: 8%;
	width: 100%;
	height: 100%;
	font-family: Arial, Helvetica, sans-serif;
	display: flex;
	justify-content: center;
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
</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<h1>Customer Home</h1>
			<form action="<%=request.getContextPath()%>/LogoutController">
				<button class="logout">Log out</button>
			</form>
		</div>

		<div class="nav">
			<button onclick="window.location.href='passbook.jsp'">Passbook</button>
			<button onclick="window.location.href='transaction.jsp'">New
				Transaction</button>
			<button onclick="window.location.href='editProfile.jsp'">Edit
				Profile</button>
		</div>

		<div class="content">
			<p>Welcome to Our Website!</p>
		</div>
	</div>
</body>
</html>
