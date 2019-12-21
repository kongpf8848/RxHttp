package com.github.kongpf8848.rxhttp.sample.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.github.kongpf8848.permissionhelper.PermissionHelper;
import com.github.kongpf8848.permissionhelper.PermissionInfomation;
import com.github.kongpf8848.rxhttp.RxHttp;
import com.github.kongpf8848.rxhttp.bean.DownloadInfo;
import com.github.kongpf8848.rxhttp.callback.DownloadCallback;
import com.github.kongpf8848.rxhttp.callback.HttpCallback;
import com.github.kongpf8848.rxhttp.callback.UploadCallback;
import com.github.kongpf8848.rxhttp.sample.Constants;
import com.github.kongpf8848.rxhttp.sample.R;
import com.github.kongpf8848.rxhttp.sample.bean.Feed;
import com.kongpf.commonhelper.ApkHelper;
import com.kongpf.commonhelper.ImageHelper;
import com.kongpf.commonhelper.ToastHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private PermissionHelper permissionHelper;

    private final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    private final int REQUEST_CODE_STORAGE = 88;
    private final int REQUEST_CODE_INSTALL = 99;
    private final int REQUEST_CODE_PICK = 100;
    private DownloadInfo downloadInfo;

    @BindView(R.id.button1)
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.button1)
    public void onButton1() {

        RxHttp.getInstance()
                .get(this)
                .url(Constants.URL_GET)
                .enqueue(new HttpCallback<Feed>() {
                    @Override
                    public void onStart() {
                        Log.d(Constants.LOG_TAG, "onStart");
                    }

                    @Override
                    public void onNext(Feed response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.LOG_TAG, "onError:" + e.getMessage());
                        Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.LOG_TAG, "onComplete");
                    }
                });
    }

    @OnClick(R.id.button2)
    public void onButton2() {
        String content = "this is post content";
        RxHttp.getInstance()
                .post(this)
                .content(content)
                .url(Constants.URL_POST)
                .enqueue(new HttpCallback<String>() {

                    @Override
                    public void onStart() {
                        Log.d(Constants.LOG_TAG, "onStart");
                    }

                    @Override
                    public void onNext(String response) {
                        Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.LOG_TAG, "onError:" + e.getMessage());
                        Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.LOG_TAG, "onComplete");
                    }
                });
    }

    @OnClick(R.id.button3)
    public void onButton3() {
        Map<String, Object> map = new HashMap<>();
        map.put("model", Build.MODEL);
        map.put("manufacturer", Build.MANUFACTURER);
        map.put("os", Build.VERSION.SDK_INT);
        RxHttp.getInstance().postForm(this).url(Constants.URL_POST_FORM).params(map).enqueue(new HttpCallback<String>() {

            @Override
            public void onStart() {
                Log.d(Constants.LOG_TAG, "onStart");
            }

            @Override
            public void onNext(String response) {
                Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(Constants.LOG_TAG, "onError:" + e.getMessage());
                Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Log.d(Constants.LOG_TAG, "onComplete");
            }
        });
    }

    @OnClick(R.id.button4)
    public void onButton4() {
        permissionHelper = new PermissionHelper(this, new PermissionHelper.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                download();
            }

            @Override
            public void onPermissionMissing(List<String> permissions) {

            }

            @Override
            public void onPermissionFailed(List<PermissionInfomation> failList) {

            }

        });
        permissionHelper.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
    }

    @OnClick(R.id.button5)
    public void onButton5() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        intent.setType("image/*");
        //startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_PICK);
        upload("");
    }

    @OnClick(R.id.btn_test)
    public void onTest() {

    }

    private void upload(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("model", Build.MODEL);
        map.put("manufacturer", Build.MANUFACTURER);
        map.put("os", Build.VERSION.SDK_INT);
        path = Constants.EXTERNAL_PATH + "Download/gradle-4.4-all.zip";
        map.put("image", new File(path));

        RxHttp.getInstance().upload(this).url(Constants.URL_UPLOAD).params(map).enqueue(new UploadCallback<String>() {
            @Override
            public void onStart() {
                showProgressDialog("正在上传,请稍等...");
            }

            @Override
            public void onProgress(final long totalBytes, final long readBytes) {
                updateProgress(totalBytes, readBytes);
            }

            @Override
            public void onNext(String response) {
                Log.d(Constants.LOG_TAG, "response:" + response);
                Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(Constants.LOG_TAG, "onError:" + e.getMessage());
                Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                closeProgressDialog();

            }

            @Override
            public void onComplete() {
                Log.d(Constants.LOG_TAG, "onComplete");
                closeProgressDialog();
            }
        });
    }


    private void download() {

        String fileName = Constants.URL_DOWNLOAD.substring(Constants.URL_DOWNLOAD.lastIndexOf("/") + 1);
        RxHttp.getInstance().download(this)
                .dir(PATH)
                .filename(fileName)
                .md5("0C7B39AC67E163F0B6119D5FB31AF2D5")
                .breakpoint(true)
                .url(Constants.URL_DOWNLOAD)
                .enqueue(new DownloadCallback() {

                    @Override
                    public void onStart() {
                        showProgressDialog("正在下载新版本,请稍等...");
                    }

                    @Override
                    public void onProgress(DownloadInfo downloadInfo) {
                        if (downloadInfo != null) {
                            updateProgress(downloadInfo.getTotal(), downloadInfo.getProgress());
                        }
                    }

                    @Override
                    public void onNext(DownloadInfo downloadInfo) {
                        closeProgressDialog();
                        MainActivity.this.downloadInfo = downloadInfo;
                        install();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.LOG_TAG, "onError:" + e.getMessage());
                        ToastHelper.toast(e.getMessage());
                        closeProgressDialog();

                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.LOG_TAG, "onComplete:");
                    }

                });


    }

    private void showProgressDialog(String title) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void updateProgress(final long totalBytes, final long readBytes) {
        Log.d(Constants.LOG_TAG, "updateProgress,readBytes:" + readBytes + ",totalBytes:" + totalBytes);
        if (progressDialog != null && progressDialog.isShowing()) {
            Log.d(Constants.LOG_TAG, "progressDialog");
            progressDialog.setProgress((int) readBytes);
            progressDialog.setMax((int) totalBytes);
            float all = totalBytes * 1.0f / 1024 / 1024;
            float percent = readBytes * 1.0f / 1024 / 1024;
            progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", percent, all));
        }
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void install() {
        String authority = getPackageName() + ".fileprovider";
        File file = new File(downloadInfo.getDestDir(), downloadInfo.getFileName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PackageManager pm = getPackageManager();
            if (pm.canRequestPackageInstalls()) {
                ApkHelper.installApk(getApplicationContext(), file, authority);
            } else {
                Uri uri = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
                startActivityForResult(intent, REQUEST_CODE_INSTALL);
            }
        } else {
            ApkHelper.installApk(getApplicationContext(), file, authority);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (permissionHelper != null) {
            permissionHelper.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == REQUEST_CODE_INSTALL) {
            if (resultCode == RESULT_OK) {
                String authority = getPackageName() + ".fileprovider";
                File file = new File(downloadInfo.getDestDir(), downloadInfo.getFileName());
                ApkHelper.installApk(getApplicationContext(), file, authority);
            }
        } else if (requestCode == REQUEST_CODE_PICK) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String urlString = uri.toString();
                urlString = Uri.decode(urlString);
                String pre = "file://" + "/storage/emulated/0" + File.separator;
                String filePath = null;
                if (urlString.startsWith(pre)) {
                    filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + urlString.substring(pre.length());
                }
                if (TextUtils.isEmpty(filePath)) {
                    filePath = ImageHelper.getAbsoluteImagePath(this, uri);
                }
                Log.d(Constants.LOG_TAG, "filePath:" + filePath);
                if (!TextUtils.isEmpty(filePath)) {
                    upload(filePath);
                }

            }
        }
    }

}
