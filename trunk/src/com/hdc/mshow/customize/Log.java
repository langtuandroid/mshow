package com.hdc.mshow.customize;

public class Log {
	static boolean flag = true;

	public static void i(String name, String value) {
		if (flag)
			android.util.Log.i(name, value);
	}

	public static void e(String name, String value) {
		if (flag)
			android.util.Log.e(name, value);
	}
	

}
