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
import javax.validation.ValidationException;

import com.manandakana.dto.UserCourseData;
import com.manandakana.ejb.UserService;
import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class CourseList
 */
@WebServlet({ "/CourseList", "/courselist" })
public class CourseList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseList() {
        super();
    }

	/**
	 * Response CourseList Web Page for the user
	 * prerequisite: user session must have been started
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		if(userid == null || userid.equals("")){
			response.sendRedirect("/");
			return;
		}
		request.changeSessionId();
		
		Vector<UserCourseData> userCourseDataList = userService.getListOfCourseData(userid);
		if(userCourseDataList != null){
			request.setAttribute("userCourseList", userCourseDataList);
		} else {
			UserCourseData emptyData = new UserCourseData();
			Vector<UserCourseData> emptyList = new Vector<UserCourseData>();
			emptyList.add(emptyData);
			request.setAttribute("userCourseList",emptyData);
		}
		
		int score = userService.getUserTotalScore(userid);
		request.setAttribute("score", score);
		
		request.getRequestDispatcher("/jsp/course.jsp").forward(request, response);
		
	}

	/**
	 * Response if it is ok to start the course using JSON format
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		String userid = (String)session.getAttribute("userid");
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			String courseId = request.getParameter("course");
			String accessCode = request.getParameter("accesscode");
			
			if(userService.isCourseAccessValid(userid, courseId, accessCode)){
				responseInfo.setStatus("OK");
				session.setAttribute("course_id", courseId);
			} else {
				responseInfo.setStatus("NG");
				responseInfo.setMessage("COURSE_ACCESS_INVALID");
			}
		} catch (ValidationException ve){
			System.err.println("validation error occured to couse start validation request");
			ve.printStackTrace();
			responseInfo.setStatus("NG");
			responseInfo.setMessage("PARAMETER_VALIDATION_FAILED");
		}
		response.getWriter().append(responseInfo.toJSONString());
		return;
	}

}
