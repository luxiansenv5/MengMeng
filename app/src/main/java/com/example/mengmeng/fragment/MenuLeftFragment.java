package com.example.mengmeng.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mengmeng.activity.Mine_SetActivity;
import com.example.mengmeng.activity.My_PetActivity;
import com.example.mengmeng.activity.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener {


    //头像的存储完整路径
    final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+ getPhotoFileName());
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private ImageView background_head;
    private ImageView mine_set;
    private TextView count_name;
    private RelativeLayout my_pet_set;


    public MenuLeftFragment() {
        // Required empty public constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.layout_menu, container, false);


            background_head = ((ImageView) view.findViewById(R.id.background_head));//头像的背景图片
            background_head.setOnClickListener(this);
            mine_set = ((ImageView) view.findViewById(R.id.mine_set));
            mine_set.setOnClickListener(this);
            //拿到显示的用户名
            count_name = ((TextView) view.findViewById(R.id.count_name));
            count_name.setText("dkgsngk");

            //拿到我的宠物部分点击控件
            my_pet_set = ((RelativeLayout) view.findViewById(R.id.my_pet_set));
            my_pet_set.setOnClickListener(this);

            return view;
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.background_head:
                popupdown2();
                break;
            case R.id.mine_set:
                Intent intent = new Intent(getActivity(), Mine_SetActivity.class);
                startActivity(intent);
                break;
            case R.id.my_pet_set:
                Intent intent1  = new Intent(getActivity(), My_PetActivity.class);
                startActivity(intent1);
                break;

        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        //System.out.println("CAMERA_REQUEST"+file.getAbsolutePath());
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
                        Bitmap photo = extras.getParcelable("data");
                        saveImageToGallery(getActivity(),photo);//保存bitmap到本地
                        System.out.println("3============");
                        //if(flag==0)
                            background_head.setImageBitmap(photo);

//                        if(flag==1){
//                            backImag.setImageBitmap(photo);
//                        }
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
            //send();  这里是指的是上传操作
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));

    }

    private void popupdown2() {
        AlertDialog.Builder dialog  = new AlertDialog.Builder(getActivity());
        dialog.setNegativeButton("拍 照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPicFromCamera();
            }
        });

        dialog.setPositiveButton("选择照片", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPicFromPhoto();
            }
        });
        dialog.show();
    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera==========="+file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);

    }

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);

    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    


}

