package com.quentindommerc.sentencify.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Playlist implements Parcelable {
	private String mOriginal;
	private ArrayList<Word> mWords;

	public Playlist() {
		mWords = new ArrayList<Word>();
	}

	public String getOriginal() {
		return mOriginal;
	}

	public void setOriginal(String original) {
		mOriginal = original;
	}

	public ArrayList<Word> getWords() {
		return mWords;
	}

	public void setWords(ArrayList<Word> words) {
		mWords = words;
	}

	public void addWord(Word w) {
		mWords.add(w);
	}

	protected Playlist(Parcel in) {
		if (in.readByte() == 0x01) {
			mWords = new ArrayList<Word>();
			in.readList(mWords, Word.class.getClassLoader());
		} else {
			mWords = null;
		}
		mOriginal = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (mWords == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(mWords);
		}
		dest.writeString(mOriginal);
	}

	public static final Parcelable.Creator<Playlist> CREATOR = new Parcelable.Creator<Playlist>() {
		@Override
		public Playlist createFromParcel(Parcel in) {
			return new Playlist(in);
		}

		@Override
		public Playlist[] newArray(int size) {
			return new Playlist[size];
		}
	};

}
