package com.example.johanna.audioprocessing;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.StringTokenizer;


public class TextReader {

    Context context;

    public TextReader(Context context) {
        this.context = context;
    }


    public float[][] scanCSV(InputStream file, int length, int width) {     //currently specified for an float[48000][2] array

        InputStream is = null;
        float[][] result = new float[width][length];


        is = file;


        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        StringTokenizer st = null;
        try {

            int linecounter = 0;
            while ((line = reader.readLine()) != null && linecounter < length) {
                st = new StringTokenizer(line, ",");

                //your attributes
                for (int i = 0; i < width; i++) {

                    String nexNumber = st.nextToken();
                    double parsed = Double.valueOf(nexNumber);
//                    result[linecounter][i] = (float) parsed;
                    result[i][linecounter] = (float) parsed;

                }

//                if (st.countTokens() > 2) {
//                    System.out.println("Some Tokens not read in line " + linecounter);
//                }
                ;
                linecounter++;
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }
}
