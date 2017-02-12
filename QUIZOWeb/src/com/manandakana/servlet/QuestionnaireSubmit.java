package com.manandakana.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import com.manandakana.ejb.UserService;
import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class QuestionnaireSubmit
 */
@WebServlet("/QuestionnaireSubmit")
public class QuestionnaireSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionnaireSubmit() {
        super();
    }

	

	/**
	 * Accepts questionnaire answer
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResponseInfo respInf = new ResponseInfo();
		response.setContentType("application/json charset=UTF8");
		try{
			String userId = request.getParameter("userid");
			String courseId = request.getParameter("course");
			String question = request.getParameter("question");
			int duration = Integer.parseInt(request.getParameter("duration"));
			int understanding = Integer.parseInt(request.getParameter("understanding"));
			int usefulness = Integer.parseInt(request.getParameter("usefulness"));
			String comment = request.getParameter("comment");
			Timestamp submittime = new Timestamp(Long.parseLong(request.getParameter("submitTime")));
			userService.logCourseQuestionnaire(userId, courseId, question, duration, understanding, usefulness, comment, submittime);
			respInf.setStatus("OK");
		} catch (ValidationException ve){
			System.err.println("validation error occured to login request");
			ve.printStackTrace();
			respInf.setStatus("NG");
			respInf.setMessage("VALIDATION_ERROR");
		}
		response.getWriter().append(respInf.toJSONString());
	}

}
