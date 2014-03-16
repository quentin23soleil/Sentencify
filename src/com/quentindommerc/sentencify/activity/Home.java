package com.quentindommerc.sentencify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.targets.ViewTarget;
import com.quentindommerc.sentencify.BuildConfig;
import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.listener.LoginDelegate;
import com.quentindommerc.sentencify.utils.LibSpotifyWrapper;
import com.quentindommerc.sentencify.utils.Utils;

import fr.nicolaspomepuy.discreetapprate.AppRate;

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
		AppRate.with(this).initialLaunchCount(3).checkAndShow();
		if (!Utils.getBooleanSharedPref(this, "help", true)) {
			Utils.setSharedPref(this, "help", true);
			showShowcase();
		}
		if (Utils.getSharedPref(this, "username").equals("")) {
			Intent i = new Intent(this, Login.class);
			startActivityForResult(i, REQUEST_LOGIN);
			return;
		}
		setup();
		LibSpotifyWrapper.init(LibSpotifyWrapper.class.getClassLoader(), Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/Android/data/com.quentindommerc.sentencify");
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

	private void showShowcase() {
		ViewTarget target = new ViewTarget(mSentence);
		ShowcaseView.insertShowcaseView(target, this, R.string.showcase_sentence_title,
				R.string.showcase_sentence_details);
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
			mSentence.setText("I love it. Thanks again. This is awesome. Lol");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.action_logout:
		// logout();
		// break;
		case R.id.action_about:
			about();
			break;
		case R.id.action_help:
			showShowcase();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// forced due to libspotify init
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(startMain);
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
