package com.manandakana.dao;

import java.sql.Timestamp;

public interface UserAchievementDAO {
	
	public void recordAchievement(String userId, String courseId, Timestamp achievedTime) throws DAOException;
	
	public boolean isUserAchieved(String userId, String courseId) throws DAOException;
	
	public int countAchievedUser(String courseId) throws DAOException;

}
