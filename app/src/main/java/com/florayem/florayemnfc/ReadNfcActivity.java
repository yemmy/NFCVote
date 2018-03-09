package com.florayem.florayemnfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

public class ReadNfcActivity extends AppCompatActivity {

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_nfc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_nfc, menu);
		return true;
	}*/
	
	static final String TAG = "ViewTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_nfc);
        resolveIntent(getIntent());       
        
    }

    void resolveIntent(Intent intent) {
        // Parse the intent
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] messages = getNdefMessages(getIntent());
            //Toast.makeText(ReadNfcActivity.this,messages.length+"",Toast.LENGTH_LONG).show();
            byte[] payload1 = messages[0].getRecords()[0].getPayload();
            String number = new String(payload1);            
            byte[] payload2 = messages[0].getRecords()[1].getPayload();
            String name = new String(payload2);
            byte[] payload3 = messages[0].getRecords()[2].getPayload();
            String dept = new String(payload3);
            byte[] payload4 = messages[0].getRecords()[3].getPayload();
            String level = new String(payload4);
            
            EditText txtnumber = (EditText) findViewById(R.id.txtvoteridr);
            EditText txtname = (EditText) findViewById(R.id.txtvoternamer);
            EditText txtdept = (EditText) findViewById(R.id.txtvoterphoner);
            EditText txtlevel = (EditText) findViewById(R.id.txtaddressr);
            
            txtnumber.setText(number.toString());
            txtname.setText(name.toString());
            txtdept.setText(dept.toString());
            txtlevel.setText(level.toString());
            
            //Toast.makeText(ReadNfcActivity.this,number+"  "+name+"  "+"  "+dept+"  "+level,Toast.LENGTH_LONG).show();
           // goDetails(id);
           
            
            
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    public void goDetails(String id){
    	/*final Intent intent = new Intent(this, ServiceDetailsActivity.class);
    	int id_int = Integer.valueOf(id);
        intent.putExtra("nfc_id", id_int);
        startActivity(intent);
        this.finish();*/
    }
    
    NdefMessage[] getNdefMessages(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = 
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = 
                    new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {
                    record
                });
                msgs = new NdefMessage[] {
                    msg
                };
            }
        } else {
            Log.d(TAG, "Unknown intent.");
            finish();
        }
        return msgs;
    }

}
