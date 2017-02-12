package com.manandakana.dto;

import java.io.Serializable;

public class CourseData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String courseId;
	private String courseName;
	private String firstStage;
	private String grade;
	private String status;
	private String accessCode;
	private String description;
	
	public CourseData(){
		courseId ="";
		courseName ="";
		firstStage ="";
		grade = "";
		status ="";
		accessCode ="";
		description ="";
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

	public String getFirstStage() {
		return firstStage;
	}

	public void setFirstStage(String firstStage) {
		this.firstStage = firstStage;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toJSONString(){
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"courseId\":\"");
		sb.append(courseId);
		sb.append("\",\"courseName\":\"");
		sb.append(courseName);
		sb.append("\",\"firstStage\":\"");
		sb.append(firstStage);
		sb.append("\",\"grade\":\"");
		sb.append(grade);
		sb.append("\",\"status\":\"");
		sb.append(status);
		sb.append("\",\"accessCode\":\"");
		sb.append(accessCode);
		sb.append("\",\"description\":\"");
		sb.append(description);
		sb.append("\"}");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		return toJSONString();
	}

}
