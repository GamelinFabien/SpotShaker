package com.example.spotshaker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.spotshaker.Models.User;
import com.example.spotshaker.R;

import java.util.ArrayList;
import java.util.List;

public class AddReportActivity extends AppCompatActivity {

    User user;
    private Spinner spSpots;
    private TextView tvCreateSpot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        initContent();
    }

    public void initContent()
    {
        user = User.getCurrentUser();
        spSpots =  (Spinner) findViewById(R.id.spSpots);
        tvCreateSpot = (TextView) findViewById(R.id.textCreateSpots);
        tvCreateSpot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(AddReportActivity.this, CreateSpotActivity.class);
            AddReportActivity.this.startActivity(i);
        }
    });

        fillSpots();
    }

    public void fillSpots()
    {
        List<String> list = new ArrayList<String>();
        //requête GET pour récupérer les spots
        //foreach add
        list.add("spot 1");

        // ajout au spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpots.setAdapter(dataAdapter);
    }
}
