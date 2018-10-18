package org.devio.simple;

import android.net.Uri;
import android.os.Environment;
import android.view.View;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class CustomHelperUtils {

    //是否裁剪
    private boolean isCorp = true;
    //是否自带裁剪工具
    private boolean isCorpOwn = false;
    //最多选择几张
    private int limit = 6;
    //裁切宽度
    private int corpWidth = 3800;
    //裁切高度
    private int corpHeight= 3000;
    //宽/高
    private boolean isAspect = false;

    //是否压缩
    private boolean isCompress = true;
    //是否自带压缩
    private boolean isCompressWithOwn = true;
    //是否自带压缩进度条
    private boolean isShowProgressBar = true;
    // 压缩到的最大大小，单位B  1024 * 1024 = 1M
    public final int COMPRESS_SIZE = 1024 * 1024;
    private int compressSize;
    private int compresWidth = 800;
    private int compressHeight = 800;
    // 拍照压缩后是否保存原图
    private boolean isSavePhoto;

    //从哪选择
    private boolean isFromFile = false;
    //takePhoto自带相册
    private boolean isFromMyGallery = true;
    //纠正拍照的旋转角度
    private boolean corrected = true;

    /**
     * 裁切配置 是否裁切,是否是自带裁切
     */
    public CustomHelperUtils setCrop(boolean isCorp,boolean isCorpOwn){
        this.isCorp = isCorp;
        if(isCorp){
            this.isCorpOwn = isCorpOwn;
        }else {
            this.isCorpOwn = false;
        }
        return this;
    }

    /**
     * 是否设置宽高比
     */
    public CustomHelperUtils setAspect(boolean isAspect){
        this.isAspect = isAspect;
        return this;
    }

    /**
     * 设置裁切宽高
     */
    public CustomHelperUtils setCorpWidthHeight(int width, int height){
        this.corpWidth = width;
        this.corpHeight= height;
        return this;
    }

    /**
     * 设置 是否压缩,是否自带压缩,是否自带压缩进度条
     */
    public CustomHelperUtils setCompress(boolean isCompress,boolean isCompressWithOwn,boolean showProgressBar){
        this.isCompress = isCompress;
        if(isCompress){
            this.isCompressWithOwn = isCompressWithOwn;
            this.isShowProgressBar = showProgressBar;
        }else {
            this.isSavePhoto = false;
            this.isCompressWithOwn = false;
            this.isShowProgressBar = false;
        }
        return this;
    }

    /**
     * 设置压缩比例
     */
    public CustomHelperUtils setCompressRange(int compressSize,int compresWidth,int compressHeight){
        this.compressSize = compressSize;
        this.compresWidth = compresWidth;
        this.compressHeight = compressHeight;
        return this;
    }
    /**
     * 是否保存原图
     */
    public CustomHelperUtils setSavePhotos(boolean isSavePhoto){
        this.isSavePhoto = isSavePhoto;
        return this;
    }
    /**
     * 设置最大张数
     */
    public CustomHelperUtils setLimit(int limit){
        this.limit = limit;
        return this;
    }

    /**
     * 拍照或者从相册取照片
     * @param isCamera true 是拍照，false 从相册取
     * @param takePhoto
     */
    public void onClick(boolean isCamera, TakePhoto takePhoto) {

        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        if(isCamera){
            if (isCorp) {
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(imageUri);
            }
        }else {
            if (limit > 1) {
                if (isCorp) {
                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                } else {
                    takePhoto.onPickMultiple(limit);
                }
                return;
            }
            //从相册取
            if (isFromFile) {
                if (isCorp) {
                    takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromDocuments();
                }
                return;
            } else {
                if (isCorp) {
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }
            }
        }
    }

    /**
     * takePhoto自带相册 旋转角度
     * @param takePhoto
     */
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        if (isFromMyGallery) {
            builder.setWithOwnGallery(true);
        }
        if (corrected) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());

    }

    /**
     *  压缩设置
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        if (!isCompress) {
            takePhoto.onEnableCompress(null, false);
            return;
        }
        CompressConfig config;
        if (isCompressWithOwn) { //自带压缩工具
            config = new CompressConfig.Builder().setMaxSize(compressSize)
                .setMaxPixel(compresWidth >= compressHeight ? compresWidth : compressHeight)
                .enableReserveRaw(isSavePhoto)
                .create();
        } else {
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(compressHeight).setMaxWidth(compresWidth).setMaxSize(compressSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(isSavePhoto);
        }
        takePhoto.onEnableCompress(config, isShowProgressBar);

    }

    private CropOptions getCropOptions() {
        if (!isCorp) {
            return null;
        }
        CropOptions.Builder builder = new CropOptions.Builder();
        if (isAspect) {
            builder.setAspectX(corpWidth).setAspectY(corpHeight);
        } else {
            builder.setOutputX(corpWidth).setOutputY(corpHeight);
        }
        builder.setWithOwnCrop(isCorpOwn);
        return builder.create();
    }

}
