package com.manandakana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.manandakana.dao.DAOBase;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.QuizDAO;
import com.manandakana.dto.QuizData;

public class QuizDAORDBImpl extends DAOBase implements QuizDAO {
	private static final String GET_QUIZ_DATA_SQL = "select course_id, stage, quiz_id, quiz_type, question, option1, option2, option3, right_answer, description, link from QUIZ where course_id = ? and stage = ? order by quiz_id";
	private static final String CREATE_QUIZ_DATA_SQL = "insert into QUIZ(course_id, stage, quiz_id, quiz_type, question, option1, option2, option3, right_answer, description, link) values (?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public Vector<QuizData> getQuizData(String courseId, String stage) throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		Vector<QuizData> res = new Vector<QuizData>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GET_QUIZ_DATA_SQL);
			pStatement.setString(1, courseId);
			pStatement.setString(2, stage);
			rs = pStatement.executeQuery();
			while(rs.next()){
				QuizData quizData = new QuizData();
				quizData.setCourseId(rs.getString("course_id").trim());
				quizData.setStage(rs.getString("stage").trim());
				quizData.setQuizId(rs.getString("quiz_id").trim());
				quizData.setQuizType(rs.getString("quiz_type").trim());
				quizData.setQuestion(rs.getString("question").trim());
				quizData.setOption1(rs.getString("option1").trim());
				quizData.setOption2(rs.getString("option2").trim());
				quizData.setOption3(rs.getString("option3").trim());
				quizData.setRightAnswer(rs.getInt("right_answer"));
				quizData.setDescription(rs.getString("description").trim());
				quizData.setLink(rs.getString("link").trim());
				res.add(quizData);
			}
			rs.close();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured getting quiz data");
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
	public void createQuizData(QuizData quizData) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(CREATE_QUIZ_DATA_SQL);
			pStatement.setString(1,quizData.getCourseId());
			pStatement.setString(2, quizData.getStage());
			pStatement.setString(3, quizData.getQuizId());
			pStatement.setString(4, quizData.getQuizType());
			pStatement.setString(5, quizData.getQuestion());
			pStatement.setString(6, quizData.getOption1());
			pStatement.setString(7, quizData.getOption2());
			pStatement.setString(8, quizData.getOption3());
			pStatement.setInt(9, quizData.getRightAnswer());
			pStatement.setString(10, quizData.getDescription());
			pStatement.setString(11, quizData.getLink());
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException se){
			throw new DAOException(se);
		} catch (DAOException de){
			throw de;
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("unhandled error occured inserting quiz data");
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
