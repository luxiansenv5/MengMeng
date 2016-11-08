package com.example.mengmeng.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddPetActivity extends AppCompatActivity {

    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.iv_pet_back)
    ImageView ivPetBack;
    @InjectView(R.id.bt_finish)
    Button btFinish;
    @InjectView(R.id.tv_headimage)
    TextView tvHeadimage;
    @InjectView(R.id.iv_head_image)
    ImageView ivHeadImage;
    @InjectView(R.id.iv_edit_head_image)
    ImageView ivEditHeadImage;
    @InjectView(R.id.rl_edit_head_image)
    RelativeLayout rlEditHeadImage;
    @InjectView(R.id.view2)
    View view2;
    @InjectView(R.id.tv_addpetname)
    TextView tvAddpetname;
    @InjectView(R.id.et_addpetname)
    EditText etAddpetname;
    @InjectView(R.id.tv_addpetkid)
    TextView tvAddpetkid;
    @InjectView(R.id.rb_dog)
    RadioButton rbDog;
    @InjectView(R.id.rb_cat)
    RadioButton rbCat;
    @InjectView(R.id.rb_other)
    RadioButton rbOther;
    @InjectView(R.id.rg_zhonglei)
    RadioGroup rgZhonglei;
    @InjectView(R.id.tv_addpettype)
    TextView tvAddpettype;
    @InjectView(R.id.et_addpettype)
    EditText etAddpettype;
    @InjectView(R.id.tv_addpetage)
    TextView tvAddpetage;
    @InjectView(R.id.et_addpetage)
    EditText etAddpetage;
    @InjectView(R.id.tv_addpetsex)
    TextView tvAddpetsex;
    @InjectView(R.id.rb_gg)
    RadioButton rbGg;
    @InjectView(R.id.rb_mm)
    RadioButton rbMm;
    @InjectView(R.id.rg_petsex)
    RadioGroup rgPetsex;

    private RadioButton checkRadioButton;
    private RadioButton checkRadioButton1;

    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+
            getPhotoFileName());

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        ButterKnife.inject(this);
        rgZhonglei.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkRadioButton = (RadioButton) rgZhonglei.findViewById(checkedId);
                Toast.makeText(getApplicationContext(), "种类为：" + checkRadioButton.getText(),
                       Toast.LENGTH_SHORT).show();
                String petKind= (String) checkRadioButton.getText();
                System.out.println(petKind+"++++++++++++++++");
            }
        });

        rgPetsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkRadioButton1 = (RadioButton) rgPetsex.findViewById(checkedId);
                Toast.makeText(getApplicationContext(), "性别为：" + checkRadioButton1.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.iv_pet_back, R.id.bt_finish, R.id.rl_edit_head_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pet_back:
                finish();
                break;
            case R.id.bt_finish:
                uploadPet();
                finish();
                break;
            case R.id.rl_edit_head_image:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择");
                builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            getPicFromCamera();
                        } else {
                            getPicFromPhoto();
                        }
                    }
                });
                builder.show();
                break;
        }
    }



    private void uploadPet() {
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String petName=etAddpetname.getText().toString().trim();
        String petType=etAddpettype.getText().toString().trim();
        String petAge=etAddpetage.getText().toString().trim();
        String petSex=(String) checkRadioButton1.getText();
        String petKind= (String) checkRadioButton.getText();

        RequestParams params = new RequestParams(NetUtil.url + "AddPetServlet");//upload 是你要访问的servlet
        params.addBodyParameter("file",file);
        params.addBodyParameter("userId",userId);

        try {
            params.addBodyParameter("petName", URLEncoder.encode(petName, "utf-8"));
            params.addBodyParameter("petType", URLEncoder.encode(petType, "utf-8"));
            params.addBodyParameter("petAge", URLEncoder.encode(petAge, "utf-8"));
            params.addBodyParameter("petSex", URLEncoder.encode(petSex, "utf-8"));
            params.addBodyParameter("petKind", URLEncoder.encode(petKind, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(AddPetActivity.this, "添加失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date)+"_"+UUID.randomUUID() + ".png";
    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
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
                        ivHeadImage.setImageBitmap(photo);
                    }
                }
                break;
            default:
                break;
        }

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
        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
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
