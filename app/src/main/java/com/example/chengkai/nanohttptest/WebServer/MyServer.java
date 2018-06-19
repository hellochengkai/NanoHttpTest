package com.example.chengkai.nanohttptest.WebServer;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fi.iki.elonen.NanoHTTPD.Response.*;
import fi.iki.elonen.NanoHTTPD;
/**
 * Created by chengkai on 18-5-23.
 */

public class MyServer  extends NanoHTTPD {
    private static final String TAG = "MyServer";
    public MyServer(int port) {
        super(port);
        mimeTypes();
    }

    @Override
    public Response serve(IHTTPSession session) {
        String fileListHtml = MyServerHelper.getFileListHtml(session.getUri());
        if(fileListHtml == null){
            String fileName = MyServerHelper.HTTP_ROOT_PATH + session.getUri();
            Log.d(TAG, "download file : " + fileName);
            File file = new File(fileName);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Response response = newFixedLengthResponse(NanoHTTPD.Response.Status.OK, getMimeTypeForFile(fileName), fileInputStream,file.length());
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            return response;
        } else {
            return newFixedLengthResponse(fileListHtml);
        }
    }
}
