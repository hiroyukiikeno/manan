package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.UserAchievementDAO;

public class UserAchievementDAORDBImpl extends DAOBase implements UserAchievementDAO {
	private static final String CREATE_ACHIEVEMENT_RECORD_SQL = "insert into user_achievement (userid, course_id, achieved_time) values (?,?,?)";
	private static final String GET_USER_ACHIEVEMENT_SQL = "select count(distinct(userid)) as ans from user_achievement where userid = ? and course_id = ?";
	private static final String GET_USER_COUNT_SQL = "select count(distinct(userid)) as ans from user_achievement where course_id = ?";

	@Override
	public void recordAchievement(String userId, String courseId, Timestamp achievedTime) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(CREATE_ACHIEVEMENT_RECORD_SQL);
			pStatement.setString(1,userId);
			pStatement.setString(2, courseId);
			pStatement.setTimestamp(3, achievedTime);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured inserting user achievement data");
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
	public boolean isUserAchieved(String userId, String courseId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_USER_ACHIEVEMENT_SQL);
			pStatement.setString(1, userId);
			pStatement.setString(2, courseId);
			rs = pStatement.executeQuery();
			if(rs.next()){
				result = rs.getInt("ans");
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting user achievement data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		return ( result > 0 ? true : false);
	}

	@Override
	public int countAchievedUser(String courseId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_USER_COUNT_SQL);
			pStatement.setString(1, courseId);
			rs = pStatement.executeQuery();
			if(rs.next()){
				result = rs.getInt("ans");
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting user achievement data");
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("unhandled error occured when closing connection");
			}
		}
		return result;
	}

}
