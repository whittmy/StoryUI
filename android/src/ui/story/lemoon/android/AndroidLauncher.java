package ui.story.lemoon.android;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ui.story.lemoon.MyGame;
import ui.story.lemoon.comunicate.BsuEvent;
import ui.story.lemoon.comunicate.MyMsg;

public class AndroidLauncher extends AndroidApplication {
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			if (mEventType == EVENT_UNKOWN) {
				unregisterReceiver(myReceiver);
				mylog("", "real close!!!!");

				System.exit(0);
			}
			else{
				mylog("", "ignore close!!!!");
			}

			// handler.postDelayed(this,2000);
			// //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
		}
	};

	NetworkStateReceiver networkStateReceiver;

	final int EVENT_NEW_ACTIVITY = 1;
	final int EVENT_UNKOWN = 0;
	final int EVENT_SCREEN_OFF = 2;

	int mEventType = EVENT_UNKOWN;

	MyReceiver myReceiver;

	
	void mylog(String tag, String msg){
		Log.e(tag, msg);
	}
	
	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_SCREEN_ON.equals(action) 
					||Intent.ACTION_SCREEN_OFF.equals(action)) {
				mylog("", "------------screen off-----------");
				mEventType = EVENT_SCREEN_OFF;
			}
			else if("lemoon.action.muplayer.lauched".equals(action)
					|| "gw.action.BT_WLANSETTING".equals(action)){
				mEventType = EVENT_NEW_ACTIVITY;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mylog("", "------AndroidLauncher onCreate");
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;

		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();

		// 锁(关)屏状态
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction("lemoon.action.muplayer.lauched");//长按启动播放器
		filter.addAction("gw.action.BT_WLANSETTING");	//启动网络设置
		
		registerReceiver(myReceiver, filter);

		if (networkStateReceiver == null) {
			networkStateReceiver = new NetworkStateReceiver();
			networkStateReceiver
					.setChangedListener(new NetworkStatusChangeListener());
			registerReceiver(networkStateReceiver, new IntentFilter(
					"android.net.conn.CONNECTIVITY_CHANGE"));
		}

		initialize(new MyGame(new BsuEvent() {
			@Override
			public void notify(MyMsg msg) {
				Intent it;
				ComponentName com;

				if (!mBConnected && (msg.what != MyMsg.ITEM_PAINT)
						&& (msg.what != MyMsg.ITEM_SETTING)
						&& (msg.what != MyMsg.ITEM_EXPLORER)) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(AndroidLauncher.this,
									"当前没有网络，将为你打开本地管理", Toast.LENGTH_SHORT)
									.show();
						}
					});

					try {
						mEventType = EVENT_NEW_ACTIVITY;

						it = new Intent();
						com = new ComponentName("com.gw.launcher",
								"com.gw.launcher.Launcher");
						it.setComponent(com);
						startActivity(it);

					} catch (ActivityNotFoundException e) {
						mEventType = EVENT_UNKOWN;

					} catch (Exception e) {
						mEventType = EVENT_UNKOWN;

					}
					return;
				}

				try {
					mEventType = EVENT_NEW_ACTIVITY;

					switch (msg.what) {
					case MyMsg.ITEM_GUOXUE:
						// =============规定： 我们的资源分类 从10000开始
						// ============================
						System.out.println("GUOXUE");

						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);

						it.putExtra("curCata", "国学教育");
						it.putExtra("cataId", 2);

						startActivity(it);
						break;
					case MyMsg.ITEM_CARTON:
						System.out.println("CARTON");
						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);
						it.putExtra("curCata", "动画城");
						it.putExtra("cataId", 1);
						startActivity(it);
						break;
					case MyMsg.ITEM_LANGUAGE:
						System.out.println("LANGUAGE");
						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);
						it.putExtra("curCata", "语言发展");
						it.putExtra("cataId", 5);

						startActivity(it);
						break;
					case MyMsg.ITEM_LIFE:
						System.out.println("LIFE");
						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);
						it.putExtra("curCata", "生活常识");
						it.putExtra("cataId", 3);

						startActivity(it);
						break;
					case MyMsg.ITEM_LOCAL:
						System.out.println("LOCAL");
						it = new Intent();
						com = new ComponentName("com.gw.launcher",
								"com.gw.launcher.Launcher");
						it.setComponent(com);
						startActivity(it);

						break;
					case MyMsg.ITEM_MATHS:
						System.out.println("MATHS");
						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);
						it.putExtra("curCata", "数理思维");
						it.putExtra("cataId", 6);

						startActivity(it);
						break;
					case MyMsg.ITEM_MUSIC:
						System.out.println("MUSIC");
						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);
						it.putExtra("curCata", "音乐儿歌");
						it.putExtra("cataId", 7);
						startActivity(it);
						break;

					case MyMsg.ITEM_PAINT:
						System.out.println("PAINT");
						it = new Intent();
						com = new ComponentName("com.ruitong.tuyajjj",
								"android.app.NativeActivity");
						it.setComponent(com);
						startActivity(it);

						break;
					case MyMsg.ITEM_SCIENCE:
						System.out.println("SCIENCE");
						it = new Intent();
						com = new ComponentName("children.lemoon",
								"children.lemoon.categrid.MoviesGridActivity");
						it.setComponent(com);
						it.putExtra("curCata", "自然科学");
						it.putExtra("cataId", 4);

						startActivity(it);
						break;
					case MyMsg.ITEM_SETTING:
						System.out.println("SETTING");
						it = new Intent();
						com = new ComponentName("com.gw.setting",
								"com.gw.setting.GWSettings");
						it.setComponent(com);
						startActivity(it);
						break;
					case MyMsg.ITEM_EXPLORER:
						System.out.println("EXPLORER");
						it = new Intent();
						com = new ComponentName("fileexplorer.lemoon",
								"fileexplorer.lemoon.DevicesActivity");
						it.setComponent(com);
						startActivity(it);
						break;
					default:
						mEventType = EVENT_UNKOWN;
						break;
					}
				} catch (ActivityNotFoundException e) {
				} catch (Exception e) {
				}
			}
		}), config);

		// ntp对时
		Intent intent = new Intent("adjusttime.lemoon.SNTP");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ComponentName cn = new ComponentName("adjusttime.lemoon",
				"adjusttime.lemoon.SNTP");
		intent.setComponent(cn);
		startService(intent);

		Intent lockService = new Intent("gw.setting.lockService");
		startService(lockService);

	}

	// 我们可以清楚地知道当某个activity（假定为activity
	// A）显示在当前task的最上层时，其onSaveInstanceState方法会在什么时候被执行，有这么几种情况：
	// 1、当用户按下HOME键时。
	// 这是显而易见的，系统不知道你按下HOME后要运行多少其他的程序，自然也不知道activity
	// A是否会被销毁，故系统会调用onSaveInstanceState，让用户有机会保存某些非永久性的数据。以下几种情况的分析都遵循该原则
	// 2、长按HOME键，选择运行其他的程序时。
	// 3、按下电源按键（关闭屏幕显示）时。 !!!!!
	// 4、从activity A中启动一个新的activity时。 !!!!!
	// 5、屏幕方向切换时，例如从竖屏切换到横屏时。

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		mylog("",
				"!!!!!!!!!!!!!!!!!!!!!  onSaveInstanceState !!!!  !!!!!!!!!!!!!!!!!!!!1");

		mylog("", "wait...for close ???.....");
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, 2000);
	}

	// 在onPause状态就把重要的数据存起来，以备在onResume时恢复。
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mylog("", "------AndroidLauncher onPause");

		super.onPause();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mylog("", "------AndroidLauncher onResume");

		super.onResume();

		handler.removeCallbacks(runnable);
		mEventType = EVENT_UNKOWN;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mylog("", "------AndroidLauncher onStop");
		// System.exit(0);

	}

 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mylog("", "------AndroidLauncher onDestroy");
		if (networkStateReceiver != null) {
			unregisterReceiver(networkStateReceiver);
			networkStateReceiver = null;
		}

		System.exit(0);
	}

	// ACTION_SCREEN_OFF and ACTION_SCREEN_ON

	boolean mBConnected = false;

	class NetworkStatusChangeListener implements
			NetworkStateReceiver.OnNetworkStateChangedListener {
		NetworkStatusChangeListener() {
		}

		public void onNetworkInvalid() {
			Toast.makeText(getApplicationContext(), "网络已断开", 0).show();
			mBConnected = false;
		}

		public void onNetworkValid() {

			Toast.makeText(getApplicationContext(), "已连接到无线网络", 1).show();
			mBConnected = true;

			return;
		}

	}
}
