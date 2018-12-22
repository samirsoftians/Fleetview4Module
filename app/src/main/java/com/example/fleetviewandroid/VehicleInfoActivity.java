package com.example.fleetviewandroid;

import java.util.ArrayList;
import java.util.Set;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VehicleInfoActivity extends Activity {

	MyDBHelper dbHelper;
	SQLiteDatabase db;
	ListView infoList;
	TextView txtApp;
	ImageButton imgBtn;
	DisplayMetrics metrics;
	private LinearLayout layoutMapView;
	private LinearLayout.LayoutParams llp;
	private LinearLayout.LayoutParams blp;
	private LinearLayout layoutMapViewButtons;
	private LinearLayout.LayoutParams llpButtons;
	int screenHeight;
	int screenWidth;
	Typeface font;
	private ImageButton imgBtnMapView;
	Button btnInfo, btnUpdate;
	Button btnSearch, btnRegister;
	String[] arr = null;
	ArrayList<String> vehicles;
	ArrayList<String> date;
	ArrayList<String> time;
	ArrayList<String> location;
	ArrayList<String> lat;
	ArrayList<String> lng;
	ArrayList<Integer> statusArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_info);
		// sample();
		initArray();
		init();
		start();
		addLayoutMapView();

	}

	private void sample() {
		MyDBHelper help = new MyDBHelper(VehicleInfoActivity.this);
		SQLiteDatabase data;
		data = help.getReadableDatabase();
		Cursor cursor = data.query("onlineInfo", null, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			mt(cursor.getString(2) + "" + cursor.getString(3));
		}

	}

	private void initArray() {
		vehicles = new ArrayList<String>();
		date = new ArrayList<String>();
		time = new ArrayList<String>();
		location = new ArrayList<String>();
		lat = new ArrayList<String>();
		lng = new ArrayList<String>();
		statusArray = new ArrayList<Integer>();
	}

	private void start() {
		SharedPreferences pref = getSharedPreferences("vList",
				Activity.MODE_PRIVATE);
		// Set<String> set = pref.getStringSet("list", null);
		// vehicles = new ArrayList<String>();
		int count = pref.getInt("count", -1);
		// mt(count+"");
		if (count <= 0) {
			mt("Please Select Vehicles...");
			startActivity(new Intent(VehicleInfoActivity.this,
					SearchActivity.class));
		}
		for (int i = 0; i < count; i++) {
			vehicles.add(pref.getString("vehicle" + i, null));
			// mt(pref.getString("vehicle"+i, null));

		}
		// mt(vehicles.size()+"");
		// SQLiteDatabase db;
		// MyDBHelper helper = new MyDBHelper(VehicleInfoActivity.this);
		dbHelper = new MyDBHelper(VehicleInfoActivity.this);
		db = dbHelper.getReadableDatabase();		
		for (int i = 0; i < vehicles.size(); i++) {
			String[] selectionArgs = { vehicles.get(i) };
			Cursor cursor = db.query("onlineInfo", null, "vNum=?", selectionArgs,
					null, null, null);
			// mt("tushar");
			// mt("name" +cursor.getString(1));
			cursor.move(0);
			while (cursor.moveToNext()) {
				date.add(cursor.getString(2));
				time.add(cursor.getString(3));
				location.add(cursor.getString(6));
				lat.add(cursor.getString(4));
				lng.add(cursor.getString(5));
				statusArray.add(cursor.getInt(8));
				cursor.moveToFirst();
				// mt(cursor.getString(7) + " " +cursor.getString(8));
			}
			//cursor.close();
		}
		db.close();
		// db.close();
		// mt(date.get(2));
		VehicleInfoAdapter adapter = new VehicleInfoAdapter(this, vehicles,
				date, time, location, lat, lng,statusArray);
		infoList.setAdapter(adapter);
	}

	private void init() {
		infoList = (ListView) findViewById(R.id.infoList);
		// txtApp = (TextView) findViewById(R.id.txtApp);
		// imgBtn = (ImageButton) findViewById(R.id.imgBtn);

		font = Typeface.createFromAsset(getAssets(), "GOTHIC.TTF");
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		screenHeight = metrics.heightPixels;
		screenWidth = metrics.widthPixels;
		infoList.setDividerHeight(4);

		// getWindow().setBackgroundDrawableResource(R.drawable.new_background);

	}

	private void mt(String text) {
		Toast.makeText(VehicleInfoActivity.this, text, Toast.LENGTH_SHORT)
				.show();
	}

	private void addLayoutMapView() {

		llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		layoutMapView = (LinearLayout) findViewById(R.id.yyy);
		// layoutMapView.setLayoutParams(llp);
		// layoutMapView.setBackgroundColor(Color.BLACK);
		// layoutMapView.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		blp = new LinearLayout.LayoutParams(screenWidth / 4,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT);

		// blp.weight = 0
		// blp.
		blp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

		llpButtons = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				(screenHeight / 12 - 8));
		llpButtons.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

		layoutMapViewButtons = (LinearLayout) findViewById(R.id.layoutAppBar1);
		layoutMapViewButtons.setLayoutParams(llpButtons);
		layoutMapViewButtons.setBackgroundColor(getResources().getColor(
				R.color.AntiqueWhite));
		layoutMapViewButtons.getBackground().setAlpha(80);
		// layoutMapView.addView(layoutMapViewButtons);

		btnUpdate = new Button(this, null, android.R.attr.buttonStyle);
		addButtonToTheMapViewOptions(btnUpdate, blp, "Refresh");

		btnInfo = new Button(this, null, android.R.attr.buttonStyle);
		addButtonToTheMapViewOptions(btnInfo, blp, "Status");

		btnRegister = new Button(this, null, android.R.attr.buttonStyle);
		addButtonToTheMapViewOptions(btnRegister, blp, "Reg.");

		btnSearch = new Button(this, null, android.R.attr.buttonStyle);
		addButtonToTheMapViewOptions(btnSearch, blp, "Search");

		layoutMapView.getBackground().setAlpha(90);
		layoutMapView.removeView(layoutMapViewButtons);

		VehicleInfoActivity.this.addContentView(layoutMapViewButtons, llp);

	}

	private void addButtonToTheMapViewOptions(Button b, LayoutParams blp,
			String text) {
		b.setLayoutParams(blp);
		b.setTextSize(12f);
		b.setText(text);
		b.setTextColor(getResources().getColor(R.color.White));
		b.setBackgroundResource(R.drawable.border);
		b.setTypeface(font);
		b.setOnClickListener(new MapViewButtonListener());
		layoutMapViewButtons.addView(b);
	}

	class MapViewButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View b) {

			Button btnMapView = (Button) b;
			if (btnMapView.getText().toString().equals("Refresh")) {
				// mt("update");
				finish();
				startActivity(new Intent(VehicleInfoActivity.this,
						VehicleInfoActivity.class));

			} else if (btnMapView.getText().toString().equals("Status")) {
				//mt("details");
				startActivity(new Intent(VehicleInfoActivity.this,StatusActivity.class));

			} else if (btnMapView.getText().toString().equals("Reg.")) {
				mt("registration");
				startActivity(new Intent(VehicleInfoActivity.this,
						LoginAct.class));

			} else if (btnMapView.getText().toString().equals("Search")) {
				mt("Search");
				startActivity(new Intent(VehicleInfoActivity.this,
						SearchActivity.class));
				finish();
			}
			/*
			 * ViewGroup parent = (ViewGroup) b.getParent().getParent()
			 * .getParent(); parent.removeView(layoutMapView);
			 * imgBtnMapView.setEnabled(true);
			 */
		}
	}	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//stopService(new Intent(VehicleInfoActivity.this,NewService.class));
		//stopService(new Intent(VehicleInfoActivity.this,MyService.class));		
	}
}
