package com.quentindommerc.sentencify.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.listener.LoginDelegate;
import com.quentindommerc.sentencify.utils.LibSpotifyWrapper;
import com.quentindommerc.sentencify.utils.Utils;

public class Login extends FragmentActivity {

	private EditText mUsername;
	private EditText mPassword;

	@Override
	protected void onCreate(Bundle arg0) {
		overridePendingTransition(0, 0);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		super.onCreate(arg0);

		LibSpotifyWrapper.init(LibSpotifyWrapper.class.getClassLoader(), Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/Android/data/com.quentindommerc.sentencify");
		setContentView(R.layout.activity_login);
		mUsername = (EditText) findViewById(R.id.username);
		mPassword = (EditText) findViewById(R.id.password);
		mPassword.setTypeface(Typeface.DEFAULT);
		mPassword.setTransformationMethod(new PasswordTransformationMethod());
		mPassword.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					signin(null);
					return true;
				}

				return false;
			}
		});
		animate();

	}

	private void animate() {
		// Buttons translate up
		Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1,
				Animation.RELATIVE_TO_PARENT, 0);
		animation.setDuration(500);
		animation.setFillAfter(true);
		findViewById(R.id.connect).startAnimation(animation);
		findViewById(R.id.connect).setVisibility(View.VISIBLE);

		// edit texts appears fade in
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new AccelerateInterpolator());
		fadeIn.setDuration(1500);
		mUsername.startAnimation(fadeIn);
		mPassword.startAnimation(fadeIn);
	}

	public void signin(View v) {
		if (!checkField())
			return;
		LibSpotifyWrapper.loginUser(mUsername.getText().toString(), mPassword.getText().toString(),
				new LoginDelegate() {

					@Override
					public void onLoginFailed(String message) {
						Toast.makeText(Login.this, getString(R.string.error_login),
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onLogin() {
						Utils.setSharedPref(Login.this, "username", mUsername.getText().toString());
						Utils.setSharedPref(Login.this, "password", mPassword.getText().toString());
						back();
					}
				});

	}

	private boolean checkField() {
		boolean f = true;
		if (mUsername.getText().toString().length() < 4) {
			mUsername.setError(getString(R.string.error_username));
			f = false;
		}
		if (mPassword.getText().toString().length() < 4) {
			mPassword.setError(getString(R.string.error_password));
			f = false;
		}
		return f;
	}

	private void back() {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

}
