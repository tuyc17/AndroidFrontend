package com.example.project.web;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpReq {
    private static final String server_url = "http://101.37.67.13:8080";

    private static Request request;

    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    // 请求的Cookie处理
    private static CookieJar cookieJar= new CookieJar() {
        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            cookieStore.put(httpUrl.host(), list);
        }

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            List<Cookie> cookies = cookieStore.get(httpUrl.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };

    public static void sendOkHttpGetRequest(String url, okhttp3.Callback callback)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();

        request = new Request.Builder().url(server_url + url).build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpPostRequest(String url, okhttp3.Callback callback, HashMap<String,String> params)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();
        FormBody.Builder builder = new FormBody.Builder();
        for(String key:params.keySet())
        {
            builder.add(key, params.get(key));
        }
        RequestBody requestBody=builder.build();

        request = new Request.Builder().url(server_url + url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void uploadImg(String url, okhttp3.Callback callback, int id, File img)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), img);
        MultipartBody multipartBody =  new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("articleId", String.valueOf(id))
                .addFormDataPart("file", img.getName(), requestBody)
                .build();

        request = new Request.Builder().url(server_url + url).post(multipartBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

//    public static String getFilePathByUri(Context context, Uri uri) {
//        String path = null;
//        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
//        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (DocumentsContract.isDocumentUri(context, uri)) {
//                if (isExternalStorageDocument(uri)) {
//                    // ExternalStorageProvider
//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//                    final String type = split[0];
//                    if ("primary".equalsIgnoreCase(type)) {
//                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
//                        return path;
//                    }
//                } else if (isDownloadsDocument(uri)) {
//                    // DownloadsProvider
//                    final String id = DocumentsContract.getDocumentId(uri);
//                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
//                            Long.valueOf(id));
//                    path = getDataColumn(context, contentUri, null, null);
//                    return path;
//                } else if (isMediaDocument(uri)) {
//                    // MediaProvider
//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//                    final String type = split[0];
//                    Uri contentUri = null;
//                    if ("image".equals(type)) {
//                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("video".equals(type)) {
//                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("audio".equals(type)) {
//                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                    }
//                    final String selection = "_id=?";
//                    final String[] selectionArgs = new String[]{split[1]};
//                    path = getDataColumn(context, contentUri, selection, selectionArgs);
//                    return path;
//                }
//            }
//        }else {
//            // 以 file:// 开头的
//            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
//                path = uri.getPath();
//                return path;
//            }
//            // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
//            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
//                if (cursor != null) {
//                    if (cursor.moveToFirst()) {
//                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        if (columnIndex > -1) {
//                            path = cursor.getString(columnIndex);
//                        }
//                    }
//                    cursor.close();
//                }
//                return path;
//            }
//        }
//        return null;
//    }
//
//    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {column};
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int column_index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(column_index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }
//
//    private static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    private static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    private static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
}
