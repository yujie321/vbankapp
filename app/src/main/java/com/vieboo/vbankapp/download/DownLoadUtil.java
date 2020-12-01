package com.vieboo.vbankapp.download;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * 文件下载
 */
public class DownLoadUtil {

    private static final String TAG = "DownLoadUtil";

    private static String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath() + File.separator;

    public static String mSinglePath = mSaveFolder + "VBankApp";

    public static void downLoad(final Context mContext, String updateUrl, final OnDownloadListener onDownloadListener) {
        deleteFiles(new File(mSaveFolder));
        BaseDownloadTask singleTask = FileDownloader.getImpl().create(updateUrl)
                .setPath(mSinglePath, true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                //.setTag()
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "pending: pending taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //百分比
                        int progress = (int) ((soFarBytes * 1.0 / totalBytes) * 100);
                        onDownloadListener.onDownloading(progress);
                        Log.e(TAG, "pending: progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + progress + ",speed:" + task.getSpeed());
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG, "pending: blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        String pdfUrl = task.getPath() + "/" + "notice.pdf";
                        String fileUrl = task.getPath() + "/" + task.getFilename();
                        renameFile(fileUrl, pdfUrl);
                        ((Activity)mContext).runOnUiThread(() -> {
                            onDownloadListener.onDownloading(100);
                            onDownloadListener.onDownloadSuccess(new File(pdfUrl));
                        });
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG, "pending: completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "pending: paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        onDownloadListener.onDownloadFailed();
                        Log.e(TAG, "pending: error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e(TAG, "pending: warn taskId:" + task.getId());
                    }
                });
        singleTask.start();
    }

    /**
     * 删除文件
     *
     * @param file 删除的文件夹的所在位置
     */
    private static void deleteFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null){
                for (int i = 0; i < files.length; i++) {
                    File f = files[i];
                    deleteFiles(f);
                }
            }
            //如要保留文件夹，只删除文件，注释这行
            boolean delete = file.delete();
        } else if (file.exists()) {
            boolean delete = file.delete();
        }
    }

    private static void renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         *
         * @param file 下载成功后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         *
         * @param progress int
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */
        void onDownloadFailed();
    }

}
