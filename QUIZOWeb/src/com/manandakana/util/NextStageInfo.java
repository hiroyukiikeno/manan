package com.manandakana.util;

import com.manandakana.dto.StageData;

public class NextStageInfo {
	
	private String nextStage;
	private String nextStageStatus;
	private boolean courseCompleted = false;
	
	public NextStageInfo(){
		nextStage ="";
		nextStageStatus = "";
		courseCompleted = false;
	}
	public NextStageInfo(StageData nextStageData){
		nextStage = nextStageData.getStage();
		nextStageStatus = nextStageData.getStatus();
	}
	public NextStageInfo(boolean isCourseCompleted){
		courseCompleted = isCourseCompleted;
		if(courseCompleted){
			nextStage = "COURSE_COMPLETED";  //for truly return
			nextStageStatus = "";
		} else {
			nextStage = "";
			nextStageStatus = "";
		}
	}
	public String toJSONString(){
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"nextstage\":\"").append(nextStage);
		sb.append("\",\"nextstagestatus\":\"").append(nextStageStatus);
		sb.append("\",\"coursecompleted\":").append((courseCompleted ? "1":"0"));
		sb.append("}");
		return sb.toString();
	}

}
