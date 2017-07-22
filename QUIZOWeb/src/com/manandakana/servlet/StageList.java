package com.manandakana.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manandakana.dto.StageData;
import com.manandakana.ejb.QuizService;
import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class StageList
 */
@WebServlet({ "/StageList", "/stagelist" })
public class StageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	QuizService quizService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StageList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		String courseId = (String)session.getAttribute("course_id");
		String stage = (String)session.getAttribute("stage");
		Vector<StageData> stageDataVector = quizService.getStagesBefore(courseId, stage);
		if(stageDataVector != null){
			System.out.println("returning: " + stageDataVector.toString());
			response.getWriter().append(stageDataVector.toString());
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			ResponseInfo respInf = new ResponseInfo();
			respInf.setStatus("NG");
			respInf.setMessage("DATA_NOT_FOUND");
			System.out.println("returning: " + respInf.toJSONString());
			response.getWriter().append(respInf.toJSONString());
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		}
		return;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
