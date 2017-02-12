package com.manandakana.dao;

import java.sql.Timestamp;

import com.manandakana.dto.UserData;

public interface UserAnswersDAO {
	
	public void logUserAnswers(UserData userData, String userAnswers, Timestamp starttime, Timestamp endtime) throws DAOException;

}
