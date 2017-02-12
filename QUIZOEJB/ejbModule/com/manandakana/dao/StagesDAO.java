package com.manandakana.dao;

import com.manandakana.dto.StageData;

public interface StagesDAO {
	
	public String getNextStage(String courseId, String stage, int score) throws DAOException;
	
	public StageData getStage(String courseId, String stage) throws DAOException;

}
