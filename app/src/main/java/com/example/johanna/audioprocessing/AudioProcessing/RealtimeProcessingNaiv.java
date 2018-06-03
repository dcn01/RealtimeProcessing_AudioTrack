package com.example.johanna.audioprocessing.AudioProcessing;

import com.example.johanna.audioprocessing.AudioProcessing.IRealtimeProcessing;

public class RealtimeProcessingNaiv implements IRealtimeProcessing {


    @Override
    public float[][] calculateNextBlock(float[][] audioBlock) {
        return new float[0][];
    }

    @Override
    public void setFilter(float[][] filter) {

    }
}
