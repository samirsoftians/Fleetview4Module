package com.example.fleetviewandroid;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class StatusActivity extends Activity {
	ListView list;
	ArrayList<String> statusList;
	MyDBHelper helper;
	ArrayList<String> status;
	ArrayList<Integer> speed;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_lay);
		statusList = new ArrayList<String>();
		status = new ArrayList<String>();
		speed = new ArrayList<Integer>();
		helper = new MyDBHelper(StatusActivity.this);
		list = (ListView) findViewById(R.id.list);
		getList();		
		StatusAdapter adapter = new StatusAdapter(this, statusList,status,speed);
		list.setAdapter(adapter);		
		list.setDividerHeight(5);		
	}

	private void getList() {
		SQLiteDatabase data1;

		data1 = helper.getReadableDatabase();
		Cursor c = data1
				.query("onlineInfo", null, null, null, null, null, null);
		c.move(0);
		while (c.moveToNext())
		{
			// mt(c.getString(1));
			statusList.add(c.getString(1));
			status.add(c.getString(7));
			speed.add(c.getInt(8));

		}
		c.close();
		data1.close();
	}
}
