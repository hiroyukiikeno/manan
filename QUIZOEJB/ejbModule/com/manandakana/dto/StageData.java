package com.manandakana.dto;

import java.io.Serializable;

public class StageData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String courseId;
	private int sequence;
	private String stage;
	private String nextStage;
	private short courseCompletion;
	private int passMark;
	private int completionBonus;
	private String description;
	private String status;
	
	public StageData(){
		courseId ="";
		sequence =0;
		stage ="";
		nextStage ="";
		courseCompletion =0;
		passMark =0;
		completionBonus =0;
		description = "";
		status ="";
	}
	
	public StageData(String courseId, int sequence, String stage, String nextStage, short courseCompletion,
			int passMark, int completionBonus, String description, String status){
		this.courseId = courseId;
		this.sequence = sequence;
		this.stage = stage;
		this.nextStage = nextStage;
		this.courseCompletion = courseCompletion;
		this.passMark = passMark;
		this.completionBonus = completionBonus;
		this.description = description;
		this.status = status;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getNextStage() {
		return nextStage;
	}

	public void setNextStage(String nextStage) {
		this.nextStage = nextStage;
	}

	public short getCourseCompletion() {
		return courseCompletion;
	}

	public void setCourseCompletion(short courseCompletion) {
		this.courseCompletion = courseCompletion;
	}

	public int getPassMark() {
		return passMark;
	}

	public void setPassMark(int passMark) {
		this.passMark = passMark;
	}

	public int getCompletionBonus() {
		return completionBonus;
	}

	public void setCompletionBonus(int completionBonus) {
		this.completionBonus = completionBonus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toJSONString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{\"courseId\":\"").append(courseId).append("\"");
		sb.append(",\"sequence\":").append(sequence);
		sb.append(",\"stage\":\"").append(stage).append("\"");
		sb.append(",\"nextStage\":\"").append(nextStage).append("\"");
		sb.append(",\"courseCompletion\":\"").append(courseCompletion);
		sb.append(",\"passMark\":").append(passMark);
		sb.append(",\"completionBonus\":").append(completionBonus);
		sb.append(",\"description\":\"").append(description).append("\"");
		sb.append(",\"status\":\"").append(status).append("\"}");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		return toJSONString();
	}

}
