package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<DataModel> list=new ArrayList<>();

    String URl="https://api.androidhive.info/contacts/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.list);

        StringRequest request=new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //     Log.e("Res",">>>>>>>>"+response);

                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("contacts");

                    for (int i=0;i<array.length();i++){
                        JSONObject object1=array.getJSONObject(i);
                        String name=object1.getString("name");
                        String email=object1.getString("email");

                        DataModel model=new DataModel();
                        model.setName(name);
                        model.setEmail(email);

                        list.add(model);

                        Base_Adapter adapter=new Base_Adapter(MainActivity.this,list);
                        listView.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        queue.add(request);


    }
}
