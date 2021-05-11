package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import builders.ExpenseBuilder;
import dao.OwnerServiceDAO;
import dao.TierServiceDAO;
import dao.UserServiceDAO;
import proxy.ExpenseDAOProxy;
import beans.Expense;
import beans.Tier;
import services.ExpenseService;
import services.OwnerService;
import services.TierService;
import services.UserService;

@WebServlet(description = "T1ER expense", urlPatterns = { "/ExpenseController" })
public class ExpenseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ExpenseService expenses;
	UserService users;
	TierService tiers;
	OwnerService ownerService;
	Tier currentTier;
	User loggedInUser;

	public ExpenseController() {
		super();
		this.expenses = new ExpenseDAOProxy();
		this.users = new UserServiceDAO();
		this.tiers = new TierServiceDAO();
		this.ownerService = new OwnerServiceDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//get User
		loggedInUser = (User) request.getAttribute("USER");

		if (loggedInUser == null) {
			String userId = request.getParameter("userId");
			if (userId == null) {
				RequestDispatcher rs = request.getRequestDispatcher("denied.jsp");
				rs.forward(request, response);
			} else {
				loggedInUser = users.readUser(userId);
			}
		}

		//get tier
		currentTier = (Tier) request.getAttribute("TIER");

		if (currentTier == null) {
			String tierId = request.getParameter("tierId");
			if (tierId == null) {
				RequestDispatcher rs = request.getRequestDispatcher("denied.jsp");
				rs.forward(request, response);
			} else {
				currentTier = tiers.readTier(tierId);
			}
		}

		//forward request
		String forward = requestHandlerdoPost(request, response);

		request.setAttribute("USER", loggedInUser);

		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	private String requestHandlerdoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			action = "";
		}

		switch (action) {
		case "createOrUpdateExpense":
			createOrUpdateExpense(request, response);
			setJSPAttributes(request, response);
			return "expenseview.jsp";
		case "deleteExpense":
			deleteExpense(request, response);
			setJSPAttributes(request, response);
			return "expenseview.jsp";
		case "userSettings":
			return "UserController";
		case "editExpense":
			setJSPAttributes(request, response);
			setEditExpenseAttribute(request, response);
			return "expenseview.jsp";
		default:
			setJSPAttributes(request, response);
			return "expenseview.jsp";
		}
	}
	
	private void setJSPAttributes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("TIER", currentTier);

		Map<UUID, Expense> tierExpenses = expenses.readExpenses(currentTier.getId().toString());
		request.setAttribute("Expenses", tierExpenses);

		Map<UUID, Expense> userExpenses = ownerService.readOwnedExpense(loggedInUser.getId().toString(),
				currentTier.getId().toString());
		request.setAttribute("UserExpenses", userExpenses);
	}
	
	private void setEditExpenseAttribute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String expenseId = request.getParameter("expenseId");
		
		if (expenseId != null) {
			Expense expense = expenses.readExpense(expenseId);
			request.setAttribute("EXPENSE", expense);	
		}
	}

	private void deleteExpense(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String expenseId = request.getParameter("expenseId");

		if (expenseId != null) {
			expenses.deleteExpense(expenseId);
		}
	}

	private void createOrUpdateExpense(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Expense expense = null;
		String id = request.getParameter("expenseId");
		String name = request.getParameter("expenseName");
		String description = request.getParameter("expenseDescription");
		String amount = request.getParameter("expenseAmount");

		if (name == null) {
			name = expenses.readExpense(id).getName();
		}

		if (description == null) {
			description = expenses.readExpense(id).getDescription();
		}

		if (amount == null) {
			amount = expenses.readExpense(id).getAmount().toString();
		}

		if (id == null || "".equals(id)) {
			// Create the expense.
			expense = new ExpenseBuilder().setName(name).setDescription(description)
					.setAmount(BigDecimal.valueOf(Double.parseDouble(amount))).setOwner(loggedInUser).createExpense();
		} else {
			// Read the expense.
			expense = this.expenses.readExpense(id);
			expense.setName(name);
			expense.setDescription(description);
			expense.setAmount(new BigDecimal(amount));
			expense.setOwner(loggedInUser);
		}
		// Update the expense.
		this.expenses.createOrUpdateExpense(expense, currentTier.getId().toString());
	}

}
