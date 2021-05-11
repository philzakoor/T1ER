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

import beans.Expense;
import beans.Notification;
import beans.Tier;
import beans.User;
import builders.TierBuilder;
import dao.MemberServiceDAO;
import dao.NotificationServiceDAO;
import dao.TierServiceDAO;
import dao.UserServiceDAO;
import proxy.ExpenseDAOProxy;
import services.ExpenseService;
import services.MemberService;
import services.NotificationService;
import services.TierService;
import services.UserService;

@WebServlet(description = "T1ER Tier", urlPatterns = { "/TierController" })
public class TierController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TierService tiers;
	private User loggedInUser;
	private MemberService members;
	private NotificationService notifications;
	private UserService users;
	private ExpenseService expenses;
	
	public TierController() {
		super();
		this.tiers = new TierServiceDAO();
		this.members = new MemberServiceDAO();
		this.notifications = new NotificationServiceDAO();
		this.users = new UserServiceDAO();
		this.expenses = new ExpenseDAOProxy();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loggedInUser = (User) request.getAttribute("USER");
		
		if (loggedInUser == null) {
			String userId = request.getParameter("userId");
			if (userId == null) {
				request.setAttribute("MESSAGE", "Please log in");
				RequestDispatcher rs = request.getRequestDispatcher("denied.jsp");
				rs.forward(request, response);
				return;
			} else {
				loggedInUser = users.readUser(userId);
			}
		}
		
		String forward = requestHandlerdoPost(request, response);
		request.setAttribute("USER", loggedInUser);
		
		RequestDispatcher rs = request.getRequestDispatcher(forward);
		rs.forward(request, response);
	}

	private String requestHandlerdoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action == null) {
			action = "";
		}
		
		switch (action) {
		case "createTier":
			createTier(request, response);
			setJSPAttributes(request, response);
			return "tierview.jsp";
		case "addMember":
			addMember(request, response);
			setJSPAttributes(request, response);
			return "tierview.jsp";
		case "removeNotification":
			removeNotification(request, response);
			setJSPAttributes(request, response);
			return "tierview.jsp";
		case "userSettings":
			return "UserController";
		case "expense":
			setExpenseAttributes(request, response);
			return "ExpenseController";
		default:
			setJSPAttributes(request, response);
			return "tierview.jsp";
		}
	}
	
	private void setJSPAttributes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<UUID,Notification> userNotifications = new HashMap<UUID,Notification>(notifications.readNotifications(loggedInUser.getId().toString()));
		request.setAttribute("Notifications", userNotifications);
		Map<UUID, Tier> userTiers = new HashMap<UUID, Tier>(tiers.readTiers(loggedInUser.getId().toString()));
		request.setAttribute("Tiers", userTiers);
		Map<UUID, String> tierTotals = new HashMap<>();
		Map<UUID, Map<UUID, Expense>> tierRecentExpense = new HashMap<>();
		
		for (Map.Entry<UUID, Tier> tierItem : userTiers.entrySet()) {
			String amount = tiers.getTierExpenseTotal(tierItem.getKey().toString());
			tierTotals.put(tierItem.getKey(), amount);
			Map<UUID, Expense> tierExpenses = expenses.getRecentExpenses(tierItem.getKey().toString());
			tierRecentExpense.put(tierItem.getKey(), tierExpenses);
		}
		
		request.setAttribute("TierRecentExpenses", tierRecentExpense);
		request.setAttribute("TierTotals", tierTotals);
	}
	
	private void setExpenseAttributes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tierId = request.getParameter("tierId");
		if (tierId != null) {
			Tier tier = tiers.readTier(tierId);
			request.setAttribute("TIER", tier);
		}
	}

	private void removeNotification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String notificationId = request.getParameter("notificationId");
		
		if (notificationId != null)
			notifications.removeNotification(loggedInUser.getId().toString(), notificationId);
		
	}

	public void addMember(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("memberEmail");
		String tierId = request.getParameter("tierId");
		
		if (email != null)
			members.addUser(email, tierId);
	}

	private void createTier(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Tier tier = null;
		String id = request.getParameter("tierId");
		String name = request.getParameter("tierName");
		
		if (name == null) {
			name = "Default Name";
		}

		if (id == null || "".equals(id)) {
			// Create the tier.
			tier = new TierBuilder().setName(name).setOwner(loggedInUser).createTier();
			this.tiers.createOrUpdateTier(tier);
		} 

	}
}
