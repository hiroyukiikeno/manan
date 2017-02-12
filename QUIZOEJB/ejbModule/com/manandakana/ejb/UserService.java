package com.manandakana.ejb;

import java.sql.Timestamp;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manandakana.dao.CourseDAO;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.DAOFactory;
import com.manandakana.dao.QuestionnaireDAO;
import com.manandakana.dao.StagesDAO;
import com.manandakana.dao.UserAchievementDAO;
import com.manandakana.dao.UserAnswersDAO;
import com.manandakana.dao.UserDAO;
import com.manandakana.dto.CourseData;
import com.manandakana.dto.UserCourseData;
import com.manandakana.dto.UserData;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService {

    /**
     * Default constructor. 
     */
    public UserService() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * login to a course. user data per course is recorded if it is new.
     * @param userid
     * @param username
     * @param courseId
     * @return UserData
     */
    public UserData login(String userid, String username, String courseId){
    	UserData ret = null;
    	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    	
    	try {
    		CourseData courseData = new CourseData();
    		CourseDAO courseDAO = DAOFactory.getInstance().getCourseDAO();
    		courseData = courseDAO.getCourse(courseId);
    		
    		ret = userDAO.getUserData(userid, courseId);
    		
        	if(ret.getUserid().equals(userid) && ret.getCourseId().equals(courseId)){
        		System.out.println("user " + ret.getUsername() + "is resuming " + ret.getCourseId() + " " + ret.getStage());
        		ret.setCourseName(courseData.getCourseName());
        		
        	} else {

        		UserData newUser = new UserData();
        		newUser.setUserid(userid);
        		newUser.setUsername(username);
        		newUser.setCourseId(courseId);
        		newUser.setCourseName(courseData.getCourseName());
        		newUser.setStage(courseData.getFirstStage());
        		newUser.setScore(0);
        		ret = userDAO.createUser(newUser);
        		
        		System.out.println("user " + ret.getUsername() + " is starting " + ret.getCourseId() + " " + ret.getStage());
        	}
        	
        	QuestionnaireDAO questionnaireDAO = DAOFactory.getInstance().getQuestionnaireDAO();
        	int count = questionnaireDAO.getCount(userid, courseId);
        	ret.setQuestionnaire(count);

    	} catch(DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    	}
    	return ret;

    }
    
    public UserData updateUserOnStageCompletion(String userid, String courseId, String stage, int score, String userAnswers, Timestamp starttime, Timestamp endtime){
    	
    	UserData ret = null;
    	StagesDAO stagesDAO = DAOFactory.getInstance().getStagesDAO();
    	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    	UserAnswersDAO userAnswersDAO = DAOFactory.getInstance().getUserAnswersDAO();
    	
    	UserData logUser = new UserData();
    	logUser.setUserid(userid);
    	logUser.setCourseId(courseId);
    	logUser.setStage(stage);
    	logUser.setScore(score);
    	
    	
    	try {
    		userAnswersDAO.logUserAnswers(logUser, userAnswers, starttime, endtime);
    		
    		String nextStage = stagesDAO.getNextStage(courseId, stage, score);
    		
    		int oldScore = userDAO.getScore(userid);
    		
    		if(nextStage != null && !nextStage.equals(stage)){
    			int bonus = stagesDAO.getStage(courseId, nextStage).getCompletionBonus();
    			int newScore = oldScore + (score * 144) + bonus;
    			userDAO.updateUserWithNextStageAndNewScore(userid, courseId, nextStage, newScore);
    		} else if(nextStage.equals(stage)){
    			int newScore = oldScore + (score * 55);
    			userDAO.updateScore(userid, courseId, stage, newScore);
    		}
    		
    		ret = userDAO.getUserData(userid, courseId);
    	} catch(DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    	}
    	return ret;
    }
    
    public void recordCourseCompletion(String userid, String courseId){
    	Timestamp achievedTime = new Timestamp(System.currentTimeMillis());
    	UserAchievementDAO userAchievementDAO = DAOFactory.getInstance().getUserAchievementDAO();
    	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    	try {
    		userAchievementDAO.recordAchievement(userid, courseId, achievedTime);
        	
        	userDAO.clearUserDataForACourse(userid, courseId);
    	} catch(DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    	}
    	
    }
    
    public Vector<UserCourseData> getListOfCourseData(String userid){
    	Vector<UserCourseData> userCourseDataList = new Vector<UserCourseData>();
    	CourseDAO courseDAO = DAOFactory.getInstance().getCourseDAO();
    	UserAchievementDAO userAchievementDAO = DAOFactory.getInstance().getUserAchievementDAO();
    	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    	try {
    		Vector<CourseData> courseDataList = courseDAO.getCourses();
    		for (int i=0; i < courseDataList.size(); i++){
    			CourseData cd = courseDataList.get(i);
    			UserCourseData ucd = new UserCourseData();
    			ucd.map(cd);
    			ucd.setUserid(userid);
    			ucd.setAchieved(userAchievementDAO.isUserAchieved(userid, ucd.getCourseId()));
    			ucd.setNumberOfAchievedUsers(userAchievementDAO.countAchievedUser(ucd.getCourseId()));
    			UserData ud = userDAO.getUserData(userid, cd.getCourseId());
    			if(ud.getCourseId().equals(cd.getCourseId())){
    				ucd.setStarted(true);
    			}
    			userCourseDataList.add(ucd);
    		}
    	} catch (DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    	}
    	return userCourseDataList;
    }
    
    public boolean isCourseAccessValid(String userId, String courseId, String accessCode){
    	boolean ret = false;
    	CourseDAO courseDAO = DAOFactory.getInstance().getCourseDAO();
    	UserAchievementDAO userAchievementDAO = DAOFactory.getInstance().getUserAchievementDAO();
    	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    	try {
    		UserData ud = userDAO.getUserData(userId,courseId);
    		CourseData cd = courseDAO.getCourse(courseId);
    		if(ud.getCourseId().equals(courseId)){ // user already started the course
    			ret = true;
    		} else if(userAchievementDAO.isUserAchieved(userId, courseId)){ // user already completed the course
    			ret = true;
    		} else if(cd.getAccessCode().equals("") || cd.getAccessCode().equals(accessCode)){  // the access code is ok
    			ret = true;
    		}
    	} catch (DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    	}
    	return ret;
    }
    
    public int getUserTotalScore(String userId){
    	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    	try {
    		return userDAO.getScore(userId);
    	} catch (DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    		return 0;
    	}
    }
    
    public void logCourseQuestionnaire(String userId, String courseId, String question, int duration, int understanding,
    		int usefulness, String comment, Timestamp submittime){
    	QuestionnaireDAO questionnaireDAO = DAOFactory.getInstance().getQuestionnaireDAO();
    	try {
    		questionnaireDAO.logQuestionnaireAnswer(userId, courseId, question, duration, understanding, usefulness, comment, submittime);
    	} catch (DAOException de){
    		System.err.println("Error occured in UserService accessing database");
    		de.printStackTrace();
    	}
    }

}
