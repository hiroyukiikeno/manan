package com.manandakana.dto;

import java.io.Serializable;

public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;
	private String username;
	private String courseId;
	private String courseName;
	private String stage;
	private int score;
	private int questionnaire;
	
	public UserData(){
		userid ="";
		username ="";
		courseId ="";
		courseName ="";
		stage ="";
		score =0;
		questionnaire =0;
	}
	
	public UserData(String useridI, String usernameI, String courseIdI, String courseNameI, String stageI, int scoreI){
		userid = useridI;
		username = usernameI;
		courseId = courseIdI;
		courseName = courseNameI;
		stage = stageI;
		score = scoreI;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(int questionnaire) {
		this.questionnaire = questionnaire;
	}
	
	public String toJSONString(){
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"userid\":\"").append(userid).append("\"");
		sb.append(",\"username\":\"").append(username).append("\"");
		sb.append(",\"courseId\":\"").append(courseId).append("\"");
		sb.append(",\"courseName\":\"").append(courseName).append("\"");
		sb.append(",\"stage\":\"").append(stage).append("\"");
		sb.append(",\"score\":").append(score);
		sb.append(",\"questionnaire\":").append(questionnaire).append("}");
		return sb.toString();
	}

}
