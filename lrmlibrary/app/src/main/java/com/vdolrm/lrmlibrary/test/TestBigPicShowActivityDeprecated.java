package com.vdolrm.lrmlibrary.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vdolrm.lrmlibrary.BaseGeneralActivity;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.file.FileUtils;
import com.vdolrm.lrmlibrary.file.MemoryUtil;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.Contant;
import com.vdolrm.lrmlibrary.utils.ImageUtil;
import com.vdolrm.lrmlibrary.widght.GeneralDialog;

/**废弃，在魅族上 选取相册里的照片后不能传到裁剪页面,但其他如拍照裁剪、图片旋转、压缩等代码可供参考*/
@Deprecated
public class TestBigPicShowActivityDeprecated extends BaseGeneralActivity {

	private Button btn;
	private ImageView imv;
	private GeneralDialog dialog;
	
	private Uri imageUri_Camera_big;//to store the big bitmap
	private Uri imageUri_Camera_cut;//to store the big bitmap
	private Uri imageUri_xiangce;
	private static String str_bm_temp_Camera_big = Contant.mulu_file_cache+"temp.jpg";
	private static String str_bm_temp_Camera_cut = Contant.mulu_file_cache+"temp2.jpg";
	private static String str_bm_temp_Photo = Contant.mulu_file_cache+"temp3.jpg";
	private static final String IMAGE_FILE_LOCATION ="file://"+ str_bm_temp_Camera_big;
	private static final String IMAGE_FILE_LOCATION2 ="file://"+ str_bm_temp_Camera_cut;
	private static final String IMAGE_FILE_LOCATION_xiangce ="file://"+ str_bm_temp_Photo;
	
	private static final String temp_filename = str_bm_temp_Camera_big;

	private static final int TAKE_BIG_PICTURE = 19;
	private static final int TAKE_BIG_PICTURE_CUT = 20;
	private static final int PHOTO_BIG_PICTURE = 21;
	
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
		btn = (Button)findViewById(R.id.button1);
		imv = (ImageView)findViewById(R.id.imageView1);
	}

	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		
		if(new File(MemoryUtil.getExternalRootPath()+"/zTest/").exists()==false){
			new File(MemoryUtil.getExternalRootPath()+"/zTest/").mkdir();
		}
		
		if(new File(Contant.mulu_chuti_anwser_pic).exists()==false){
			new File(Contant.mulu_chuti_anwser_pic).mkdir();
		}
		
		if(new File(Contant.mulu_file_cache).exists()==false){
			new File(Contant.mulu_file_cache).mkdir();
		}
		
		imageUri_Camera_big = Uri.parse(IMAGE_FILE_LOCATION);
		imageUri_Camera_cut = Uri.parse(IMAGE_FILE_LOCATION2);
		imageUri_xiangce = Uri.parse(IMAGE_FILE_LOCATION_xiangce);
		
		
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = new GeneralDialog(TestBigPicShowActivityDeprecated.this,R.layout.vdo_generaldialog);
				View view = dialog.getConvertView();
				TextView messageView=(TextView)view.findViewById(R.id.dialog_tv1);
				TextView messageView2=(TextView)view.findViewById(R.id.dialog_tv2);
				
				messageView.setText("拍照");
				messageView2.setText("图库");
				messageView.setOnClickListener(new DialogClickListener(0));
				messageView2.setOnClickListener(new DialogClickListener(1));
			}
		});
	}
	
	
	class DialogClickListener implements OnClickListener{
		private int position;
		public DialogClickListener(int p){
			this.position = p;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(position==0){
				paizhao();
			}else{
				//xiangce();
				xiangce2();
			}
		}
		
	}
	
	
	
	
	protected void paizhao() {
		//先验证手机是否有sdcard 
		
		if(MemoryUtil.isExitsSdcard()){ 
			try { 
				File dir=new File(Contant.mulu_chuti_anwser_pic); 
				if(!dir.exists()){			
					dir.mkdirs(); 
				}
			
			
				File dir2=new File(Contant.mulu_file_cache); 
				if(!dir2.exists()){			
					dir2.mkdirs(); 
				}
	
			//temp_id = i;
			
			if(imageUri_Camera_big == null)
				MyLog.e("lrm", "image uri can't be null");
			
			Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_Camera_big);
			startActivityForResult(intent_camera, TAKE_BIG_PICTURE);
			
			
			} catch (ActivityNotFoundException  e) { 
				Toast.makeText(TestBigPicShowActivityDeprecated.this,"没有找到储存目录",Toast.LENGTH_LONG).show();  
			} 
		}else{ 
			Toast.makeText(TestBigPicShowActivityDeprecated.this, "没有储存卡",Toast.LENGTH_LONG).show(); 
		} 
		
		
		if(dialog!=null)
			dialog.dismiss();
	
	}
	
	private void xiangce() {			
		Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT, null);
		//Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//让选择时只能选择图片不能选择其他文件
		intent2.setType("image/*");
		//intent2.putExtra("crop", "true");
		intent2.putExtra("aspectX", 4);
		intent2.putExtra("aspectY", 3);
		intent2.putExtra("outputX", Contant.maxPicSize);
		intent2.putExtra("outputY", Contant.heightPicSize);
		intent2.putExtra("scale", true);
		intent2.putExtra("return-data", false);
		intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_xiangce);
		intent2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent2.putExtra("noFaceDetection", false); 
		startActivityForResult(intent2, PHOTO_BIG_PICTURE);
		
		MyLog.d("PHOTO_BIG_PICTURE");
			
		if(dialog !=null)
			dialog.dismiss();
			
		}
	
	
	private void xiangce_new(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_xiangce);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, PHOTO_BIG_PICTURE);
	}
	
	
	private void xiangce2(){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
		//intent.putExtra("crop", "true");
	    startActivityForResult(intent, PHOTO_BIG_PICTURE);
	    
	    if(dialog !=null)
			dialog.dismiss();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);		
		MyLog.d("lrm","CCCresultCode="+resultCode);
		MyLog.d("lrm","CCCrequestcode="+requestCode);
		MyLog.d("lrm","CCCdata="+data);
		
		if (resultCode == Activity.RESULT_OK ) { 
	         if(MemoryUtil.isExitsSdcard()==false){
	            Toast.makeText(TestBigPicShowActivityDeprecated.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
		         return;  
	          }
	           
	            if(data ==null){//拍照 裁剪前
	            	MyLog.d("lrm","CCCdata=null");
	            	if(requestCode == TAKE_BIG_PICTURE){
		            	int degree = ImageUtil.readPictureDegree(temp_filename);//读取旋转属性
						MyLog.d("lrm","拍完照 i="+degree);
						Bitmap bm_yasuo = ImageUtil.decodeBitmap(temp_filename, Contant.maxPicSize);//压缩一下，防止内存泄露
						Bitmap bm_overDegree = ImageUtil.rotaingImageView(degree, bm_yasuo);//转正
		            	try {
								FileUtils.saveBitmapToFile(Contant.mulu_chuti_anwser_pic, bm_overDegree, temp_filename,true);//存储一下 更新图片为压缩后的转正过来的图片
							} catch (IOException e) {
								e.printStackTrace();
							}
						if(bm_yasuo!=null&&!bm_yasuo.isRecycled()){
							bm_yasuo.recycle();
						}	
						
						if(bm_overDegree!=null&&!bm_overDegree.isRecycled()){
							bm_overDegree.recycle();
						}
						System.gc();
		            	cropImageUri(imageUri_Camera_big, Contant.maxPicSize, Contant.heightPicSize);
	            	}	

	            }else {//data!=null
	            	
	            	MyLog.d("lrm","CCCdata!!!=null");
	            	String fileName="";
	            	
	            	if(requestCode == TAKE_BIG_PICTURE_CUT){//拍照裁剪回来的
	            		fileName = Contant.mulu_chuti_anwser_pic+System.currentTimeMillis()+".jpg";
	  					if(imageUri_Camera_cut != null){
	  						Bitmap bitmap = decodeUriAsBitmap(imageUri_Camera_cut);
	  						try {
	  							FileUtils.saveBitmapToFile(Contant.mulu_chuti_anwser_pic, bitmap, fileName,true);//把拍照完裁剪回来的图片存到文件缓存中
	  						} catch (IOException e) {
	  							e.printStackTrace();
	  						}
	  						
	  						imagesoso(bitmap, fileName);					
	  					}
	            	}else if(requestCode==PHOTO_BIG_PICTURE){//相册 
	            		MyLog.d("ONACTIVITYRESULT PHOTO_BIG_PICTURE,imageUri_xiangce="+imageUri_xiangce);
		            	Bitmap bitmap = decodeUriAsBitmap(imageUri_xiangce);
		            	if(bitmap !=null){
			            	try {
			            		Bitmap bm_yasuo = ImageUtil.decodeBitmap(bitmap,Contant.maxPicSize);//压缩一下，防止内存泄露
			            		fileName = Contant.mulu_chuti_anwser_pic+System.currentTimeMillis()+".jpg"; 
			    				FileUtils.saveBitmapToFile(Contant.mulu_chuti_anwser_pic,bm_yasuo, fileName,true);//把从相册选取完裁剪回来的图片存到文件缓存中
			    				imagesoso(bm_yasuo, fileName);
			    			} catch (IOException e) {
			    				e.printStackTrace();
			    			}
			            	
			   	            Bitmap bm_thumbnail = ImageUtil.decodeBitmap(fileName,50);//再压缩成缩略图
			   	            if(bm_thumbnail !=null){
			   	            	imagesoso(bm_thumbnail, fileName);
			   	            }
					   	     
		            	}else{//从相册取出来的是空的
		            		MyLog.d("从相册取出来是空的");
		            		fileName = "";
		            		imagesoso(null, fileName);
		            	}
		            
		            	// 先判断是否已经回收
		            	if(bitmap != null && !bitmap.isRecycled()){ 
		            	        bitmap.recycle(); 
		            	        bitmap = null; 
		            	} 
		            	System.gc();
	            }
	            }
	        }

		}
	
	/**拍照完的裁剪命令*/
	private void cropImageUri(Uri uri, int outputX, int outputY){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		//intent.putExtra("aspectX", 2);
		intent.putExtra("aspectX", 4);
		//intent.putExtra("aspectY", 1);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_Camera_cut);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, TAKE_BIG_PICTURE_CUT);
	}
	
	
	/**根据uri取出图片*/
	private Bitmap decodeUriAsBitmap(Uri uri){
		//File f = new File(str_bm_temp_Photo);
		 /*if(!f.exists()){  
	            f.mkdir();  
	        } */ 
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			MyLog.d("根据uri取出图片错误"+e.getMessage());
			return null;
		}
		return bitmap;
	}
	

	
	private void imagesoso(Bitmap bm,String path){
		if(bm==null){
			MyLog.d("bm=null"+",path="+path);
		}else{
			MyLog.d("宽="+bm.getWidth()+",高="+bm.getHeight()+",path="+path);
			imv.setImageBitmap(bm);
		}
	}
}
