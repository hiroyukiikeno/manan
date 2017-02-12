package com.manandakana.util;

public class EncodeHelper {
	private char[] charTobeEncoded = {'&','<','>','\'','"'};
	private String[] encodeStringForChar = {"&amp;","&lt;","&gt;","&#39;","&quot;"}; 
	
	public EncodeHelper(){
		
	}
	
	/**
	 * <code> replace < > ' " into html-coded text</code>
	 * @param input : string to be encoded
	 * @return encoded string
	 */
	public String htmlEncode(String input){
		if(input != null){
			StringBuffer sb = new StringBuffer(input);
			for(int i=0; i<charTobeEncoded.length; i++){
				int idx = sb.toString().indexOf(charTobeEncoded[i]);
				while(idx != -1){
					sb.replace(idx,idx+1,encodeStringForChar[i]);
					idx = idx + encodeStringForChar[i].length();
					idx = sb.toString().indexOf(charTobeEncoded[i],idx);
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}

}
