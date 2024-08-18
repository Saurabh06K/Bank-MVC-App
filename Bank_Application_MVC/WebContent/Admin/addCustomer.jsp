<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="admin.css">
<title>Add Customer</title>
<style type="text/css">
body {
	background-color: #E7E8D1;
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
	padding-bottom: 5%;
}

.header {
	position: relative;
	background-color: #1995AD;
	padding: 15px;
	font-size: 20px;
	text-align: center;
	color: #ffffff;
}

.header h1 {
	margin: 0;
	font-size: 2em;
}

.nav {
	display: flex;
	justify-content: space-around;
	background-color: #C4DFE6;
	padding: 10px 0;
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

input[type="submit"], input[type="reset"] {
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
<script>
	function validateForm() {
		var email = document.getElementById('email').value;
		var password = document.getElementById('password').value;

		// Email validation
		var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailPattern.test(email)) {
			alert("Please enter a valid email address.");
			return false;
		}

		// Password validation
		var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
		if (!passwordPattern.test(password)) {
			alert("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
			return false;
		}
	}
    function showAlert() {
        var errorMessage = <%= request.getAttribute("errorMessage") != null ? "'" + request.getAttribute("errorMessage") + "'" : "null" %>;
        var successMessage = <%= request.getAttribute("successMessage") != null ? "'" + request.getAttribute("successMessage") + "'" : "null" %>;

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
		<div class="header">
			<h1>Add New Customer</h1>
		</div>

		<div>
			<form class="login"
				action="<%=request.getContextPath()%>/CustomerController"
				method="post" onsubmit="return validateForm()">

				<input type="text" id="firstname" name="firstname"
					placeholder="Enter First Name" required><br> <br>
				<input type="text" id="lastname" name="lastname"
					placeholder="Enter Last Name" required><br> <br>
				<input type="email" id="email" name="email"
					placeholder="Enter Email Id" required><br> <br> <input
					type="password" id="password" name="password"
					placeholder="Password" required><br>
				<div class="button-container">
					<input type="submit" value="Submit"> <button type="button" onclick="window.location.href='/Bank_Application_MVC/Admin/admin.jsp'">Back</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>