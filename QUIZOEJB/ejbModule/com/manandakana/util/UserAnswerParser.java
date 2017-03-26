package com.manandakana.util;

import java.util.ArrayList;
import java.util.Vector;

public class UserAnswerParser {
	
	/**
	 * @param userAnswers : {"quizid1": answer1, "quizid2": answer2, ... "quizidX":["answerX1","answerX2", ..] ...}
	 * @param oxs : {"quizid1": right(1)/wrong(0), "quizid2": right(1)/wrong(0), ...}
	 * @return vector(UserAnswer(quizid1,answer1,right/wrong),UserAnswer(quizid2,answer2,right/wrong)...)
	 */
	public Vector<UserAnswer> getUserAnswerList(String userAnswers, String oxs) throws UserAnswerParserException{
		Vector<UserAnswer> userAnswerList = new Vector<UserAnswer>();
		
		try{
			// strip userAnswers into string array of key:value pairs
			ArrayList<String> str = new ArrayList<String>();
			int xbl = userAnswers.indexOf('[');
			int xcm = userAnswers.indexOf(',');
			int xbr = userAnswers.indexOf('}');
			for (int loc = 1; loc < userAnswers.length(); ){
				
				if (0 < xcm && ( xcm < xbl || xbl < 0)){ // next answer is not array
					str.add(userAnswers.substring(loc, xcm).replaceAll("&quot;", ""));
					loc = xcm + 1;
				} else if( 0 < xcm  && 0 < xbl && xbl < xcm){  // next answer is array
					int xble = userAnswers.indexOf(']',xbl);
					String vl = userAnswers.substring(loc, xbl).replaceAll("&quot;", "") + userAnswers.substring(xbl, xble+1);
					str.add(vl);
					loc = xble + 1;
					if(loc == userAnswers.indexOf(',', loc)){
						loc = loc + 1;
					}
				} else if( xcm < 0 && 0 < xbr){ // last
					if(xbr == loc){
						break;
					}
					str.add(userAnswers.substring(loc,xbr).replaceAll("&quot;", ""));
					loc = xbr + 1;
				} else {
					throw new UserAnswerParserException("error parsing userAnswers");
				}
				xbl = userAnswers.indexOf('[', loc +1);
				xcm = userAnswers.indexOf(',', loc +1);
				xbr = userAnswers.indexOf('}', loc);
			}
			
			// strip oxs into string array of key:value pairs
			ArrayList<String> sts = new ArrayList<String>();
			int icm = oxs.indexOf(',');
			int ibr = oxs.indexOf('}');
			for (int ix = 1; ix < oxs.length(); ix++){
				if (0 < icm){
					sts.add(oxs.substring(ix,icm).replaceAll("&quot;", ""));
					ix = icm + 1;
				} else if(0 < ibr){
					sts.add(oxs.substring(ix,ibr).replaceAll("&quot;", ""));
					ix = ibr + 1;
				} else {
					throw new UserAnswerParserException("error parsing oxs");
				}
				icm = oxs.indexOf(',',ix);
				ibr = oxs.indexOf('}',ix);
			}
			
			
			
			for (int i = 0; i < str.size(); i++){
				String kv = str.get(i);
				String kox = sts.get(i);
				String key = kv.substring(0, kv.indexOf(':'));
				String val = kv.substring(kv.indexOf(':')+1,kv.length());
				int ox = Integer.parseInt(kox.substring(kox.indexOf(':')+1, kox.length()));
				UserAnswer ua = new UserAnswer(key, val, ox);
				userAnswerList.add(ua);
			}
		} catch (RuntimeException re){
			throw new UserAnswerParserException(re);
		}
		
		
		
		return userAnswerList;
	}
	
	

}
