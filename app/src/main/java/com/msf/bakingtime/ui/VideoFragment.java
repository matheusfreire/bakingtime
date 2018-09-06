package com.msf.bakingtime.ui;


import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String NEXT_STEP = "nextStep";
    private static final String CURRENT_STEP = "currentStep";
    private static final String CLASS_NAME = VideoFragment.class.getSimpleName().toUpperCase();

    private Step step;

    @BindView(R.id.video_step)
    SimpleExoPlayerView mExoPlayerView;

    private SimpleExoPlayer mSimpleExoPlayer;
    private MediaSessionCompat mMediaSessionCompat;
    private PlaybackStateCompat.Builder mStateBuilder;

    public VideoFragment() {
    }


    public static VideoFragment newInstance(Step step, Step nextStep) {
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_STEP, step);
        args.putParcelable(NEXT_STEP, nextStep);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(CURRENT_STEP)) {
            step = getArguments().getParcelable(CURRENT_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);
        initMediaSession();
        initPlayer(step.getVideoUrl());
        return rootView;
    }


    private void initPlayer(String url){
        if(mSimpleExoPlayer == null){
            TrackSelector defaultTrackSelector = new DefaultTrackSelector();
            LoadControl defaultLoadControl = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), defaultTrackSelector,
                    defaultLoadControl);
            mExoPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "bakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initMediaSession(){
        mMediaSessionCompat = new MediaSessionCompat(getContext(),CLASS_NAME);
        mMediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSessionCompat.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                            PlaybackStateCompat.ACTION_PLAY |
                            PlaybackStateCompat.ACTION_PAUSE |
                            PlaybackState.ACTION_SKIP_TO_PREVIOUS |
                            PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSessionCompat.setPlaybackState(mStateBuilder.build());
        mMediaSessionCompat.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAndRelease();
        mMediaSessionCompat.setActive(false);
    }

    private void stopAndRelease(){
        mSimpleExoPlayer.stop();
        mSimpleExoPlayer.release();
        mSimpleExoPlayer = null;
    }

}
