package com.hubpsace.bookblaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public void send_request(){
        try{
            OkHttpClient client = new OkHttpClient();
            String url = "https://secure-plateau-13343.herokuapp.com/books";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace(); // on failure
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        final String myResponse = response.body().string();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // print response

                                //TextView response_label = findViewById(R.id.response_textView);
                                //response_label.setText(myResponse);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void post_request(){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "https://secure-plateau-13343.herokuapp.com/books_add";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();

        // get the values from the Views
        EditText book_name_input = findViewById(R.id.input_bookName);
        String book_name = book_name_input.getText().toString();
        EditText author_name_input = findViewById(R.id.input_author);
        String author_name = author_name_input.getText().toString();
        //EditText date_name_input = findViewById(R.id.input_date);
        //String date_name = date_name_input.getText().toString();
        EditText genre_input = findViewById(R.id.input_genre);
        String genre = genre_input.getText().toString();

        try {
            if(book_name+author_name != ""){
                postdata.put("book_name", book_name);
                postdata.put("book_author", author_name);
                postdata.put("book_date", "2019-05-04");
                postdata.put("book_genre", genre);
                book_name_input.setText(""); author_name_input.setText("");
                //date_name_input.setText("");
                genre_input.setText("");
            } else {
                Log.d("look_", "Wtf" + book_name + " , " + author_name);
                return;
            }

        } catch(JSONException e){
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e("debug_me", mMessage);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.upload_book);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // animation stuff
                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                button.startAnimation(animFadein);

                post_request();
            }
        });

        final Button button2 = findViewById(R.id.button_1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // animation stuff
                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.zoom_in);
                button2.startAnimation(animFadein);

                post_request();
            }
        });
    }
}
