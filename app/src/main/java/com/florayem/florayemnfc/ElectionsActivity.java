package com.florayem.florayemnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ElectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        Button btnvote=(Button)findViewById(R.id.btnprelection);
        //Load Elections from service
        btnvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ElectionsActivity.this,PositionsActivity.class);
                startActivity(in);
            }
        });
    }
}
