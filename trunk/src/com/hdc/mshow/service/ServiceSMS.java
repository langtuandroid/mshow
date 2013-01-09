package com.hdc.mshow.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.hdc.mshow.model.Album;
import com.hdc.mshow.model.Category;
import com.hdc.mshow.model.Item;
import com.hdc.mshow.model.Promotion;
import com.hdc.mshow.model.Sms;
import com.hdc.mshow.ultilities.DownloadImage;

public class ServiceSMS extends Service {
	// TODO instance
	public static ServiceSMS instance = new ServiceSMS();

	// TODO Resources
	public String MyBrand = "";
	public String MyModel = "";
	public final static String type_new = "3"; // mShow
	public final static String REGISTER = "Register.php";
	public final static String ACTIVATION = "Activation_msoi.php";
	public final static String UPDATE_VIEW_APP = "UpdateViewApp.php";
	public final static String VERSION = "Version.php";
	public final static String SMS = "Sms.php";
	public final static String ACTIVE_SMS = "Active_msoi.php"; // check activie
																// sms
	public final static String ADVERTISE = "Adv_msoi.php";
	public final static String SMSKUTE = "SmsKuteNew.php";
	public final static String SMS_SMSKUTE = "SmsSmsKute.php";
	public String m_AppID = "";
	public String isFirstTime = "";

	public Sms m_Sms = new Sms();

	public ServiceSMS() {
		// TODO Auto-generated constructor stub
		HOST = "http://taoviec.com/data_app/";
	}

	@Override
	public String getDataJson(String info, String link) {
		// TODO Auto-generated method stub
		return super.getDataJson(info, link);
	}

	// get data for appID
	public String getAppID(int w, int h) {
		// TODO Value for paramater Info
		VALUE = "refCode" + Equals + REF_CODE + And + "token" + Equals + "TAoViEC@)!#2012" + And
				+ "branch" + Equals + MyBrand + And + "handset" + Equals + MyModel + And + "w"
				+ Equals + w + And + "h" + Equals + h + And + "midp" + Equals + midp + And
				+ "operation_system" + Equals + "Android" + And + "type_content" + Equals
				+ type_new;

		// TODO Link
		LINK = HOST + REGISTER;

		// TODO get data json
		DATA = getDataJson(VALUE, LINK);

		// TODO get AppId
		String appId = "";
		try {
			JSONObject json = new JSONObject(DATA);
			appId = json.getString("res");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return appId;
	}
	
	// TODO get SMS for mShow
	public void getSMS() {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "refCode" + Equals + REF_CODE + And + "appId"
				+ Equals + m_AppID + And + "type" + Equals + type_new;

		// TODO Link
		LINK = HOST + SMS;

		// TODO data json
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject json = new JSONObject(DATA);
			String status = json.getString("status");
			if (status.trim().equals("1")) {
				m_Sms.mo = json.getString("mo");
				m_Sms.serviceCode = json.getString("serviceCode");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO update View App
	public void updateViewApp(int w, int h) {
		// TODO Value
		VALUE = "appId" + Equals + m_AppID + And + "token" + Equals + "TAoViEC@)!#2012" + And
				+ "branch" + Equals + MyBrand + And + "handset" + Equals + MyModel + And + "w"
				+ Equals + w + And + "h" + Equals + h + And + "midp" + Equals + midp + And
				+ "operation_system" + Equals + "Android" + And + "type_content" + Equals
				+ type_new;

		// TODO Link
		LINK = HOST + UPDATE_VIEW_APP;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject json = new JSONObject(DATA);
			String status = json.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO get data for version
	public int getVersion() {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "type" + Equals + type_new + And + "v" + Equals
				+ v;

		// TODO Link
		LINK = HOST + VERSION;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		String m_version = "";
		int version = 0;
		// jsonObject
		try {
			JSONObject j = new JSONObject(DATA);
			m_version = j.getString("status");
			version = Integer.parseInt(m_version.trim());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return version;
	}

	// ///////////////////////////////////////////////////////////////
	// ///
	// /// mShow Service
	// ///
	// ///////////////////////////////////////////////////////////////

	public String catId = "";
	public int totalPage;
	public int currentPage = 1;
	public final String ALBUM = "Album.php";
	public final String PROMOTION = "Promotion.php";
	public final String CATEGORY = "Category.php";
	public final String ALBUM_SEARCH = "AlbumSearch.php";
	public final String ALBUM_OTHER = "AlbumOther.php";
	public final String ALBUM_DETAIL = "AlbumDetail.php";
	
	// TODO DATA
	public ArrayList<Album> m_ListAlbums = new ArrayList<Album>();
	public Promotion m_Promotion = new Promotion();
	public ArrayList<Category> m_ListCategory = new ArrayList<Category>();
	public ArrayList<Album> m_ListAlbum_Other = new ArrayList<Album>();
	public ArrayList<Item> m_ListAlbum_Items = new ArrayList<Item>();

	// TODO Lấy danh sách tất cả Album
	public void getAll_Albums() {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "p" + Equals + currentPage + And + "catId"
				+ Equals + catId;

		// TODO Link
		LINK = HOST + ALBUM;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject j = new JSONObject(DATA);
			j = new JSONObject(j.getString("data"));
			String t = j.getString("item");
			if (t != "null") {
				totalPage = Integer.parseInt(j.getString("totalPage"));
				m_ListAlbums = getListAlbum(j.getString("item"));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private ArrayList<Album> getListAlbum(String string) {
		ArrayList<Album> aa = new ArrayList<Album>();
		JSONArray json = null;
		try {
			json = new JSONArray(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < json.length(); i++) {
			Album album = new Album();
			try {
				JSONObject j = json.getJSONObject(i);
				album.setId(j.getString("id").trim());
				album.setTitle(j.getString("title"));
				album.setTotal_image(j.getString("total_image"));
				album.setHit(j.getString("hit"));
				album.setSrc(j.getString("src"));

				// TODO get Bitmap from Url
//				Bitmap b = null;
//				try {
//					b = DownloadImage.instance.getImage(album.getSrc());
//				} catch (Exception ex) {
//					ex.printStackTrace();
//					b = null;
//				}
//				if (b != null)
//					album.setImg(b);
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				aa.add(album);
			}
		}
		return aa;
	}

	// TODO Lấy promotion
	public void getPromotion() {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "type=3" + And + "refCode" + Equals + REF_CODE
				+ And + "os_type=Android";

		// TODO Link
		LINK = HOST + PROMOTION;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject j = new JSONObject(DATA);
			j = new JSONObject(j.getString("promotion"));
			try {
				m_Promotion.setLink(j.getString("link"));
				m_Promotion.setSrc(j.getString("src"));
				if (!m_Promotion.getSrc().equals("")) {
					Bitmap b = null;
					b = com.hdc.mshow.ultilities.DownloadImage.instance.getImage(m_Promotion
							.getSrc());
					m_Promotion.setImg(b);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// TODO lấy danh mục album ảnh
	public void getCategorys() {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "type=3";

		// TODO Link
		LINK = HOST + CATEGORY;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject j = new JSONObject(DATA);
			j = new JSONObject(j.getString("data"));
			String t = j.getString("item");
			if (t != "null") {
				m_ListCategory = getList_Category(j.getString("item"));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private ArrayList<Category> getList_Category(String string) {
		ArrayList<Category> aa = new ArrayList<Category>();
		JSONArray json = null;
		try {
			json = new JSONArray(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < json.length(); i++) {
			Category item = new Category();
			try {
				JSONObject j = json.getJSONObject(i);
				item.setId(j.getString("id").trim());
				item.setName(j.getString("name").trim());
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				aa.add(item);
			}
		}
		return aa;
	}

	// TODO Lấy danh sách album search
	public void getAlbum_Search(String keyword) {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "p" + Equals + currentPage + And + "catId"
				+ Equals + catId + And + "keyword" + Equals + keyword;

		// TODO Link
		LINK = HOST + ALBUM_SEARCH;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject j = new JSONObject(DATA);
			j = new JSONObject(j.getString("data"));
			String t = j.getString("item");
			if (t != "null") {
				totalPage = Integer.parseInt(j.getString("totalPage"));
				m_ListAlbums = getListAlbum(j.getString("item"));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// TODO Lấy danh sách Other album
	public void getAlbum_Other(String id) {
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "albumId" + Equals + id;

		// TODO Link
		LINK = HOST + ALBUM_OTHER;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject j = new JSONObject(DATA);
			j = new JSONObject(j.getString("data"));
			String t = j.getString("item");
			if (t != "null") {
				m_ListAlbum_Other = getListAlbum(j.getString("item"));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//TODO Lấy danh sách Item
	public void getAlbum_Item(String id){
		// TODO Value
		VALUE = "token" + Equals + TOKEN + And + "albumId" + Equals + id;

		// TODO Link
		LINK = HOST + ALBUM_DETAIL;

		// TODO Data
		DATA = getDataJson(VALUE, LINK);

		try {
			JSONObject j = new JSONObject(DATA);
			j = new JSONObject(j.getString("data"));
			String t = j.getString("item");
			if (t != "null") {
				m_ListAlbum_Items = getListAlbum_Items(j.getString("item"));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private ArrayList<Item> getListAlbum_Items(String string) {
		ArrayList<Item> aa = new ArrayList<Item>();
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
				item.setId(j.getString("id").trim());
				item.setSrc(j.getString("src").trim());
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				aa.add(item);
			}
		}
		return aa;
	}
	
}
