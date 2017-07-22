package com.manandakana.dao;

import java.util.Vector;

import com.manandakana.dto.StageData;

public interface StagesDAO {
	
	public String getNextStage(String courseId, String stage, int score) throws DAOException;
	
	public StageData getStage(String courseId, String stage) throws DAOException;
	
	public Vector<StageData> getStages(String courseId, int sequence) throws DAOException;
	
	public StageData getPreviousStage(String courseId, String stage) throws DAOException;

}
