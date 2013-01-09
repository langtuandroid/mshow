package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class BlankActivity extends Activity {
	// Button btsearch;
	// Button btmnsearch;
	// EditText edsearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.blank_activity);

		Thread thread = new Thread();
		try {
			thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = new Intent(BlankActivity.this, ListCatActivity.class);
		startActivity(intent);
		finish();
		// initListView();
	}

	// private void initListView() {
	// // TODO Auto-generated method stub
	// btsearch = (Button) findViewById(R.id.blank_btsearch);
	// edsearch = (EditText) findViewById(R.id.blank_editsearch);
	//
	// btmnsearch = (Button) findViewById(R.id.blank_btmnsearch);
	// }
}
