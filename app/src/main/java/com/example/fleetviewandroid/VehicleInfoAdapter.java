package com.example.fleetviewandroid;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VehicleInfoAdapter extends BaseAdapter
{

	Activity context;
	ArrayList<String> vehicleList;
	LayoutInflater inflater;
	ArrayList<String> date;
	ArrayList<String> time;
	ArrayList<String> location;
	ArrayList<String> lat;
	ArrayList<String> lng;
	ArrayList<Integer> statusArray;
	int size = 0; 
	public VehicleInfoAdapter(Activity context, ArrayList<String> vehicleList,
			ArrayList<String> date, ArrayList<String> time,
			ArrayList<String> location, ArrayList<String> lat,
			ArrayList<String> lng, ArrayList<Integer> statusArray) {
		this.context = context;
		this.vehicleList = vehicleList;
		inflater = context.getLayoutInflater();
		this.date = date;
		this.time = time;
		this.lat = lat;
		this.lng = lng;
		this.location = location;
		this.statusArray = statusArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vehicleList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		size = date.size();
		ImageView img;
		TextView txtName;
		TextView txtDate;
		TextView txtTime;
		TextView txtLocation;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.vehicle_info_custom, null);
		}
		img = (ImageView) convertView.findViewById(R.id.google);
		txtName = (TextView) convertView.findViewById(R.id.vName);
		txtDate = (TextView) convertView.findViewById(R.id.vDate);
		txtTime = (TextView) convertView.findViewById(R.id.vTime);
		txtLocation = (TextView) convertView.findViewById(R.id.vLocation);
		// if (date.size() == 0) {
		// for (int i = 0; i < vehicleList.size(); i++) {
		// date.add("date");
		// time.add("time");
		// location.add("loaction");
		// }
		// }
		txtName.setText(vehicleList.get(position));
		txtDate.setText("Date: " + date.get(position));
		txtTime.setText("Time: " + time.get(position));
		txtLocation.setText(location.get(position));

		switch (statusArray.get(position)) {
		case 0:
			// convertView.setBackgroundColor(Color.WHITE);
			convertView.setBackgroundColor(Color.rgb(255, 255, 204));
			break;

		default:
			// convertView.setBackgroundColor(context.getResources().getColor(R.color.LawnGreen));
			convertView.setBackgroundColor(Color.rgb(204, 255, 204));
			break;
		}

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (date.get(0) == null) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setIcon(R.drawable.transworldlogo1);
					builder.setTitle("Current Position App");
					builder.setMessage("Please click REFRESH button to ope google map.");
					builder.setCancelable(false);
					DialogInterface.OnClickListener detailsListener = new DetailsDialog();
					builder.setPositiveButton("Yes", detailsListener);
					builder.setNeutralButton("Cancel", detailsListener);
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					Intent intent = new Intent(context, NewGoogle.class);
					intent.putExtra("lat", lat.get(position));
					intent.putExtra("lng", lng.get(position));
					intent.putExtra("date", date.get(position));
					intent.putExtra("time", time.get(position));
					intent.putExtra("vname", vehicleList.get(position));
					context.startActivity(intent);
				}
			}
		});
		return convertView;
	}

	class DetailsDialog implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if (which == DialogInterface.BUTTON_POSITIVE) {
				dialog.dismiss();
			}
			if (which == DialogInterface.BUTTON_NEUTRAL) {
				dialog.dismiss();
			}
		}
	}
}
