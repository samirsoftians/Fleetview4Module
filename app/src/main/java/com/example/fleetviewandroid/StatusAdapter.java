package com.example.fleetviewandroid;

import java.util.ArrayList;



import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatusAdapter extends BaseAdapter {
	Activity context;
	ArrayList<String> vehiclesName;
	ArrayList<String> status;
	ArrayList<Integer> speed;
	LayoutInflater inflater;

	public StatusAdapter(Activity context, ArrayList<String> vehiclesName,
			ArrayList<String> status, ArrayList<Integer> speed) {
		this.context = context;
		this.vehiclesName = vehiclesName;
		this.status = status;
		this.speed = speed;
		inflater = context.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vehiclesName.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		int x = 0;
		TextView txt;
		TextView text;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.status_custom, null);
		}
		txt = (TextView) convertView.findViewById(R.id.text);
		text = (TextView) convertView.findViewById(R.id.statusText);
		txt.setText(vehiclesName.get(position));
		text.setTextColor(Color.BLUE);
		if (status.get(position).trim().equals("-")) {
			text.setText("Active");
			// convertView.setBackgroundColor(Color.CYAN);
		} else if (status.get(position).trim().equals("DeActivated")) {
			text.setText("DeActivated");
			// convertView.setBackgroundColor(context.getResources().getColor(R.color.LightGoldenrodYellow));
			convertView.setBackgroundColor(Color.rgb(220, 220, 220));
			x = 1;
		} else if (status.get(position).trim().equals("Removed")) {
			text.setText(status.get(position));
			convertView.setBackgroundColor(Color.rgb(189, 237, 255));
			// convertView.setBackgroundColor(context.getResources().getColor(R.color.LightGoldenrodYellow));
			x = 1;
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.LightGoldenrodYellow));
			x = 1;
		}

		switch (speed.get(position)) {
		case 0:
			if (x == 0) {
				// convertView.setBackgroundColor(Color.LTGRAY);
				convertView.setBackgroundColor(Color.rgb(255, 255, 204));
			}
			break;

		default:
			if (x == 0) {
				// convertView.setBackgroundColor(context.getResources().getColor(R.color.LawnGreen));
				convertView.setBackgroundColor(Color.rgb(204, 255, 204));
			}
			break;
		}
		return convertView;
	}
}
