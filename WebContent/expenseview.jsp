<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.UUID"%>
<%@page import="beans.User"%>
<%@page import="beans.Notification"%>
<%@page import="beans.Tier"%>
<%@page import="beans.Expense"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>T1ER - Expenses</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

	<%
		//initalize 	
	User user = (User) request.getAttribute("USER");

	if (user == null) {
		request.setAttribute("MESSAGE", "Please log in.");
		RequestDispatcher rd = request.getRequestDispatcher("denied.jsp");
		rd.forward(request, response);
		return;
	}

	Tier tier = (Tier) request.getAttribute("TIER");

	if (tier == null) {
		RequestDispatcher rd = request.getRequestDispatcher("denied.jsp");
		rd.forward(request, response);
	}
	
	Expense editExpense = (Expense) request.getAttribute("EXPENSE");
	String expenseName = "";
	String expenseDescription = "";
	String expenseAmount = "";
	String expenseId = "";
	if (editExpense != null){
		expenseId = editExpense.getId().toString();
		expenseName = editExpense.getName();
		expenseDescription = editExpense.getDescription();
		expenseAmount = editExpense.getAmount().toString();
	}

	Map<UUID, Expense> expenses = (Map) request.getAttribute("Expenses");
	Map<UUID, Expense> userExpenses = (Map) request.getAttribute("UserExpenses");
	%>

	<!-- top banner -->
	<div class="container-fluid" style="margin-bottom: 0">
		<div class="row">
			<div class="col ml-auto">
				<h1 class="display-4 t1er">T1ER</h1>
			</div>
		</div>

	</div>

	<!-- navbar -->

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark sticky-top">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
			<form action="TierController" method="post">
				<input type="hidden" name="userId" id="userId" value="<%=user.getId().toString()%>" />
				<button class="btn btn-link btn-sm text-white" type="submit">My T1ERS</button>
				</form>
			</li>
		</ul>
		<div class="media ml-auto text-white">
			<div class="media-body align-self-center">
				<span class="sr-only">Username:</span> <span class="text-bottom"><%=user.getEmail()%></span>
			</div>

			<form action="TierController?action=userSettings" method="post">
				<input type="hidden" name="userId" id="userId"
					value="<%=user.getId().toString()%>" />
				<button class="btn btn-link btn-sm text-dark align-self-center"
					type="submit">
					<svg width="1.25em" height="1.25em" viewBox="0 0 18 18"
						class="bi bi-gear-fill" fill="grey"
						xmlns="http://www.w3.org/2000/svg">
	  		<path fill-rule="evenodd"
							d="M9.405 1.05c-.413-1.4-2.397-1.4-2.81 0l-.1.34a1.464 1.464 0 0 1-2.105.872l-.31-.17c-1.283-.698-2.686.705-1.987 1.987l.169.311c.446.82.023 1.841-.872 2.105l-.34.1c-1.4.413-1.4 2.397 0 2.81l.34.1a1.464 1.464 0 0 1 .872 2.105l-.17.31c-.698 1.283.705 2.686 1.987 1.987l.311-.169a1.464 1.464 0 0 1 2.105.872l.1.34c.413 1.4 2.397 1.4 2.81 0l.1-.34a1.464 1.464 0 0 1 2.105-.872l.31.17c1.283.698 2.686-.705 1.987-1.987l-.169-.311a1.464 1.464 0 0 1 .872-2.105l.34-.1c1.4-.413 1.4-2.397 0-2.81l-.34-.1a1.464 1.464 0 0 1-.872-2.105l.17-.31c.698-1.283-.705-2.686-1.987-1.987l-.311.169a1.464 1.464 0 0 1-2.105-.872l-.1-.34zM8 10.93a2.929 2.929 0 1 0 0-5.86 2.929 2.929 0 0 0 0 5.858z" />
			</svg>
				</button>
			</form>
			
			<a href="index.jsp">
			<button class="btn btn-link btn-sm text-white align-self-center"
					type="Button">
			<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-door-closed-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  			<path fill-rule="evenodd" d="M4 1a1 1 0 0 0-1 1v13H1.5a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2a1 1 0 0 0-1-1H4zm2 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
			</svg>
			</button>
			</a>
		</div>
	</nav>
	
	<!-- navbar ends -->

	<!-- Content Starts -->

	<div class="container" style="margin-top: 30px">

		<h2><%=tier.getName()%></h2>

		<!-- Add Expense Form -->

		<form class="form-inline"
			action="ExpenseController?action=createOrUpdateExpense" method="post">
			<input type="hidden" name="userId" id="userId"
				value="<%=user.getId().toString()%>" /> <input type="hidden"
				name="tierId" id="tierId" value="<%=tier.getId().toString()%>" />
				<input type="hidden" name="expenseId" id="expenseId" value="<%=expenseId %>" /> <label
				for="expenseName">Expense Name:</label> <input
				class="form-control mr-sm-2 p-2 rounded" type="text"
				placeholder="Expense Name" id="expenseName" name="expenseName" value="<%=expenseName%>" /> <label
				for="expenseDescription">Description:</label> <input
				class="form-control mr-sm-2 p-2 rounded" type="text"
				placeholder="Expense Description" id="expenseDescription" name="expenseDescription" value="<%=expenseDescription %>" />
			<label for="expenseAmount">Amount:</label> <input
				class="form-control mr-sm-2 p-2 rounded" type="number"
				placeholder="0.00" id="expenseAmount" name="expenseAmount" value="<%=expenseAmount%>" />

			<button class="btn btn-dark rounded p-2" type="submit">Submit</button>
		</form>

		<!-- Add Expense Form End -->

		<!-- Tab Nav -->

		<ul class="nav nav-tabs" role="tablist" style="margin-top: 30px">
			<li class="nav-item"><a class="nav-link active"
				data-toggle="tab" href="#all">All Expenses</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#your">Your Expenses</a></li>

		</ul>

		<!-- Tab Nav End -->
		<!-- Tab panes -->

		<div class="tab-content border border-top-0">

			<!-- All Tier Expenses -->
			<div id="all" class="container tab-pane active">
				<br>
				<h3>All Expenses</h3>
				<ul class="list-group list-group-flush">
					<%
						for (Map.Entry<UUID, Expense> expense : expenses.entrySet()) {
					%>
					<li class="list-group-item">
						<div class="row">
							<div class="col"><%=expense.getValue().getName()%></div>
							<div class="col"><%=expense.getValue().getAmount().toString()%></div>
							<div class="col"><%=expense.getValue().getDescription()%></div>
							<div class="col">
								<span class="sr-only">Owner:</span>
								<%=expense.getValue().getOwner().getEmail()%>
							</div>
						</div>
					</li>
					<%
						}
					%>
				</ul>
			</div>

			<!-- All Tier Expense End -->
			<!-- User Expense -->

			<div id="your" class="container tab-pane fade">
				<br>
				<h3>Your Expenses</h3>
				<ul class="list-group list-group-flush">
					<%
						for (Map.Entry<UUID, Expense> expense : userExpenses.entrySet()) {
					%>
					<li class="list-group-item">
						<div class="row">
							<div class="col">
								<span class="sr-only">Name:</span>
								<%=expense.getValue().getName()%>
							</div>
							<div class="col">
								<span class="sr-only">Amount: </span>
								<%=expense.getValue().getAmount().toString()%>
							</div>
							<div class="col">
								<%=expense.getValue().getDescription()%>
							</div>
							<div class="col">
								<form action="ExpenseController?action=deleteExpense" method="post">
									<input type="hidden" name="userId" id="userId"
										value="<%=user.getId().toString()%>" /> <input type="hidden" name="tierId"
										id="tierId" value="<%=tier.getId().toString()%>" /> <input type="hidden" 
										name="expenseId" id="expenseId"
										value="<%=expense.getValue().getId().toString()%>"/>
									<button class="btn btn-link btn-sm text-dark align-self-center">
										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-trash" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
	  									<path
												d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
	  									<path fill-rule="evenodd"
												d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z" />
									</svg>
									</button>
								</form>
								<form action="ExpenseController?action=editExpense" method="post">
									<input type="hidden" name="userId" id="userId"
										value="<%=user.getId().toString()%>" /> <input type="hidden" name="tierId"
										id="tierId" value="<%=tier.getId().toString()%>" /> <input type="hidden"
										name="expenseId" id="expenseId"
										value="<%=expense.getValue().getId().toString()%>" />
									<button class="btn btn-link btn-sm text-dark align-self-center">
										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-pencil-square" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
  										<path
												d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
  										<path fill-rule="evenodd"
												d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
									</svg>
									</button>
								</form>
							</div>
						</div>
					</li>
					<%
						}
					%>
				</ul>
			</div>

			<!-- User Expense End -->
		</div>
		<!-- Tabs End -->
	</div>

</body>
</html>