package com.example.network;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class networkclass
{
	//String url="http://192.168.2.239:8181/FleetViewProjectTest/MyServlet";
    String url="http://twtech.in:8080/FleetViewProject/MyServlet";

	public void insertmenu(String vecode,String fdate,String tdate,String vehcom,String entrby)
	{
		ArrayList<NameValuePair> pList = new ArrayList<NameValuePair>();
		pList.add(new BasicNameValuePair("vecode", vecode));
		pList.add(new BasicNameValuePair("fdate", fdate));
		pList.add(new BasicNameValuePair("tdate", tdate));
		pList.add(new BasicNameValuePair("vehcom", vehcom));
		pList.add(new BasicNameValuePair("entrby", entrby));
		System.out.println("===============inserting============");
		try {
			HttpPost httpPost;
			HttpClient client = new DefaultHttpClient();
			httpPost = new HttpPost(url);
					//"http://gopajibaba.com/HotelManagement/insert.php");
			System.out.println("on netcheck :" + pList);
			httpPost.setEntity(new UrlEncodedFormEntity(pList));
			
			HttpResponse entity = client.execute(httpPost);
			
		} catch (Exception e) {
			// TODO: handle exception	
		}
	
	}
		
}
	