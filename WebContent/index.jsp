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
		<div class="row border border-5">
			<div class="col-sm-8">
				<h2 class="display-2">Easy Shared Expenses</h2>
				<p class="lead">No more shilly-shally expenses between friends.
				</p>
				<a href="signup.jsp"><button type="button"
						class="btn btn-dark rounded-pill">Sign-up</button></a>
			</div>
			<div class="col-sm-4"></div>
		</div>
	</div>

</body>
</html>