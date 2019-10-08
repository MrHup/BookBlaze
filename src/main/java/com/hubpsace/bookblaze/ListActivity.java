package com.hubpsace.bookblaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Book> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        JSONArray json;
        try{
            json = new JSONArray(getIntent().getStringExtra("array"));
            Log.d("main", json.get(0).toString());

            for(int n = 0; n < json.length(); n++)
            {
                JSONObject object = json.getJSONObject(n);
                arrayList.add(new Book(object));
            }

        }catch (Exception e){
            Log.d("main","Problem in passing data or no data at all");
            e.printStackTrace();
        }

        try{

            ArrayList<String> aux_arrayList = new ArrayList<>();
            for(Book el: arrayList){
                aux_arrayList.add(el.toString());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, aux_arrayList);
            listView.setAdapter(arrayAdapter);
        }catch (Exception e){
            Log.d("main", "Error showing array");
            e.printStackTrace();
        }

    }


}
