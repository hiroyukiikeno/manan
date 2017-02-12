package com.manandakana.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manandakana.ejb.QuizService;

/**
 * Servlet implementation class QuizStarter
 */
@WebServlet({ "/QuizStarter", "/quizstarter" })
public class QuizStarter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private QuizService quizService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizStarter() {
        super();
    }

	/**
	 * Responses quiz data in JSON format
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json charset=UTF-8");
		HttpSession session = request.getSession();
		String courseId = (String)session.getAttribute("course_id");
		String stage = (String)session.getAttribute("stage");
		String resp = quizService.getQuizzes(courseId,stage).toString();
		response.getWriter().append(resp);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
