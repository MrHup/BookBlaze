package com.hubpsace.bookblaze;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Controller extends MainActivity {

    private Context context;
    Activity activity;
    Toast toast;

    public Controller(Context context, Activity activity){ // Initialize Controller
        Log.d("output","A controller has be instantiated");
        this.context = context;
        this.activity = activity;
    }

    /*
     *    Makes a post request to the server.
     *    Uploads a book if the fields are completed correctly.
     */
    public void post_request(String book_name, String author_name, String date_name, String genre){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "https://secure-plateau-13343.herokuapp.com/books_add";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();


        try {
            if( !(book_name.equals("") || author_name.equals("") && date_name.equals("") || genre.equals("")) ){
                postdata.put("book_name", book_name);
                postdata.put("book_author", author_name);
                postdata.put("book_date", date_name);
                postdata.put("book_genre", genre);

                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        EditText book_name_input = activity.findViewById(R.id.input_bookName);
                        EditText author_name_input = activity.findViewById(R.id.input_author);
                        EditText date_name_input = activity.findViewById(R.id.input_date);
                        EditText genre_input = activity.findViewById(R.id.input_genre);
                        book_name_input.setText(""); author_name_input.setText("");
                        date_name_input.setText(""); genre_input.setText("");
                    }
                });
            } else {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        toast = Toast.makeText(context, "All input is required", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                return;
            }

        } catch(JSONException e){
            e.printStackTrace(); // ERR
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder() // make request
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
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


    /*
     *     Sends a get request to the DB,
     *     returns a JSON object containing the response.
     *     Used by the search book activity
     */
    public void send_request(String field, String input){
        try{
            OkHttpClient client = new OkHttpClient();
            // create request string
            String url = "https://secure-plateau-13343.herokuapp.com/books/"+field+"/'"+input+"'";
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
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                TextView response_label = activity.findViewById(R.id.response_textView);
                                response_label.setText(myResponse);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

