package com.example.johanna.audioprocessing.AudioOutput;
import android.content.Context;

import com.example.johanna.audioprocessing.AudioProcessing.RealtimeProcessingNaiv;
import com.example.johanna.audioprocessing.R;
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
        //TEST
        (new RealtimeProcessingNaiv()).processNextFilter(this.getBaseAudioSamples(), this.getFilter(60));

        audioThread = new AudioThread(this.getBaseAudioSamples(), this.getFilter(60));

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


    private float[][] getBaseAudioSamples(){
        float[][] samples = (new TextReader(context)).scanCSV(context.getResources().openRawResource(R.raw.castanetes), 48000*10, 2 );
        return  samples;
    }

    private float[][] getFilter(int degrees){
        //TODO mangage for 360° directions
        float[][] samples = (new TextReader(context)).scanCSV(context.getResources().openRawResource(R.raw.sixtydeg_hrir), 2400, 2 );
        return  samples;
    }



}
