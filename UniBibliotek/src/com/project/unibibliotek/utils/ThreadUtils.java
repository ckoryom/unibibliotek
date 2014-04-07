package com.project.unibibliotek.utils;

import com.project.unibibliotek.logic.WebService;
import com.project.unibibliotek.model.ThreadStatus;

public class ThreadUtils {

	private ThreadStatus threadStatus;
	
	private Thread thread;
	
	private Object object;
	
	private void checkThread () {
		while (threadStatus != ThreadStatus.FINISHED) {
			if (threadStatus == ThreadStatus.FINISHED)
				break;
		}
	}
	
	public Object startThread(Object outsideObject) {
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (object.getClass() == WebService.class) {
					((WebService) object).search("");
				}
				threadStatus = ThreadStatus.FINISHED;
				Thread.currentThread().interrupt();
				
			}
		});
		threadStatus = ThreadStatus.NONE;
		object = null;
		if (object.getClass() == WebService.class) {
			object = (WebService) outsideObject;
		}
		if (object != null){
			thread.start();
			checkThread();
		}
		return object;
		
	}
	
}
