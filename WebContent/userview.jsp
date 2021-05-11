<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.UUID"%>
<%@page import="beans.User"%>
<%@page import="beans.Notification"%>
<%@page import="beans.Tier"%>
<%@page import="java.util.HashMap"%>
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
		//initalize User, Messages, Tiers		
	User user = (User) request.getAttribute("USER");

	if (user == null) {
		RequestDispatcher rd = request.getRequestDispatcher("denied.jsp");
		rd.forward(request, response);
		return;
	}

	Map<UUID, Tier> userTiers = (Map) request.getAttribute("UserTiers");
	Map<UUID, Tier> memberTiers = (Map) request.getAttribute("MemberTiers");
	String message = (String) request.getAttribute("MESSAGE");
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

		<%
			if (message != null) {
		%>
		<div class="alert alert-warning" role="alert">
			<%=message%>
		</div>
		<%
			}
		%>

		<!-- Tab nav -->
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item"><a class="nav-link active"
				data-toggle="tab" href="#yourTiers">Your T1ERs</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#memberTiers">Member T1ERs</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#changePassword">Change Password</a></li>
		</ul>

		<!-- tab nav ends -->
		<!-- Tab panes -->

		<div class="tab-content border border-top-0">

			<!-- User Tier Tab Start -->
			<div id="yourTiers" class="container tab-pane active">
				<br>
				<h2>Your T1ERs</h2>
				<ul class="list-group list-group-flush">


					<%
						if (userTiers != null) {
						int counter = 0;
						for (Map.Entry<UUID, Tier> userTierItem : userTiers.entrySet()) {
					%>

					<li class="list-group-item">
						<!-- Add Member Form -->

						<div class="media ml-auto collapse"
							id="<%="addMember" + counter%>" style="margin-bottom: 30px">
							<div class="media-body align-self-center">
								<form class="form-inline" action="UserController?action=addMember"
									method="post">
									<input type="hidden" id="userId" name="userId"
										value="<%=user.getId().toString()%>" /> <input type="hidden"
										id="tierId" name="tierId"
										value="<%=userTierItem.getKey().toString()%>" /> <label
										for="name">Add Member:</label> <input
										class="form-control mr-sm-2 p-2 rounded" type="email"
										placeholder="Member Email" id="memberEmail" name="memberEmail" />
									<button class="btn btn-dark rounded p-2" type="submit">Add
										Member</button>
								</form>
							</div>

							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse" data-target="<%="#addMember" + counter%>">
								<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
									class="bi bi-dash-circle-fill" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
										d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7z" />
								</svg>
							</button>
						</div> <!-- Member Form End --> <!-- Remove Member Form -->

						<div class="media ml-auto collapse"
							id="<%="removeMember" + counter%>" style="margin-bottom: 30px">
							<div class="media-body align-self-center">
								<form class="form-inline"
									action="UserController?action=removeMember" method="post">
									<input type="hidden" id="userId" name="userId"
										value="<%=user.getId().toString()%>" /> <input type="hidden"
										id="tierId" name="tierId"
										value="<%=userTierItem.getKey().toString()%>" /> <label
										for="name">Remove Member:</label> <input
										class="form-control mr-sm-2 p-2 rounded" type="email"
										placeholder="Member Email" id="memberEmail" name="memberEmail" />
									<button class="btn btn-dark rounded p-2" type="submit">Remove
										Member</button>
								</form>
							</div>

							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse"
								data-target="<%="#removeMember" + counter%>">
								<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
									class="bi bi-dash-circle-fill" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
										d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7z" />
								</svg>
							</button>
						</div> <!-- Remove Form End --> <!-- Edit Tier Form -->

						<div class="media ml-auto collapse" id="<%="editTier" + counter%>"
							style="margin-bottom: 30px">
							<div class="media-body align-self-center">
								<form class="form-inline" action="UserController?action=editTier"
									method="post">
									<input type="hidden" id="userId" name="userId"
										value="<%=user.getId().toString()%>" /> <input type="hidden"
										id="tierId" name="tierId"
										value="<%=userTierItem.getKey().toString()%>" /> <label
										for="name">Edit Tier:</label> <input
										class="form-control mr-sm-2 p-2 rounded" type="text"
										placeholder="<%=userTierItem.getValue().getName()%>"
										id="tierName" name="tierName" />
									<button class="btn btn-dark rounded p-2" type="submit">Update
										Tier</button>
								</form>
							</div>

							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse"
								data-target="<%="#removeMember" + counter%>">
								<svg width="1.5em" height="1.5em" viewBox="0 0 16 16"
									class="bi bi-dash-circle-fill" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
										d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7z" />
								</svg>
							</button>
						</div> <!--  Edit Tier Form end -->

						<div class="media ml-auto">
							<div class="media-body align-self-center"><%=userTierItem.getValue().getName()%></div>

							<!-- Edit Tier -->
							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse" data-target="<%="#editTier" + counter%>">
								<svg width="1em" height="1em" viewBox="0 0 16 16"
									class="bi bi-pencil-square" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
							  <path
										d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
							  <path fill-rule="evenodd"
										d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
							</svg>
							</button>

							<!-- delete tier -->

							<form action="UserController?action=deleteTier" method="post">
								<input type="hidden" id="userId" name="userId"
									value="<%=user.getId().toString()%>" /> <input type="hidden"
									id="tierId" name="tierId"
									value="<%=userTierItem.getKey().toString()%>" />
								<button class="btn btn-link btn-sm text-dark align-self-center"
									type="submit">
									<svg width="1.25em" height="1.25em" viewBox="0 0 16 16"
										class="bi bi-trash" fill="currentColor"
										xmlns="http://www.w3.org/2000/svg">
  									<path
											d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
  									<path fill-rule="evenodd"
											d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z" />
								</svg>
								</button>
							</form>

							<!-- Delete Tier End -->

							<!-- Remove Member -->

							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse"
								data-target="<%="#removeMember" + counter%>">
								<svg width="1.25em" height="1.25em" viewBox="0 0 16 16"
									class="bi bi-person-dash-fill" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
										d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm5-.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z" />
								</svg>
							</button>

							<!-- Remove Member End -->
							<!--  Add Member -->

							<button class="btn btn-link btn-sm text-dark align-self-center"
								data-toggle="collapse" data-target="<%="#addMember" + counter%>">
								<svg width="1.25em" height="1.25em" viewBox="0 0 16 16"
									class="bi bi-person-plus-fill" fill="currentColor"
									xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
										d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm7.5-3a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z" />
								</svg>
							</button>

							<!-- Add Member End -->
						</div>
					</li>
					<%
						++counter;
					}
					}
					%>
				</ul>
			</div>

			<!-- User Tiers End -->
			<!-- Member Tiers Tab Starts -->

			<div id="memberTiers" class="container tab-pane fade">
				<br>
				<h3>Member T1ERs</h3>
				<ul class="list-group list-group-flush">
					<%
						if (memberTiers != null) {
						for (Map.Entry<UUID, Tier> memberTierItem : memberTiers.entrySet()) {
					%>
					<li class="list-group-item">
						<div class="media ml-auto">
							<div class="media-body align-self-center"><%=memberTierItem.getValue().getName()%></div>

							<form action="UserController?action=unsubscribe" method="post">
								<input type="hidden" name="userId" id="userId"
									value="<%=user.getId().toString()%>" /> <input type="hidden"
									name="tierId" id="tierId"
									value="<%=memberTierItem.getKey().toString()%>" />
								<button class="btn btn-link btn-sm text-dark align-self-center"
									type="submit">
									<svg width="1.25em" height="1.25em" viewBox="0 0 16 16"
										class="bi bi-person-x-fill" fill="currentColor"
										xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd"
											d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z" />
								</svg>
								</button>
							</form>
						</div>
					</li>
					<%
						}
					}
					%>
				</ul>
			</div>

			<!-- Member Tier Tab Ends -->
			<!-- Change Password Tab -->

			<div id="changePassword" class="container tab-pane fade">
				<br>
				<h3>Change Password</h3>
				<form action="UserController?action=changePassword" method="post">
					<input type="hidden" name="userId" id="userId"
						value="<%=user.getId().toString()%>" />
					<div class="form-group col-sm-3">
						<label for="password1">New Password:</label> <input
							class="form-control mr-sm-2 rounded" type="password"
							placeholder="Password" id="password1" name="password1" />
					</div>
					<div class="form-group col-sm-3">
						<label for="password2">Re-type Password:</label> <input
							class="form-control mr-sm-2 rounded" type="password"
							placeholder="Retype Password" id="password2" name="password2" />
					</div>
					<button class="btn btn-dark rounded" type="submit">Change
						Password</button>
				</form>
			</div>

			<!--  Change Password Tab End -->
		</div>
		<!-- Tabs End -->
	</div>
	<!-- Content End -->
</body>
</html>