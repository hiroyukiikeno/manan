package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.QuestionnaireDAO;

public class QuestionnaireDAORDBImpl extends DAOBase implements QuestionnaireDAO {
	private static final String INSERT_SQL = "insert into questionnaire(userid, course_id, question, duration, understanding, usefulness, comment, submit_time) values (?,?,?,?,?,?,?,?)";
	private static final String COUNT_SQL = "select count(*) as ans from questionnaire where userid = ? and course_id = ?";

	@Override
	public void logQuestionnaireAnswer(String userid, String courseId, String question, int duration, int understanding,
			int usefulness, String comment, Timestamp submittime) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_SQL);
			pStatement.setString(1,userid);
			pStatement.setString(2, courseId);
			pStatement.setString(3, question);
			pStatement.setInt(4, duration);
			pStatement.setInt(5, understanding);
			pStatement.setInt(6, usefulness);
			pStatement.setString(7, comment);
			pStatement.setTimestamp(8, submittime);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured inserting questionnaire data");
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
	public int getCount(String userid, String courseId) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(COUNT_SQL);
			pStatement.setString(1, userid);
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
			throw new DAOException("unhandled error occured counting questionnaire data");
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
