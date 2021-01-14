package com.geekbang.jvm;

import com.sun.corba.se.spi.ior.ObjectKey;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoadClassTest extends ClassLoader{

	public static void main(String[] args) {
		
		try {
			Object instance = new LoadClassTest().findClass("Hello").newInstance();

			instance.getClass().getMethod("hello").invoke(instance);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		try {
			File file = new File("D:/workspace/week_01/resource/Hello.xlass");
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] barray = new byte[(int) (file.length())];
			fileInputStream.read(barray);
			fileInputStream.close();

			BASE64Encoder bASE64Decoder = new BASE64Encoder();
			String classStr = bASE64Decoder.encode(barray).replaceAll("\r\n", "");

			byte[] decodeArray = Base64.getDecoder().decode(classStr);
			byte b = (byte)255;
			for (int i = 0; i < decodeArray.length; i++) {
				byte a = decodeArray[i];
				 decodeArray[i] = (byte) (b - a);
			}
			return defineClass(name, decodeArray , 0 , decodeArray.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		 
	 }
}
