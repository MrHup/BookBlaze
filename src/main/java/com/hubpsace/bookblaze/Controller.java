package com.hubpsace.bookblaze;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Controller {
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
                        Log.d("finalR", myResponse);
//                        MainActivity.this.runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                // print response
////
////                                //TextView response_label = findViewById(R.id.response_textView);
////                                //response_label.setText(myResponse);
////                            }
////                        });

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

