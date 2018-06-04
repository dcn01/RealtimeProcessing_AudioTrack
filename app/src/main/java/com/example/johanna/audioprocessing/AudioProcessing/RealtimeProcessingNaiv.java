package com.example.johanna.audioprocessing.AudioProcessing;

import android.util.Log;


import com.example.johanna.audioprocessing.AudioProcessing.IRealtimeProcessing;

import java.util.ArrayList;
import java.util.Arrays;

import be.tarsos.dsp.util.fft.FFT;
import be.tarsos.dsp.util.fft.FloatFFT;

public class RealtimeProcessingNaiv implements IRealtimeProcessing {

    private static final String TAG = "NaivRealtimeProcessing";


    @Override
    public float[][] calculateNextBlock(float[][] audioBlock) {
        return new float[0][];
    }

    @Override
    public float[][] processNextFilter(float[][] audioData, float[][] filter) {

        //TODO: just for testing here
        float[] rare_L = getDimensionOf2dArray(audioData, 0, 0, 48000 / 4);
        float[] rare_R = getDimensionOf2dArray(audioData, 1, 0, 48000 / 4);


//        float[] rare_L = getDimensionOf2dArray(audioData, 0);
//        float[] rare_R = getDimensionOf2dArray(audioData, 1);

        float[] filter_L = getDimensionOf2dArray(filter, 0);
        float[] filter_R = getDimensionOf2dArray(filter, 1);

        float[] folded_L = convolute(rare_L, filter_L, filter_L.length);
        float[] folded_R = convolute(rare_R, filter_R, filter_R.length);

        float[][] result = {folded_L, folded_R};

        return result;

    }

    @Override
    public void fadeToNextOutput() {

    }

    private float[] convolute(float[] u, float[] v, int blockSize) {
        //choose smaller lenght
        int len = 0;
        float[] smaller;
        float[] bigger;
        if (v.length < u.length) {
            len = v.length;
            smaller = v;
            bigger = u;
        } else {
            len = u.length;
            smaller = u;
            bigger = v;
        }

        try {

            float[] convoluted = new float[bigger.length];
            //split into blocks which match to the smaller array size
            FloatFFT fft = new FloatFFT(len);
            fft.realForward(smaller);    //transform u

            for (int i = 0; i < bigger.length - len; i = i + len) {
                float[] partOfBigger = Arrays.copyOfRange(bigger, i, i + len);
                fft.realForward(partOfBigger);

                float[] product = multiplyArrays(partOfBigger, smaller);

                float[] partConv = product;
                fft.realInverse(partConv, true);  //scale?

                System.arraycopy(partConv, 0, convoluted, i + len, partConv.length);

            }
            return convoluted;

        } catch (Exception e) {
            Log.d(TAG, "Exception of Type " + e.getClass() + "occured while convolution. \n"
                    + "Message: " + e.getMessage());
            e.printStackTrace();
        }
        return new float[0];
    }

    public float[] getDimensionOf2dArray(float[][] array, int dimesion) {
        float[] newArray = new float[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i][dimesion];
        }

        return newArray;
    }

    public float[] getDimensionOf2dArray(float[][] array, int dimesion, int fromIndex, int toIndex) {

        float[] newArray = new float[toIndex - fromIndex];
//        float[] newArray = new float[array[0].length];

        for (int i = fromIndex; i < toIndex; i++) {
            newArray[i - fromIndex] = array[dimesion][i - fromIndex];
        }
//        for (int i = 0; i < array[0].length; i++) {
//            newArray[i] = array[i][dimesion];
//        }

//        return newArray;





        return newArray;
    }

    public float[] multiplyArrays(float[] u, float[] v) throws IllegalArgumentException {
        if (u.length != v.length)
            throw new IllegalArgumentException("Lengths of input arguments are not equal. Cannot multiply.");

        float[] result = new float[u.length];
        for (int i = 0; i < u.length; i++) {
            result[i] = u[i] * v[i];
        }
        return result;
    }
}
