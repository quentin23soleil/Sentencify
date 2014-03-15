package com.quentindommerc.sentencify.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.utils.LibSpotifyWrapper;

public class Playlist extends FragmentActivity {

	private com.quentindommerc.sentencify.bean.Playlist mPlaylist;
	private EditText mName;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mPlaylist = (com.quentindommerc.sentencify.bean.Playlist) getIntent().getExtras().get(
				"playlist");
		setContentView(R.layout.activity_playlist);
		mName = (EditText)findViewById(R.id.playlist_name);
		mName.setText(mPlaylist.getOriginal());

	}

	public void create(View v) {
		ArrayList<String> mUri = new ArrayList<String>();
		for (Word w : mPlaylist.getWords()) {
			mUri.add(w.getSelectedTrack().getHref());
		}
		LibSpotifyWrapper.createPlaylist(mPlaylist.getOriginal(), mUri);

	}
}
