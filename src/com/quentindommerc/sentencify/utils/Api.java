package com.quentindommerc.sentencify.utils;

import android.content.Context;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class Api {

	private static final String TRACK_SEARCH_URL = "https://ws.spotify.com/search/1/track.json?q=";

	public static void getTracks(Context ct, String word, final OnApiResult listener) {
		Ion.with(ct).load(Api.TRACK_SEARCH_URL + word).asString().withResponse()
				.setCallback(new FutureCallback<Response<String>>() {

					@Override
					public void onCompleted(Exception arg0, Response<String> arg1) {
						if (arg1.getHeaders().getResponseCode() == 200
								|| arg1.getHeaders().getResponseCode() == 304)
							listener.success(arg1.getHeaders().getResponseCode(), arg1.getResult());
						else
							listener.error(arg1.getHeaders().getResponseCode(),
									arg1.getResult() == null ? "" : arg1.getResult());
					}
				});
	}
}
