package com.quentindommerc.sentencify.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.adapter.FinalPlaylistAdapter;
import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.utils.LibSpotifyWrapper;

public class Playlist extends FragmentActivity {

	private com.quentindommerc.sentencify.bean.Playlist mPlaylist;
	private EditText mName;
	private ListView mList;
	private FinalPlaylistAdapter mFinalPlaylistAdapter;

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mPlaylist = (com.quentindommerc.sentencify.bean.Playlist) getIntent().getExtras().get(
				"playlist");
		setContentView(R.layout.activity_playlist);
		mName = (EditText) findViewById(R.id.playlist_name);
		mName.setText(mPlaylist.getOriginal());
		mList = (ListView) findViewById(R.id.list);
		mFinalPlaylistAdapter = new FinalPlaylistAdapter(this);
		mFinalPlaylistAdapter.addAll(mPlaylist.getWords());
		mList.setAdapter(mFinalPlaylistAdapter);

	}

	public void create(View v) {
		ArrayList<String> mUri = new ArrayList<String>();
		for (Word w : mPlaylist.getWords()) {
			mUri.add(w.getSelectedTrack().getHref());
		}
		LibSpotifyWrapper.createPlaylist(mPlaylist.getOriginal(), mUri);
		Toast.makeText(this, getString(R.string.congrats), Toast.LENGTH_LONG).show();
		setResult(RESULT_OK);
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			about();
			break;

		case android.R.id.home:
			setResult(RESULT_OK);
			finish();
			break;

		default:
			break;
		}
		return true;
	}

	private void about() {
		Intent i = new Intent(this, About.class);
		startActivity(i);
	}
}
