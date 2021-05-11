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
<title>T1ER - Main</title>
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
		//initalize User, Notifications, Tiers		
	User user = (User) request.getAttribute("USER");

	if (user == null) {
		request.setAttribute("MESSAGE", "Please log in.");
		RequestDispatcher rd = request.getRequestDispatcher("denied.jsp");
		rd.forward(request, response);
		return;
	}

	Map<UUID, Notification> userNotifications = (Map<UUID, Notification>) request.getAttribute("Notifications");
	Map<UUID, Tier> userTiers = (Map) request.getAttribute("Tiers");
	Map<UUID, String> tierTotals = (Map) request.getAttribute("TierTotals");
	Map<UUID, Map<UUID, Expense>> tierRecentExpenses = (Map) request.getAttribute("TierRecentExpenses");
	String activeTier = request.getParameter("activeTier");
	if (activeTier == null) {
		activeTier = "";
	}
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
					<input type="hidden" name="userId" id="userId"
						value="<%=user.getId().toString()%>" />
					<button class="btn btn-link btn-sm text-white" type="submit">My
						T1ERS</button>
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
					<svg width="1em" height="1em" viewBox="0 0 16 16"
						class="bi bi-door-closed-fill" fill="currentColor"
						xmlns="http://www.w3.org/2000/svg">
  			<path fill-rule="evenodd"
							d="M4 1a1 1 0 0 0-1 1v13H1.5a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2a1 1 0 0 0-1-1H4zm2 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z" />
			</svg>
				</button>
			</a>
		</div>
	</nav>

	<!-- navbar ends -->
	<!-- Content -->

	<div class="container" style="margin-top: 30px">

		<!-- Show notifications -->

		<!-- todo: create dynamic notifications -->

		<%
			for (Map.Entry<UUID, Notification> notificationItem : userNotifications.entrySet()) {
		%>

		<div class="alert alert-warning" role="alert">
			<div class="d-flex flex-row justify-content-between">
				<div class="d-flex flex-col justify-content-start">
					<p>
						<span class="badge badge-success">New</span>
						<%=notificationItem.getValue().getMessage()%>
					</p>
				</div>
				<div class="d-flex flex-col justify-content-end">
					<form action="TierController?action=removeNotification" method="post">
						<input type="hidden" name="notificationId"
							value="<%=notificationItem.getKey().toString()%>" /> <input
							type="hidden" name="userId" value="<%=user.getId().toString()%>" />
						<button class="btn btn-link" type="submit">
							<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
								class="bi bi-x-circle-fill" fill="black"
								xmlns="http://www.w3.org/2000/svg">
 						<path fill-rule="evenodd"
									d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z" />
						</svg>
						</button>
					</form>
				</div>
			</div>
		</div>

		<%
			}
		%>

		<!-- Notification end -->

		<!-- Tiers -->

		<!-- Page Title -->

		<div class="media ml-auto">
			<div class="media-body align-self-center">
				<h2>Tiers</h2>
			</div>

			<button class="btn btn-link btn-sm text-dark align-self-center"
				data-toggle="collapse" data-target="#addTier">
				<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
					class="bi bi-plus-circle-fill" fill="black"
					xmlns="http://www.w3.org/2000/svg">
  										<path fill-rule="evenodd"
						d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z" />
										</svg>
			</button>
		</div>

		<!-- Page Title End -->

		<!-- New Tier Form -->

		<div class="media ml-auto collapse" id="addTier"
			style="margin-bottom: 30px">
			<div class="media-body align-self-center">
				<form class="form-inline"
					action="TierController?action=createTier" method="post">
					<input type="hidden" id="userId" name="userId"
						value="<%=user.getId().toString()%>" /> <label for="name">Add
						new Tier:</label> <input class="form-control mr-sm-2 p-2 rounded"
						type="text" placeholder="Tier Name" id="tierName" name="tierName" />
					<button class="btn btn-dark rounded p-2" type="submit">Add
						New Tier</button>
				</form>
			</div>

			<button class="btn btn-link btn-sm text-dark align-self-center"
				data-toggle="collapse" data-target="#addTier">
				<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
					class="bi bi-dash-circle-fill" fill="currentColor"
					xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
						d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7z" />
								</svg>
			</button>
		</div>

		<!-- New Tier Form End -->

		<!-- Tier Views -->

		<div id="accordion" style="margin-top: 30px">

			<%
				//loop and display tiers
			int counter = 0;
			for (Map.Entry<UUID, Tier> tierItem : userTiers.entrySet()) {
				Tier thisTier = tierItem.getValue();
				String visible = "";
				if (activeTier.contentEquals(tierItem.getKey().toString())) {
					visible = "show";
				}
			%>
			<!-- Tier -->

			<div class="card">

				<!-- Tier Name -->

				<div class="card-header bg-dark " id="<%="headerTier" + counter%>">
					<div class="d-flex flex-row justify-content-between">
						<div class="d-flex justify-content-start">
							<h3 class="text-white"><%=thisTier.getName()%></h3>
						</div>
						<div class="d-flex justify-content-end">
							<button
								class="btn btn-link btn-sm collapsed font-weight-bold text-white"
								data-toggle="collapse" data-target="#<%="tier" + counter%>"
								aria-expanded="false" aria-controls="<%="tier" + counter%>">
								<svg width="2em" height="2em" viewBox="0 0 16 16"
									class="bi bi-caret-down float-right" fill="white"
									xmlns="http://www.w3.org/2000/svg">
  					<path fill-rule="evenodd"
										d="M3.204 5L8 10.481 12.796 5H3.204zm-.753.659l4.796 5.48a1 1 0 0 0 1.506 0l4.796-5.48c.566-.647.106-1.659-.753-1.659H3.204a1 1 0 0 0-.753 1.659z" />
					</svg>
							</button>
						</div>
					</div>
				</div>

				<!-- Tier Name End -->

				<!-- tier data -->

				<div id="<%="tier" + counter%>" class="collapse <%=visible%>"
					aria-labelledby="<%="header" + tierItem.getKey().toString()%>"
					data-parent="#accordion">
					<div class="card-body">

						<!-- Add Members Form -->

						<div class="media ml-auto collapse" id="addMember"
							style="margin-bottom: 30px">
							<div class="media-body align-self-center">
								<form class="form-inline" action="TierController?action=addMember"
									method="post">
									<input type="hidden" id="userId" name="userId"
										value="<%=user.getId().toString()%>" /> <input type="hidden"
										id="tierId" name="tierId"
										value="<%=tierItem.getKey().toString()%>" /> <label
										for="name">Add Member:</label> <input
										class="form-control mr-sm-2 p-2 rounded" type="email"
										placeholder="Member Email" id="memberEmail" name="memberEmail" />
									<button class="btn btn-dark rounded p-2" type="submit">Add
										Member</button>
								</form>
							</div>

							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse" data-target="#addMember">
								<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
									class="bi bi-dash-circle-fill" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
										d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7z" />
								</svg>
							</button>
						</div>

						<!-- Add Member Form End -->
						<!-- Tier Members -->

						<div class="row">
							<div class="col-sm-4">
								<div class="d-flex flex-row justify-content-between">
									<div class="d-flex flex-col justify-content-start">
										<h3>Tier Members</h3>
									</div>
									<div class="d-flex flex-col justify-content-end">
										<button class="btn btn-link" data-toggle="collapse"
											data-target="#addMember">
											<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
												class="bi bi-plus-circle-fill" fill="black"
												xmlns="http://www.w3.org/2000/svg">
  										<path fill-rule="evenodd"
													d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z" />
										</svg>
										</button>
									</div>
								</div>
								<ul class="list-group list-group-flush">
									<%
										//loop through and display tier members
									for (User member : thisTier.getMembers()) {
									%>
									<li class="list-group-item"><%=member.getEmail()%></li>
									<%
										}
									%>
								</ul>
							</div>

							<!-- End Tier Members -->

							<!-- Tier Totals -->

							<div class="col-sm-4">
								<h3>Tier Total:</h3>
								<p><%=tierTotals.get(tierItem.getKey())%></p>
							</div>

							<!-- End Tier Totals -->

							<!-- Last 4 Expenses -->

							<div class="col-sm-4 border border-dark">
								<div class="d-flex flex-row justify-content-between">
									<div class="d-flex flex-col justify-content-start">
										<h3>Last Expenses</h3>
									</div>
									<div class="d-flex flex-col justify-content-end">
										<form action="TierController?action=expense" method="post">
											<input type="hidden" name="userId" id="userID"
												value="<%=user.getId().toString()%>" /> <input
												type="hidden" name="tierId" id="tierId"
												value="<%=tierItem.getKey().toString()%>" />
											<button class="btn btn-link" type="submit">
												<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
													class="bi bi-plus-circle-fill" fill="black"
													xmlns="http://www.w3.org/2000/svg">
  												<path fill-rule="evenodd"
														d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z" />
												</svg>
											</button>
										</form>
									</div>
								</div>
								<ol class="list-group list-group-flush">
									<%
										Map<UUID, Expense> expenses = tierRecentExpenses.get(tierItem.getKey());
									for (Map.Entry<UUID, Expense> expense : expenses.entrySet()) {
									%>
									<li class="list-group-item">
										<div class="row">
											<div class="col"><%=expense.getValue().getName()%></div>
											<div class="col"><%=expense.getValue().getAmount().toString()%></div>
										</div>
									</li>
									<%
										}
									%>
								</ol>
							</div>

							<!-- End Last Expense -->

						</div>
					</div>
				</div>

				<!-- End Tier Data -->

			</div>
			<%
				++counter;
			}
			%>

			<!-- Tier End -->

		</div>

		<!-- End Tier Views -->

	</div>

	<!-- End Content -->

</body>
</html>