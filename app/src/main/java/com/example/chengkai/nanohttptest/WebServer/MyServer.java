package com.example.chengkai.nanohttptest.WebServer;

import android.util.Log;
import java.io.ByteArrayInputStream;
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
        if(session.getMethod().equals(Method.POST)){
            String responseJson = "{\"status\":1,\"msg\":\"OK\"}";
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "text/html", responseJson);
        }else if(session.getMethod().equals(Method.GET)){

        }
        return newFixedLengthResponse(Status.OK,NanoHTTPD.mimeTypes().get("text"),"chengkai test");
//      return newFixedLengthResponse(MyServerHelper.getFileListHtml(session.getUri()));
    }
}
