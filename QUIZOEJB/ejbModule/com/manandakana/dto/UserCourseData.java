package com.manandakana.dto;

import java.io.Serializable;

public class UserCourseData extends CourseData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;
	private boolean achieved;
	private int numberOfAchievedUsers;
	private boolean started;
	
	public UserCourseData(){
		super();
		userid ="";
		achieved = false;
		numberOfAchievedUsers = 0;
		started = false;
	}
	
	public void map(CourseData cd){
		setCourseId(cd.getCourseId());
		setCourseName(cd.getCourseName());
		setFirstStage(cd.getFirstStage());
		setGrade(cd.getGrade());
		setStatus(cd.getStatus());
		setAccessCode(cd.getAccessCode());
		setDescription(cd.getDescription());
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

	public int getNumberOfAchievedUsers() {
		return numberOfAchievedUsers;
	}

	public void setNumberOfAchievedUsers(int numberOfAchievedUsers) {
		this.numberOfAchievedUsers = numberOfAchievedUsers;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

}
