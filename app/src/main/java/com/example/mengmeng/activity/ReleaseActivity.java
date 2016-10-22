package com.example.mengmeng.activity;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import application.MyApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 程和 on 2016/10/17.
 */
public class ReleaseActivity extends AppCompatActivity {


    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+
            getPhotoFileName());

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;

    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.iv_release_back)
    ImageView ivReleaseBack;
    @InjectView(R.id.iv_release_report)
    ImageView ivReleaseReport;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.iv_release_addimage)
    ImageView ivReleaseAddimage;
    @InjectView(R.id.iv_release_imgs)
    ImageView ivReleaseImgs;
    @InjectView(R.id.iv_release_place)
    ImageView ivReleasePlace;
    @InjectView(R.id.tv_release_place)
    TextView tvReleasePlace;
    @InjectView(R.id.iv_show_pet)
    ImageView ivShowPet;
    @InjectView(R.id.rl_release_dynamic)
    RelativeLayout rlReleaseDynamic;
    @InjectView(R.id.v_zhixian3)
    View vZhixian3;

    boolean flag=true;
    String path="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_dynamic);
                ButterKnife.inject(this);
    }


    @OnClick({R.id.iv_release_back, R.id.iv_release_report, R.id.iv_release_addimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_release_back:
                finish();
                break;
            case R.id.iv_release_report:
                uploadDynamic();
                Toast.makeText(ReleaseActivity.this, "分享成功,刷新查看", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.iv_release_addimage:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择");
                EditText tv = new EditText(ReleaseActivity.this);
                builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            getPicFromCamera();
                            flag=true;
                        } else {
                            getPicFromPhoto();
                            flag=false;
                        }
                    }
                });
                builder.show();
                break;
        }
    }

    //上传动态
    private void uploadDynamic() {

        String release_text=etContent.getText().toString().trim();

        RequestParams params = new RequestParams(NetUtil.url+"UploadDynamicServlet");

        params.addBodyParameter("userId", String.valueOf(((MyApplication)this.getApplication()).getUser().getUserId()));
        try {
            params.addBodyParameter("release_text", URLEncoder.encode(release_text,"utf-8"));
            params.addBodyParameter("place",URLEncoder.encode(((MyApplication)this.getApplication()).getDynamic().getPlace(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("time", String.valueOf(System.currentTimeMillis()));

        if(flag) {
            params.addBodyParameter("file", file);
        }else {
            params.addBodyParameter("file", new File(path));
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("成功！！！！！！！！！");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date)+"_"+ UUID.randomUUID() + ".png";
    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        System.out.println("getPicFromCamera==========="+file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST"+file.getAbsolutePath());
                        ivReleaseImgs.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        break;
                    default:
                        break;
                }
                break;
            case PHOTO_REQUEST:
//
                if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
                    Log.e("TAG->onresult","ActivityResult resultCode error");
                    return;
                }
                Bitmap bm = null;
                //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();
                //此处的用于判断接收的Activity是不是你想要的那个
                if (requestCode == PHOTO_REQUEST) {
                    try {
                        Uri originalUri = data.getData();        //获得图片的uri
                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        //显得到bitmap图片
                        ivReleaseImgs.setImageBitmap(bm);
                        //这里开始的第二部分，获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        path = cursor.getString(column_index);
                        System.out.println(path);

                    }catch (IOException e) {
                        Log.e("TAG-->Error",e.toString());
                    }
                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        saveImageToGallery(getApplication(),photo);//保存bitmap到本地
                        ivReleaseImgs.setImageBitmap(photo);
                    }
                }
                break;
            default:
                break;
        }

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


}

