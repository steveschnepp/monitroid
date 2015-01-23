package com.munin.monitdroid;

import android.content.Context;

import java.util.Random;

public class Util {
	public static int getStatusBarHeight(Context c) {
		int result = 0;
		int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0)
			result = c.getResources().getDimensionPixelSize(resourceId);
		return result;
	}

	public static int randomInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
}
