package com.vdolrm.lrmlibrary.test;

import java.io.File;
import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vdolrm.lrmlibrary.BaseGeneralActivity;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.file.FileUtils;
import com.vdolrm.lrmlibrary.file.MemoryUtil;
import com.vdolrm.lrmlibrary.img.CropAddCamera;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.Contant;
import com.vdolrm.lrmlibrary.utils.ImageUtil;

/** 不再区分拍照还是相册，统一从相册选，大部分机型在相册页有独立的拍照按钮 */
public class TestBigPicShowActivity extends BaseGeneralActivity {

	private Button btn_camera, btn_photo;
	private ImageView imv;
	private static String str_bm_temp_Camera_big = Contant.mulu_file_cache
			+ "temp.jpg";
	private static final String temp_filename = str_bm_temp_Camera_big;//必须和str_bm_temp_Camera_big一样，所以赋值为str_bm_temp_Camera_big
	private String IMAGE_FILE_LOCATION = "file://" + str_bm_temp_Camera_big;
	private Uri imageUri_Camera_big = Uri.parse(IMAGE_FILE_LOCATION);

	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.vdo_test_bigpicshow);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		btn_camera = (Button) findViewById(R.id.btn_camera);
		btn_photo = (Button) findViewById(R.id.btn_photo);
		imv = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	public void initActionBar() {

	}

	@Override
	public void initEvent() {
		if (new File(MemoryUtil.getExternalRootPath() + "/zTest/").exists() == false) {
			new File(MemoryUtil.getExternalRootPath() + "/zTest/").mkdir();
		}

		if (new File(Contant.mulu_chuti_anwser_pic).exists() == false) {
			new File(Contant.mulu_chuti_anwser_pic).mkdir();
		}

		if (new File(Contant.mulu_file_cache).exists() == false) {
			new File(Contant.mulu_file_cache).mkdir();
		}

		try {
			boolean bb = new File(Contant.mulu_root, ".nomedia")
					.createNewFile();// false为已经创建过了
			MyLog.d("lrm", "bb=" + bb);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 测试progressdialog
		ProgressDialog dialog = ProgressDialog.show(this, "title",
				"测试新型progressbar", true, true);// indeterminate不确定的
		// dialog.setProgress(30);//好像无效
		dialog.show();
		// dialog.hide();
		// dialog.dismiss();

		
		btn_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imv.setImageResource(R.drawable.ic_launcher);
				CropAddCamera.pickCameraImage(TestBigPicShowActivity.this,
						CropAddCamera.RESULT_CAMERA, imageUri_Camera_big);// camera(note2会进入拍照页面时和进入裁剪页面时都会重新创建TestBigPicShowActivity 从onCreate开始执行)

			}
		});
		btn_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imv.setImageResource(R.drawable.ic_launcher);
				CropAddCamera.pickImage(TestBigPicShowActivity.this);// 从相册选取
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent result) {
		/*
		 * MyLog.d("lrm","CCCresultCode="+resultCode);
		 * MyLog.d("lrm","CCCrequestcode="+requestCode);
		 * MyLog.d("lrm","CCCdata="+result); try{ Bundle extras =
		 * result.getExtras(); Parcelable mPhoto = extras.getParcelable("data");
		 * if (mPhoto == null) { String filePath = extras.getString("filePath");
		 * if (filePath!=null && filePath.equals("")==false){ //mPhoto =
		 * ImageUtils.decodeSampledBitmapFromFile(filePath, 400, 600);
		 * MyLog.d("lrm","有filePath:"+filePath); } }else{ Bitmap bm =
		 * (Bitmap)mPhoto; imv.setImageBitmap(bm); } }catch(RuntimeException e){
		 * e.printStackTrace(); }
		 */

		if (requestCode == CropAddCamera.REQUEST_PICK && resultCode == RESULT_OK) {
			beginCrop(result.getData());
		} else if (requestCode == CropAddCamera.REQUEST_CROP) {
			handleCrop(resultCode, result);
		} else if (requestCode == CropAddCamera.RESULT_CAMERA && resultCode == RESULT_OK) {
			// beginCrop(result.getData());//部分手机为空

			// beginCrop(imageUri_Camera_big);//可以使用不会OOM,但图片可能不是正着的

			// 增加旋转功能
			int degree = ImageUtil.readPictureDegree(temp_filename);// 读取旋转属性
			MyLog.d("lrm", "拍完照 i=" + degree);
			if (degree == 0) {//正着的就直接用，假如不是再旋转、压缩、载入
				beginCrop(imageUri_Camera_big);
			} else {
				Bitmap bm_yasuo = ImageUtil.decodeBitmap(temp_filename,
						Contant.maxPicSize);// 压缩一下，防止内存泄露
				// Bitmap bm_yasuo =
				// ImageUtil.decodeUriAsBitmap(TestBigPicShowActivity.this,
				// imageUri_Camera_big);//不压缩了 直接根据URI读取 如果不压缩的话会导致out of
				// memory（在rotaingImageView Bitmap.createBitmap方法时）
				Bitmap bm_overDegree = ImageUtil.rotaingImageView(degree,
						bm_yasuo);// 转正
				try {
					FileUtils.saveBitmapToFile(Contant.mulu_chuti_anwser_pic,
							bm_overDegree, temp_filename, true);// 存储一下
																// 更新图片为压缩后的转正过来的图片
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bm_yasuo != null && !bm_yasuo.isRecycled()) {
					bm_yasuo.recycle();
				}

				if (bm_overDegree != null && !bm_overDegree.isRecycled()) {
					bm_overDegree.recycle();
				}
				System.gc();
				beginCrop(imageUri_Camera_big);
			}
		}
	}

	private void beginCrop(Uri source) {
		MyLog.d("lrm", "beginCrop.source=" + source);
		MyLog.d("lrm", "CacheDir=" + getCacheDir());// CacheDir=/data/data/com.soundcloud.android.crop.example/cache
		// Uri destination = Uri.fromFile(new File(getCacheDir(),
		// System.currentTimeMillis()+".jpg"));//要缓存的路径和名称
		Uri destination = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory().getAbsolutePath() + "/zTest/",
				System.currentTimeMillis() + ".jpg"));
		CropAddCamera.of(source, destination).asSquare().start(this);
	}

	private void handleCrop(int resultCode, Intent result) {
		if (resultCode == RESULT_OK) {
			Uri uri = CropAddCamera.getOutput(result);//可以用这个uri做最后的统一缩放处理
			imv.setImageURI(uri);
		} else if (resultCode == CropAddCamera.RESULT_ERROR) {
			Toast.makeText(this, CropAddCamera.getError(result).getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

}
