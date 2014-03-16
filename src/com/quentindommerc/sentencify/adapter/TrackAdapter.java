package com.quentindommerc.sentencify.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.targets.ViewTarget;
import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.bean.Track;
import com.quentindommerc.sentencify.listener.OnTrackSelected;
import com.quentindommerc.sentencify.utils.Utils;

public class TrackAdapter extends ArrayAdapter<Track> {

	private final LayoutInflater mInflater;
	private final OnTrackSelected mListener;
	private final Context mContext;
	private Button mFirstButton;

	public TrackAdapter(Context context, OnTrackSelected listener) {
		super(context, 0);
		mInflater = LayoutInflater.from(context);
		mListener = listener;
		mContext = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mInflater.inflate(R.layout.r_track, parent, false);
			holder = new ViewHolder();
			holder.img = (ImageView) v.findViewById(R.id.img);
			holder.title = (TextView) v.findViewById(R.id.title);
			holder.album = (TextView) v.findViewById(R.id.album);
			holder.artist = (TextView) v.findViewById(R.id.artist);
			holder.select = (Button) v.findViewById(R.id.select);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final Track t = getItem(position);
		holder.title.setText(t.getName());
		holder.album.setText(t.getAlbum().getName());
		holder.artist.setText(t.getArtist().get(0).getName());
		if (t.isSelected())
			holder.select.setBackgroundResource(R.drawable.sentencify_btn_default_holo_light);
		else
			holder.select.setBackgroundResource(R.drawable.grey_btn_default_holo_light);
		holder.select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.trackSelected(t, position);
			}
		});
		if (position == 0)
			mFirstButton = holder.select;

		return v;
	}

	public void showHelp() {
		Utils.setSharedPref(mContext, "help_list", true);
		ViewTarget target = new ViewTarget(mFirstButton);
		ShowcaseView.insertShowcaseView(target, (Activity) mContext,
				R.string.showcase_sentence_title, R.string.showcase_sentence_details);

	}

	static class ViewHolder {
		ImageView img;
		TextView title;
		TextView album;
		TextView artist;
		Button select;
	}

}
