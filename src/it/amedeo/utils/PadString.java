package it.amedeo.utils;

public class PadString {

	public static String PadLeft(String s, Integer n) {
		return String.format("%1$" + n + "s", s);
	}

	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

}
