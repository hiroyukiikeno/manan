package com.manandakana.dao;

import java.sql.Timestamp;

public interface QuestionnaireDAO {
	
	public void logQuestionnaireAnswer (String userid, String courseId, String question, int duration, int understanding, int usefulness, String comment, Timestamp submittime) throws DAOException;
	
	public int getCount(String userid, String courseId) throws DAOException;

}
