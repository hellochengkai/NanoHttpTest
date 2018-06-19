package com.example.chengkai.nanohttptest.WebServer;

import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chengkai on 18-6-17.
 */

public class MyServerHelper {


    public final static String HTTP_ROOT_PATH = "/sdcard";
    private final static float KB = 1024;
    private final static float MB = 1024 * KB;
    public final static int HTTP_PORT = 8080;
    private static final String TAG = "MyServerHelper";
    public static String getFileListHtml(String folderName) {

        folderName = HTTP_ROOT_PATH + folderName;
        Log.d(TAG, "getFileListHtml: " + folderName);
        File rootDir = new File(folderName);
        if(!rootDir.exists()){
            rootDir = new File(HTTP_ROOT_PATH);
        }
        if(!rootDir.isDirectory()){
            Log.d(TAG, "getFileListHtml: " + folderName + "is not a Directory");
            return null;
        }

        String answer = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>sdcard0 - TECNO P5 - WiFi File Transfer Pro</title>";
        StringBuilder answerBuilder = new StringBuilder(answer);
        answerBuilder.append("<body>");
        answerBuilder.append("<table width=\"100%\">");
        answerBuilder.append("<tr align=\"LEFT\">");
        answerBuilder.append("<th>");
        answerBuilder.append("文件名");
        answerBuilder.append("</th>");
        answerBuilder.append("<th>");
        answerBuilder.append("修改时间");
        answerBuilder.append("</th>");
        answerBuilder.append("<th>");
        answerBuilder.append("大小");
        answerBuilder.append("</th>");
        answerBuilder.append("</tr>");
        answerBuilder.append(getFileListHtml(rootDir.listFiles(),TYPE_DIR));
        answerBuilder.append(getFileListHtml(rootDir.listFiles(),TYPE_FILE));
        answerBuilder.append("</table>");
        answerBuilder.append("</body>");
        answerBuilder.append("</head></html>");
        return answerBuilder.toString();
    }

    private static final int TYPE_NULL = -1;
    private static final int TYPE_DIR = 0;
    private static final int TYPE_FILE = 1;
    private static String getFileListHtml(File[] files2,int type)
    {
        StringBuilder answerBuilder = new StringBuilder();
        for (File detailsOfFiles : files2) {
            if(!detailsOfFiles.exists()){
                continue;
            }
            String url = "http://" + NetWorkHelper.getLocalHost() + ":" + HTTP_PORT;
            url += detailsOfFiles.getAbsolutePath().replace(HTTP_ROOT_PATH ,"");

            String hrefString = null;
            if(detailsOfFiles.isDirectory()){
                hrefString = "<a href=\"" + url
                        + "\" alt = \"\">" + detailsOfFiles.getName() + "\\"
                        + "</a>";
            }else {
                hrefString = "<a href=\"" + url
                        + "\" alt = \"\">" + detailsOfFiles.getName()
                        + "</a>";
            }
            if(detailsOfFiles.isDirectory()){
                if (type == TYPE_FILE){
                    continue;
                }
            }else {
                if(type == TYPE_DIR){
                    continue;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(detailsOfFiles.lastModified());
            String filetime = sdf.format(cal.getTime());
            answerBuilder.append("<tr>");
            answerBuilder.append("<td>");
            answerBuilder.append(hrefString);
            answerBuilder.append("</td>");
            answerBuilder.append("<td>");
            answerBuilder.append(filetime);
            answerBuilder.append("</td>");
            if(detailsOfFiles.isFile()) {
                long fileLength = detailsOfFiles.length();
                String strLength = "";
                if(fileLength > MB) {
                    float size = fileLength / MB;
                    size = (float) (Math.round(size * 100)) / 100;
                    strLength = new String(size + "MB");
                } else if (fileLength > KB) {
                    float size = fileLength / KB;
                    size = (float) (Math.round(size * 100)) / 100;
                    strLength = new String(size + "KB");
                } else {
                    strLength = String.valueOf(fileLength + "B");
                }
                answerBuilder.append("<td>");
                answerBuilder.append(strLength);
                answerBuilder.append("</td>");
            }
            answerBuilder.append("</tr>");
        }
        return answerBuilder.toString();
    }
}
