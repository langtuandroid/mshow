package com.hdc.mshow.customize;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.content.Context;

public class Toast {
	public static Toast instance = new Toast();
	public StringWriter stringWriter = new StringWriter();
	private PrintWriter printWriter = new PrintWriter(stringWriter, true);

	public Toast() {
		// TODO Auto-generated constructor stub
	}

	public void showToast(Context c, Exception ex) {
		ex.printStackTrace(printWriter);
		printWriter.flush();
		stringWriter.flush();
		android.widget.Toast.makeText(c, stringWriter.toString(), android.widget.Toast.LENGTH_LONG)
				.show();
	}

	public void show(Context c, String msg) {
		android.widget.Toast.makeText(c, msg, android.widget.Toast.LENGTH_LONG).show();
	}

}
