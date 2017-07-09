package com.example.administrator.okhttpdownload;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class IOUtil {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
