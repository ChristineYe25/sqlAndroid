package com.example.yayingye.sqlproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mysql.jdbc.ResultSetMetaData;

public class MainActivity extends AppCompatActivity {
   
    public static List<Object> objList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Download(MainActivity.this, url).execute();
    }

    public class Download extends AsyncTask<Void, Void, String> {
        ProgressDialog mProgressDialog;
        Context context;
        private String url;

        public Download(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(context, "",
                    "Please wait, getting database...");
        }

        protected String doInBackground(Void... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, pass);
                java.sql.Statement st = con.createStatement();
                java.sql.ResultSet rs = st.executeQuery("select * from metardata_2017_06 limit 6");
                objList = new ArrayList<Object>();

                while (rs.next()) {
                    java.sql.ResultSetMetaData resultSetMetaData= rs.getMetaData();
                    for(int i=0;i<resultSetMetaData.getColumnCount();i++) {
                        Log.d("sql",resultSetMetaData.getColumnName(i));
                    }


                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return "Complete";
        }

        protected void onPostExecute(String result) {
            if (result.equals("Complete")) {
                mProgressDialog.dismiss();
            }
        }
    }
}
