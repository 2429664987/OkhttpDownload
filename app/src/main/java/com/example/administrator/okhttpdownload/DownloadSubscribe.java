package com.example.administrator.okhttpdownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
    private DownloadInfo downloadInfo;
    private HashMap<String, Call> downCalls;//用来存放各个下载的请求
    private OkHttpClient mClient;//OKHttpClient;

    public DownloadSubscribe(){
        downCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
    }

    public DownloadSubscribe(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    @Override
    public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
        String url = downloadInfo.getUrl();
        long downloadLength = downloadInfo.getProgress();//已经下载好的长度
        long contentLength = downloadInfo.getTotal();//文件的总长度
        //初始进度信息
        e.onNext(downloadInfo);

        Request request = new Request.Builder()
                //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                .url(url)
                .build();
        Call call = mClient.newCall(request);
        downCalls.put(url, call);//把这个添加到call里,方便取消
        Response response = call.execute();

        File file = new File(MyApp.sContext.getFilesDir(), downloadInfo.getFileName());
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        try {
            is = response.body().byteStream();
            fileOutputStream = new FileOutputStream(file, true);
            byte[] buffer = new byte[2048];//缓冲数组2kB
            int len;
            while ((len = is.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                downloadLength += len;
                downloadInfo.setProgress(downloadLength);
                e.onNext(downloadInfo);
            }
            fileOutputStream.flush();
            downCalls.remove(url);
        } finally {
            //关闭IO流
            IOUtil.closeAll(is, fileOutputStream);

        }
        e.onComplete();//完成
    }
}
