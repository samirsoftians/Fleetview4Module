package com.example.fleetviewandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginForm extends Activity {
    String url="http://192.168.2.239:8181/FleetViewProjectTest/MyServlet";
  //  String url="http://twtech.in:8080/FleetViewProject/MyServlet";

    EditText u_name,u_pass;
    Button login;


    static ArrayList<String> arrayList=new ArrayList<>();

    public static String name,pass;
    private String[] vno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println("*** My thread is now configured to allow connection");
        }

        u_name=(EditText)findViewById(R.id.loginform_user_name);
        u_pass=(EditText)findViewById(R.id.loginform_user_password);

        login=(Button)findViewById(R.id.login_form_login_button);
//        response_text=(TextView)findViewById(R.id.response_string);


        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                name=u_name.getText().toString();
                pass=u_pass.getText().toString();

                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connManager.getActiveNetworkInfo();


                if(!name.equals("")&&!pass.equals(""))
                {

                    if(networkInfo!=null&&networkInfo.isConnected())
                    {


                        RequestLogin(name,pass);


                    }
                    else
                    {

                     Toast.makeText(LoginForm.this, "Please Check the Internet Connection", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {

                    u_name.setError("Enter the valid Name");
                    u_pass.setError("Enter the valid Password");



                }



            }
        });

            }

    public void  RequestLogin(String u_id, String u_password)
    {

        int  status= 0;
        InputStream is=null;
        String result=null;
        String line=null;

//        String url ="http://MyFleetView.com:8181/AndrFleetApp/LoginServlet";
       //
        // String url ="http://192.168.2.239:8181/FleetViewProjectTest/MyServlet";
//        String url ="http://192.168.2.26:9090/FleetViewAndroidProject/MyServlet";
//        String url ="http://192.168.2.26:9090/FleetViewProject/MyServlet";

        ArrayList<String>  vehicle_no=new ArrayList<>();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name",""+u_id ));
        nameValuePairs.add(new BasicNameValuePair("password",""+u_password));


//			nameValuePairs.add(new BasicNameValuePair("MenuType",""+menuType));

//        List<String> list  = new ArrayList<String>();

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {

            Log.e("Fail 1", e.toString());
            Toast.makeText(LoginForm.this, "Server is Offline..Please Try Again ", Toast.LENGTH_LONG).show();

        }
        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
                arrayList.add(line);
            }
            is.close();
            result = sb.toString();
            Log.d("FLEET","LOG"+result);

            if(result!=null)
            {
                String item=result.substring(0, result.length()-1);
                Log.i("FleetView"," Result "+result);
                String   item1=","+item;
                int commas = 0;
                for(int i = 0; i < item1.length(); i++)
                {

                    if(item1.charAt(i) == ',')
                        commas++;
                }

                for(int j=1;j<=commas;j++)
                {
                    vno=item1.split(",");
                    //System.out.println("=======in activitycccccccccc ============"+aa[j]);
//                    arrayList.add(vno[j]);
                }
                Intent intent=new Intent(getApplicationContext(),MainForm.class);
                intent.putExtra("Username",name);
                intent.putExtra("password",pass);
                finish();
                startActivity(intent);
                Log.d("Result",""+result);
                Log.e("pass 2", "connection success ");

            }

        }
        catch(Exception e)
        {


            u_name.setError("Enter the valid Username");
            u_pass.setError("Enter the valid Password");


            Log.e("Fail 2", e.toString());
        }

    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginForm.this);
        builder1.setTitle("FleetView Application");
        builder1.setMessage("Are you sure want to exit?");
        builder1.setIcon(R.drawable.transworldlogo1);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        //exit();

                        finish();


                    }
                });

        builder1.setNegativeButton
                (
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}


