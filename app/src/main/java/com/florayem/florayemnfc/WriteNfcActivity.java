package com.florayem.florayemnfc;

import java.io.IOException;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WriteNfcActivity extends AppCompatActivity {

	static final String TAG = "WriteTag";
	boolean mWriteMode = false;
	boolean mWriteAndLock = false;
	boolean mLockMode = false;
	int id = 1234;
	IntentFilter[] mWriteTagFilters;
	NfcManager mNfcManager;
	NfcAdapter mNfcAdapter;
	PendingIntent mNfcPendingIntent;

	String vid = "";
	String name = "";
	String phone = "";
	String addy = "";
	EditText txtvid, txtname, txtphone, txtaddy;
	//AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_writenfc);

		mNfcManager = (NfcManager) getSystemService(NFC_SERVICE);
		mNfcAdapter = mNfcManager.getDefaultAdapter();
		/*
		 * Bundle extras = getIntent().getExtras(); if(extras != null){ id =
		 * extras.getInt("nfc_id"); mLockMode = extras.getBoolean("lock",false);
		 * }
		 * 
		 * else{ finish(); }
		 */

		if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {

			//Yes NFC available
			Toast.makeText(getApplicationContext(),"NFC supported",Toast.LENGTH_SHORT).show();
		}else{
Toast.makeText(getApplicationContext(),"NFC not supported",Toast.LENGTH_SHORT).show();
			//Your device doesn't support NFC
		}

		Button btn = (Button) findViewById(R.id.btncreate);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String textAlert = null;

				if (mLockMode)
					textAlert = "Tap the tag to lock it, press return to cancel";
				else
					textAlert = "Tap the Card to write it, press return to cancel";

				AlertDialog dialog = new AlertDialog.Builder(WriteNfcActivity.this)
						.setTitle(textAlert)
						.setOnCancelListener(
								new DialogInterface.OnCancelListener() {
									@Override
									public void onCancel(DialogInterface dialog) {
										disableTagWriteMode();
									}
								}).create();

				LinearLayout layout = new LinearLayout(WriteNfcActivity.this);
				/*
				 * CheckBox cbView = new CheckBox(this);
				 * cbView.setText("lock after writing");
				 * cbView.setChecked(mWriteAndLock);
				 * cbView.setOnCheckedChangeListener(new
				 * OnCheckedChangeListener() {
				 * 
				 * @Override public void onCheckedChanged(CompoundButton
				 * buttonView, boolean isChecked) { mWriteAndLock = isChecked; }
				 * }); layout.addView(cbView);
				 */
				dialog.setView(layout);

				dialog.show();
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {
			enableTagWriteMode();
			//Yes NFC available
		}else{
			Toast.makeText(getApplicationContext(),"NFC not supported",Toast.LENGTH_SHORT).show();
			//Your device doesn't support NFC
		}

	}

	private void enableTagWriteMode() {
		mWriteMode = true;
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		mWriteTagFilters = new IntentFilter[] { tagDetected };
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,mWriteTagFilters, null);
	}

	private void disableTagWriteMode() {
		mWriteMode = false;
		mLockMode = false;
		mNfcAdapter.disableForegroundDispatch(this);
	}

	@Override
	public void onNewIntent(Intent intent) {
		// Tag writing mode
		txtvid = (EditText) findViewById(R.id.txtvoterid);
		txtname = (EditText) findViewById(R.id.txtvotername);
		txtphone = (EditText) findViewById(R.id.txtvoterphone);
		txtaddy = (EditText) findViewById(R.id.txtaddress);
		if (mWriteMode
				&& NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			

			vid = txtvid.getText().toString();
			name = txtname.getText().toString();
			phone = txtphone.getText().toString();
			addy = txtaddy.getText().toString();

			if (writeTag(getCardDataAsNdef(vid, name, phone, addy),
					detectedTag)) {

				Toast.makeText(this, "Successful writing, You may now read the card to confirm successful write", Toast.LENGTH_LONG)
						.show();

			} else {
				Toast.makeText(this, "Failed writing", Toast.LENGTH_LONG)
						.show();
			}
		}
		// Tag lock mode
		if (mLockMode
				&& NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			if (lockTag(detectedTag)) {
				Toast.makeText(this, "Successful lock", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(this, "Failed locking", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	public static NdefMessage getCardDataAsNdef(String number, String name,
			String phone, String addy) {
		// String msg = ((Integer) id).toString();
		byte[] numberb = number.getBytes();
		byte[] nameb = name.getBytes();
		byte[] phoneb = phone.getBytes();
		byte[] addyb = addy.getBytes();

		NdefRecord numberrec = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				"application/florayem".getBytes(), new byte[] {}, numberb);
		NdefRecord namerec = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				"application/florayem".getBytes(), new byte[] {}, nameb);
		NdefRecord deptrec = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				"application/florayem".getBytes(), new byte[] {}, phoneb);
		NdefRecord levrec = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				"application/florayem".getBytes(), new byte[] {}, addyb);
		return new NdefMessage(
				new NdefRecord[] {
						numberrec,
						namerec,
						deptrec,
						levrec,
						NdefRecord
								.createApplicationRecord("com.florayem.florayemnfc") });

	}

	/*
	 * Writes an NdefMessage to a NFC tag
	 */
	public boolean writeTag(NdefMessage message, Tag tag) {
		Log.i(TAG, "write tag ( lock : " + mWriteAndLock + " )");
		int size = message.toByteArray().length;
		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();
				if (!ndef.isWritable()) {
					return false;
				}
				if (ndef.getMaxSize() < size) {
					return false;
				}
				Log.i(TAG, "canMakeReadOnly() " + ndef.canMakeReadOnly());
				ndef.writeNdefMessage(message);
				/*
				 * if(ndef.canMakeReadOnly() && mWriteAndLock){ Log.i(TAG,
				 * "formatReadOnly"); return ndef.makeReadOnly(); }
				 */
				return true;
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						/*
						 * if(mWriteAndLock){ Log.i(TAG, "formatReadOnly");
						 * format.formatReadOnly(message); }
						 */
						// else
						format.format(message);
						return true;
					} catch (IOException e) {
						Log.i(TAG, "exception " + e.getMessage());
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			Log.i(TAG, "exception " + e.getMessage());
			return false;
		}
		
	}

	public static boolean lockTag(Tag tag) {
		Log.i(TAG, "lockTag()");
		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();
				if (!ndef.isWritable()) {
					Log.i(TAG, "not writable ...");
					return false;
				}
				Log.i(TAG, "makeReadOnly !");
				return ndef.makeReadOnly();
			}
			return false;
		} catch (Exception e) {
			Log.i(TAG, "exception " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voting, menu);
		return true;
	}

}
