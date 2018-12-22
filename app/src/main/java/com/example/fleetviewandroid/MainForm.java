package com.example.fleetviewandroid;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainForm extends Activity implements OnClickListener
{
	//String url="http://192.168.2.239:8181/FleetViewProjectTest/MyServlet";
	String url="http://twtech.in:8080/FleetViewProject/MyServlet";

	String user,passwd;
	ListView listView;
	int activity=1;
	String[] list={"Add Request","Add Odometer","View Violation Report","L1 Generate Report"};
	TextView  Request,Odometer,viewreport,l1generate,currentpos;
	String getusername;
	ArrayList<String> array=new ArrayList<String>();
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainform_new);
		Request=(TextView)findViewById(R.id.img1_vehicle_intimation);
		Odometer=(TextView)findViewById(R.id.img2_odometer_reading);
		viewreport=(TextView)findViewById(R.id.img3_alerts);
		l1generate=(TextView)findViewById(R.id.img4_violation);
		//currentpos=(TextView) findViewById(R.id.img6);
	    
		  Typeface face = Typeface.createFromAsset(getAssets(),
		             "arial.ttf");
		 /*Request.setTypeface(face);
		 Odometer.setTypeface(face);
		 viewreport.setTypeface(face);
		 l1generate.setTypeface(face);*/
		// fuel.setTypeface(face);
		 
		Intent ins=getIntent();
		user=ins.getStringExtra("Username");
		passwd=ins.getStringExtra("password");

		TextView textView= (TextView) findViewById(R.id.loginname);
		textView.setText(""+LoginForm.name);

//		array=ins.getExtras().getStringArrayList("VehicleNo");
/*
		//Insert the data into  shared prefernces
		SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("key",array.size());
		for(int i=0;i<=array.size();i++)
		{

				editor.remove("keyvalue"+i);
				editor.putString("keyvalue",array.get(1));

		}
		editor.commit(); //commit changes

		//Getting the data from the sharedprefernces
		array.clear();
		int size = sharedPreferences.getInt("key", 0);
		for(int i=0;i<size;i++)
		{
			varray.add(sharedPreferences.getString("keyvalue" + i, null));

		}*/
		Request.setOnClickListener(this);
		Odometer.setOnClickListener(this);
		viewreport.setOnClickListener(this);
		l1generate.setOnClickListener(this);
		//currentpos.setOnClickListener(this);
//		System.out.println("edtUsername"+user+" "+"edtPassword"+passwd+array.size());

	}
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.img1_vehicle_intimation:
			Intent i=new Intent(getApplicationContext(),AddRequest.class);
			i.putExtra("name",LoginForm.name);
			finish();
//			i.putExtra("vehlist",LoginForm.arrayList);
			startActivity(i);
			break;
		case R.id.img2_odometer_reading:
			Intent in=new Intent(getApplicationContext(),OdoMeter.class);
			in.putExtra("name",LoginForm.name);
			finish();
//			in.putExtra("vehlist", array);
			startActivity(in);
			break;
		case R.id.img3_alerts:
			Intent ins=new Intent(getApplicationContext(),Alerts.class);
//			ins.putExtra("name",LoginForm.name);
			ins.putExtra("Alerts", "Alerts");
			startActivity(ins);
			break;
		case R.id.img4_violation:
			//Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(getApplicationContext(),Violation.class);
//			intent.putExtra("name",LoginForm.name);
			intent.putExtra("violations", "Violations");
			startActivity(intent);
			break;
	 	/*case R.id.img5:
			Intent intent1=new Intent(getApplicationContext(),FuelEntry.class);
			intent1.putExtra("name", LoginForm.name);
			intent1.putExtra("Fuel", "fuel");
			intent1.putExtra("vehlist",LoginForm.arrayList);
			System.out.println("--===in fuelintent");
			startActivity(intent1);
			break;			
		*/
	/*case R.id.img6:
		
		//Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
		Intent intent2=new Intent(getApplicationContext(),LoginAct.class);
		intent2.putExtra("name", user);
		intent2.putExtra("passwd", passwd);
		System.out.println("--===in pos");
		startActivity(intent2);
		break;	
	*/
	}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuItem menuItem;

		menuItem=menu.add(0,1,1,"LOG OUT");

		return super.onCreateOptionsMenu(menu);


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if(item.getItemId()==1)
		{

			Intent intent=new Intent(getApplicationContext(),LoginForm.class);
			LoginForm.arrayList.clear();
			finish();
			startActivity(intent);

		}


		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed()
	{
		
	       /* SharedPreferences sharedpreferences=PreferenceManager.getDefaultSharedPreferences(this);
	        SharedPreferences.Editor editor;
			editor = sharedpreferences.edit();
			editor.putString("user",getusername);
			*/
		
		Intent i=new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	
		
	}
@Override
   public void onResume(){
       super.onResume();

     /*  SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); 
       SharedPreferences.Editor editor;
        getusername= sharedpreferences.getString("user", null);*/
	}

}