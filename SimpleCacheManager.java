package com.betsson.android.lib.SimpleCacheManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

public class SimpleCacheManager {

	private static final String TAG = "SimpleCacheManager";

	private SimpleCacheManager() {
	}

	static public <T> void saveCache(T objectToSave, Context context) {
		if (context != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(objectToSave);

			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor spe = sp.edit();

			spe.putString(objectToSave.getClass().getName(), jsonString);
			spe.commit();
			Log.v("SimpleCacheManager", objectToSave.getClass().getName() + " saved");
		}
	}

	static public <T> T loadCache(Class<T> clazz, Context context) {
		
		if (context != null) {
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(context);
			String jsonString = sp.getString(clazz.getName(), null);
			if (jsonString == null) {
				Log.v(TAG, clazz.getName() + " not loaded");
				return null;
			}
			Log.v(TAG, clazz.getName() + " loaded");
			return new Gson().fromJson(jsonString, clazz);
		}
		return null;
	}
}
