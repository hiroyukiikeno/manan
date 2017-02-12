package com.manandakana.util;


public class ParameterProperties {
	
	public static String typeOf(String name){
		if(name != null){
			switch(name){
			case "duration":
			case "understanding":
			case "usefulness":
				return "int";
			case "submitTime":
			case "start":
			case "end":
				return "Timestamp";
			default:
				return "String";
			}
		} else {
			return null;
		}
	}
	
	/* *
	 * get length for String field
	 * 
	 * */
	public static int lengthOf(String name){
		if(name != null){
			switch(name){
			case "useranswers":
			case "comment":
				return 12000;
			case "answer1":
			case "answer2":
			case "answer3":
				return 360;
			default:
				return 32;
			}
		} else {
			return 0;
		}
	}
	
	public static boolean isTruncatable(String name){
		if(name != null){
			switch(name){
			case "comment":
			case "answer1":
			case "answer2":
			case "answer3":
				return true;
			default:
				return false;
			}
		} else {
			return false;
		}
	}

}
