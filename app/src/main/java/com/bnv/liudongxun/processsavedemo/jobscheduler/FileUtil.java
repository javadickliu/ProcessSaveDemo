package com.bnv.liudongxun.processsavedemo.jobscheduler;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 这个工具类主简化了读取设备存储内文件内容,这里主要是针对内部存储进行了test,其他未测
 * 后期会添加一些对file文件进行多样化操作的方法.
 * Created by liudongxun on 2018/1/15.
 */

public class FileUtil {
    private static final String TAG = "FileUtil";
    /**
     * 此方法作用是往android设备指定的文件写入字符串数据.内部存储和外部存储sd卡都可以,提供存储的路径即可.
     *
     * @param fileppPathname 要写入数据的文件目录,字符串形式
     * @param fileName       具体写入数据的文件名字
     * @param string         要写入的字符串数据
     * @param append         覆盖原来的数据还是在之前的数据后面继续写入.
     */
    public static synchronized void writeToFile(String fileppPathname, String fileName, String string, boolean append) {//synchronized同步影响效率
        String filePath = fileppPathname + "/" + fileName;
        Log.d(TAG, "writeToFile: 1111filePath="+filePath);
        File file = new File(filePath);
        File fileDirs = new File(fileppPathname);
        if (!file.exists()) {
            fileDirs.mkdirs();
            try {
                boolean createFileResult=file.createNewFile();
                file.setReadable(true);
                file.setWritable(true);
                Log.d(TAG, "writeToFile: createFileResult="+createFileResult);
            } catch (IOException e) {
                Log.d(TAG, "writeToFile: IOException="+e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(filePath, append);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string);
            bufferedWriter.close();
         //   LogUtils.d("fileUtil", "writeToFile:OK ");
            Log.d(TAG, "writeToFile: ");
        } catch (IOException e) {
        //    LogUtils.d("fileUtil", "writeToFile: exception= " + e.toString());
            Log.d(TAG, "writeToFile: error="+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 这个方法主要是从指定file读取数据.
     *
     * @param filePathname 要读数据的文件的目录,不是绝对路径
     * @param fileName     要读数据的文件名字
     */
    public static String readFromFile(String filePathname, String fileName) {
        String filePath = filePathname + "/" + fileName;
        BufferedReader reader = null;
        FileReader fileReader = null;
        StringBuilder content = new StringBuilder();
        try {
            fileReader = new FileReader(filePath);
            reader = new BufferedReader(fileReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 这个方法主要是删除指定目录下的指定文件
     *
     * @param filePathname 要删除的文件的目录,不是绝对路径
     * @param fileName     要删除的文件名字
     * @return 返回false表示要删除的文件不存在, 返回true表示删除成功
     */
    public static boolean deleteFile(String filePathname, String fileName) {
        String filePath = filePathname + "/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {

            return  file.delete();
        }
    }

    /**
     * 这个方法主要是删除指定目录(文件夹)和其目录下所有文件
     *
     * @param filePathname 要删除的目录路径
     * @return 返回false表示要删除的文件不存在或者删除失败, 返回true表示删除成功
     */
    public static boolean deleteDir(String filePathname) {
        String filePath;
        filePath = filePathname;
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            File[] fileArray = file.listFiles();
            for (File index : fileArray) {
                return index.delete();
            }
            return file.delete();
        }
    }

}
