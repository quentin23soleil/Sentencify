package com.quentindommerc.sentencify.listener;

public interface PlayerUpdateDelegate {

	public void onEndOfTrack();

	public void onPlayerPositionChanged(float position);

	public void onPlayerPause();

	public void onPlayerPlay();

	public void onTrackStarred();

	public void onTrackUnStarred();
}
