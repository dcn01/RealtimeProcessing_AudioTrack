package com.example.johanna.audioprocessing.AudioOutput;

import android.media.AudioTrack;
import android.os.AsyncTask;

public class AudioThread extends AsyncTask {

    private AudioTrack myTrack;
    private float[][] audioFile;
    private float[][] filter;
    private boolean running;

    public AudioThread(float[][] audioFile, float[][] filter){
        this.audioFile = audioFile;
        this.filter = filter;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //TODO
        System.out.println("Background worker started.");
        running = true;
        //Process
        //Output
        while (running){
            //do some work
        }
        System.out.println("Background worker broke up.");
        return null;
    }


    public void stop(){
        running = false;
    }
}
