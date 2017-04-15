package com.manandakana.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import com.manandakana.dto.CourseData;
import com.manandakana.dto.UserData;
import com.manandakana.ejb.QuizService;
import com.manandakana.ejb.UserService;
import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/LoginServlet", "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserService userService;
	
	@EJB
	private QuizService quizService;
	
	private String ACC_REQUIRED = "ACC_REQUIRED";
	private String ACC_NOT_REQUIRED = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * Login to the course directly by URL parameter. Forwards or redirects to a page according to parameters
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		String username = (String)session.getAttribute("username");
		String courseId = request.getParameter("course");
		try {
			
			// User have logged in and course id is specified
			if(userid != null && !userid.equals("") && username != null && courseId != null && !courseId.equals("")){
				CourseData courseData = quizService.getCourse(courseId);
				if(courseData == null){
					request.getRequestDispatcher("/courselist").forward(request, response);
					return;
				}
				if(userService.isCourseAccessValid(userid, courseId, "")){
					session.setAttribute("acccodereq", ACC_NOT_REQUIRED);
				} else {
					session.setAttribute("acccodereq", ACC_REQUIRED);
				}
				
				session.setAttribute("course_id", courseId);
				request.setAttribute("coursename", courseData.getCourseName());
				request.getRequestDispatcher("/jsp/manan.jsp").forward(request, response);
				return;
				
			// User have not logged in
			} else {
				// if course id is specified, move to user login page
				if(courseId != null && !courseId.equals("")){
					CourseData courseData = quizService.getCourse(courseId);
					if(courseData == null){
						session.setAttribute("course_id", courseId);
					}
					request.getRequestDispatcher("/index.jsp").forward(request, response);
					return;
				}
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
	 * API: Login to the course. if ok return data including course and stage.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		try {
			
			String userid = request.getParameter("userid");
			String courseId = request.getParameter("course");
			
			String sessionUserid = (String)session.getAttribute("userid");
			String sessionUsername = (String)session.getAttribute("username");
			
			String acccodereq = (String)session.getAttribute("acccodereq");
			
			if(!sessionUserid.equals(userid)){
				response.sendRedirect("/");
				return;
			}
			
			String username = "";
			if(sessionUsername != null && !sessionUsername.equals("")){
				username = sessionUsername;
			} else {
				username = userid;
			}
			
			if(ACC_REQUIRED.equals(acccodereq)){
				String acccode = request.getParameter("acccode");
				if( !userService.isCourseAccessValid(userid, courseId, acccode)){
					// failure case
					ResponseInfo responseInfo = new ResponseInfo();
					responseInfo.setStatus("NG");
					responseInfo.setMessage("COURSE_ACCESS_INVALID");
					response.getWriter().append(responseInfo.toJSONString());
					return;
				} else {
					session.removeAttribute("acccodereq");
				}
			}
			
			if(userid != null && !userid.equals("") && courseId != null && !courseId.equals("")){
				System.out.println("logging in : user " + userid + "  " + username + " course " + courseId);
				UserData userData = userService.login(userid, username, courseId);
				
				response.getWriter().append(userData.toJSONString());
				session.setAttribute("userid", userData.getUserid());
				session.setAttribute("username", userData.getUsername());
				session.setAttribute("course_id", userData.getCourseId());
				session.setAttribute("stage", userData.getStage());
				
				return;
				

			} else {
				
				// failure case
				ResponseInfo responseInfo = new ResponseInfo();
				responseInfo.setStatus("NG");
				responseInfo.setMessage("INVALID_PARAMETER_TO_LOGIN");
				response.getWriter().append(responseInfo.toJSONString());
				return;
			}
		} catch (ValidationException ve){
			System.err.println("validation error occured to login request");
			ve.printStackTrace();
			ResponseInfo responseInfo = new ResponseInfo();
			responseInfo.setStatus("NG");
			responseInfo.setMessage("PARAMETER_VALIDATION_FAILED");
			response.getWriter().append(responseInfo.toJSONString());
			return;
		}
	}

}
