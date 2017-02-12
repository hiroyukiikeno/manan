package com.manandakana.dao;

import java.util.Vector;

import com.manandakana.dto.CourseData;

public interface CourseDAO {
	
	public CourseData getCourse(String courseId) throws DAOException;
	
	public Vector<CourseData> getCourses() throws DAOException;

}
