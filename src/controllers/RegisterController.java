
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.UserServiceDAO;
import services.UserService;

/**
 * Servlet implementation class Register
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService users;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
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
			users.activateUser(key);
			request.setAttribute("MESSAGE", "Your account has been activated");
			return "denied.jsp";
		}

		switch (action) {
		case "register":
			registerUser(request, response);
			return "signup.jsp";
		default:
			return "signup.jsp";
		}
	}

	public void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		if (password == null || password == "") {
			request.setAttribute("MESSAGE", "Please enter a password");
			return;
		}

		if (password2 == null || password == "") {
			request.setAttribute("MESSAGE", "Please enter a password");
			return;
		}

		if (password.contentEquals(password2)) {
			User user = new User(password, email);
			users.createUser(user);
			request.setAttribute("MESSAGE", "Email authentication sent - please click link on email");
		} else {
			request.setAttribute("MESSAGE", "Passwords not matching!");
		}
	}
}
