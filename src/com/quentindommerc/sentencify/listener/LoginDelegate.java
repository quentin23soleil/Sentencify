package com.quentindommerc.sentencify.listener;

public interface LoginDelegate {

	public void onLogin();

	public void onLoginFailed(String message);
}
