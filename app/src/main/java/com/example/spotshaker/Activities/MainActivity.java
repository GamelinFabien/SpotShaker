package com.example.spotshaker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spotshaker.R;

public class MainActivity extends AppCompatActivity {

    Button bStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent();
    }

    public void initContent()
    {
        bStart = (Button) findViewById(R.id.bStart);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                   Intent i = new Intent(MainActivity.this, LoginActivity.class);
                   MainActivity.this.startActivity(i);
            }
        });
    }
}
