package com.quentindommerc.sentencify.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.listener.OnPlaylistSelectionListener;

public class ButtonFragment extends Fragment {

	public static ButtonFragment newInstance() {
		ButtonFragment b = new ButtonFragment();
		return b;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_button, container, false);
		Button b = (Button) v.findViewById(R.id.create);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((OnPlaylistSelectionListener) getActivity()).done();
			}
		});
		return v;
	}

}
