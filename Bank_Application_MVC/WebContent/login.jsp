<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
</head>
<body onload="showError('${errorMessage}')">
	<div class="container">
		<h2>Login</h2>
		<form class="login" action="LoginController" method="post">

			<input type="email" id="email" name="email"
				placeholder="Enter Email" required><br> <br> <input
				type="password" id="password" name="password"
				placeholder="Enter Password" required><br> <br> <select
				id="role" name="role">
				<option value="admin">Login as: Admin</option>
				<option value="customer">Login as: Customer</option>
			</select> <br>
			<input type="submit" name="login"> <input type="reset"
				value="Cancel">
		</form>
	</div>
	<script>
		function showError(message) {
			if (message) {
				alert(message);
			}
		}
	</script>
</body>
</html>