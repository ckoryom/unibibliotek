package com.project.unibibliotek;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class Unibibliotek extends Application {
	
	private static ImageLoaderConfiguration config;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
	}
	
	public static void initImageLoader (Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
		
		ImageLoader.getInstance().init(config);
	}
	 
}
