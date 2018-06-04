package com.example.johanna.audioprocessing.AudioProcessing;

public interface IRealtimeProcessing {

    public float[][] calculateNextBlock(float[][] audioBlock);

    public  float[][] processNextFilter(float[][] audioData, float[][] filter);

    public void fadeToNextOutput();

}
