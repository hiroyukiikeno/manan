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

import com.manandakana.dto.UserData;
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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * Login to the course. if ok return data including course and stage.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		try {
			
			String userid = request.getParameter("userid");
			String courseId = request.getParameter("course");
			
			String sessionUserid = (String)session.getAttribute("userid");
			String sessionUsername = (String)session.getAttribute("username");
			
			
			if(!sessionUserid.equals(userid)){
				System.out.println("userid changed. session userid " + sessionUserid + " will be updated to " + userid);
			}
			
			String username = "";
			if(sessionUsername != null && !sessionUsername.equals("")){
				username = sessionUsername;
			} else {
				username = userid;
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
