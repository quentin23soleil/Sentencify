package com.quentindommerc.sentencify.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.utils.LibSpotifyWrapper;

public class Playlist extends FragmentActivity {

	private com.quentindommerc.sentencify.bean.Playlist mPlaylist;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mPlaylist = (com.quentindommerc.sentencify.bean.Playlist) getIntent().getExtras().get(
				"playlist");
		ArrayList<String> mUri = new ArrayList<String>();
		for (Word w : mPlaylist.getWords()) {
			mUri.add(w.getSelectedTrack().getHref());
		}
		LibSpotifyWrapper.createPlaylist(mPlaylist.getOriginal(), mUri);

	}
}
