package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.UserDAO;
import com.manandakana.dto.UserData;

public class UserDAORDBImpl extends DAOBase implements UserDAO {
	private static final String GET_USER_SQL = "select userid, username, course_id, stage, score from USERDATA where userid = ? and course_id = ?";
	private static final String CREATE_USER_SQL = "insert into USERDATA(userid,username,course_id,stage,score,update_time) values (?,?,?,?,?,?)";
	private static final String UPDATE_USER_SQL = "update USERDATA set stage=?, score=?, update_time=? where userid = ? and course_id = ?";
	private static final String UPDATE_COURSE_SQL = "update USERDATA set course_id = ?, update_time=? where userid = ? and course_id = ?";
	private static final String GET_USER_SCORE_SQL = "select max(score) as userscore from USERDATA where userid = ?";
	private static final String UPDATE_SCORE_SQL = "update USERDATA set score = ?, update_time=? where userid = ? and course_id = ? and stage = ?";

	@Override
	public UserData getUserData(String userId, String courseId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		UserData res = new UserData();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_USER_SQL);
			pStatement.setString(1, userId);
			pStatement.setString(2, courseId);
			rs = pStatement.executeQuery();
			if(rs.next()){
				res.setUserid(userId);
				res.setUsername(rs.getString("username").trim());
				res.setCourseId(rs.getString("course_id").trim());
				res.setStage(rs.getString("stage").trim());
				res.setScore(rs.getInt("score"));
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting user data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		return res;
	}

	@Override
	public UserData createUser(UserData userData) throws DAOException {
		UserData res = userData;
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			UserData existingUser = getUserData(userData.getUserid(), userData.getCourseId());
			if(existingUser.getUserid().equals(userData.getUserid())){
				System.err.println("UserID already exists : " + userData.getUserid());
			} else {
				try {
					connection = getConnection();
					pStatement = connection.prepareStatement(CREATE_USER_SQL);
					pStatement.setString(1,userData.getUserid());
					pStatement.setString(2,userData.getUsername());
					pStatement.setString(3, userData.getCourseId());
					pStatement.setString(4, userData.getStage());
					pStatement.setInt(5, userData.getScore());
					pStatement.setTimestamp(6, getCurrentTimestamp());
					pStatement.executeUpdate();
					pStatement.close();
				} catch (SQLException se){
					throw new DAOException(se);
				} catch (DAOException de){
					throw de;
				} catch (Exception e){
					e.printStackTrace();
					throw new DAOException("unhandled error occured inserting user data");
				} finally {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new DAOException("unhandled error occured when closing connection");
					}
				}
			}
		} catch (DAOException e){
			throw e;
		}
		return res;
		
	}

	@Override
	public void updateUserWithNextStageAndNewScore(String userId, String courseId, String nextStage, int score)
			throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_USER_SQL);
			pStatement.setString(1,nextStage);
			pStatement.setInt(2, score);
			pStatement.setTimestamp(3, getCurrentTimestamp());
			pStatement.setString(4, userId);
			pStatement.setString(5, courseId);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured updating user data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}

	}

	@Override
	public void clearUserDataForACourse(String userId, String courseId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		String clear = "";
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_COURSE_SQL);
			pStatement.setString(1,clear);
			pStatement.setTimestamp(2, getCurrentTimestamp());
			pStatement.setString(3, userId);
			pStatement.setString(4, courseId);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured updating user data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}

	}

	@Override
	public int getScore(String userId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		int userScore = 0;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_USER_SCORE_SQL);
			pStatement.setString(1, userId);
			rs = pStatement.executeQuery();
			if(rs.next()){
				userScore = rs.getInt("userscore");
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
		return userScore;
	}

	@Override
	public void updateScore(String userId, String courseId, String stage, int newScore) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_SCORE_SQL);
			pStatement.setInt(1,newScore);
			pStatement.setTimestamp(2, getCurrentTimestamp());
			pStatement.setString(3, userId);
			pStatement.setString(4, courseId);
			pStatement.setString(5, stage);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured updating user data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}

	}

}
