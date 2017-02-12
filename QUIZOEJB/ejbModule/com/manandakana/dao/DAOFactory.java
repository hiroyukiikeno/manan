package com.manandakana.dao;

import com.manandakana.dao.impl.CourseDAORDBImpl;
import com.manandakana.dao.impl.QuestionnaireDAORDBImpl;
import com.manandakana.dao.impl.QuizDAORDBImpl;
import com.manandakana.dao.impl.StagesDAORDBImpl;
import com.manandakana.dao.impl.UserAchievementDAORDBImpl;
import com.manandakana.dao.impl.UserAnswersDAORDBImpl;
import com.manandakana.dao.impl.UserDAORDBImpl;

public class DAOFactory {
	private String databaseType;
	private static DAOFactory instance;
	static {
		instance = new DAOFactory();
	}
	private DAOFactory(){
		databaseType = (new DAOProperties()).getDatabaseType();
	}
	public static DAOFactory getInstance(){
		return instance;
	}
	public UserDAO getUserDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new UserDAORDBImpl();
			}
		}
		return null;
	}
	public QuizDAO getQuizDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new QuizDAORDBImpl();
			}
		}
		return null;
	}
	public StagesDAO getStagesDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new StagesDAORDBImpl();
			}
		}
		return null;
	}
	public UserAnswersDAO getUserAnswersDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new UserAnswersDAORDBImpl();
			}
		}
		return null;
	}
	public CourseDAO getCourseDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new CourseDAORDBImpl();
			}
		}
		return null;
	}
	public UserAchievementDAO getUserAchievementDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new UserAchievementDAORDBImpl();
			}
		}
		return null;
	}
	public QuestionnaireDAO getQuestionnaireDAO(){
		if(databaseType != null){
			if(databaseType.equals("RDB")){
				return new QuestionnaireDAORDBImpl();
			}
		}
		return null;
	}

}
