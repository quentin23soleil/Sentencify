package com.quentindommerc.sentencify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.quentindommerc.sentencify.R;

public class Home extends Activity {

	private EditText mSentence;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mSentence = (EditText) findViewById(R.id.sentence);
		mSentence.setText("I want to. work for you. So. Hire me");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	// TODO implement sub-sentences like :
	// "I want to. work for you. So. Hire me"
	// would only be one sentence
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
}
