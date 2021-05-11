package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Tier;
import beans.User;
import dao.MemberServiceDAO;
import dao.OwnerServiceDAO;
import dao.TierServiceDAO;
import dao.UserServiceDAO;
import sendemail.EmailSpooler;
import services.MemberService;
import services.OwnerService;
import services.TierService;
import services.UserService;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TierService tiers;
	private UserService user;
	private MemberService members;
	private OwnerService ownerService;
	private User loggedInUser;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		tiers = new TierServiceDAO();
		user = new UserServiceDAO();
		members = new MemberServiceDAO();
		ownerService = new OwnerServiceDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		loggedInUser = (User) request.getAttribute("USER");
		String userId = request.getParameter("userId");

		if (loggedInUser == null && userId != null) {
			loggedInUser = user.readUser(userId);
		}

		if (loggedInUser == null) {
			RequestDispatcher rs = request.getRequestDispatcher("deny.jsp");
			rs.forward(request, response);
		}

		String forward = requestHandler(request, response);

		Map<UUID, Tier> userTiers = new HashMap<>(
				ownerService.readOwnedOrUnownedTiers(loggedInUser.getId().toString(), true));
		request.setAttribute("UserTiers", userTiers);
		Map<UUID, Tier> memberTiers = new HashMap<>(
				ownerService.readOwnedOrUnownedTiers(loggedInUser.getId().toString(), false));
		request.setAttribute("MemberTiers", memberTiers);

		request.setAttribute("USER", loggedInUser);

		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	private String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
		case "deleteTier":
			deleteTier(request, response);
			return "userview.jsp";
		case "unsubscribe":
			unsubscribe(request, response);
			return "userview.jsp";
		case "changePassword":
			changePassword(request, response);
			return "userview.jsp";
		case "addMember":
			addMember(request, response);
			return "userview.jsp";
		case "removeMember":
			removeMember(request, response);
			return "userview.jsp";
		case "editTier":
			editTier(request, response);
			return "userview.jsp";
		default:
			return "userview.jsp";
		}

	}
	
	private void editTier(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tierId = request.getParameter("tierId");
		String tierName = request.getParameter("tierName");
		
		Tier thisTier = tiers.readTier(tierId);
		thisTier.setName(tierName);
		tiers.createOrUpdateTier(thisTier);
		
		request.setAttribute("MESSAGE", "Tier Name Changed to " + tierName);
	}

	private void addMember(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("memberEmail");
		String tierId = request.getParameter("tierId");

		if (email != null) {
			members.addUser(email, tierId);
		}
		
		request.setAttribute("MESSAGE", "User " + email + " added to tier.");

	}

	private void removeMember(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("memberEmail");
		String tierId = request.getParameter("tierId");

		if (email != null) {
			members.removeUser(email, tierId);
		}
	
		request.setAttribute("MESSAGE", "User " + email + " removed from tier.");
	}

	private void unsubscribe(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tierId = request.getParameter("tierId");
		members.addOrRemoveUser(loggedInUser.getEmail(), tierId);
		request.setAttribute("MESSAGE", "Sucessfully Unsubscribed.");
	}

	private void deleteTier(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tierId = request.getParameter("tierId");
		tiers.deleteTier(tierId);
		request.setAttribute("MESSAGE", "Tier Sucessfully Deleted.");
	}

	private void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		if (password1 == "" || password2 == "")
			request.setAttribute("MESSAGE", "Please Enter Password.");

		if (password1.contentEquals(password2)) {
			loggedInUser.setPassword(password1);
			user.createOrUpdateUser(loggedInUser);
			EmailSpooler.getInstance().enqueue(loggedInUser.getEmail(), "Password Was Changed",
					"T1ER Password Changed");
			request.setAttribute("MESSAGE", "Password Sucessfully Changed.");
		} else {
			request.setAttribute("MESSAGE", "Passwords not matching");
		}
	}
}
