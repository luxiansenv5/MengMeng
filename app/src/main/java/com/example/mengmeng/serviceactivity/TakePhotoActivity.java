package com.example.mengmeng.serviceactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.AdoaptInfo;
import com.example.mengmeng.pojo.PetInfo;
import com.example.mengmeng.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import application.MyApplication;

public class TakePhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;

    //头像的存储完整路径
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+
            getPhotoFileName());
    private ImageView iv_camera;
    private ImageView iv_openPhoto;
    private ImageView sImage;
    private PetInfo petInfo;
    private Button btn_confirm;
    private EditText et_desc;
    private AdoaptInfo adoaptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        initView();

        initEvent();

        initData();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_camera:

                getPicFromCamera();
                break;
            case R.id.iv_openPhoto:

                getPicFromPhoto();
                break;
            case R.id.btn_confirm:

                Integer userId=((MyApplication)getApplication()).getUser().getUserId();
                Integer petId=petInfo.petId;
                String describle=et_desc.getText().toString();
                Boolean state=false;
                Date releaseTime=new Date(System.currentTimeMillis());

                adoaptInfo=new AdoaptInfo(userId,petId,describle,state,releaseTime);
                Gson gson=new Gson();
                String adoaptStr=gson.toJson(adoaptInfo);

                Log.i("TakePhotoActivity","adoaptStr================"+adoaptStr);

                RequestParams requestParams=new RequestParams(HttpUtils.HOST+"addadoapt");
                requestParams.addBodyParameter("adoaptInfo",adoaptStr);
                requestParams.addBodyParameter("file",file);

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("111111111111111111");
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                        System.out.println("TakePhotoError================"+ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST"+file.getAbsolutePath());
                        if (file.exists()) {
                            photoClip(Uri.fromFile(file));
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());
                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        saveImageToGallery(getApplication(),photo);//保存bitmap到本地
                        sImage.setImageBitmap(photo);

                    }
                }
                break;
            default:
                break;
        }
    }

    public void initView(){

        iv_camera = ((ImageView) findViewById(R.id.iv_camera));
        iv_openPhoto = ((ImageView) findViewById(R.id.iv_openPhoto));
        sImage = ((ImageView) findViewById(R.id.simage));
        btn_confirm = ((Button) findViewById(R.id.btn_confirm));
        et_desc = ((EditText) findViewById(R.id.et_desr));
    }

    public void initEvent(){

        iv_camera.setOnClickListener(this);
        iv_openPhoto.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    public void initData(){

        Intent intent=getIntent();
        petInfo=intent.getExtras().getParcelable("petInfo");

    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

//        System.out.println("============"+ UUID.randomUUID());
        return sdf.format(date)+"_"+UUID.randomUUID() + ".png";
    }

    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera==========="+file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);
    }
}
