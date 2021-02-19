package com.ucarez.util;

import org.apache.commons.codec.binary.Base64;

  
public class Base64Util {  
	
	/** 默认编码UTF-8 */  
    public static final String DEFAULT_ENCODING = "UTF-8";
	
	/** 
     * @param bytes 
     * @return 
	 * @throws Exception 
     */  
    public static String decode(String str) throws Exception {  
    	byte[] decodeBytes = Base64.decodeBase64(str.getBytes(DEFAULT_ENCODING));  
        return new String(decodeBytes, DEFAULT_ENCODING);  
    }
    
    /** 
     * @param bytes 
     * @return 
	 * @throws Exception 
     */  
    public static byte[] decodeToByte(String str) throws Exception {  
        return Base64.decodeBase64(str.getBytes(DEFAULT_ENCODING));  
    }
  
    /** 
     * 二进制数据编码为BASE64字符串 
     * 
     * @param bytes 
     * @return 
     * @throws Exception
     */  
    public static String encode(String str) throws Exception {  
    	byte[] encodeBytes = Base64.encodeBase64(str.getBytes(DEFAULT_ENCODING));  
        return new String(encodeBytes, DEFAULT_ENCODING);  
    }  
    
    
    
    /** 
     * 二进制数据编码为BASE64字符串 
     * 
     * @param bytes 
     * @return 
     * @throws Exception
     */  
    public static String encode(byte[] str) throws Exception {  
        return new String(Base64.encodeBase64(str), DEFAULT_ENCODING);  
    }  
    
    
    public static void main(String[] args) throws Exception {
		String str = "你好 不是吧 怎么可能 哈哈 你好 nihao";
		String code = encode(str);
		System.out.println(code + "  |  " + decode(code));
	}
} 
