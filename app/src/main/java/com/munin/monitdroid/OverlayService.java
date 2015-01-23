package com.munin.monitdroid;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class OverlayService extends Service {
	private WindowManager windowManager;
	private View barContainer;
	private View cpuBar;

	@Override public IBinder onBind(Intent intent) {
		// Not used
		return null;
	}

	@Override public void onCreate() {
		super.onCreate();

		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		// Inflate bar XML
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		barContainer = inflater.inflate(R.layout.overlaybar, null);
		cpuBar = barContainer.findViewById(R.id.cpuBar);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 10;
		params.y = Util.getStatusBarHeight(this);

		windowManager.addView(barContainer, params);

		// Refresh bar every 2 seconds
		final Handler h = new Handler();

		h.postDelayed(new Runnable() {
			public void run() {
				refreshBar();

				h.postDelayed(this, 1000);
			}
		}, 1000);
	}

	private void refreshBar() {
		cpuBar.setLayoutParams(new LinearLayout.LayoutParams(Util.randomInt(0, 100), LinearLayout.LayoutParams.MATCH_PARENT));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (barContainer != null) windowManager.removeView(barContainer);
	}
}
