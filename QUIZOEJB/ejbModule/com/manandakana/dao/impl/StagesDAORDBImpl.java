package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.StagesDAO;
import com.manandakana.dto.StageData;

public class StagesDAORDBImpl extends DAOBase implements StagesDAO {
	private static final String GET_SQL = "select course_id, sequence, stage, next_stage, course_complete, pass_mark, completion_bonus, description, status from STAGES where course_id = ? and stage = ?";

	@Override
	public String getNextStage(String courseId, String stage, int score) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		String nextStage = "";
		int passMark = 0;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_SQL);
			pStatement.setString(1, courseId);
			pStatement.setString(2, stage);
			rs = pStatement.executeQuery();
			if(rs.next()){
				nextStage = rs.getString("next_stage").trim();
				passMark = rs.getInt("pass_mark");
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting stage data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		if(score < passMark){
			nextStage = stage;
		}
		return nextStage;
	}

	@Override
	public StageData getStage(String courseId, String stage) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		StageData ret = new StageData();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_SQL);
			pStatement.setString(1, courseId);
			pStatement.setString(2, stage);
			rs = pStatement.executeQuery();
			if(rs.next()){
				ret.setCourseId(rs.getString("course_id").trim());
				ret.setSequence(rs.getInt("sequence"));
				ret.setStage(rs.getString("stage").trim());
				ret.setNextStage(rs.getString("next_stage").trim());
				ret.setCourseCompletion(rs.getShort("course_complete"));
				ret.setPassMark(rs.getInt("pass_mark"));
				ret.setCompletionBonus(rs.getInt("completion_bonus"));
				ret.setDescription(rs.getString("description").trim());
				ret.setStatus(rs.getString("status").trim());
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting stage data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		
		return ret;
	}

}
