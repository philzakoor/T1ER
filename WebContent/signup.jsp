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

	<!-- Content -->

	<div class="container" style="margin-top: 30px">

		<!-- Alert Message -->
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

		<!-- Signup Form -->
		<div class="row border">
			<div class="col-sm-8">
				<h2 class="display-2">Sign-up</h2>
				<form action="RegisterController?action=register" method="post">
					<div class="form-group">
						<label for="email">Email Address:</label> <input type="email"
							class="form-control" placeholder="Enter Email" id="email" name="email" />
					</div>
					<div class="form-group">
						<label for="password1">Password:</label> <input type="password"
							class="form-control" placeholder="Enter Password" id="password1" name="password1"/>
					</div>
					<div class="form-group">
						<label for="password2">Re-type Password:</label> <input
							type="password" class="form-control"
							placeholder="Re-type Password" id="password2" name="password2" />
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-dark rounded-pill">Submit</button>
					</div>
				</form>
			</div>
			<div class="col-sm-4"></div>
		</div>
	</div>

</body>
</html>