package org.devio.simple;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 选择设置头像对话框
 * @author dwl
 */
public class SetPhotoImgDialog extends Dialog implements View.OnClickListener {

	private TextView tv_takePhoto,tv_selectPic,tv_cancel;
	private OnNameCListener OnNameCListener;
	public static final String CAMERA = "拍照";
	public static final String PHOTO = "相册";
	private Context context;

	public SetPhotoImgDialog(Context context) {
		super(context, R.style.ShareDialog);
		Window window   = this.getWindow();
		int screenWidth = PhoneUtils.getScreenWidth();
		this.context = context;
		WindowManager.LayoutParams lParams = window.getAttributes();
		lParams.width  = screenWidth;
		lParams.height = lParams.WRAP_CONTENT;
		lParams.gravity = Gravity.BOTTOM;
		lParams.alpha = 0.95f;
		window.setAttributes(lParams);
		this.setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_set_photo);
		tv_cancel  = findViewById(R.id.tv_cancel);
		tv_takePhoto  = findViewById(R.id.tv_takePhoto);
		tv_selectPic  = findViewById(R.id.tv_selectPic);
		tv_selectPic.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		tv_takePhoto.setOnClickListener(this);
	}

	/**
	 * 回调接口
	 * @author dwl
	 */
	public interface OnNameCListener {
		 void onClick(String name);
	}

	public void setNamekListener(OnNameCListener OnNameCListener) {
		this.OnNameCListener = OnNameCListener;
	}

	@Override
	public void onClick(View v) {
		if (v == tv_takePhoto) {
			if (OnNameCListener != null) {
				OnNameCListener.onClick(CAMERA);
			}
		}else if(v == tv_selectPic){
			OnNameCListener.onClick(PHOTO);
		}
		dismiss();
	}

}