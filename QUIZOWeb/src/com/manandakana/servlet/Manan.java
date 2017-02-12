package com.manandakana.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

/**
 * Servlet implementation class Manan
 */
@WebServlet({ "/Manan", "/manan" })
public class Manan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manan() {
        super();
    }

	/**
	 * Response: the application web page
	 * Prerequisite: user session must have been started and the course selected to start
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		String username = (String)session.getAttribute("username");
		String courseId = (String)session.getAttribute("course_id");
		try {
			if(userid != null && !userid.equals("") && username != null && courseId != null && !courseId.equals("")){
				request.getRequestDispatcher("/jsp/manan.jsp").forward(request, response);
				return;
			} else {
				System.err.println("the session does not contain information to start application");
				response.sendRedirect("/");
				return;
			}
		} catch (ValidationException ve){
			System.err.println("validation error occured to couse start validation request");
			ve.printStackTrace();
			response.sendRedirect("/");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
