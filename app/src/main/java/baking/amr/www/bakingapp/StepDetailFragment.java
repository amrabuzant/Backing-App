package baking.amr.www.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by amr on 11/15/17.
 */

public class StepDetailFragment extends Fragment {
    private Step step;
    public SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;

    private TextView stepSecription;
    private ImageView thumbnail;
    private long position;


    public StepDetailFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_step_detail,container,false);
        if(savedInstanceState!=null)
        {
            step = savedInstanceState.getParcelable("step");
            position = savedInstanceState.getLong("position");
        }

        stepSecription=(TextView)rootView.findViewById(R.id.stepDescriptionTextView);
        simpleExoPlayerView=(SimpleExoPlayerView)rootView.findViewById(R.id.videoPlayerView);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        stepSecription.setText(step.getDescription());
        if (!step.getVideoURL().isEmpty()) {
            initializePlayer(Uri.parse(step.getVideoURL()));

        }
        else simpleExoPlayerView.setVisibility(View.GONE);


        if (!step.getThumbnailURL().isEmpty()){
            Uri uri=Uri.parse(step.getThumbnailURL()).buildUpon().build();
            thumbnail=(ImageView)rootView.findViewById(R.id.thumbImageView);
            Picasso.with(getContext()).load(uri).into(thumbnail);


        }






        return rootView;
    }

    public void setStep(Step step) {
        this.step = step;
    }




    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (position > 0 ) simpleExoPlayer.seekTo(position);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer!=null) {
            position = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putLong("position",position);
        outState.putParcelable("step",step);
    }



}