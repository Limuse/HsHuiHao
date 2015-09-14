package com.huihao.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.RawRes;

import com.huihao.R;
import com.leo.base.util.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huisou on 2015/9/12.
 */
public class PccDb {
    //数据库存储路径
    String filePath="data/data/com.android.hshuihao/database/pcc.db";
    //数据库存放的文件夹data/data/com.android.hshuihoa/database/下面
    String pathStr="data/data/com.android.hshuihao/database/";
    SQLiteDatabase database;
    public SQLiteDatabase openDatabase(Context context){
        System.out.println("filePath:"+filePath);
        File jhPath=new File(filePath);

        //查看数据库文件是否存在
        if(jhPath.exists()){
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath,null);
        }else{
            //不存在则先创建文件夹
            File path=new File(pathStr);
            if(path.mkdir()){
                L.i("test","创建成功");
            }else{
                L.i("test","创建失败");
            }
            try{

                InputStream is= context.getResources().openRawResource(R.raw.pcc);
                FileOutputStream fos=new FileOutputStream(jhPath);
                byte[] buffer=new byte[1024];
                int count=0;
                while((count=is.read(buffer))>0){
                    fos.write(buffer,0,count);
                }
                fos.flush();
                fos.close();
                is.close();
            }catch (IOException e){
                e.printStackTrace();
                return  null;
            }
            return openDatabase(context);
        }

    }

}
