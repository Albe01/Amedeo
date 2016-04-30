package it.amedeo.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
 
//PackedDecimal conversions
 

public class PackedDecimal {
 
   // Define constants for conversion process
   final static int PlusSign = 0x0C;       // Plus sign
   final static int MinusSign = 0x0D;      // Minus sign
   final static int NoSign = 0x0F;         // Unsigned
   final static int DropHO = 0xFF;         // AND mask to drop HO sign bits
   final static int GetLONibble  = 0x0F;   // Get only LO digit
 
   //---------------------------------------------------
   // Convert byte[] of packed decimal to long
 
public static long parse(byte[] pdIn) throws Exception {
long val = 0;                    // Value to return 
 
      for(int i=0; i < pdIn.length; i++) {
         int aByte = pdIn[i] & DropHO; // Get next 2 digits & drop sign bits
         if(i == pdIn.length - 1) {    // last digit?
            int digit = aByte >> 4;    // First get digit
            val = val*10 + digit;
//            System.out.println("digit=" + digit + ", val=" + val);
            int sign = aByte & GetLONibble;  // now get sign
            if (sign == MinusSign)
               val = -val;
            else {
               // Do we care if there is an invalid sign?
               if(sign != PlusSign && sign != NoSign)
                  throw new Exception("OC7");
            }
         }else {
            int digit = aByte >> 4;    // HO first   
            val = val*10 + digit;
//            System.out.println("digit=" + digit + ", val=" + val);
            digit = aByte & GetLONibble;      // now LO
            val = val*10 + digit;
//            System.out.println("digit=" + digit + ", val=" + val);
         }
      } // end for(i)
return val;
} // end parse()
 
   //-----------------------------------------------------------------------------------
   // Convert String of numberic characters to packedDecimal in byte[]
 
   public static byte[] pack(String nbrs)throws Exception {
      int digit = PlusSign;            // default if no sign
      int startAt = 0;
      // Test if HO char is a sign
      if(nbrs.startsWith("+") || nbrs.startsWith("-")) {
         digit = ( nbrs.startsWith("+") ? PlusSign : MinusSign);
         startAt = 1;
      }
      byte[] chars = nbrs.getBytes();  // Move input into an array
      byte[] packed = new byte[(nbrs.length()-startAt)/2+1];
      int inIdx = chars.length - 1;          // index of end  
      int outIdx = packed.length - 1;
  
      // Now get digit to go with sign
      int temp = (chars[inIdx--] & GetLONibble) << 4;             // Move LO digit to HO
      digit = digit | temp;
      packed[outIdx--] = (byte)(digit & DropHO);
//      System.out.println("LO=" + Integer.toHexString(digit) + ", outIdx=" + outIdx);  //LO=2c OK 
 
      for(; outIdx >= 0; outIdx--) {
         if(inIdx < 0)  break;
         if(chars[inIdx] < '0' || chars[inIdx] > '9')   // must be between 0 & 9
            throw new Exception("Not numberic data: " + nbrs + " at " + inIdx);
         digit = chars[inIdx--] & GetLONibble;
         if(inIdx >= 0) {
            temp = (chars[inIdx--] & GetLONibble) << 4; // get digit for HO
            digit = digit | temp;
         }
//         System.out.println("nxt=" + Integer.toHexString(digit) + ", outIdx=" + outIdx + ", inIdx=" + inIdx);
         packed[outIdx] = (byte) digit;
      } // end for(outIdx) thru digits
 
//      System.out.println("packed=" + bytesToHex(packed) + ", len=" + packed.length);
      return packed;
   } // end pack()
 

   //---------------------------------------------------------------
   // Convert bytes to hex 2-pules with blank spacer in String
   static String bytesToHex(byte[] buf) {
      final String HexChars = "0123456789ABCDEF";
      StringBuffer sb = new StringBuffer((buf.length/2)*5+3);
      for(int i=0; i < buf.length; i++ ) {
         byte b = buf[i];
         b = (byte)(b >> 4);     // Hit to bottom
         b = (byte)(b & 0x0F);   // get HI byte
         sb.append(HexChars.charAt(b));
         b = buf[i];             // refresh
         b = (byte)(b & 0x0F);   // get LOW byte
         sb.append(HexChars.charAt(b));
         if(i % 2 == 1)
            sb.append(' ');
      }
      return sb.toString();
   } // end bytesToHex()
 
   //-------------------------------------------------------------------
   // Test the above
public static void main(String[] args) throws Exception {
/*
byte[] pd = new byte[] {0x19, 0x2C};             // 192
System.out.println(PackedDecimal.parse(pd));
      pd = new byte[] {(byte)0x98, 0x44, 0x32, 0x3D};  //-9844323
System.out.println(PackedDecimal.parse(pd));
      pd = new byte[] {(byte)0x98, 0x44, 0x32};  //invalid sign
//System.out.println(PackedDecimal.parse(pd));  
*/
      // Now test pack
      String nbr1 = "12345";
      byte[] packed = PackedDecimal.pack(nbr1);
      System.out.println("packed=" + bytesToHex(packed));
      System.out.println(PackedDecimal.parse(packed)); //-12345
      
      
      
      
		File fileOut = new File("D:/FileInfoCam/PROVAHEX.TXT");
		Writer recOut = null;
		try {
			recOut = new BufferedWriter(new FileWriter(fileOut,false));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		String outRiga = bytesToHex(packed) + ";" + PackedDecimal.parse(packed);
		try {
			recOut.write(PadString.padRight(outRiga, 50) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		recOut.close();
      
      
      
 
} // end main()
} // end class
   