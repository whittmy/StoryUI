package ui.story.lemoon.android;

 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkStateReceiver extends BroadcastReceiver {
	private static final String TAG = "NetworkStateReceiver";
	private OnNetworkStateChangedListener changedListener;
	public boolean firstLoad = true;
	private long preTime;
	String tag;
	
	
	void mylog(String t, String msg){
		Log.d(t, msg);
	}
	
	public OnNetworkStateChangedListener getChangedListener() {
		return changedListener;
	}

	public String getTag() {
		return tag;
	}

	public void onReceive(Context paramContext, Intent paramIntent) {
		long l = System.currentTimeMillis();
		if (l - preTime < 2000L) {
			mylog("NetworkStateReceiver", "间隔时间太短……");
			return;
		}

		preTime = l;
		mylog("NetworkStateReceiver", "网络已连接------intent=" + paramIntent);
		if (!checkNetwork(paramContext)) {
			// cond2
			mylog("NetworkStateReceiver", "网络已断开---" + tag);
			changedListener.onNetworkInvalid();
		} else {
			mylog("NetworkStateReceiver", "网络已连接------" + tag);
			if (changedListener != null)
				changedListener.onNetworkValid();
		}

		return;

	}

	public boolean checkNetwork(Context paramContext) {
 
//		try {
//			ConnectivityManager mgr = (ConnectivityManager) paramContext
//					.getSystemService("connectivity");
//			if (mgr != null) {
//				NetworkInfo ni = mgr.getActiveNetworkInfo();
//				if (ni != null && ni.isConnected() && ni.getState() == NetworkInfo.State.CONNECTED) {
//							return true;
//				}
//			}
// 		} catch (Exception localException) {
//		}
//		return false;
		
		ConnectivityManager manager = (ConnectivityManager) paramContext.getSystemService("connectivity");
		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		if(activeNetworkInfo==null||!activeNetworkInfo.isAvailable()){
		    //Toast.makeText(getApplicationContext(), "网络连接不可用", Toast.LENGTH_SHORT).show();
			return false;
		}else{//可能联网
		    int networkType = activeNetworkInfo.getType();
		    if(networkType == ConnectivityManager.TYPE_WIFI){
		        //Toast.makeText(paramContext, "当前成功连接WIFI"             +activeNetworkInfo.isConnected(), Toast.LENGTH_SHORT).show();
		    	return true;
		    }else if(networkType==ConnectivityManager.TYPE_MOBILE){
		    	return true;
//		        if(activeNetworkInfo.isRoaming()){
//		            Toast.makeText(getApplicationContext(), "当前成功连接漫游3G网络"+activeNetworkInfo.isConnected(), Toast.LENGTH_SHORT).show();
//		        }else{
//		            Toast.makeText(getApplicationContext(), "当前成功连接非漫游3G网络"  +activeNetworkInfo.isConnected(), Toast.LENGTH_SHORT).show();
//		        }
		    }
		}		
		
		return false;
	}
	  
	public void setChangedListener(OnNetworkStateChangedListener paramOnNetworkStateChangedListener) {
		changedListener = paramOnNetworkStateChangedListener;
	}

	public void setTag(String paramString) {
		tag = paramString;
	}

	public static abstract interface OnNetworkStateChangedListener {
		public abstract void onNetworkInvalid();

		public abstract void onNetworkValid();
	}
}