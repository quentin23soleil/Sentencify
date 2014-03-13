package com.quentindommerc.sentencify.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
	private String mHref;
	private String mName;

	public Artist() {

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

	protected Artist(Parcel in) {
		mHref = in.readString();
		mName = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mHref);
		dest.writeString(mName);
	}

	public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
		@Override
		public Artist createFromParcel(Parcel in) {
			return new Artist(in);
		}

		@Override
		public Artist[] newArray(int size) {
			return new Artist[size];
		}
	};

}
