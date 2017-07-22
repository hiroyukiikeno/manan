package com.manandakana.dao;

import com.manandakana.dto.UserData;

public interface UserDAO {
	
	public UserData getUserData(String userId, String courseId) throws DAOException;
	
	public UserData createUser(UserData userData) throws DAOException;
	
	public void updateUserWithNextStageAndNewScore(String userId, String courseId, String nextStage, int score) throws DAOException;
	
	public void clearUserDataForACourse(String userId, String courseId) throws DAOException;
	
	public int getScore(String userId) throws DAOException;
	
	public void updateScore(String userId, String courseId, String stage, int newScore) throws DAOException;
	
	public void updateStage(String userId, String courseId, String stage) throws DAOException;

}
