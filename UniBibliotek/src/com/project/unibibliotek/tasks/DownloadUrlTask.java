package com.project.unibibliotek.tasks;

import java.io.InputStream;

import android.os.AsyncTask;
import android.util.Log;

import com.project.unibibliotek.utils.NetworkUtils;

public class DownloadUrlTask extends AsyncTask<String, Integer, InputStream> {

	private NetworkUtils networkUtils;
	
	@Override
	protected InputStream doInBackground(String... params) {
		try {
			InputStream stream = networkUtils.downloadUrl(params[0]);
			return stream;
		}
		catch (Exception e) {
			Log.e("DownloadUrlTask", "Error on thread.");
			return null;
		}
	}

	
	
}
