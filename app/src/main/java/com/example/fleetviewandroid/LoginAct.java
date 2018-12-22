package com.example.fleetviewandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginAct extends Activity
{
	String url="http://twtech.in:8080/FleetViewProject/MyServlet";
	LoginUser loginThread;
	//TextView txtUsername, txtPassword;
	String edtUsername, edtPassword;
	//Button btnLogin, btnOfflineData;
	
	String name = "";
	String name2 = "";
	String status = "";
	SQLiteDatabase db;
	SQLiteDatabase db3;
	SQLiteDatabase db1;
	SQLiteDatabase db5;
	//SQLiteDatabase db4;
	int k = 0;
	SharedPreferences pref;
	ArrayList<String> names = null;
	ArrayList<String> code = null;
	String str = "Active";
	MyDBHelper helper ;
	// boolean bool;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//ssetContentView(R.layout.activity_main);

		setContentView(R.layout.lognact);

		Intent ins=getIntent();
		edtUsername=ins.getStringExtra("name");
		edtPassword=ins.getStringExtra("passwd");
		System.out.println("edtUsername"+edtUsername+" "+"edtPassword"+edtPassword);
		 helper = new MyDBHelper(LoginAct.this);
		db5 = helper.getWritableDatabase();
		db1 = helper.getWritableDatabase();
		
		stopService(new Intent(LoginAct.this, NewService.class));
		stopService(new Intent(LoginAct.this, MyService.class));
		boolean cancel = false;
		// View focusView = null;

		if (cancel) {
			// There was an error; don't attempt login and focus the
			// first
			// form field with an error.
			// focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task
			// to
			// perform the user login attempt.
			boolean bool = isOnline();
			if (bool == true) {
				// mDataLodingStatusMessageView.setText(R.string.data_fetching_progress_in);
				// showProgress(true);

				MyDBHelper helper = new MyDBHelper(LoginAct.this);
				db = helper.getWritableDatabase();
				db3 = helper.getWritableDatabase();
				db.delete("vehicleInfo", null, null);
				db3.delete("onlineInfo", null, null);

				// mt("bool is true");
				loginThread = new LoginUser();
				loginThread.execute((Void) null);
				db.close();
				db3.close();
			} else {
				Toast.makeText(getApplicationContext(),
						"No Internet connection.", Toast.LENGTH_LONG)
						.show();
			}
		}
	}


	public boolean isOnline()
	{

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		boolean result = false;
		if (ni != null)
		{
			if (ni.getState() == NetworkInfo.State.CONNECTED)
			{

					result = true;
			}
		}

		return result;

	}

/*	private void init() {
		names = new ArrayList<String>();
		code = new ArrayList<String>();
		txtUsername = (TextView) findViewById(R.id.txtUsername);
		txtPassword = (TextView) findViewById(R.id.txtPassword);
		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnOfflineData = (Button) findViewById(R.id.btnUpdateData);

		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				
				stopService(new Intent(LoginAct.this, NewService.class));
				stopService(new Intent(LoginAct.this, MyService.class));
				boolean cancel = false;
				// View focusView = null;

				if (cancel) {
					// There was an error; don't attempt login and focus the
					// first
					// form field with an error.
					// focusView.requestFocus();
				} else {
					// Show a progress spinner, and kick off a background task
					// to
					// perform the user login attempt.
					boolean bool = isOnline();
					if (bool == true) {
						// mDataLodingStatusMessageView.setText(R.string.data_fetching_progress_in);
						// showProgress(true);

						MyDBHelper helper = new MyDBHelper(LoginAct.this);
						db = helper.getWritableDatabase();
						db3 = helper.getWritableDatabase();
						db.delete("vehicleInfo", null, null);
						db3.delete("onlineInfo", null, null);

						// mt("bool is true");
						loginThread = new LoginUser();
						loginThread.execute((Void) null);
						db.close();
						db3.close();
					} else {
						Toast.makeText(getApplicationContext(),
								"No Internet connection.", Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		});

		btnOfflineData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginAct.this,
						VehicleInfoActivity.class));
				finish();
			}
		});
	}*/

	public class LoginUser extends AsyncTask<Void, String, Boolean>
	{

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(LoginAct.this);
			progressDialog.setTitle("Fleetview App");
			progressDialog.setMessage("Accessing data....");
			progressDialog.setIcon(R.drawable.transworldlogo1);
			Log.i("FLEET","PRE EXECUTE");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{

			String nameOfUser = "";
			//103.241.181.36
			try { // Simulate network access.
				
				String url = "http://103.241.181.36:8080/AndrFleetApp/LoginServlet?username="
						+ edtUsername
						+ "&password="
						+ edtPassword;

				Log.i("FLEET","user :"+edtUsername+" Password :"+edtPassword);
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);

				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null)
				{
					nameOfUser += s;

					Log.i("FLEET","FETCHING RESULT FROM THE Login Servlet");
				}
				// publishProgress(nameOfUser);
				System.out.println("The response =>" + nameOfUser);

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// return false;
			}

			catch (Exception e)
			{
				System.out.println("Exception occured!!" + e);
				// return false;
			}
			if (!(nameOfUser.equals("Not_OK")) && nameOfUser != null) {
				// update the database
				// addEvent(nameOfUser,device_id);
				// mt("username is :" +nameOfUser);
				String response = "";
				try {

					String url = "http://103.241.181.36:8080/AndrFleetApp/CurrentPosition?typevalue="
							+ nameOfUser;
					url = url.replaceAll(" ", "%20");
					DefaultHttpClient client = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);

					Log.i("FLEET","FETCH THE RESULT FORM current Position page");

					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));

					String s = "";
					while ((s = buffer.readLine()) != null)
					{
						response += s;
					}
					System.out.println("The response =>" + response);

					Thread.sleep(2000);
				} catch (InterruptedException e) {
					return false;
				} catch (Exception e) {
					System.out.println("Exception occured!!" + e);
					return false;
				}

				if (!(response.equals("No_Data"))) {

					try {
						int count = 0;
						String[] str = response.split(" ## ");
						while (count < str.length) {
							String line = str[count];
							String[] rows = line.split("\\$");

							publishProgress(rows);

							count++;

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

			// TODO: register the new account here. //return true;

		}

		@Override
		protected void onProgressUpdate(String... arg) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(arg);
			// name = values[0];
			name = arg[0];
			name2 = arg[1];
			status = arg[2];

			Log.i("FLEET","PUT THE DATA INTO LOCAL DB");
			ContentValues values = new ContentValues();
			values.put("vehicleCode", name);
			values.put("vehicleRegNum", name2);
			values.put("active", "YES");

			Log.i("FLEET","FETCHING RESULT "+name+" name2 "+name2);

			ContentValues values1 = new ContentValues();
			values1.put("vehicleCode", name);
			values1.put("vNum", name2);
			values1.put("date", "date");
			values1.put("time", "time");
			values1.put("lat", "54.5441");
			values1.put("lng", "78.5484");
			values1.put("location", "location");
			values1.put("speed", "0");
			values1.put("status", status);

			Log.i("FLEET","FETCHING "+status);
		
			db5.insert("vehicleInfo", null, values);
			db1.insert("onlineInfo", null, values1);
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			loginThread = null;
			// db.close();
			// db1.close();
			// showProgress(false);
			progressDialog.dismiss();
			if (success) {
				// Go to the Dash board
				mt("Login successful");
				// mt("username is: " + name);
				k = 1;
				pref = getSharedPreferences("myPref", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putLong("date", System.currentTimeMillis());
				editor.commit();
			} else {
				k = 0;
				// mt("username is: " + name);

				mt("Wrong username or password");
			}
			progressDialog.dismiss();
			if (k == 1) {
				db1.close();
				db5.close();
				//db4.close();
				startService(new Intent(LoginAct.this, NewService.class));
				startActivity(new Intent(LoginAct.this, SearchActivity.class));
				finish();
				// mt(code.get(2) + " " + names.get(2));
			} else {
				mt("Please enter correct username and password!!");
			}
		}
	}

	private void mt(String text) {
		Toast.makeText(LoginAct.this, text, Toast.LENGTH_LONG).show();
	}
}
