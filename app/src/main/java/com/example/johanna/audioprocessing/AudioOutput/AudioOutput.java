package com.example.johanna.audioprocessing.AudioOutput;
import android.content.Context;
import android.os.AsyncTask;

import com.example.johanna.audioprocessing.TextReader;


public class AudioOutput{


    private AudioThread audioThread;
    private  Context context;

    public AudioOutput(Context context){
        this.context = context;
        //TODO init audiotrack
    }

    private  void createOutputThread(){
        if(audioThread!= null) {
            audioThread.cancel(true);
            audioThread.stop();



        }
        audioThread = new AudioThread(this.getAudioSamples(), new float[0][0]); //TODO give files with filter
    }

    public void startCurrentOutputThread(){
        createOutputThread();
        audioThread.execute();
    }

    public void stopAudioOutputThread(){
        if(audioThread!= null) {
            audioThread.cancel(true);
            audioThread.stop();
        }
    }


    private float[][] getAudioSamples(){
        float[][] samples = (new TextReader(context)).scanCSV();
        return  samples;
    }



}
