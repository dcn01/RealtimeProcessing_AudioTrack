package com.example.johanna.audioprocessing.AudioProcessing;

public interface IRealtimeProcessing {

    public float[][] calculateNextBlock(float[][] audioBlock);

    public  void setFilter(float[][] filter);
}
