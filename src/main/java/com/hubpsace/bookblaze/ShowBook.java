package com.hubpsace.bookblaze;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ShowBook extends AppCompatActivity {

    public String selected;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);
        input =findViewById(R.id.input_search_field);

        // smooths out shared transitioning
        getWindow().setEnterTransition(null);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);
        selected = "name";
        // create dropDown to select filter
        Spinner dropdown = findViewById(R.id.spinner1);
        final String[] items = new String[]{"Name", "Author", "Date", "Genre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("main",items[i]+" has been selected");
                selected = items[i].toLowerCase();

                input.setHint("Book "+ selected);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("main","Nothing selected");
            }
        });

        final Button button = findViewById(R.id.find_book);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // animation stuff
                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                button.startAnimation(animFadein);

                try{ // try to close keyboard
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    if( getCurrentFocus()!=null)
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }catch(Exception e){
                    Log.d("main","Everything is fine, just the keyboard wasnt open");
                }


                if( !input.getText().toString().equals("")){
                    Controller controller = new Controller(getApplicationContext(), ShowBook.this);
                    controller.send_request(selected, input.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"Please fill the required box", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button button2 = findViewById(R.id.button_2);  // back button
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                button2.startAnimation(anim);

                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                //myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP); // remove last activity from stack
                //myIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(button,"main_button");
                pairs[1] = new Pair<View, String> (findViewById(R.id.linearLayout2),"frame_label");
                pairs[2] = new Pair<View, String> (button2,"switch_button");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ShowBook.this, pairs);

                // smooths out animations
                getWindow().setExitTransition(null);
                startActivity(myIntent, options.toBundle());

                //finish();


            }
        });


    }
}
