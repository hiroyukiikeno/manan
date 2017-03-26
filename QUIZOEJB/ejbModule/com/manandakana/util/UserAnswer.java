package com.manandakana.util;

public class UserAnswer {
	private String quizId;
	private String answer;
	private int ox;
	public String getQuizId() {
		return quizId;
	}
	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getOx() {
		return ox;
	}
	public void setOx(int ox) {
		this.ox = ox;
	}
	public UserAnswer(){
		quizId = "";
		answer = "";
		ox =1;
	}
	public UserAnswer(String key, String val, int rorw){
		quizId = key;
		answer = val;
		ox = rorw;
	}
}
