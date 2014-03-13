package com.quentindommerc.sentencify.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
	private String mWord;
	private ArrayList<Track> mTracks;
	private Track mSelectedTrack;

	public Word() {
		mTracks = new ArrayList<Track>();
	}

	public void addTrack(Track t) {
		mTracks.add(t);
	}

	public String getWord() {
		return mWord;
	}

	public void setWord(String word) {
		mWord = word;
	}

	public ArrayList<Track> getTracks() {
		return mTracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		mTracks = tracks;
	}

	public Track getSelectedTrack() {
		return mSelectedTrack;
	}

	public void setSelectedTrack(Track selectedTrack) {
		mSelectedTrack = selectedTrack;
	}

	protected Word(Parcel in) {
		mWord = in.readString();
		if (in.readByte() == 0x01) {
			mTracks = new ArrayList<Track>();
			in.readList(mTracks, Track.class.getClassLoader());
		} else {
			mTracks = null;
		}
		mSelectedTrack = (Track) in.readValue(Track.class.getClassLoader());
	}

	public Word(String i) {
		this();
		mWord = i;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mWord);
		if (mTracks == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(mTracks);
		}
		dest.writeValue(mSelectedTrack);
	}

	public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
		@Override
		public Word createFromParcel(Parcel in) {
			return new Word(in);
		}

		@Override
		public Word[] newArray(int size) {
			return new Word[size];
		}
	};

}
