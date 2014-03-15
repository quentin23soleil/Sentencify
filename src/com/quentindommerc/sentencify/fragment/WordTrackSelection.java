package com.quentindommerc.sentencify.fragment;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quentindommerc.sentencify.R;
import com.quentindommerc.sentencify.adapter.TrackAdapter;
import com.quentindommerc.sentencify.bean.Track;
import com.quentindommerc.sentencify.bean.Word;
import com.quentindommerc.sentencify.listener.OnApiResult;
import com.quentindommerc.sentencify.listener.OnTrackSelected;
import com.quentindommerc.sentencify.listener.OnTrackSelectedFromPage;
import com.quentindommerc.sentencify.utils.Api;
import com.quentindommerc.sentencify.utils.ApiParser;
import com.quentindommerc.sentencify.utils.Logger;

public class WordTrackSelection extends Fragment implements OnTrackSelected {

	private Word mWord;
	private ListView mList;
	private TrackAdapter mAdapter;
	private ProgressBar mProgress;
	private OnTrackSelectedFromPage mListener;

	public static WordTrackSelection newInstance(Word w) {
		Bundle b = new Bundle();
		b.putParcelable("word", w);
		WordTrackSelection f = new WordTrackSelection();
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListener = (OnTrackSelectedFromPage) getActivity();
		mWord = getArguments().getParcelable("word");
		mAdapter = new TrackAdapter(getActivity(), this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_word_track_selection, container, false);
		mList = (ListView) v.findViewById(R.id.list);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getTrackList();
	}

	private void getTrackList() {
		Api.getTracks(getActivity(), mWord.getWord(), new OnApiResult() {

			@Override
			public void success(int code, String s) {
				mProgress.setVisibility(View.VISIBLE);
				new AddTracks().execute(s);
			}

			@Override
			public void error(int code, String s) {
			}
		});
	}

	private class AddTracks extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mProgress.setVisibility(View.GONE);
			mList.setAdapter(mAdapter);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// TODO: put if track name contains original word in preference
		@Override
		protected Void doInBackground(String... params) {
			mWord.getTracks().clear();
			mAdapter.clear();
			String s = params[0];
			try {
				JSONObject obj = new JSONObject(s);
				JSONArray tracks = obj.getJSONArray("tracks");
				TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(
						Context.TELEPHONY_SERVICE);

				String ISO2 = manager.getSimCountryIso();

				String ISO3 = new Locale("", ISO2).getISO3Country();
				Logger.d(ISO3);
				for (int i = 0; i < tracks.length(); i++) {
					Track t = ApiParser.parseTrack(tracks.getJSONObject(i));
					if (t.getAlbum().getTerritories().contains(ISO3))
						// if
						// (t.getName().toLowerCase().contains(mWord.getWord().toLowerCase()))
						mWord.addTrack(t);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mAdapter.addAll(mWord.getTracks());
			return null;
		}
	}

	@Override
	public void trackSelected(Track t, int pos) {
		for (int i = 0; i < mAdapter.getCount(); i++) {
			mAdapter.getItem(i).setSelected(false);
		}
		mAdapter.getItem(pos).setSelected(true);
		mAdapter.notifyDataSetChanged();
		mWord.setSelectedTrack(t);
		Toast.makeText(getActivity(), t.getName() + " selected", Toast.LENGTH_LONG).show();
		mListener.trackSelected(mWord);
	}

}
