package com.han.cleanhome;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;


public class fragment_tab0 extends Fragment {

    @BindView(R.id.iv_0)
    ImageView iv_0;
    @BindView(R.id.iv_1)
    ImageView iv_1;
    Context mContext;
    final static int TAKE_PICTURE = 1;
    final static int TAKE_PICTURE2 = 2;  //onActivityResult의 request code는 같으면 안된다. 그래서 기존 1에서 2로 변경

    public fragment_tab0(Context context) {
        mContext = context;
    }

    public fragment_tab0() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab0, container, false);
        ButterKnife.bind(this, view);
        CameraPermission();
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",false);
        loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",true);
        return view;
    }

    // 카메라 불러오는 동작
    @OnClick({R.id.tv_take_picture0})  //클릭 동작을 하기 위해서는 ({R.id.tv_take_picture0}) 를 추가로 넣어야함
    void TakePicture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PICTURE);
    }

    @OnClick(R.id.tv_take_picture1)   //클릭 동작을 하기 위해서는 ({R.id.tv_take_picture0}) 를 추가로 넣어야함
    void TakePicture2() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PICTURE2);
    }

    // 카메라 권한 설정을 위한 함수
    void CameraPermission() {
        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    // 카메라 권한에 수락, 거절에 따른 결과 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        iv_0.setImageBitmap(bitmap);
                        String path = createPictureFilePath("img1.jpg");
                        saveBitmapAsFile(bitmap,path);
                        scanMediaFile(new File(path));
                    }
                }
                break;
            case TAKE_PICTURE2:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        iv_1.setImageBitmap(bitmap);
                        String path = createPictureFilePath("img2.jpg");
                        saveBitmapAsFile(bitmap,path);
                        scanMediaFile(new File(path));
                    }
                }
                break;
        }
    }

    // 이미지 파일을 생성하는 함수
    public static final String SAVE_MEDIA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/";
    public String createPictureFilePath(String fileName) {
        createMediaFileDirectory();
        //String fileName = "img1"+ ".jpg";
        String fullPath = SAVE_MEDIA_PATH + fileName;
        return fullPath;
    }

    // 특정 미디어 파일의 디렉토리를 생성하는 함수
    private void createMediaFileDirectory() {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }

        File cameraDir = new File(SAVE_MEDIA_PATH);
        if (!cameraDir.exists()) {
            cameraDir.mkdir();
        }
    }

    // 저장된 사진 불러오는 함수
    private void loadImageFromStorage(String path, boolean flag)
    {
        try {
            File f;
            if(flag) {
                f = new File(path, "img1.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                iv_0.setImageBitmap(b);
            }else{
                f = new File(path, "img2.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                iv_1.setImageBitmap(b);
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("HAN","exception: "+e);
            e.printStackTrace();
        }
    }

    // 카메라로 찍은 함수를 특정 위치로 저장하는 함수
    private void saveBitmapAsFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);
        OutputStream os = null;

        try {
            file.createNewFile();
            os = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
            Toast.makeText(mContext,"사진 저장",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("HAN","Exception: "+e);
            e.printStackTrace();
        }
    }

    // 미디어 스캔으로 업데이트
    private void scanMediaFile(final File file) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MediaScanning(mContext, file);
            }
        }, 900);
    }

}