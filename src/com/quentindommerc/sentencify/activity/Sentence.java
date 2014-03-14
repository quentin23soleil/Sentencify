package com.quentindommerc.sentencify.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.bean.Playlist;
import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.fragment.WordTrackSelection;
import com.quentindommerc.sentencify.listener.OnTrackSelectedFromPage;

public class Sentence extends FragmentActivity implements OnTrackSelectedFromPage {

	private ArrayList<Word> mWords;
	private ViewPager mPager;
	private WordPagerAdapter mAdapter;
	private String mInitialSentence;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sentence);
		mPager = (ViewPager) findViewById(R.id.view_pager);
		mWords = new ArrayList<Word>();
		String initialSentence = getIntent().getExtras().getString("sentence");
		mInitialSentence = initialSentence.replaceAll("\\p{Punct}+", " ");
		String[] words = initialSentence.split("\\.");
		for (String i : words) {
			i = i.replaceAll("\\p{Punct}+", " ");
			mWords.add(new Word(i));
		}
		mAdapter = new WordPagerAdapter(getSupportFragmentManager(), mWords);
		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(5);
	}

	public class WordPagerAdapter extends FragmentStatePagerAdapter {

		private final ArrayList<Word> mWords;

		public WordPagerAdapter(FragmentManager fm, ArrayList<Word> words) {
			super(fm);
			mWords = words;
		}

		@Override
		public Fragment getItem(int arg0) {
			return WordTrackSelection.newInstance(mWords.get(arg0));
		}

		@Override
		public int getCount() {
			return mWords.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mWords.get(position).getWord().substring(0, 1).toUpperCase()
					+ mWords.get(position).getWord().substring(1);
		}

	}

	private void createPlaylist() {
		Thread t = new Thread() {
			@Override
			public void run() {
				final Playlist p = new Playlist();
				for (Word w : mWords) {
					if (w.getSelectedTrack() != null)
						p.addWord(w); // not google's (Adwords badum tss)
				}
				p.setOriginal(mInitialSentence);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Intent i = new Intent(Sentence.this,
								com.quentindommerc.sentencify.activity.Playlist.class);
						i.putExtra("playlist", p);
						startActivity(i);
					}
				});
			}
		};
		t.start();
	}

	// TODO finir else
	@Override
	public void trackSelected(Word w) {
		if (mPager.getCurrentItem() < (mAdapter.getCount() - 1))
			mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
		else {
			Toast.makeText(this, "fini", Toast.LENGTH_LONG).show();
			createPlaylist();
		}
	}

}
