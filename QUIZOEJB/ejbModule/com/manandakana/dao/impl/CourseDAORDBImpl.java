package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.manandakana.dao.CourseDAO;
import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dto.CourseData;

public class CourseDAORDBImpl extends DAOBase implements CourseDAO {
	private static final String GET_COURSE_SQL = "select course_id, course_name, first_stage, grade, status, access_code, description from COURSES where course_id = ?";
	private static final String GET_ALL_COURSES_SQL = "select course_id, course_name, first_stage, grade, status, access_code, description from COURSES order by course_id";
			

	@Override
	public CourseData getCourse(String courseId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		CourseData res = new CourseData();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_COURSE_SQL);
			pStatement.setString(1, courseId);
			rs = pStatement.executeQuery();
			if(rs.next()){
				res.setCourseId(rs.getString("course_id").trim());
				res.setCourseName(rs.getString("course_name").trim());
				res.setFirstStage(rs.getString("first_stage").trim());
				res.setGrade(rs.getString("grade").trim());
				res.setStatus(rs.getString("status").trim());
				res.setAccessCode(rs.getString("access_code") != null ? rs.getString("access_code").trim() : "");
				res.setDescription(rs.getString("description") != null ? rs.getString("description").trim() : "");
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting course data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		
		return res;
		
	}

	@Override
	public Vector<CourseData> getCourses() throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		Vector<CourseData> res = new Vector<CourseData>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_ALL_COURSES_SQL);
			rs = pStatement.executeQuery();
			while(rs.next()){
				CourseData courseData = new CourseData();
				courseData.setCourseId(rs.getString("course_id").trim());
				courseData.setCourseName(rs.getString("course_name").trim());
				courseData.setFirstStage(rs.getString("first_stage").trim());
				courseData.setGrade(rs.getString("grade").trim());
				courseData.setStatus(rs.getString("status").trim());
				courseData.setAccessCode(rs.getString("access_code") != null ? rs.getString("access_code").trim() : "");
				courseData.setDescription(rs.getString("description") != null ? rs.getString("description").trim() : "");
				res.add(courseData);
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting course data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		
		return res;
	}

}
