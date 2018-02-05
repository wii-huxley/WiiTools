package com.huxley.wiitoolssample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.companyUtils.yscm.YscmSubscriber;
import com.huxley.wiitools.companyUtils.yscm.model.YscmHttpModel;
import com.huxley.wiitools.companyUtils.yscm.model.YscmUserModel;
import com.huxley.wiitools.companyUtils.yscm.model.bean.YscmUserBean;
import com.huxley.wiitools.utils.DataUtils;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.utils.camera.TakePhotoManager;
import com.huxley.wiitools.utils.camera.TakePhotoOptions;
import com.huxley.wiitools.utils.camera.TakePhotoResult;
import com.huxley.wiitools.utils.camera.TakePhotoUtil;
import com.huxley.wiitools.utils.logger.Logger;
import com.huxley.wiitools.view.WiiToast;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

//////////////////////////////////////////////////////////
//
//      我们的征途是星辰大海
//
//		...．．∵ ∴★．∴∵∴ ╭ ╯╭ ╯╭ ╯╭ ╯∴∵∴∵∴ 
//		．☆．∵∴∵．∴∵∴▍▍ ▍▍ ▍▍ ▍▍☆ ★∵∴ 
//		▍．∴∵∴∵．∴▅███████████☆ ★∵ 
//		◥█▅▅▅▅███▅█▅█▅█▅█▅█▅███◤ 
//		． ◥███████████████████◤
//		.．.．◥████████████████■◤
//      
//      Created by huxley on 2017/10/16.
//
//////////////////////////////////////////////////////////
public class TestCameraActivity extends AppCompatActivity {

    List<File> pics = new ArrayList<>();
    CommonAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_camera_activity);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(adapter = new CommonAdapter<File>(this, R.layout.test_camera_item_pic, pics) {
            @Override protected void convert(ViewHolder holder, File file, final int position) {
                Glide.with(TestCameraActivity.this)
                    .load(file)
                    .centerCrop()
                    .override(ResUtils.dpToPx(60), ResUtils.dpToPx(60))
                    .into((ImageView) holder.getView(R.id.iv_pic));
                holder.setOnClickListener(R.id.iv_delete_pic, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        pics.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        YscmHttpModel.getInstance()
            .setDefaultUrl(App.Url.YSCM_MOBILE_ERP_APP_SERVICE)
            .setAction(App.Action.LOGIN)
            .addParam("phoneNumber", "13995561262")
            .addParam("password", DataUtils.getMD5Str("123456"))
            .post(new YscmSubscriber<YscmUserBean>() {
                @Override public void onSuccess(YscmUserBean userBean) {
                    Logger.json(userBean);
                    YscmUserModel.getInstance().setUser(userBean);
                }


                @Override public void onError(String msg) {
                    Logger.i("2222");
                    Logger.i(msg);
                }
            });

        TakePhotoManager.getInstance().createForResult(savedInstanceState, new TakePhotoResult() {
            @Override
            public void onFailure(String message) {
                WiiToast.show("拍照失败");
            }


            @Override
            public void onResultFile(final File compressedFile) {
                pics.add(compressedFile);
                adapter.notifyDataSetChanged();
                Logger.i(compressedFile.getName() + " : " + compressedFile.getAbsolutePath());
                WiiToast.show("拍照成功");
            }
        });

        findViewById(R.id.btn_upload_pic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Logger.i(MessageFormat.format("上传 {0} 张图片", pics.size()));
                for (int i = 0; i < pics.size(); i++) {
                    final File file = pics.get(i);
                    YscmHttpModel.getInstance().uploadPic(file).subscribe(
                        new Subscriber<String>() {
                            @Override public void onCompleted() {

                            }


                            @Override public void onError(Throwable throwable) {
                                Logger.i("上传失败：" + file.getAbsolutePath());
                            }


                            @Override public void onNext(String responseBody) {
                                Logger.i("上传成功：" + file.getAbsolutePath());
                                Logger.i(responseBody.toString());
                            }
                        });
                }
            }
        });
    }


    @AfterPermissionGranted(11)
    public void openCamera(View view) {
        String[] perms = { Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (EasyPermissions.hasPermissions(this, perms)) {
            File takePhotoDir = TakePhotoUtil.getCacheDir("cn.cosmosmedia");
            File takePhotoFile = createImgFile(takePhotoDir);
            TakePhotoManager.getInstance().request(
                this,
                new TakePhotoOptions.Builder()
                    .setTakePhotoDir(takePhotoDir)
                    .setTakePhotoFile(takePhotoFile)
                    .build()
            );
        } else {
            EasyPermissions.requestPermissions(this, "111111", 11, perms);
        }
    }


    public static File createImgFile(File pathDir) {
        //确定文件名
        String loginId = YscmUserModel.getInstance().getLoginId();
        String fileName;
        if (!TextUtils.isEmpty(loginId)) {
            fileName = loginId + "_" + System.currentTimeMillis() + ".jpg";
        } else {
            fileName = System.currentTimeMillis() + ".jpg";
        }
        return new File(pathDir, fileName);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TakePhotoManager.getInstance().activityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TakePhotoManager.getInstance().saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
