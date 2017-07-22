package com.manandakana.ejb;

import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manandakana.dao.CourseDAO;
import com.manandakana.dao.DAOException;
import com.manandakana.dao.DAOFactory;
import com.manandakana.dao.QuizDAO;
import com.manandakana.dao.StagesDAO;
import com.manandakana.dto.CourseData;
import com.manandakana.dto.QuizData;
import com.manandakana.dto.StageData;

/**
 * Session Bean implementation class QuizService
 */
@Stateless
@LocalBean
public class QuizService {

    /**
     * Default constructor. 
     */
    public QuizService() {
    }
    
    public Vector<QuizData> getQuizzes(String courseId, String stage){
    	QuizDAO quizDAO = DAOFactory.getInstance().getQuizDAO();
    	return quizDAO.getQuizData(courseId, stage);
    }
    
    public StageData getStage(String courseId, String stage){
    	StagesDAO stagesDAO = DAOFactory.getInstance().getStagesDAO();
    	return stagesDAO.getStage(courseId, stage);
    }
    
    public Vector<StageData> getStagesBefore(String courseId, String stage){
    	StagesDAO stagesDAO = DAOFactory.getInstance().getStagesDAO();
    	try{
    		StageData currentStage = stagesDAO.getPreviousStage(courseId, stage);
    		int seq = currentStage.getSequence();
    		Vector<StageData> v = stagesDAO.getStages(courseId, seq);
    		System.out.println("course id: " + courseId + "  stage: " + stage);
    		System.out.println("current seq no of the stage: " + seq + "  number of stages before: " + v.size());
        	return stagesDAO.getStages(courseId, currentStage.getSequence());
    	} catch (DAOException e){
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    public Vector<CourseData> getCourses(){
    	CourseDAO courseDAO = DAOFactory.getInstance().getCourseDAO();
    	return courseDAO.getCourses();
    }
    
    public CourseData getCourse(String courseId){
    	CourseDAO courseDAO = DAOFactory.getInstance().getCourseDAO();
    	try {
    		CourseData courseData = courseDAO.getCourse(courseId);
    		return courseData;
    	} catch (DAOException e){
    		return null;
    	}
    	
    	
    }
    

}
