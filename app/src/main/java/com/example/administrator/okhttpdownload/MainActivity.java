package com.example.administrator.okhttpdownload;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button downloadBtn1, downloadBtn2, downloadBtn3;
    private Button cancelBtn1, cancelBtn2, cancelBtn3;
    private ProgressBar progress1, progress2, progress3;
    private String url1 = "http://192.168.43.246:8080/lieji/musics/aaa.mp3";
    private String url2 = "http://192.168.43.246:8080/lieji/musics/b.mp3";
    private String url3 = "http://192.168.43.246:8080/lieji/musics/cc.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadBtn1 = bindView(R.id.main_btn_down1);
        downloadBtn2 = bindView(R.id.main_btn_down2);
        downloadBtn3 = bindView(R.id.main_btn_down3);

        cancelBtn1 = bindView(R.id.main_btn_cancel1);
        cancelBtn2 = bindView(R.id.main_btn_cancel2);
        cancelBtn3 = bindView(R.id.main_btn_cancel3);

        progress1 = bindView(R.id.main_progress1);
        progress2 = bindView(R.id.main_progress2);
        progress3 = bindView(R.id.main_progress3);

        downloadBtn1.setOnClickListener(this);
        downloadBtn2.setOnClickListener(this);
        downloadBtn3.setOnClickListener(this);

        cancelBtn1.setOnClickListener(this);
        cancelBtn2.setOnClickListener(this);
        cancelBtn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_down1:
                DownloadManager.getInstance().download(url1, new DownLoadObserver() {
                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        progress1.setMax((int) value.getTotal());
                        progress1.setProgress((int) value.getProgress());
                    }

                    @Override
                    public void onComplete() {
                        if(downloadInfo != null){
                            Toast.makeText(MainActivity.this,
                                    downloadInfo.getFileName() + "-DownloadComplete",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.main_btn_down2:
                DownloadManager.getInstance().download(url2, new DownLoadObserver() {
                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        progress2.setMax((int) value.getTotal());
                        progress2.setProgress((int) value.getProgress());
                    }

                    @Override
                    public void onComplete() {
                        if(downloadInfo != null){
                            Toast.makeText(MainActivity.this,
                                    downloadInfo.getFileName() + Uri.encode("下载完成"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.main_btn_down3:

                System.out.println("获取点击事件3");

                DownloadManager.getInstance().download(url3, new DownLoadObserver() {
                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        progress3.setMax((int) value.getTotal());
                        progress3.setProgress((int) value.getProgress());
                    }

                    @Override
                    public void onComplete() {
                        if(downloadInfo != null){
                            Toast.makeText(MainActivity.this,
                                    downloadInfo.getFileName() + Uri.encode("下载完成"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.main_btn_cancel1:
                DownloadManager.getInstance().cancel(url1);
                break;
            case R.id.main_btn_cancel2:
                DownloadManager.getInstance().cancel(url2);
                break;
            case R.id.main_btn_cancel3:
                DownloadManager.getInstance().cancel(url3);
                break;
        }
    }



    //把 findViewById封装了
    private <T extends View> T bindView(@IdRes int id){
        View viewById = findViewById(id);
        return (T) viewById;
    }




}
