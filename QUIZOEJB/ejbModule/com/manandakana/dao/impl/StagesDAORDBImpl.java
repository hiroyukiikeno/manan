package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.StagesDAO;
import com.manandakana.dto.StageData;

public class StagesDAORDBImpl extends DAOBase implements StagesDAO {
	private static final String GET_SQL = "select course_id, sequence, stage, next_stage, course_complete, pass_mark, completion_bonus, description, status from STAGES where course_id = ? and stage = ?";
	private static final String GET_PREVIOUS_SQL = "select course_id, sequence, stage, next_stage, course_complete, pass_mark, completion_bonus, description, status from STAGES where course_id = ? and next_stage = ?";
	private static final String GET_BEFORE_DATA_SQL = "select course_id, sequence, stage, next_stage, course_complete, pass_mark, completion_bonus, description, status from STAGES where course_id = ? and sequence <= ?";

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
	
	@Override
	public Vector<StageData> getStages(String courseId, int sequence) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		Vector<StageData> ret = new Vector<StageData>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_BEFORE_DATA_SQL);
			pStatement.setString(1, courseId);
			pStatement.setInt(2, sequence);
			rs = pStatement.executeQuery();
			while(rs.next()){
				StageData dat = new StageData();
				dat.setCourseId(rs.getString("course_id").trim());
				dat.setSequence(rs.getInt("sequence"));
				dat.setStage(rs.getString("stage").trim());
				dat.setNextStage(rs.getString("next_stage").trim());
				dat.setCourseCompletion(rs.getShort("course_complete"));
				dat.setPassMark(rs.getInt("pass_mark"));
				dat.setCompletionBonus(rs.getInt("completion_bonus"));
				dat.setDescription(rs.getString("description").trim());
				dat.setStatus(rs.getString("status").trim());
				ret.addElement(dat);
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
	
	@Override
	public StageData getPreviousStage(String courseId, String stage) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		StageData ret = new StageData();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_PREVIOUS_SQL);
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
