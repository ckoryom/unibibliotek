package com.project.unibibliotek.logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.project.unibibliotek.R;

public interface ImageCommonInterface {

	 DisplayImageOptions options = new DisplayImageOptions.Builder()
	   .showStubImage(R.drawable.empty_book_cover)
	   .showImageForEmptyUri(R.drawable.empty_book_cover)
	   .showImageOnFail(R.drawable.empty_book_cover).cacheInMemory().cacheOnDisc()
	   .build();
	 
	 
	 // Image Loader 
	 ImageLoader imageLoader = ImageLoader.getInstance();

	 AnimateFirstDisplayListener animationListener = new AnimateFirstDisplayListener();

	 static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

	  static final List<String> displayedImages = Collections
	    .synchronizedList(new LinkedList<String>());

	  @Override
	  public void onLoadingComplete(String imageUri, View view,
	    Bitmap loadedImage) {
	   if (loadedImage != null) {
	    ImageView imageView = (ImageView) view;
	    boolean firstDisplay = !displayedImages.contains(imageUri);
	    if (firstDisplay) {
	     FadeInBitmapDisplayer.animate(imageView, 500);
	     displayedImages.add(imageUri);
	    }
	   }
	  }
	 }
}
