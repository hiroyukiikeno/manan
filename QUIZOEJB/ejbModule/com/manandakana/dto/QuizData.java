package com.manandakana.dto;

import java.io.Serializable;

public class QuizData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String courseId, stage, quizId, quizType, question, option1, option2, option3, description, link;
	private int rightAnswer;
	public QuizData(){
		courseId ="";
		stage ="";
		quizId ="";
		quizType ="";
		question ="";
		option1 ="";
		option2 ="";
		option3 ="";
		rightAnswer =0;
		description ="";
		link ="";
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getQuizId() {
		return quizId;
	}
	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
	public String getQuizType() {
		return quizType;
	}
	public void setQuizType(String quizType) {
		this.quizType = quizType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
	public String toJSONString(){
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"courseId\":\"").append(courseId).append("\"");
		sb.append(",\"stage\":\"").append(stage).append("\"");
		sb.append(",\"quizId\":\"").append(quizId).append("\"");
		sb.append(",\"quizType\":\"").append(quizType).append("\"");
		sb.append(",\"question\":\"").append(question).append("\"");
		sb.append(",\"option1\":\"").append(option1).append("\"");
		sb.append(",\"option2\":\"").append(option2).append("\"");
		sb.append(",\"option3\":\"").append(option3).append("\"");
		sb.append(",\"rightAnswer\":").append(rightAnswer);
		sb.append(",\"description\":\"").append(description).append("\"");
		sb.append(",\"link\":\"").append(link).append("\"}");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		return toJSONString();
	}
	

}
