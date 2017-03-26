package com.manandakana.dao;

import java.sql.Timestamp;
import java.util.Vector;

import com.manandakana.dto.UserData;
import com.manandakana.util.UserAnswer;

public interface UserAnswersDAO {
	
	public void logUserAnswers(UserData userData, String userAnswers, Timestamp starttime, Timestamp endtime) throws DAOException;
	public void logUserAnswerEach(UserData userData, Vector<UserAnswer> userAnswerList, Timestamp starttime, Timestamp endtime) throws DAOException;

}
