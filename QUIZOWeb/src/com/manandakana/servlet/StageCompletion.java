package com.manandakana.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import com.manandakana.dto.StageData;
import com.manandakana.dto.UserData;
import com.manandakana.ejb.QuizService;
import com.manandakana.ejb.UserService;
import com.manandakana.util.NextStageInfo;
import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class StageCompletion
 */
@WebServlet({ "/StageCompletion", "/stagecompletion" })
public class StageCompletion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserService userService;
	
	@EJB
	private QuizService quizService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StageCompletion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		String userid = (String)session.getAttribute("userid");
		String courseId = (String)session.getAttribute("course_id");
		String stage = (String)session.getAttribute("stage");
		try {
			String sco = request.getParameter("score");
			String userAnswers = request.getParameter("useranswers");
			String starttime = request.getParameter("start");
			String endtime = request.getParameter("end");
			String oxs = request.getParameter("oxs");
			
			int score = Integer.parseInt(sco);
			Timestamp startTime = new Timestamp(Long.parseLong(starttime));
			Timestamp endTime = new Timestamp(Long.parseLong(endtime));
			
			StageData completedStageData = quizService.getStage(courseId, stage);
			UserData userData = userService.updateUserOnStageCompletion(userid, courseId, stage, score, userAnswers, startTime, endTime, oxs);
			NextStageInfo nextStageInfo;
			
			if(completedStageData.getCourseCompletion() == 1 && !userData.getStage().equals(stage)){
				nextStageInfo = new NextStageInfo(true); // return as COURSE_COMPLETED
				userService.recordCourseCompletion(userid, courseId);
			} else {
				String nextStage = userData.getStage();
				StageData nextStageData = quizService.getStage(courseId, nextStage);
				nextStageInfo = new NextStageInfo(nextStageData);
				session.setAttribute("stage", nextStage);
			}
			response.getWriter().append(nextStageInfo.toJSONString());
			return;
		} catch (ValidationException ve){
			System.err.println("validation error occured to stage completion request");
			ve.printStackTrace();
			ResponseInfo respInf = new ResponseInfo();
			respInf.setStatus("NG");
			respInf.setMessage("VALIDATION_ERROR");
			response.getWriter().append(respInf.toJSONString());
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
