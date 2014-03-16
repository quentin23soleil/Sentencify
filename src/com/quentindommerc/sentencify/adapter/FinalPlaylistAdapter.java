package com.quentindommerc.sentencify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.adapter.TrackAdapter.ViewHolder;
import com.quentindommerc.sentencify.bean.Track;
import com.quentindommerc.sentencify.bean.Word;

public class FinalPlaylistAdapter extends ArrayAdapter<Word> {
	private final LayoutInflater mInflater;

	public FinalPlaylistAdapter(Context ct) {
		super(ct, 0);
		mInflater = LayoutInflater.from(ct);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
			holder.select.setVisibility(View.GONE);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final Track t = getItem(position).getSelectedTrack();
		holder.title.setText(t.getName());
		holder.album.setText(t.getAlbum().getName());
		holder.artist.setText(t.getArtist().get(0).getName());
		return v;

	}
}
