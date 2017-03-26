package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.UserAnswersDAO;
import com.manandakana.dto.UserData;
import com.manandakana.util.UserAnswer;

public class UserAnswersDAORDBImpl extends DAOBase implements UserAnswersDAO {
	private static final String INSERT_USER_ANSWERS_SQL = "insert into USER_ANSWERS(userid, course_id, stage, score, answers, start_time, end_time) values (?,?,?,?,?,?,?)";
	private static final String INSERT_USER_ANSWER_EACH_SQL = "insert into USER_ANSWER_EACH(userid, course_id, stage, quizid, correct, answer, start_time, end_time) values (?,?,?,?,?,?,?,?)";

	@Override
	public void logUserAnswers(UserData userData, String userAnswers, Timestamp starttime, Timestamp endtime)
			throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_USER_ANSWERS_SQL);
			pStatement.setString(1,userData.getUserid());
			pStatement.setString(2, userData.getCourseId());
			pStatement.setString(3, userData.getStage());
			pStatement.setInt(4, userData.getScore());
			pStatement.setString(5,userAnswers);
			pStatement.setTimestamp(6, starttime);
			pStatement.setTimestamp(7, endtime);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured inserting answer data");
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
	public void logUserAnswerEach(UserData userData, Vector<UserAnswer> userAnswerList, Timestamp starttime, Timestamp endtime)
			throws DAOException{
		Connection connection = null;
		PreparedStatement pStatement = null;
		for (UserAnswer ua : userAnswerList){
			try {
				connection = getConnection();
				pStatement = connection.prepareStatement(INSERT_USER_ANSWER_EACH_SQL);
				pStatement.setString(1,userData.getUserid());
				pStatement.setString(2, userData.getCourseId());
				pStatement.setString(3, userData.getStage());
				pStatement.setString(4, ua.getQuizId());
				pStatement.setInt(5, ua.getOx());
				pStatement.setString(6, ua.getAnswer());
				pStatement.setTimestamp(7, starttime);
				pStatement.setTimestamp(8, endtime);
				pStatement.executeUpdate();
				pStatement.close();
			} catch (SQLException se){
				throw new DAOException(se);
			} catch (DAOException de){
				throw de;
			} catch (Exception e){
				e.printStackTrace();
				throw new DAOException("unhandled error occured inserting answer-each data");
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

}
