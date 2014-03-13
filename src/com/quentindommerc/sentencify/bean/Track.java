package com.quentindommerc.sentencify.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {

	private String mHref;
	private String mName;
	private float mPopularity;
	private float mLength;
	private int mTrackNumber;
	private Album mAlbum;
	private ArrayList<Artist> mArtist;

	public Track() {
		mArtist = new ArrayList<Artist>();
	}

	public Album getAlbum() {
		return mAlbum;
	}

	public void setAlbum(Album album) {
		mAlbum = album;
	}

	public void addArtist(Artist a) {
		mArtist.add(a);
	}

	public ArrayList<Artist> getArtist() {
		return mArtist;
	}

	public void setArtist(ArrayList<Artist> artist) {
		mArtist = artist;
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

	public float getPopularity() {
		return mPopularity;
	}

	public void setPopularity(float popularity) {
		mPopularity = popularity;
	}

	public float getLength() {
		return mLength;
	}

	public void setLength(float length) {
		mLength = length;
	}

	public int getTrackNumber() {
		return mTrackNumber;
	}

	public void setTrackNumber(int trackNumber) {
		mTrackNumber = trackNumber;
	}

	protected Track(Parcel in) {
		mHref = in.readString();
		mName = in.readString();
		mPopularity = in.readFloat();
		mLength = in.readFloat();
		mTrackNumber = in.readInt();
		mAlbum = (Album) in.readValue(Album.class.getClassLoader());
		if (in.readByte() == 0x01) {
			mArtist = new ArrayList<Artist>();
			in.readList(mArtist, Artist.class.getClassLoader());
		} else {
			mArtist = null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mHref);
		dest.writeString(mName);
		dest.writeFloat(mPopularity);
		dest.writeFloat(mLength);
		dest.writeInt(mTrackNumber);
		dest.writeValue(mAlbum);
		if (mArtist == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(mArtist);
		}
	}

	public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
		@Override
		public Track createFromParcel(Parcel in) {
			return new Track(in);
		}

		@Override
		public Track[] newArray(int size) {
			return new Track[size];
		}
	};

}
