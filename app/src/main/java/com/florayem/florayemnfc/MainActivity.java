package com.florayem.florayemnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnwrite=(Button)findViewById(R.id.btncreatecard);
        Button btnraed=(Button)findViewById(R.id.btnreadcard);
        Button btnvote=(Button)findViewById(R.id.btnvote);
        btnvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,VoterLoginActivity.class);

                startActivity(i);
            }
        });

        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,WriteNfcActivity.class);
                startActivity(i);
            }
        });

        btnraed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ReadNfcActivity.class);
                startActivity(i);
            }
        });




    }
}
