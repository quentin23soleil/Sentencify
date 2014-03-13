package com.quentindommerc.sentencify.utils;

public interface OnApiResult {
	public void success(int code, String s);

	public void error(int code, String s);
}
