package com.quentindommerc.sentencify.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;
import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.bean.Playlist;
import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.fragment.ButtonFragment;
import com.quentindommerc.sentencify.fragment.WordTrackSelection;
import com.quentindommerc.sentencify.listener.OnPlaylistSelectionListener;
import com.quentindommerc.sentencify.listener.OnTrackSelectedFromPage;
import com.quentindommerc.sentencify.utils.Utils;

public class Sentence extends FragmentActivity implements OnTrackSelectedFromPage,
		OnPlaylistSelectionListener {

	private ArrayList<Word> mWords;
	private ViewPager mPager;
	private WordPagerAdapter mAdapter;
	private String mInitialSentence;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sentence);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mPager = (ViewPager) findViewById(R.id.view_pager);
		PagerTabStrip strip = (PagerTabStrip) findViewById(R.id.pager_strip);
		strip.setTabIndicatorColor(getResources().getColor(R.color.sentencify_color));
		mWords = new ArrayList<Word>();
		String initialSentence = getIntent().getExtras().getString("sentence");
		mInitialSentence = initialSentence.replaceAll("\\p{Punct}+", " ");
		mInitialSentence = mInitialSentence.replaceAll("\\s+", " ");
		mInitialSentence = mInitialSentence.toLowerCase();
		mInitialSentence = mInitialSentence.substring(0, 1).toUpperCase()
				+ mInitialSentence.substring(1);
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
			if (arg0 == getCount() - 1) {
				return ButtonFragment.newInstance();
			} else if (arg0 == 0 && !Utils.getBooleanSharedPref(Sentence.this, "help_list", true))
				return WordTrackSelection.newInstance(mWords.get(arg0), true);
			return WordTrackSelection.newInstance(mWords.get(arg0), false);
		}

		@Override
		public int getCount() {
			return mWords.size() + 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position == getCount() - 1)
				return getString(R.string.next_step);
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
						startActivityForResult(i, 0);
					}
				});
			}
		};
		t.start();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			about();
			break;
		case android.R.id.home:
			finish();
			return true;

		default:
			break;
		}
		return true;
	}

	private void about() {
		Intent i = new Intent(this, About.class);
		startActivity(i);
	}

	@Override
	public void trackSelected(Word w) {
		if (mPager.getCurrentItem() < (mAdapter.getCount() - 1))
			mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
		// else {
		// createPlaylist();
		// }
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg1 == RESULT_OK && arg0 == 0)
			finish();

	}

	@Override
	public void done() {
		createPlaylist();
	}

}
