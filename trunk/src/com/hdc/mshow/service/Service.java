package com.hdc.mshow.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.hdc.mshow.ultilities.Base64;

public abstract class Service {

	// TODO JSON
	static HttpClient client;
	static HttpParams p;
	static ArrayList<NameValuePair> nameValuePair;
	static HttpPost httppost;
	public String VALUE;
	public String LINK;
	public String DATA;

	// TODO Resources
	public String INFO = "info";
	public static String Question = "?";
	public static String And = "&";
	public static String Equals = "=";
	public static String HOST = "http://taoviec.com/data_app/";
	public final static String EMPTY = "EMPTY";
	public String REF_CODE = "10e36710a919d7f43080695cf8587e9c";
	public final static String v = "1.0";
	public final static String midp = "2.0";
	public static String SPACE = " ";
	public String PROVIDER_ID = "0";
	public String LINK_UPDATE = "http://thegioigame.mobi/";
	public static String TOKEN = "TAoViEC@)!#2012";
	public static String TOKEN_ADMIN = "SmsKUteTAovieCAdmin@)!#";
	public String LIST_SMS_ID = "";
	public String ICON = "";
	public String PRICE = "";
	public String COMBO = "0";
	public String EVENT_ID = "";	

	// TODO get Data json
	public String getDataJson(String info, String link) {
		String m_DataJson = "";

		// TODO BASE64 for info
		String Info_Base64 = Base64.encode(info.getBytes());

		// TODO httpparams
		p = new BasicHttpParams();
		p.setParameter(INFO, Info_Base64);

		// TODO httpclient
		client = new DefaultHttpClient(p);

		// TODO list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair(INFO, Info_Base64));
		String url = link + Question + INFO + Equals + Info_Base64;

		// TODO httppost
		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// TODO get data json
			m_DataJson = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m_DataJson;
	}

}
