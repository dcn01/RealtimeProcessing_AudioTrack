package com.example.johanna.audioprocessing.AudioOutput;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AudioThread extends AsyncTask {

    private static final String TAG = "MyAudioThread";

    private AudioTrack player;
    private float[][] audioFile;
    private float[][] filter;
    private boolean running;
    private boolean filterActive = false;
    private int blocksize = 48000 * 10;
    private Boolean playFinished = true;

    public AudioThread(float[][] audioFile, float[][] filter) {
        this();
        this.audioFile = audioFile;
        this.filter = filter;
        if (this.filter != null && this.filter.length != 0)
            filterActive = true;


    }

    public AudioThread(float[][] audioFile) {
        this();
        this.audioFile = audioFile;
        this.filterActive = false;
    }

    private AudioThread() {
        super();
        int outputBufferSize = AudioTrack.getMinBufferSize(48000,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        player = new AudioTrack(AudioManager.STREAM_MUSIC, 48000
                , AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_FLOAT
                , outputBufferSize, AudioTrack.MODE_STREAM);
        player.setNotificationMarkerPosition(100 - 1);
        player.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {

            @Override
            public void onMarkerReached(AudioTrack audioTrack) {
                System.out.println("Finished audioOutput");

                // Do something
                // If the condition is true, do the following:
//                synchronized (playFinished) {
                    playFinished = true;
//                    playFinished.notify();
//                }
            }

            @Override
            public void onPeriodicNotification(AudioTrack audioTrack) {

            }
        });

    }



    @Override
    protected Object doInBackground(Object[] objects) {
        //TODO
        System.out.println("Background worker started.");
        running = true;
        //Process
        if (filterActive) {
            //filter audioFile
        }
        //Output
        while (running) {
            //do some work
//            synchronized (playFinished) {
//                try {
                    // Calling wait() will block this thread until another thread
                    // calls notify() on the object.
                    if (playFinished){
                        playOutAudio();
                        Log.v(TAG, "Wait for audio to finish from now.");
                    }

//                    playFinished.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    // Happens if someone interrupts your thread.
//                }
//            }
        }
        System.out.println("Background worker broke up.");
        return null;
    }

    private void playOutAudio() {
        //synchronized (playFinished) {

            if (running && playFinished) {
                System.out.println("Init audioOutput");

                float[] firstCh = this.getDimensionOf2dArray(audioFile, 0);
                float[] secondCh = this.getDimensionOf2dArray(audioFile, 1);
//                player.flush();
                player.write(firstCh, 0, audioFile.length, AudioTrack.WRITE_BLOCKING);
                player.write(secondCh, 0, audioFile.length, AudioTrack.WRITE_BLOCKING);
                player.play();          //is it waiting here?
                System.out.println("Start audioOutput");
                playFinished = false;
            }
            //playFinished.notify();
        //}
    }


    public void stop() {
        running = false;
        Log.v(TAG, "Stop running.");
    }

    private float[] getDimensionOf2dArray(float[][] array, int dimesion) {
        float[] newArray = new float[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i][dimesion];
        }

        return newArray;
    }
}
