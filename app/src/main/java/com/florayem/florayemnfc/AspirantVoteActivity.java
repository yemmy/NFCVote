package com.florayem.florayemnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.florayem.florayemnfc.adapters.AspirantsListAdapter;
import com.florayem.florayemnfc.adapters.PositionsListAdapter;
import com.florayem.florayemnfc.models.Aspirant;
import com.florayem.florayemnfc.models.Position;

import java.util.ArrayList;
import java.util.List;

public class AspirantVoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspirant_vote);
        //load aspirants
        Intent in=getIntent();
        String position=in.getStringExtra("position");
        //get position selected, to load aspirants for
        ListView lst=(ListView)findViewById(R.id.lstvoteaspirants);
        List<Aspirant> poss=new ArrayList<Aspirant>();
        Aspirant p1=new Aspirant();
        p1.name="Abolaji Odussesan";
        p1.party="PDP";
        poss.add(p1);
        Aspirant p2=new Aspirant();
        p2.name="Tiamiyu Giwa";
        p2.party="APC";
        poss.add(p2);
        Aspirant p3=new Aspirant();
        p3.name="Onome Brandopolo";
        p3.party="LABOUR";
        poss.add(p3);
        AspirantsListAdapter posad=new AspirantsListAdapter(this,poss,"");
        lst.setAdapter(posad);
    }
}
