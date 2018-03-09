package com.florayem.florayemnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.florayem.florayemnfc.adapters.PositionsListAdapter;
import com.florayem.florayemnfc.models.Position;

import java.util.ArrayList;
import java.util.List;

public class PositionsActivity extends AppCompatActivity {

    List<Position> poss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_vote);

        //load positions
        ListView lst=(ListView)findViewById(R.id.lstpositions);
        poss=new ArrayList<Position>();
        Position p1=new Position();
        p1.name="President";
        p1.description="President";
        poss.add(p1);
        Position p2=new Position();
        p2.name="Governor";
        p2.description="Governor";
        poss.add(p2);


        PositionsListAdapter posad=new PositionsListAdapter(this,poss,"");
        lst.setAdapter(posad);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(PositionsActivity.this,AspirantVoteActivity.class);
                i.putExtra("position",poss.get(position).name);
                startActivity(i);
            }
        });


    }
}
