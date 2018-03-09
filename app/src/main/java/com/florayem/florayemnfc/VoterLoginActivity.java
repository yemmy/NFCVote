package com.florayem.florayemnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VoterLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtid=(EditText)findViewById(R.id.txtVoterCode);
        Button btnvote=(Button)findViewById(R.id.btnvote);

        btnvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VoterLoginActivity.this,ElectionsActivity.class);
                i.putExtra("voterid",txtid.getText().toString());
                startActivity(i);
            }
        });
    }
}
