package com.manandakana.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manandakana.ejb.UserService;
import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class ResetStage
 */
@WebServlet({ "/ResetStage", "/resetstage" })
public class ResetStage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetStage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		String courseId = (String)session.getAttribute("course_id");
		String userid = (String)session.getAttribute("userid");
		String stage = request.getParameter("resetToStage");
		boolean success = userService.updateCurrentStage(userid, courseId, stage);
		if(success){
			session.setAttribute("stage",stage);
			ResponseInfo respInf = new ResponseInfo();
			respInf.setStatus("OK");
			respInf.setMessage("User's Stage Updated To " + stage);
			System.out.println("returning: " + respInf.toJSONString());
			response.getWriter().append(respInf.toJSONString());
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			ResponseInfo respInf = new ResponseInfo();
			respInf.setStatus("NG");
			respInf.setMessage("User's Stage Not Updated");
			System.out.println("returning: " + respInf.toJSONString());
			response.getWriter().append(respInf.toJSONString());
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		}
		return;
	}

}
