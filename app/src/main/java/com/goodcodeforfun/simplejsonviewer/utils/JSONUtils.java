package com.goodcodeforfun.simplejsonviewer.utils;

import android.widget.Toast;

import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.SimpleJSONViewerApplication;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by snigavig on 15.07.16.
 */

public class JSONUtils {
    public static JSONArray getJSONFromFile(String filename) throws EmptyJSONFileException {
        String json;
        try {
            InputStream is = SimpleJSONViewerApplication.getInstance().getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            int no_bytes_read = is.read(buffer);

            is.close();

            if (no_bytes_read > 0)
                json = new String(buffer, "UTF-8");
            else
                throw new EmptyJSONFileException(SimpleJSONViewerApplication.getInstance().getString(R.string.empty_file_error));


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public static double parseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return 0.0d;
            }
        }
        else return 0;
    }

    public static class EmptyJSONFileException extends Exception {

        EmptyJSONFileException(String message) {
            super(message);
        }
    }
}
