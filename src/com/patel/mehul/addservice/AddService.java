package com.patel.mehul.addservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class AddService extends Service{

	private Binder mBinder = new LocalBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
	
		return mBinder;
	}
	
	//add Method
	public int add(int a, int b){
		return (a + b);
	}

	public class LocalBinder extends Binder{
		//Get AddService
		public AddService getService(){
			return AddService.this;
		}
	}
}
