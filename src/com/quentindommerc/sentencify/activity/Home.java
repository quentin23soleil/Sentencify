package com.quentindommerc.sentencify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quentindommerc.sentencify.BuildConfig;
import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.listener.LoginDelegate;
import com.quentindommerc.sentencify.utils.LibSpotifyWrapper;
import com.quentindommerc.sentencify.utils.Utils;

public class Home extends Activity {

	private static final int REQUEST_LOGIN = 0;
	private EditText mSentence;
	static {
		System.loadLibrary("spotify");
		System.loadLibrary("spotifywrapper");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mSentence = (EditText) findViewById(R.id.sentence);
		if (Utils.getSharedPref(this, "username").equals("")) {
			Intent i = new Intent(this, Login.class);
			startActivityForResult(i, REQUEST_LOGIN);
			return;
		}
		setup();
		LibSpotifyWrapper.init(LibSpotifyWrapper.class.getClassLoader(), Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/Android/data/com.spotify.hacks.psyonspotify");
		LibSpotifyWrapper.loginUser(Utils.getSharedPref(this, "username"),
				Utils.getSharedPref(this, "password"), new LoginDelegate() {

					@Override
					public void onLoginFailed(String message) {
						Toast.makeText(Home.this, getString(R.string.error_login),
								Toast.LENGTH_LONG).show();
						Utils.rmSharedPref(Home.this, "username");
						Utils.rmSharedPref(Home.this, "password");
						Intent i = new Intent(Home.this, Login.class);
						startActivityForResult(i, REQUEST_LOGIN);
						return;

					}

					@Override
					public void onLogin() {
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void sentencify(View v) {
		String sentence = mSentence.getText().toString();
		if (sentence.isEmpty()) {
			mSentence.setError(getString(R.string.error_sentence_empty));
			return;
		}
		sentence = sentence.replaceAll("\\s+", " ");
		Intent i = new Intent(this, Sentence.class);
		i.putExtra("sentence", sentence);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_LOGIN:
				setup();
				break;

			default:
				break;
			}
		} else
			finish();

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setup() {
		if (BuildConfig.DEBUG)
			mSentence.setText("I want to. work for you. So. Hire me");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout:
			logout();
			break;
		case R.id.action_about:
			about();
		default:
			break;
		}
		return true;
	}

	private void about() {
		Intent i = new Intent(this, About.class);
		startActivity(i);
	}

	private void logout() {
		Utils.rmSharedPref(this, "username");
		Utils.rmSharedPref(this, "password");
		Intent i = new Intent(this, Login.class);
		startActivityForResult(i, REQUEST_LOGIN);
	}
}
