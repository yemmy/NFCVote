package com.florayem.florayemnfc.adapters;

import com.florayem.florayemnfc.R;
import com.florayem.florayemnfc.models.Aspirant;


import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AspirantsListAdapter extends BaseAdapter {

	private Context context;
	String email;
	private List<Aspirant> messagesItems;
	Aspirant[] users;

	public AspirantsListAdapter(Context context, List<Aspirant> navDrawerItems, String email) {
		this.context = context;
		this.messagesItems = navDrawerItems;
		this.email=email;
		this.users=users;
	}

	@Override
	public int getCount() {
		return messagesItems.size();
	}

	@Override
	public Object getItem(int position) {
		return messagesItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/**
		 * The following list not implemented reusable list items as list items
		 * are showing incorrect data Add the solution if you have one
		 * */
		Aspirant m = messagesItems.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


				// message belongs to other person, load the left aligned layout
			convertView = mInflater.inflate(R.layout.voting_row,null);
			TextView name = (TextView) convertView.findViewById(R.id.txtaspirantname);
			TextView party = (TextView) convertView.findViewById(R.id.txtparty);
		ImageView img=(ImageView)convertView.findViewById(R.id.imgpix);
		name.setText(m.name);
		party.setText(m.party);
		return convertView;
	}

//	public User GetUser(String email) {
//		User usr = new User();
//		if (users != null) {
//			for (int c = 0; c < users.length; c++) {
//				if (users[c].email.equals((email))) {
//					usr = users[c];
//					break;
//				}
//			}
//		}
//		return usr;
//	}
}
