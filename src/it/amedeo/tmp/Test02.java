package it.amedeo.tmp;

import it.amedeo.utils.ASCII2EBCDIC;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Test02 {

public static void main(String[] args)  {
	
	
	
	Short sh = 10;
	
	int integer = 16;
	String binary = Integer.toBinaryString(integer);
	String sasa =  Integer.toHexString(integer).toUpperCase();
	
	
	
	System.out.println("Binary value of " + integer + " is " + binary + ".");
	
	String qqq = ASCII2EBCDIC.ASCII2EBCDIC("12345");
	
	
	
	
	
   try {
    Charset charset = Charset.forName("IBM1047");// creo il set di caratteri rappresentante lo std EBCDIC

    CharsetDecoder decoder = charset.newDecoder();// creo un decodificatore da ebcdic a unicode
    CharsetEncoder encoder = charset.newEncoder();// un codificatore da unicode a ebcdic

    FileChannel chan;// un canale verso il file
    long size;// dimensioni file
    FileOutputStream fos = new FileOutputStream("D:/FileInfoCam/test.txt");// stream verso il file

    String stringa = "AAAAA BBBBB 12345";
    
    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(stringa));// buffer di byte contentnte
                                                                             // la stringa in ebcdic
   
    
    String sss =  byteBuffer2String(bbuf);
    
    chan = fos.getChannel();// ottengo il canale dello stream
    size = chan.size();// le dimensioni(del file)
    bbuf.rewind();// mi posiziono all'inizio del buffer
    chan.write(bbuf);// lo scrivo dentro test.txt tramite il canale prima creato
    chan.close();// chiudo canale
    fos.close();//  chiudo stream

    System.out.println("Dentro test.txt c'è la stringa in ebcdic\n Proviamo a riconvertirla in unicode");
    bbuf.rewind();
    CharBuffer cb = decoder.decode(bbuf);
    System.out.println(cb.toString());
    if (cb.toString().equals("Stringa qualsiasi"))
      System.out.println("Ho ottenuto la stessa!!");
  
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static String byteBuffer2String(ByteBuffer pckt) {

	// Convert ByteBuffer to String
	byte[] bytearray = new byte[pckt.remaining()];
	pckt.get(bytearray);

	return new String(bytearray);
}
}