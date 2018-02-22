package com.example.nambd.mvvm.data.source.remote;

import android.os.AsyncTask;


import com.example.nambd.mvvm.data.source.TrackDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoadAsync extends AsyncTask<String, String, String> {
    private TrackDataSource.Callback<String> mCallback;

    public LoadAsync(TrackDataSource.Callback<String> callback) {
        mCallback = callback;
    }


    @Override
    protected String doInBackground(String... strings) {
        String urlStr = strings[0];
        String result = null;
        try {
            URL url = new URL(urlStr);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            result = readStream(inputStream);
        } catch (IOException e) {
        }
        return result;
    }

    private String readStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer builder = new StringBuffer();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } finally {
            inputStream.close();
        }
        return builder.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            mCallback.onGetFailure(null);
        } else {
            mCallback.onGetSuccess(result);
            mCallback.onComplete();
        }
    }
}
