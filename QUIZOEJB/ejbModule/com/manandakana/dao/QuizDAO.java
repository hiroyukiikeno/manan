package com.manandakana.dao;

import java.util.Vector;

import com.manandakana.dto.QuizData;

public interface QuizDAO {
	
	public Vector<QuizData> getQuizData(String courseId, String stage) throws DAOException;
	
	public void createQuizData(QuizData quizData);

}
