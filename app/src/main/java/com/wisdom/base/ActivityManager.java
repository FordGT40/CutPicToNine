package com.wisdom.base;

import android.app.Activity;

import java.util.Stack;

/**
 * activity管理器
 * 
 * @author longway
 * 
 */
public class ActivityManager {

	private static ActivityManager manager;
	private static final Object[] LOCK = new Object[0];
	private Stack<Activity> activities = new Stack<Activity>();

	private ActivityManager() {

	}

	public void destoryActivityManager() {
		if (manager != null) {
			manager = null;
		}
	}

	private boolean activityNullorActivityContainers(Activity activity) {
		if (activity == null || activities.contains(activity)) {
			return true;
		}
		return false;
	}

	public void pushActivity(Activity activity) {
		if (!activityNullorActivityContainers(activity)) {
			activities.push(activity);
		}

	}

	public Activity popActivity() {
		if (!activities.isEmpty()) {
			return activities.pop();
		}
		return null;
	}

	public static ActivityManager getActivityManagerInstance() {
		if (manager == null) {
			synchronized (LOCK) {
				if (manager == null) {
					manager = new ActivityManager();
				}
			}
		}
		return manager;
	}

	public void addActivity(Activity activity) {
		if (!activityNullorActivityContainers(activity)) {
			activities.add(activity);
		}
	}

	public void clearAllActivity() {
		if (activities == null || activities.isEmpty()) {
			return;
		}
		for (Activity activity : activities) {
			if (activity != null) {
				try {
					activity.finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		activities.clear();
	}

}
