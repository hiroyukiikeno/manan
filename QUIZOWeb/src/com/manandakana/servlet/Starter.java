package com.manandakana.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import com.manandakana.util.ResponseInfo;

/**
 * Servlet implementation class Starter
 */
@WebServlet({ "/Starter", "/starter" })
public class Starter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Starter() {
        super();
    }

	/**
	 * Show Login Page (usually not used)
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		return;
	}

	/**
	 * User login as API
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json charset=UTF-8");
		ResponseInfo respInfo = new ResponseInfo();
		try{
			String userid = request.getParameter("userid");
			String username = request.getParameter("username");
			if(userid == null || userid.equals("")){
				respInfo.setStatus("NG");
				respInfo.setMessage("USERID_NOT_SPECIFIED");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().append(respInfo.toJSONString());
				return;
			}
			if(username == null || username.equals("")){
				username = userid;
			}
			session.setAttribute("userid", userid);
			session.setAttribute("username", username);
			respInfo.setStatus("OK");
			response.getWriter().append(respInfo.toJSONString());
		} catch (ValidationException ve){
			System.err.println("validation error occured to starter request");
			ve.printStackTrace();
			respInfo.setStatus("NG");
			respInfo.setMessage("VALIDATION_ERROR");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().append(respInfo.toJSONString());
			return;
		}
	}

}
