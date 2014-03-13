package com.quentindommerc.sentencify.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.adapter.WordPagerAdapter;
import com.quentindommerc.sentencify.bean.Word;

public class Sentence extends FragmentActivity {

	private ArrayList<Word> mWords;
	private ViewPager mPager;
	private WordPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sentence);
		mPager = (ViewPager) findViewById(R.id.view_pager);
		mWords = new ArrayList<Word>();
		String initialSentence = getIntent().getExtras().getString("sentence");
		String[] words = initialSentence.split("\\.");
		for (String i : words) {
			i = i.replaceAll("\\p{Punct}+", " ");
			mWords.add(new Word(i));
		}
		mAdapter = new WordPagerAdapter(getSupportFragmentManager(), mWords);
		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(5);

	}

}
