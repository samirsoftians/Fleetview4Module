package com.example.fleetviewandroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	ListView searchList;
	private ArrayList<String> listVehicles;
	EditText edtSearch;
	ArrayAdapter<String> adapter;
	int color;
	View lastHighlightItem = null;
	ArrayList<String> selectedList = null;
	int i = 0;
	Button btnGo;
	SharedPreferences pref;
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
	ArrayList<Integer> vCode = null;

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(getApplicationContext(), "Inside Broadcast",
					Toast.LENGTH_SHORT).show();
			PendingIntent service = null;
			Intent intentForService = new Intent(
					context.getApplicationContext(), MyService.class);
			final AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			final Calendar time = Calendar.getInstance();
			time.set(Calendar.MINUTE, 0);
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			if (service == null) {
				service = PendingIntent.getService(context, 0,
						intentForService, PendingIntent.FLAG_CANCEL_CURRENT);
			}
			
			alarmManager.setRepeating(AlarmManager.RTC, time.getTime()
					.getTime(), 600000, service);
		}
	};
	IntentFilter filter = new IntentFilter("action.broadcast");

	// Button b1,b2,b3,b4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlay);
		selectedList = new ArrayList<String>();
		listVehicles = new ArrayList<String>();
		vCode = new ArrayList<Integer>();
		
		getList();
		// getCode();
		init();
		// addLayoutMapView();
		getWindow().setBackgroundDrawableResource(R.drawable.new_background);
		// listVehicles = getIntent().getStringArrayListExtra("list");

		searchList = (ListView) findViewById(R.id.list_view);
		edtSearch = (EditText) findViewById(R.id.inputSearch);
		btnGo = (Button) findViewById(R.id.sendList);

		adapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.vehicle_name, listVehicles);
		searchList.setAdapter(adapter);

		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				SearchActivity.this.adapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		searchList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				// String item = ((TextView)view).getText().toString();

				// view.setFocusable(true);
				// view.setAlpha(1);

				if (view.isEnabled() == false) { // isEnable() = isActivated()
													// for higher versions of
													// APK
					((TextView) view).setTextColor(Color.BLACK);
					Toast.makeText(SearchActivity.this, "Removed Selection",
							Toast.LENGTH_SHORT).show();
					String item = ((TextView) view).getText().toString();
					selectedList.remove(item);
					view.setEnabled(true);
					i--;
				} else {
					if (i < 5) {
						((TextView) view).setTextColor(Color.RED);
						view.setEnabled(false);
						// Toast.makeText(SearchActivity.this, "Selected",
						// Toast.LENGTH_SHORT).show();
						String item = ((TextView) view).getText().toString();
						selectedList.add(item);
						i++;
					} else {
						Toast.makeText(SearchActivity.this,
								"You can not select more than five item",
								Toast.LENGTH_LONG).show();
					}
				}
				// view.setVisibility(70);

				/*
				 * Drawable background = view.getBackground(); if (background
				 * instanceof ColorDrawable) color = ((ColorDrawable)
				 * background).getColor();
				 * 
				 * if(color == Color.RED){ view.setBackgroundColor(Color.WHITE);
				 * }
				 */

				// Toast.makeText(SearchActivity.this,
				// listVehicles.get(position), Toast.LENGTH_SHORT).show();
				// view.setBackgroundColor(Color.RED);
			}

		});

		btnGo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (i == 0) {

					// SharedPreferences sp = getSharedPreferences("vList",
					// Activity.MODE_PRIVATE);
					// Set<String> set2 = sp.getStringSet("list", null);
					// ArrayList<String> vlist = new ArrayList<String>(set2);

					Intent intent = new Intent(SearchActivity.this,
							VehicleInfoActivity.class);
					// intent.putExtra("list", vlist);
					startActivity(intent);
					finish();
					//registerReceiver(receiver, filter);
					/*
					 * MyDBHelper helper = new MyDBHelper(SearchActivity.this);
					 * SQLiteDatabase data = helper.getReadableDatabase();
					 * 
					 * Cursor curs = data.query("onlineInfo", null, null, null,
					 * null, null, null); curs.move(0); while(curs.moveToNext())
					 * { mt(curs.getString(1) + " " + curs.getString(2) + " "
					 * +curs.getString(7)); } curs.close(); data.close();
					 */
				} else {
					// Set<String> set = new HashSet<String>(selectedList);
					pref = getSharedPreferences("vList", Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = pref.edit();
					editor.putInt("count", selectedList.size());
					for (int i = 0; i < selectedList.size(); i++) {
						editor.putString("vehicle" + i, selectedList.get(i));
					}
					//registerReceiver(receiver, filter);
					// editor.putStringSet("list", set);
					editor.commit();
					//stopService(new Intent(SearchActivity.this,NewService.class));							

					registerReceiver(receiver, filter);
					Intent intent = new Intent("action.broadcast");
					sendBroadcast(intent);

					Intent intent1 = new Intent(SearchActivity.this,
							VehicleInfoActivity.class);

					startActivity(intent1);
					finish();
				}

			}
		});

	}

	private void init() {

		font = Typeface.createFromAsset(getAssets(), "GOTHIC.TTF");
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		screenHeight = metrics.heightPixels;
		screenWidth = metrics.widthPixels;
	}

	private void mt(String text) {
		Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	private void getList() {
		SQLiteDatabase db3;
		MyDBHelper helper = new MyDBHelper(SearchActivity.this);

		db3 = helper.getReadableDatabase();

		Cursor c = db3.query("vehicleInfo", null, null, null, null, null, null);
		c.move(0);
		while (c.moveToNext()) {
			// mt(c.getString(1));
			listVehicles.add(c.getString(1));
		}
		c.close();
		db3.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item;
		item = menu.add(0, 1, 1, "Login");
		item.setIcon(R.drawable.ic_launcher);
		item = menu.add(0, 2, 2, "Exit");
		item.setIcon(R.drawable.ic_launcher);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			startActivity(new Intent(SearchActivity.this, LoginAct.class));
		} else if (item.getItemId() == 2) {
			System.exit(0);
		}

		return true;

	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);

	}
}
/*
 * private void getCode() { int x = listVehicles.size(); arr = new String[x];
 * for (int i = 0; i < x; i++) { arr[i] = listVehicles.get(i); }
 * 
 * dbHelper = new MyDBHelper(SearchActivity.this); db =
 * dbHelper.getReadableDatabase(); for (int i = 0; i < arr.length; i++) {
 * String[] selectionArgs = { arr[i] }; Cursor c = db.query("vehicleInfo", null,
 * "vehicleRegNum = ?", selectionArgs, null, null, null); c.move(0); while
 * (c.moveToNext()) { // mt(c.getString(1)); vCode.add(c.getInt(0)); } }
 * db.close(); }
 */
/*
 * private void addButtonToTheMapViewOptions(Button b, LayoutParams blp, String
 * text) { b.setLayoutParams(blp); b.setTextSize(13f); b.setText(text);
 * b.setTextColor(getResources().getColor(R.color.White));
 * b.setBackgroundResource(R.drawable.border); b.setTypeface(font);
 * b.setOnClickListener(new MapViewButtonListener());
 * layoutMapViewButtons.addView(b); }
 * 
 * class MapViewButtonListener implements View.OnClickListener {
 * 
 * @Override public void onClick(View b) {
 * 
 * Button btnMapView = (Button) b; if
 * (btnMapView.getText().toString().equals("Update")) { mt("update");
 * startActivity(new Intent(SearchActivity.this, VehicleInfoActivity.class));
 * 
 * } else if (btnMapView.getText().toString().equals("Detail")) {
 * mt("deatails");
 * 
 * } else if (btnMapView.getText().toString().equals("Reg.")) {
 * mt("registration");
 * 
 * startActivity(new Intent(SearchActivity.this, LoginAct.class)); } else if
 * (btnMapView.getText().toString().equals("Search")) { mt("Search"); }
 * 
 * ViewGroup parent = (ViewGroup) b.getParent().getParent() .getParent();
 * parent.removeView(layoutMapView); imgBtnMapView.setEnabled(true);
 * 
 * } }
 */