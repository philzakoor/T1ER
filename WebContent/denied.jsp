<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>T1ER</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

	<jsp:include page="banner.jsp" />

	<jsp:include page="nav-login.jsp" />

	<!-- Content starts -->

	<div class="container" style="margin-top: 30px">

		<%
			String message = (String) request.getAttribute("MESSAGE");
		if (message != null) {
		%>
		<div class="alert alert-warning" role="alert">
			<%=message%>
		</div>
		<%
			}
		%>

		<form action="LoginController" method="post">
			<div class="form-group">
				<label for="email">Username:</label> <input
					class="form-control mr-sm-2 rounded" type="email"
					placeholder="Username" id="email" name="email" />
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input
					class="form-control mr-sm-2 rounded" type="password"
					placeholder="Password" id="password" name="password" />
			</div>
			<button class="btn btn-dark rounded" type="submit">Login</button>
			<a href ="forgotpassword.jsp">
			<button class="btn btn-dark rounded" type="button">Forgot
				Password</button> </a>
			<a href ="signup.jsp">
			<button class="btn btn-dark rounded" type="button">Sign-Up</button> </a>
		</form>

	</div>

</body>
</html>