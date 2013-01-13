package com.hdc.mshow.customize;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hdc.mshow.ListAlbumActivity;
import com.hdc.mshow.R;
import com.hdc.mshow.service.ServiceSMS;

public class Footer implements OnClickListener {

	Context c;

	public View instance;

	// TODO Layout paging
	LinearLayout layout_paging;

	// TODO Button page
	Button bt_Previous_All;
	Button bt_Previous;
	Button bt_Page_1;
	Button bt_Page_2;
	Button bt_Page_3;
	Button bt_Next;
	Button bt_Next_All;
	// Page : |< < 1 2 3 > >|
	Button[] m_List_Button_Page = new Button[7];
	int[] id = { R.id.previous_all, R.id.previous, R.id.page_1, R.id.page_2,
			R.id.page_3, R.id.next, R.id.next_all };

	// TODO Color
	int color_focus = Color.RED;
	int color_normal = Color.WHITE;

	// TODO focus
	int index_focus = 2;

	// TODO Promotion
	ImageView imgPromotion;

	public Footer(Context context) {
		// TODO Context
		c = context;

		// View
		LayoutInflater inflater = (LayoutInflater) ((Activity) context)
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		instance = (View) inflater.inflate(R.layout.myfooter, null, false);

		// TODO Layout paging
		layout_paging = (LinearLayout) instance
				.findViewById(R.id.layout_paging);

		// TODO init all button page
		// initAll_Button_Page(instance);
		for (int i = 0; i < m_List_Button_Page.length; i++) {
			initButton_Page(instance, id[i], i);
		}
		setColor_Page(2, true);

		imgPromotion = (ImageView) instance.findViewById(R.id.footer_advertise);
		if (ServiceSMS.instance.m_Promotion.getImg() != null) {
			imgPromotion.setImageBitmap(ServiceSMS.instance.m_Promotion
					.getImg());
		}
	}

	// TODO init all button page
	private void initButton_Page(View v, int id, int index) {
		m_List_Button_Page[index] = (Button) v.findViewById(id);
		m_List_Button_Page[index].setOnClickListener(this);
		setColor_Page(index, false);
	}

	// TODO set color focus and normal
	private void setColor_Page(int index, boolean focus) {
		if (focus) {
			// color focus for currentPage = 1
			// m_List_Button_Page[index].setTextColor(color_focus);
			m_List_Button_Page[index].setBackgroundResource(R.drawable.number1);
		} else {
			// color normal
			// m_List_Button_Page[index].setTextColor(color_normal);
			m_List_Button_Page[index].setBackgroundResource(R.drawable.number2);
		}
	}

	// TODO Visile layout Paging before and after loading listview
	public void setVisible_Paging(int visible) {
		layout_paging.setVisibility(visible);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for (int i = 0; i < m_List_Button_Page.length; i++) {
			if (v == m_List_Button_Page[i]) {
				if (index_focus != i)
					updateListView(caculatePage(i));
				else
					Toast.instance.show(c, "B?n �ang ? trang hi?n t?i !!!");
			}
		}
	}

	// TODO set color page
	private void setColor(int index) {
		for (int i = 0; i < m_List_Button_Page.length; i++) {
			setColor_Page(i, false);
		}
		setColor_Page(index, true);
	}

	// TODO caculate Page
	private int caculatePage(int index) {
		int page = 0;
		int currentPage = ServiceSMS.instance.currentPage;
		int totalPage = ServiceSMS.instance.totalPage;
		switch (index) {
		case 0: // Page : '|<'

			// disable 2 button |< & <
			m_List_Button_Page[0].setVisibility(View.GONE);
			m_List_Button_Page[1].setVisibility(View.GONE);
			m_List_Button_Page[5].setVisibility(View.VISIBLE);
			m_List_Button_Page[6].setVisibility(View.VISIBLE);

			// set text 3 page 1 2 3
			m_List_Button_Page[2].setText("1");
			m_List_Button_Page[3].setText("2");
			m_List_Button_Page[4].setText("3");

			// set color
			index_focus = 2;
			setColor(index_focus);

			// trang �?u ti�n
			page = 1;
			break;
		case 1: // Page '<'

			// set text 3 page 1 2 3
			m_List_Button_Page[2].setText((currentPage - 2) + "");
			m_List_Button_Page[3].setText((currentPage - 1) + "");
			m_List_Button_Page[4].setText(currentPage + "");

			// set color
			index_focus = 3;
			setColor(index_focus);

			page = currentPage - 1;
			break;
		case 2: // Page '1'
			if (currentPage != 1) {
				if (currentPage == 3) {
					// disable 2 button |< & <
					m_List_Button_Page[0].setVisibility(View.GONE);
					m_List_Button_Page[1].setVisibility(View.GONE);
				}

				if (currentPage != 2) {
					// set text 3 page 1 2 3
					m_List_Button_Page[2].setText((currentPage - 2) + "");
					m_List_Button_Page[3].setText((currentPage - 1) + "");
					m_List_Button_Page[4].setText(currentPage + "");
					// set color
					index_focus = 3;
					setColor(index_focus);
				} else if (currentPage == 2) {
					// disable 2 button |< & <
					m_List_Button_Page[0].setVisibility(View.GONE);
					m_List_Button_Page[1].setVisibility(View.GONE);

					// set text 3 page 1 2 3
					m_List_Button_Page[2].setText((currentPage - 1) + "");
					m_List_Button_Page[3].setText((currentPage) + "");
					m_List_Button_Page[4].setText((currentPage + 1) + "");
					// set color
					index_focus = 2;
					setColor(index_focus);
				}

				page = currentPage - 1;
			}
			break;
		case 3: // Page '2'
			// tr�?ng h?p current Page = 1
			if (currentPage == 1) {
				// enable 2 button |< & <
				m_List_Button_Page[0].setVisibility(View.VISIBLE);
				m_List_Button_Page[1].setVisibility(View.VISIBLE);
				m_List_Button_Page[5].setVisibility(View.VISIBLE);
				m_List_Button_Page[6].setVisibility(View.VISIBLE);

				// set text 3 page 1 2 3
				m_List_Button_Page[2].setText(currentPage + "");
				m_List_Button_Page[3].setText((currentPage + 1) + "");
				m_List_Button_Page[4].setText((currentPage + 2) + "");

				// set color
				index_focus = 3;
				setColor(index_focus);
				page = currentPage + 1;
			}
			// tr�?ng h?p current page = totalPage
			if (currentPage == totalPage) {
				// disable 2 button > & >|
				m_List_Button_Page[0].setVisibility(View.GONE);
				m_List_Button_Page[1].setVisibility(View.GONE);
				m_List_Button_Page[5].setVisibility(View.VISIBLE);
				m_List_Button_Page[6].setVisibility(View.VISIBLE);

				// set text 3 page 1 2 3
				m_List_Button_Page[2].setText((currentPage - 2) + "");
				m_List_Button_Page[3].setText((currentPage - 1) + "");
				m_List_Button_Page[4].setText(currentPage + "");

				// set color
				index_focus = 3;
				setColor(index_focus);

				page = currentPage - 1;
			}
			break;
		case 4: // Page '3'
			if (currentPage == 1) {
				// enable 2 button |< & <
				m_List_Button_Page[0].setVisibility(View.VISIBLE);
				m_List_Button_Page[1].setVisibility(View.VISIBLE);

				// set text 3 page 1 2 3
				m_List_Button_Page[2].setText((currentPage + 1) + "");
				m_List_Button_Page[3].setText((currentPage + 2) + "");
				m_List_Button_Page[4].setText((currentPage + 3) + "");

				// set color
				index_focus = 3;
				setColor(index_focus);
				page = currentPage + 2;
			} else {
				if (currentPage == totalPage - 1) {
					// disable 2 button > & >|
					m_List_Button_Page[5].setVisibility(View.GONE);
					m_List_Button_Page[6].setVisibility(View.GONE);

					// set text 3 page 1 2 3
					m_List_Button_Page[2].setText((currentPage - 1) + "");
					m_List_Button_Page[3].setText((currentPage) + "");
					m_List_Button_Page[4].setText((currentPage + 1) + "");
					// set color
					index_focus = 4;
					setColor(index_focus);
				} else {
					// set text 3 page 1 2 3
					m_List_Button_Page[2].setText(currentPage + "");
					m_List_Button_Page[3].setText((currentPage + 1) + "");
					m_List_Button_Page[4].setText((currentPage + 2) + "");
					// set color
					index_focus = 3;
					setColor(index_focus);
				}
				page = currentPage + 1;
			}
			break;
		case 5: // Page '>'
			// set text 3 page 1 2 3
			m_List_Button_Page[2].setText(currentPage + "");
			m_List_Button_Page[3].setText((currentPage + 1) + "");
			m_List_Button_Page[4].setText((currentPage + 2) + "");

			// set color
			index_focus = 3;
			setColor(index_focus);

			page = currentPage + 1;
			break;
		case 6: // Page '>|'
			// enable 2 button |< & <
			m_List_Button_Page[0].setVisibility(View.VISIBLE);
			m_List_Button_Page[1].setVisibility(View.VISIBLE);
			// disable 2 button > & >|
			m_List_Button_Page[5].setVisibility(View.GONE);
			m_List_Button_Page[6].setVisibility(View.GONE);

			// set text 3 page 1 2 3
			m_List_Button_Page[2].setText((totalPage - 2) + "");
			m_List_Button_Page[3].setText((totalPage - 1) + "");
			m_List_Button_Page[4].setText(totalPage + "");

			// set color
			index_focus = 4;
			setColor(index_focus);

			// trang �?u ti�n
			page = totalPage;

			break;
		default:
			break;
		}

		return page;
	}

	// TODO update listview with paging
	private void updateListView(int page) {
		if (!ListAlbumActivity.instance.isExcuting) {
			// l?y d? li?u t? Page
			ServiceSMS.instance.currentPage = page;
			// ServiceSMS.instance.currentPage++;
			ServiceSMS.instance.getAlbum_Search("");
			ListAlbumActivity.instance.updateListView();
		} else {
			Toast.instance.show(c, "�ang l?y d? li?u Album .... ");
		}
	}

}
