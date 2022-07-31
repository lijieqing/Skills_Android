package hua.lee.skills.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import hua.lee.skills.databinding.FragmentMediaBinding;

public class MediaFragment extends Fragment {
    private static final String TAG = "MediaFragment";
    private FragmentMediaBinding binding;
    private MediaPlayer mediaPlayer;
    private final MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.d(TAG, "onError:what=" + what + ",extra=" + extra);
            return false;
        }
    };
    private final MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            Log.d(TAG, "onBufferingUpdate:" + percent);
        }
    };
    private final MediaPlayer.OnPreparedListener prepareListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, "onPrepared");
            mediaPlayer.start();
        }
    };
    private final SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            initMedia();
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            destroyMedia();
        }
    };
    private final MediaPlayer.OnInfoListener infoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            Log.d(TAG, "onInfo:what=" + what + ",extra=" + extra);
            return false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMediaBinding.inflate(inflater, container, false);
        binding.svMedia.getHolder().addCallback(callback);
        int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        binding.svMedia.setLayoutParams(new RelativeLayout.LayoutParams(w, (w * 9) / 16));
        binding.btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBird();
            }
        });
        return binding.getRoot();
    }


    private void initMedia() {
        try {
            if (mediaPlayer == null) {
                Log.d(TAG, "initMedia");
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnPreparedListener(prepareListener);
                mediaPlayer.setOnErrorListener(errorListener);
                mediaPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
                mediaPlayer.setOnInfoListener(infoListener);
                mediaPlayer.setSurface(binding.svMedia.getHolder().getSurface());
                // set data Source
                mediaPlayer.setDataSource("https://media.w3.org/2010/05/sintel/trailer.mp4");
                mediaPlayer.setLooping(true);
                mediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "initMedia:" +  e.toString());
        }
    }

    private void destroyMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playBird() {
        try {
            mediaPlayer.reset();
            // mediaPlayer.setDataSource("https://v-cdn.zjol.com.cn/276984.mp4");
            // 因为 Android 系统的安全限制，http 请求需要声明 usesCleartextTraffic=true
            mediaPlayer.setDataSource("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.d(TAG, "playBird:" +  e.toString());
            e.printStackTrace();
        }
    }
}
