
package controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DBConnection;
import dao.UserServiceDAO;
import services.UserService;

import java.sql.*;

@WebServlet(description = "T1ER login", urlPatterns = { "/LoginController" })
public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserService users;

	public LoginController() {
		this.users = new UserServiceDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (logIn(email, password)) {
			request.setAttribute("USER", users.searchUser(email));
			RequestDispatcher rs = request.getRequestDispatcher("TierController");
			rs.forward(request, response);
		} else {
			RequestDispatcher rs = request.getRequestDispatcher("denied.jsp");
			request.setAttribute("MESSAGE", "User name or Password are incorrect or account is not active.");
			rs.forward(request, response);
		}
	}

	private boolean logIn(String email, String password) {
		boolean EmPa = false;
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			PreparedStatement sq = conn
					.prepareStatement("select * from user where email=? and password=? and isActivated = ?;");
			sq.setString(1, email);
			sq.setString(2, password);
			sq.setBoolean(3, true);
			ResultSet res = sq.executeQuery();
			EmPa = res.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EmPa;
	}
}
