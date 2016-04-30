package it.amedeo.tmp;

import java.math.BigInteger;
 
public class Test01 {
 
    private static BigInteger hundred = BigInteger.valueOf(100);
 
    public static void main(String[] args) {
    	
    	String input ="f.lli 23 / \\";
    	String alphaAndDigits1 = input.replaceAll("[^a-zA-Z0-9 ]+","");
    	String alphaAndDigits2 = input.replaceAll("[^a-zA-Z0-9 ]+"," ");
    	
    	
    	
    	
    	
    	
		try {
		    print(pack("1234567890", 8));
		    print(pack("-1234567890", 8));
		    print(pack(123456789012345L, 8));
		    print(pack(1234567890123456L, 8)); // <=== decimal overflow
		} catch (Exception e) {
		    e.printStackTrace(System.out);
		}
    }
 
    public static byte[] pack(BigInteger value, int length) {
		BigInteger workValue = value.abs();
		byte[] bytes = new byte[length];
		int index = length - 1;
		bytes[index] = (byte) (value.compareTo(BigInteger.ZERO) < 0 ? 13 : 12);
		boolean left = true;
		while (!workValue.equals(BigInteger.ZERO)) {
		    if (index < 0) {
			throw new RuntimeException("Decimal overflow: " + value
				+ " does not fit in " + length + " bytes");
		    }
		    int digit = workValue.mod(BigInteger.TEN).intValue();
		    workValue = workValue.divide(BigInteger.TEN);
		    if (left) {
			digit = digit << 4;
			bytes[index--] |= (byte) digit;
			left = false;
		    } else {
			bytes[index] = (byte) digit;
			left = true;
		    }
		}
		return bytes;
    }
 
    public static byte[] pack(long value, int length) {
    	return pack(BigInteger.valueOf(value), length);
    }
 
    public static byte[] pack(String value, int length) {
    	return pack(new BigInteger(value), length);
    }
 
    public static String unpk(byte[] bytes) {
		BigInteger value = BigInteger.ZERO;
		for (int index = 0; index < bytes.length; index++) {
		    int high = (bytes[index] & 0x000000ff) >> 4;
		    if (high > 9) {
			throw new RuntimeException("0C7 Invalid data");
		    }
		    int low = bytes[index] & 0x0000000f;
		    if (index < bytes.length - 1) {
			if (low > 9) {
			    throw new RuntimeException("0C7 Invalid data");
			}
			high = 10 * high + low;
		    }
		    value = value.add(BigInteger.valueOf(high));
		    if (index < bytes.length - 2) {
			value = value.multiply(hundred);
		    } else if (index == bytes.length - 2) {
			value = value.multiply(BigInteger.TEN);
		    } else {
			if (low < 10) {
			    throw new RuntimeException("0C7 Invalid data");
			}
			// Positive: A C E F
			// Negative: B D
			if (low == 11 || low == 13) {
			    value = value.negate();
			}
	    }
	}
	return value.toString();
    }
 
    private static void print(byte[] bytes) {
	StringBuilder sb = new StringBuilder();
	for (byte b : bytes) {
	    String hex = "0" + Integer.toHexString(b);
	    if (hex.length() > 2) {
		hex = hex.substring(hex.length() - 2);
	    }
	    sb.append(hex);
	}
	System.out.println(sb.toString().toUpperCase() + "\t" + unpk(bytes));
    }
 
}