package com.hubpsace.bookblaze;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // smooths out shared transitioning
        //getWindow().setEnterTransition(null);
        //getWindow().setAllowEnterTransitionOverlap(false);
        //getWindow().setAllowReturnTransitionOverlap(false);

        final Button button = findViewById(R.id.upload_book);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // animation stuff
                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                button.startAnimation(animFadein);

                // get the values from the Views
                EditText book_name_input = findViewById(R.id.input_bookName);
                String book_name = book_name_input.getText().toString();
                EditText author_name_input = findViewById(R.id.input_author);
                String author_name = author_name_input.getText().toString();
                EditText date_name_input = findViewById(R.id.input_date);
                String date_name = date_name_input.getText().toString();
                EditText genre_input = findViewById(R.id.input_genre);
                String genre = genre_input.getText().toString();

                Controller c1 = new Controller(getApplicationContext(), MainActivity.this);
                c1.post_request(book_name,author_name,date_name,genre);
            }
        });

        final Button button2 = findViewById(R.id.button_1);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // animation stuff
                //Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
                //button2.startAnimation(animFadein);
                Intent myIntent = new Intent(getApplicationContext(), ShowBook.class);

                // creates shared transition animation between MainActivity and ShowBook
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(button,"main_button");
                pairs[1] = new Pair<View, String>(findViewById(R.id.input_date),"date_label");
                pairs[2] = new Pair<View, String> (findViewById(R.id.linearLayout),"frame_label");
                pairs[3] = new Pair<View, String> (button2,"switch_button");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                // smooths out animations
                getWindow().setExitTransition(null);
                startActivity(myIntent, options.toBundle());
            }
        });
    }
}
