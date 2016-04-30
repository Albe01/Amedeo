package it.amedeo.utils;

public class LeftZero {

	public LeftZero() {
		// TODO Auto-generated constructor stub
	}
	
	public static String LeftZero(String str, int width, String filler) {
		while (str.length() < width) {
			str = filler + str;
		}
		return str;
	}

}
