package com.xrea.s601.hdsd.line_bird.functions;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 1621013 on 2017/04/27.
 */

public class JsonLoader extends AsyncTaskLoader<JSONObject> {

    private String urlText;

    public JsonLoader(Context context, String urlText) {
        super(context);
        this.urlText = urlText;
    }

    @Override
    public JSONObject loadInBackground() {
        HttpURLConnection connection = null;

        try{
            URL url = new URL(urlText);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
        }
        catch(MalformedURLException exception) {
            Log.e("URL","MalformedURLException");
        }
        catch(IOException exception) {
            Log.e("IO","IOException");
        }
        try{
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while((length = inputStream.read(buffer)) != -1) {
                if(length > 0)
                        outputStream.write(buffer, 0, length);
            }

            JSONObject json = new JSONObject(new String(outputStream.toByteArray()));
            return json;
        } catch (IOException exception) {
            Log.e("IO","IOException");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
