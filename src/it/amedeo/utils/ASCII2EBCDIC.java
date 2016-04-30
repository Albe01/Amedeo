package it.amedeo.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class ASCII2EBCDIC {
	public static String ASCII2EBCDIC(String strASCII)  {
		Charset charset = Charset.forName("IBM1047");// creo il set di caratteri rappresentante lo std EBCDIC
	    CharsetEncoder encoder = charset.newEncoder();// un codificatore da unicode a ebcdic
	    ByteBuffer bbuf = null;;
		try {
			bbuf = encoder.encode(CharBuffer.wrap(strASCII));
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		byte[] bytearray = new byte[bbuf.remaining()];
		bbuf.get(bytearray);
		return new String(bytearray);
	}
}
