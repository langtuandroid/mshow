package com.example.ultilities;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.activity.ListCatActivity;
import com.example.activity.ListDetailActivity;
import com.example.adapter.Custom_Dialog;
import com.example.entity.Active;
import com.example.entity.CateTitle;
import com.example.entity.Category;
import com.example.entity.Item;
import com.example.entity.Promotion;
import com.example.entity.Sms;

public class ConnectServer {
	public static ConnectServer instance = new ConnectServer();

	public static String HOST = "http://taoviec.com/data_app/";
	public static String ALBUM = "Album.php";
	public static String ALBUM_SEARCH = "AlbumSearch.php";
	public static String ALBUM_OTHER = "AlbumOther.php";
	public static String ALBUM_DETAIL = "AlbumDetail.php";
	public static String CATEGORY = "Category.php";
	public static String REGISTER = "Register.php";
	public static String UPDATEVIEW = "UpdateViewApp.php";
	public static String VERSION = "Version.php";
	public static String ACTIVATION = "Activation.php";
	public static String PROMOTION = "Promotion.php";
	public static String SMS = "Sms.php";
	public static String TYPE = "type";
	public static String QUESTION = "?";
	public static String AND = "&";
	public static String INFO = "info";
	public static String EQUALS = "=";
	public static String TOKEN = "token=TAoViEC@)!#2012";
	public static String PAGE = "p";
	public static String KEYWORD = "keyword";
	public final static String EMPTY = "EMPTY";
	public final static String midp = "2.0";
	public final static String v = "1.0";

	public Active m_Active = new Active();
	public Sms m_Sms = new Sms();
	public ArrayList<Category> m_ListCategory = new ArrayList<Category>();
	public ArrayList<Category> m_ListCatOther = new ArrayList<Category>();
	public ArrayList<Item> m_ListItem = new ArrayList<Item>();
	public ArrayList<Promotion> m_promotion = new ArrayList<Promotion>();
	public ArrayList<CateTitle> m_ListSpinner = new ArrayList<CateTitle>();
	public ArrayList<Bitmap> m_ListbitmapCat = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> m_ListbitmapCatOther = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> m_ListbitmapItem = new ArrayList<Bitmap>();

	// public static Thread[] threadIMG_Cat;
	public static Thread[] threadItems;
	public static Thread[] threadIMG_Others;

	public static String CATID = "catId";

	public static String ALBUMID = "albumId";

	public static Integer currentpage = 1;

	public static Integer totalpage = 1;

	String m_Info = "";

	public static String PROVIDER_ID = "";

	public static String LINK_UPDATE = "";

	public String REF_CODE = "";

	public boolean isFirstTime = true;

	// public static Integer countLuot = 0;

	// http param
	static HttpParams p;
	// httpClient
	static HttpClient client;
	// httppost
	static HttpPost httppost;
	// list nameValuePair
	static ArrayList<NameValuePair> nameValuePair;

	public String m_AppID = "";

	public static boolean hasdelivered = false;

	public void getCategory(Integer idspin, String searchText, Context context) {
		if (idspin == null) {
			m_Info = CATID + EQUALS + EMPTY + AND + PAGE + EQUALS + currentpage
					+ AND + TOKEN;
			if (searchText != "") {
				m_Info = CATID + EQUALS + EMPTY + AND + KEYWORD + EQUALS
						+ searchText + AND + PAGE + EQUALS + currentpage + AND
						+ TOKEN;

			}
		} else {
			m_Info = CATID + EQUALS + idspin + AND + PAGE + EQUALS
					+ currentpage + AND + TOKEN;
			if (searchText != "") {
				m_Info = CATID + EQUALS + idspin + AND + KEYWORD + EQUALS
						+ searchText + AND + PAGE + EQUALS + currentpage + AND
						+ TOKEN;
			}
		}

		String Info_Base64 = Base64.encode(m_Info.getBytes());
		// init httpparams
		String data = "";
		String url = "";

		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// String url = HOST + Category + Question + "info" + Equals +
		// Info_Base64;
		if (searchText == "") {
			url = HOST + ALBUM + QUESTION + INFO + EQUALS + Info_Base64;
		} else {
			url = HOST + ALBUM_SEARCH + QUESTION + INFO + EQUALS + Info_Base64;
		}

		httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			data = client.execute(httppost, responseHandler);
			Log.i("data", data);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonObject
		try {

			JSONObject j = new JSONObject(data);

			j = new JSONObject(j.getString("data"));
			String t = j.getString("item");
			if (t != "null") {
				totalpage = Integer.parseInt(j.getString("totalPage"));
				m_ListCategory = getListCategory(j.getString("item"));
				if (m_ListSpinner.isEmpty()) {
					getSpinnerCat();
				}
				if (m_promotion.isEmpty()) {
					getPromotion();

				}
			} else {
				Custom_Dialog.transferBlank(context);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// return m_ListCategory;
	}

	private ArrayList<Category> getListCategory(String string) {
		// TODO Auto-generated method stub
		ArrayList<Category> aa = new ArrayList<Category>();
		// m_ListbitmapCat = new ArrayList<Bitmap>();
		final ArrayList<Bitmap> aa1 = new ArrayList<Bitmap>();
		JSONArray json = null;
		try {
			json = new JSONArray(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < json.length(); i++) {
			Category cate = new Category();
			try {
				JSONObject j = json.getJSONObject(i);
				cate.setId(Integer.parseInt(j.getString("id").trim()));
				cate.setNamecate(j.getString("title"));
				cate.setUrl(j.getString("src"));
				cate.setCountDown(Integer.parseInt(j.getString("total_image")
						.trim()));
				cate.setCountView(Integer.parseInt(j.getString("hit").trim()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// add into list item
				// aa1.add(DownloadImage.instance.getImage(cate.getUrl()));
				aa.add(cate);
				// m_ListbitmapCat = new ArrayList<Bitmap>();

			}
		}

		for (int i = 0; i < aa.size(); i++) {
			Category cate = new Category();
			cate = aa.get(i);

			aa1.add(DownloadImage.instance.getImage(cate.getUrl()));
		}

		m_ListbitmapCat = aa1;
		return aa;
	}

	public void getOtherCategory(int id) {
		// TODO Auto-generated method stub
		String m_Info = ALBUMID + EQUALS + id + AND + TOKEN;
		String Info_Base64 = Base64.encode(m_Info.getBytes());
		// init httpparams
		String data = "";
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// String url = HOST + Category + Question + "info" + Equals +
		// Info_Base64;
		String url = HOST + ALBUM_OTHER + QUESTION + INFO + EQUALS
				+ Info_Base64;

		httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			data = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonObject
		try {
			JSONObject j = new JSONObject(data);
			j = new JSONObject(j.getString("data"));
			// m_ListCatOther=new ArrayList<Category>();
			m_ListCatOther = getListCategoryOther(j.getString("item"));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private ArrayList<Category> getListCategoryOther(String string) {
		// TODO Auto-generated method stub
		ArrayList<Category> cc = new ArrayList<Category>();
		final ArrayList<Bitmap> cc1 = new ArrayList<Bitmap>();

		JSONArray json = null;
		try {
			json = new JSONArray(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < json.length(); i++) {
			Category cate = new Category();
			try {
				JSONObject j = json.getJSONObject(i);
				cate.setId(Integer.parseInt(j.getString("id").trim()));

				cate.setNamecate(j.getString("title"));
				cate.setUrl(j.getString("src"));
				cate.setCountView(Integer.parseInt(j.getString("hit").trim()));
				cate.setCountDown(Integer.parseInt(j.getString("total_image")
						.trim()));
				// m_ListbitmapCatOther.remove(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// add into list item
				// cc1.add(DownloadImage.instance.getImage(cate.getUrl()));
				cc.add(cate);

			}
		}
		Log.i("size OtherCat", cc.size() + "");

		for (int i = 0; i < cc.size(); i++) {
			Category category = new Category();
			category = cc.get(i);

			cc1.add(DownloadImage.instance.getImage(category.getUrl()));
		}

		m_ListbitmapCatOther = cc1;
		return cc;

	}

	public void getImage(Integer id) {
		// TODO Auto-generated method stub
		String m_Info = ALBUMID + EQUALS + id + AND + PAGE + EQUALS
				+ currentpage + AND + TOKEN;
		String Info_Base64 = Base64.encode(m_Info.getBytes());
		// init httpparams
		String data = "";
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// String url = HOST + Category + Question + "info" + Equals +
		// Info_Base64;
		String url = HOST + ALBUM_DETAIL + QUESTION + INFO + EQUALS
				+ Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			data = client.execute(httppost, responseHandler);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonObject
		try {
			JSONObject j = new JSONObject(data);
			j = new JSONObject(j.getString("data"));
			m_ListItem = getListItem(j.getString("item"));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private ArrayList<Item> getListItem(String string) {
		// TODO Auto-generated method stub
		final ArrayList<Item> bb = new ArrayList<Item>();
		final ArrayList<Bitmap> bb1 = new ArrayList<Bitmap>();
		JSONArray json = null;
		try {
			json = new JSONArray(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < json.length(); i++) {
			Item item = new Item();
			try {
				JSONObject j = json.getJSONObject(i);
				item.setId(Integer.parseInt(j.getString("id").trim()));
				item.setUrlimg(j.getString("src"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// add into list item
				// bb1.add(DownloadImage.instance.getImage(item.getUrlimg()));
				bb.add(item);

			}
		}
		 for (int i = 0; i < bb.size(); i++) {
		 Item item = new Item();
		 item = bb.get(i);
		 bb1.add(DownloadImage.instance.getImage(item.getUrlimg()));
		 }

//		Log.i("size ITem", bb.size() + "");
//		try { 
//			threadItems = new Thread[bb.size()];
//			for (int i = 0; i < bb.size(); i++) {
//				Item item = new Item();
//				item = bb.get(i);
//				final String stritem = item.getUrlimg();
//				threadItems[i] = new Thread(new Runnable() {
//
//					public void run() {
//						// TODO Auto-generated method stub
//
//						bb1.add(DownloadImage.instance.getImage(stritem));
//					}
//				});
//				threadItems[i].start();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

		m_ListbitmapItem = bb1; 
		return bb;

	}

	public void getSpinnerCat() {
		// TODO Auto-generated method stub

		String m_Info = TYPE + EQUALS + "3" + AND + TOKEN;
		String Info_Base64 = Base64.encode(m_Info.getBytes());
		// init httpparams
		String data = "";
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// String url = HOST + Category + Question + "info" + Equals +
		// Info_Base64;
		String url = HOST + CATEGORY + QUESTION + INFO + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			data = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonObject
		try {
			JSONObject j = new JSONObject(data);
			j = new JSONObject(j.getString("data"));
			m_ListSpinner = getListSpinner(j.getString("item"));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private ArrayList<CateTitle> getListSpinner(String string) {
		// TODO Auto-generated method stub
		ArrayList<CateTitle> cc = new ArrayList<CateTitle>();

		JSONArray json = null;
		try {
			json = new JSONArray(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < json.length(); i++) {
			CateTitle item = new CateTitle();
			try {
				JSONObject j = json.getJSONObject(i);
				item.setId(Integer.parseInt(j.getString("id").trim()));
				item.setName(j.getString("name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// add into list item
				cc.add(item);

			}
		}

		return cc;
	}

	public String getAppID(int w, int h, String brand, String model) {
		String m_Info = "refCode" + EQUALS + REF_CODE + AND + TOKEN + AND
				+ "brand" + EQUALS + brand + AND + "model" + EQUALS + model
				+ AND + "w" + EQUALS + w + AND + "h" + EQUALS + h + AND
				+ "midp" + EQUALS + midp + AND + "operation_system" + EQUALS
				+ "android" + AND + "type_content" + EQUALS + "3";

		String Info_Base64 = Base64.encode(m_Info.getBytes());

		String userID = "";

		// init httpparams
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// init httppost
		// String url = HOST + Register + Question + "brand" + Equals + EMPTY
		// + And + "model" + Equals + EMPTY + And + "w" + Equals + w + And
		// + "h" + Equals + h + And + "midp" + Equals + midp + And + "v"
		// + Equals + v + And + "refCode" + Equals + REF_CODE;
		String url = HOST + REGISTER + QUESTION + INFO + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			userID = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// slip string
		// String[] s = userID.split("#");
		String res = "";
		try {
			JSONObject json = new JSONObject(userID);
			String status = json.getString("status");
			res = json.getString("res");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public int getVersion() {
		// TODO Auto-generated method stub
		String m_Info = "type" + EQUALS + "3" + AND + "v" + EQUALS + v + AND
				+ TOKEN;

		String Info_Base64 = Base64.encode(m_Info.getBytes());

		String m_version = "";

		// init httpparams
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		// init httpclient
		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// init httppost
		// String url = HOST + Version + Question + "type" + Equals + type + And
		// + "v" + Equals + v + And + "refCode" + Equals + REF_CODE;
		String url = HOST + VERSION + QUESTION + "info" + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			m_version = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonObject
		try {
			JSONObject j = new JSONObject(m_version);
			m_version = j.getString("status");
			// j = new JSONObject(m_version);
			// m_version = j.getString("message");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return Integer.parseInt(m_version.trim());
	}

	public void getSMS() {
		String m_Info = TYPE + EQUALS + "3" + AND + "appId" + EQUALS + m_AppID
				+ AND + "refCode" + EQUALS + REF_CODE + AND + TOKEN;

		String Info_Base64 = Base64.encode(m_Info.getBytes());

		String userID = "";

		// init httpparams
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);
		// init httpclient
		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		String url = HOST + SMS + QUESTION + INFO + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			userID = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// slip string
		// String[] s = userID.split("#");
		// String res = "";
		try {
			JSONObject json = new JSONObject(userID);
			json = new JSONObject(json.getString("sms"));
			m_Sms.setMo(json.getString("mo"));
			m_Sms.setServiceCode(json.getString("serviceCode"));
			// m_Sms.setServiceCode("6022");
			m_Sms.setMessage(json.getString("message"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getActive() {
		// TODO Auto-generated method stub
		String m_Info = "appId" + EQUALS + m_AppID + AND + TOKEN;

		String Info_Base64 = Base64.encode(m_Info.getBytes());

		String userID = "";

		// init httpparams
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);
		// init httpclient
		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		String url = HOST + ACTIVATION + QUESTION + INFO + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			userID = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// slip string
		// String[] s = userID.split("#");
		// String res = "";
		try {
			JSONObject json = new JSONObject(userID);
			m_Active.setStatus(json.getString("status"));
			m_Active.setStatus("0");
			m_Active.setMsg(json.getString("msg"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getPromotion() {
		// TODO Auto-generated method stub
		String m_Info = TYPE + EQUALS + "3" + AND + "refCode" + EQUALS
				+ REF_CODE + AND + "os_type" + EQUALS + EMPTY + AND + TOKEN;
		Log.e("m_infopromotion", m_Info);
		String Info_Base64 = Base64.encode(m_Info.getBytes());

		String data = "";

		// init httpparams
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);
		// init httpclient
		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		String url = HOST + PROMOTION + QUESTION + INFO + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			data = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// slip string
		// String[] s = userID.split("#");
		// String res = "";
		try {
			JSONObject json = new JSONObject(data);
			json = new JSONObject(json.getString("data"));
			m_promotion = getlistPromotion(json.getString("promotion"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<Promotion> getlistPromotion(String string) {
		// TODO Auto-generated method stub
		ArrayList<Promotion> aa = new ArrayList<Promotion>();
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(string);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for (int i = 0; i < jsonArray.length(); i++) {
			Promotion promo = new Promotion();
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				promo.setLink(jsonObject.getString("link"));
				promo.setSrc(jsonObject.getString("src"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				aa.add(promo);
			}
		}
		return aa;
	}

	public void updateViewApp(Integer width, Integer height, String brand,
			String model) {
		String m_Info = "refCode" + EQUALS + REF_CODE + AND + TOKEN + AND
				+ "brand" + EQUALS + brand + AND + "model" + EQUALS + model
				+ AND + "w" + EQUALS + width + AND + "h" + EQUALS + height
				+ AND + "midp" + EQUALS + midp + AND + "operation_system"
				+ EQUALS + "android" + AND + "type_content" + EQUALS + "3";

		String Info_Base64 = Base64.encode(m_Info.getBytes());

		String userID = "";

		// init httpparams
		p = new BasicHttpParams();
		p.setParameter("info", Info_Base64);

		client = new DefaultHttpClient(p);
		// init list nameValuepair
		nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("info", Info_Base64));

		// init httppost
		// String url = HOST + Register + Question + "brand" + Equals + EMPTY
		// + And + "model" + Equals + EMPTY + And + "w" + Equals + w + And
		// + "h" + Equals + h + And + "midp" + Equals + midp + And + "v"
		// + Equals + v + And + "refCode" + Equals + REF_CODE;
		String url = HOST + UPDATEVIEW + QUESTION + INFO + EQUALS + Info_Base64;

		httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			userID = client.execute(httppost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
