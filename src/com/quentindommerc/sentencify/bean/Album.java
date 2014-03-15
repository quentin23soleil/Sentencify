package com.quentindommerc.sentencify.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {
	private String mReleaseYear;
	private String mHref;
	private String mName;
	private ArrayList<String> mTerritories;

	public Album() {
		mTerritories = new ArrayList<String>();
	}

	public ArrayList<String> getTerritories() {
		return mTerritories;
	}

	public String getReleaseYear() {
		return mReleaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		mReleaseYear = releaseYear;
	}

	public String getHref() {
		return mHref;
	}

	public void setHref(String href) {
		mHref = href;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	protected Album(Parcel in) {
		mReleaseYear = in.readString();
		mHref = in.readString();
		mName = in.readString();
		if (in.readByte() == 0x01) {
			mTerritories = new ArrayList<String>();
			in.readList(mTerritories, String.class.getClassLoader());
		} else {
			mTerritories = null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mReleaseYear);
		dest.writeString(mHref);
		dest.writeString(mName);
		if (mTerritories == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(mTerritories);
		}
	}

	public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
		@Override
		public Album createFromParcel(Parcel in) {
			return new Album(in);
		}

		@Override
		public Album[] newArray(int size) {
			return new Album[size];
		}
	};

	public void setTerritories(ArrayList<String> terrArr) {
		mTerritories = terrArr;
	}

}
