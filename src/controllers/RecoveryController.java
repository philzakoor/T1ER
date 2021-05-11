package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.EmailPasswordRecovery;
import beans.KeyGenerator;
import beans.User;
import dao.UserServiceDAO;
import sendemail.EmailSpooler;
import services.UserService;

/**
 * Servlet implementation class PasswordRecovery
 */
@WebServlet("/RecoveryController")

public class RecoveryController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserService users;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecoveryController() {
		this.users = new UserServiceDAO();
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
		
		String forward = requestHandler(request, response);
		
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("kid");
		String action = request.getParameter("action");
		
		if (action == null) {
			action = "";
		}
		
		if (key != null) {
			hasKey(request, response, key);
			return "recoverpassword.jsp";
		}
		
		switch (action) {
		case "recoverPassword":
			recoverPassword(request, response);
			return "forgotpassword.jsp";
		case "changePassword":
			return changePassword(request,response);
		default: 
			return "forgotpassword.jsp";
		}
	}
	
	private String changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String userId = request.getParameter("userId");
			User user = users.readUser(userId);
					
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");

			if (password1 == "" || password2 == "") {
				request.setAttribute("MESSAGE", "Please Enter Password.");
				request.setAttribute("USER", user);
				return "recoverpassword.jsp";
			}
				
			if (password1.contentEquals(password2)) {
				user.setPassword(password1);
				users.createOrUpdateUser(user);
				EmailSpooler.getInstance().enqueue(user.getEmail(), "Password Was Changed",
						"T1ER Password Changed");
				request.setAttribute("MESSAGE", "Password Sucessfully Changed.");
				return "denied.jsp";
			} else {
				request.setAttribute("MESSAGE", "Passwords not matching");
				request.setAttribute("USER", user);
				return "recoverpassword.jsp";
			}
		}


	
	private void recoverPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String key = KeyGenerator.getInstance().key();
		User user = users.searchUser(email);
		
		users.setUserKey(user, key);
		EmailPasswordRecovery.sendPasswordRecoveryEmail(email, key);
		request.setAttribute("MESSAGE","Password recovery email sent if account exists.");
	}
	
	private void hasKey(HttpServletRequest request, HttpServletResponse response, String key)
			throws ServletException, IOException {
		User user = users.searchKey(key);
		request.setAttribute("USER", user);
	}
}
