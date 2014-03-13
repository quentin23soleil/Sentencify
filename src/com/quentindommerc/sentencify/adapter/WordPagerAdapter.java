package com.quentindommerc.sentencify.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.fragment.WordTrackSelection;

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
