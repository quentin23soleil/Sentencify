package com.quentindommerc.sentencify.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.analytics.tracking.android.EasyTracker;
import com.quentindommerc.sentencify.R;

public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	public void twitter(View v) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("twitter://user?screen_name=kentin_dommerc"));
			startActivity(intent);

		} catch (Exception e) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://twitter.com/#!/kentin_dommerc")));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return true;
	}
}
